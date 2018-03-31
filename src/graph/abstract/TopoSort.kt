package graph.abstract

import graph.abstract.Status.*
import util.OneArray
import util.toOneArray

// topological sort of a directed acyclic graph (dag)
fun <V> Graph<V>.topoSort(checkIdentity: Boolean = true): OneArray<Vertex<V>> {
	val sorted = ArrayList<Vertex<V>>()

	val status = HashMap<Vertex<V>, Status>()
	vertices.forEach { status[it] = NEW }

	var clock = vertices.size

	vertices.forEach {
		if (status[it] == NEW) {
			clock = topoSort(it, status, clock, sorted, checkIdentity)
		}
	}

	return sorted.toOneArray()
}

private fun <V> Graph<V>.topoSort(vertex: Vertex<V>,
                                  status: HashMap<Vertex<V>, Status>,
                                  clock: Int,
                                  list: ArrayList<Vertex<V>>,
                                  checkIdentity: Boolean): Int {
	var counter = clock
	status[vertex] = ACTIVE
	getEdgesOf(vertex, checkIdentity).forEach { (_, u) ->
		when (status[u]) {
			NEW -> counter = topoSort(u, status, counter, list, checkIdentity)
			ACTIVE -> throw CycleDetectedException()
			else -> {
			}
		}
	}
	status[vertex] = FINISHED
	list.add(0, vertex)
	return counter - 1
}

class CycleDetectedException
	: Exception("this graph contains cycles thus cannot be sorted")

enum class Status {
	NEW, ACTIVE, FINISHED;
}

fun main(args: Array<String>) {
	val vertices = (1..5).map { Vertex(it) }
	val edges = setOf(
			Edge(vertices[0], vertices[1], true),
			Edge(vertices[0], vertices[2], true),
			Edge(vertices[0], vertices[3], true),
			Edge(vertices[0], vertices[4], true),
			Edge(vertices[1], vertices[4], true),
			Edge(vertices[2], vertices[1], true),
			Edge(vertices[2], vertices[3], true),
			Edge(vertices[3], vertices[4], true))
	val graph = Graph(vertices, edges)
	println(graph.topoSort())
}
