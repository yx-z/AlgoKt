package graph.abstract

import java.util.*

// given a weighted graph, a starting vertex s, and an ending vertex t
// find the shortest path with min weight from s to t
// assume the weights are all positive ints

fun <V> WeightedGraph<V, Int>.dijkstra(s: Vertex<V>,
                                       t: Vertex<V>):  {
	val dist = HashMap<Vertex<V>, Int>()
	vertices.forEach { dist[it] = Int.MAX_VALUE }
	dist[s] = 0

	val minHeap = PriorityQueue<Vertex<V>>()
	minHeap.add(s)

	while (minHeap.isNotEmpty()) {
		val v = minHeap.remove()
		getEdgesOf(v).forEach { (_, w) ->

		}
	}

	return dist[t]!!
}