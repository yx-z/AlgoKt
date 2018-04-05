package graph.core

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.collections.Set
import kotlin.collections.forEach
import kotlin.collections.isNotEmpty
import kotlin.collections.listOf
import kotlin.collections.map
import kotlin.collections.mapOf
import kotlin.collections.set

// given a graph as an adjacency list and a starting point
// return a set of spanning tree (that is reachable vertices) of it
fun <V> Graph<V>.whateverFirstSearch(start: Vertex<V>,
                                     checkIdentity: Boolean = true,
                                     pMarked: HashMap<Vertex<V>, Boolean>? = null)
		: Set<Vertex<V>> {
	var marked = pMarked
	val random = Random()
	val reachableVertices = HashSet<Vertex<V>>()

	if (marked == null) {
		marked = HashMap()
		vertices.forEach { marked[it] = false }
	}

	val bag = ArrayList<Vertex<V>>()
	bag.add(start)
	while (bag.isNotEmpty()) {
		// we use random number here to emphasize Whatever First
		val vertex = bag.removeAt(random.nextInt(bag.size))

		if (marked[vertex] == false) {
			marked[vertex] = true
			reachableVertices.add(vertex)
			bag.addAll(getEdgesFrom(vertex, checkIdentity).map { it.vertex2 })
		}
	}

	return reachableVertices
}

fun <V> Graph<V>.whateverFirstSearchAll(checkIdentity: Boolean = true)
		: Set<Set<Vertex<V>>> {
	val set = HashSet<Set<Vertex<V>>>()
	val marked = HashMap<Vertex<V>, Boolean>()
	vertices.forEach { marked[it] = false }
	vertices.forEach {
		if (marked[it] == false) {
			set.add(whateverFirstSearch(it, checkIdentity, marked))
		}
	}
	return set
}

fun main(args: Array<String>) {
	val vs = Array(5) { Vertex(it) }
	val graph = AdjListGraph(mapOf(
			vs[0] to listOf(vs[1], vs[2]),
			vs[1] to listOf(vs[0], vs[2]),
			vs[2] to listOf(vs[0], vs[1]),
			vs[3] to listOf(vs[4]),
			vs[4] to listOf(vs[3])
	))
//	println(graph.whateverFirstSearch(vs[0]))

	println(graph.whateverFirstSearchAll())
}