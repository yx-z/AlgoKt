package graph.abstract

import util.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

// given a graph as an adjacency list and a starting point
// return a set of spanning tree (that is reachable vertices) of it
fun <V> Graph<V>.whateverFirstSearch(start: Vertex<V>,
                                     marked: HashMap<Vertex<V>, Boolean>? = null)
		: Set<Vertex<V>> {
	var marked = marked
	val random = Random()
	val reachableVertices = HashSet<Vertex<V>>()

	if (marked == null) {
		marked = HashMap()
		vertices.forEach { marked[it] = false }
	}

	val bag = ArrayList<Vertex<V>>()
	bag.add(start)
	while (bag.isNotEmpty()) {
		// we use random number here tu emphasize Whatever First
		val vertex = bag.removeAt(random.nextInt(bag.size))

		if (marked[vertex] == false) {
			marked[vertex] = true
			reachableVertices.add(vertex)
			bag.addAll(getEdgesOf(vertex).map { it.end })
		}
	}

	return reachableVertices
}

fun <V> Graph<V>.whateverFirstSearchAll() {
	val marked = HashMap<Vertex<V>, Boolean>()
	vertices.forEach { marked[it] = false }
	vertices.forEach {
		if (marked[it] == false) {
			println(whateverFirstSearch(it, marked))
		}
	}
}

fun main(args: Array<String>) {
	val vs = Array(5) { Vertex(it) }
	val graph = AdjListGraph(listOf(
			vs[0] tu listOf(vs[1], vs[2]),
			vs[1] tu listOf(vs[0], vs[2]),
			vs[2] tu listOf(vs[0], vs[1]),
			vs[3] tu listOf(vs[4]),
			vs[4] tu listOf(vs[3])
	))
	println(graph.whateverFirstSearch(vs[0]))

	println(graph.whateverFirstSearchAll())
}