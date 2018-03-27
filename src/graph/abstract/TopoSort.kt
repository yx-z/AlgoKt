package graph.abstract

import util.OneArray
import util.toOneArray

// topological sort of a directed acyclic graph (dag)
fun <V> Graph<V>.topoSort(): OneArray<Vertex<V>> {
	val V = vertices.size

	val sorted = ArrayList<Vertex<V>>(V)

	val status = HashMap<Vertex<V>, Status>()
	vertices.forEach { status[it] = Status.NEW }

	var clock = V

	vertices.forEach {
		if (status[it] == Status.NEW) {
			clock = topoSort(it, status, clock, sorted)
		}
	}

	return sorted.toOneArray()
}

fun <V> Graph<V>.topoSort(vertex: Vertex<V>,
                          status: HashMap<Vertex<V>, Status>,
                          clock: Int,
                          list: ArrayList<Vertex<V>>): Int {
	var counter = clock
	status[vertex] = Status.ACTIVE
	getEdgesOf(vertex).forEach { (s, e) ->
		val u = if (s === vertex) e else s
		if (status[u] == Status.NEW) {
			counter = topoSort(u, status, counter, list)
		} else if (status[u] == Status.ACTIVE) {
			throw CycleDetectedException()
		}
	}
	status[vertex] = Status.FINISHED
	list.add(0, vertex)
	return counter - 1
}

class CycleDetectedException
	: Exception("this graph contains cycles thus cannot be sorted")

enum class Status {
	NEW, ACTIVE, FINISHED;
}

fun main(args: Array<String>) {
	val vertices = (1..3).map { Vertex(it) }
	val edges = setOf(
			Edge(vertices[0], vertices[1], true),
			Edge(vertices[0], vertices[2], true),
			Edge(vertices[2], vertices[1], true))
	val graph = Graph(vertices, edges)
	println(graph.topoSort())
}
