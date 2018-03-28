package graph.abstract

import util.Tuple2
import util.tu
import java.util.*
import kotlin.collections.HashMap

// given a weighted graph, a starting vertex s
// find the shortest path with min weight from s to all other vertices
// assume the weights are all positive ints

fun <V> WeightedGraph<V, Int>.dijkstra(s: Vertex<V>)
		: Tuple2<Map<Vertex<V>, Int>, Map<Vertex<V>, Vertex<V>?>> {
	val dist = HashMap<Vertex<V>, Int>()
	val parent = HashMap<Vertex<V>, Vertex<V>?>()
	vertices.forEach {
		dist[it] = Int.MAX_VALUE
		parent[it] = null
	}
	dist[s] = 0

	val minHeap = PriorityQueue<Vertex<V>>()
	minHeap.add(s)

	while (minHeap.isNotEmpty()) {
		val v = minHeap.remove()
		getEdgesOf(v).forEach { (_, w, _, d) ->
			if (dist[v]!! + d!! < dist[w]!!) {
				dist[w] = dist[v]!! + d
				parent[w] = v
				minHeap.add(w)
			}
		}
	}

	return dist tu parent
}

fun main(args: Array<String>) {
	val vertices = (1..5).map { ComparableVertex(it) }
	val edges = setOf(
			WeightedEdge(vertices[0], vertices[1], true, 1),
			WeightedEdge(vertices[0], vertices[3], true, 3),
			WeightedEdge(vertices[1], vertices[2], true, 1),
			WeightedEdge(vertices[2], vertices[3], true, 2),
			WeightedEdge(vertices[2], vertices[4], true, 3),
			WeightedEdge(vertices[3], vertices[4], true, 1))
	val graph = WeightedGraph(vertices, edges)
	println(graph.dijkstra(vertices[0]))
}