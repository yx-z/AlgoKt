package graph

import util.*
import java.util.*
import kotlin.collections.HashMap

// given a graph as an adjacency list, a start vertex, and an end vertex,
// find whether there is a path from start to end
fun <V> AdjListGraph<V>.whateverFirstSearch(start: Vertex<V>, end: Vertex<V>): Boolean {
	val rand = Random()

	val marked = HashMap<Vertex<V>, Boolean>()
	vertices.forEach { marked.put(it, false) }

	val bag = ArrayList<Vertex<V>>()
	bag.add(start)
	while (bag.isNotEmpty()) {
		// we use random number here to emphasize Whatever First
		val vertex = bag.removeAt(rand.nextInt(bag.size))
		if (vertex === end) {
			return true
		}

		if (marked[vertex] == false) {
			marked[vertex] = true
			bag.addAll(getEdgesOf(vertex).map { it.end })
		}
	}

	return false
}

fun main(args: Array<String>) {
	val vs = Array(5) { Vertex(it) }
	val graph = AdjListGraph(listOf(
			vs[0] to listOf(vs[1], vs[2]),
			vs[1] to listOf(vs[0], vs[2]),
			vs[2] to listOf(vs[0], vs[1]),
			vs[3] to listOf(vs[4]),
			vs[4] to listOf(vs[3])
	))
	println(graph.whateverFirstSearch(vs[0], vs[2]))
}