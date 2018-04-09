package graph.variation

import graph.core.Vertex
import graph.core.WeightedEdge
import graph.core.WeightedGraph
import graph.core.dijkstra
import util.INF
import util.Tuple2
import java.util.*
import kotlin.collections.set

// given a weighted graph G = (V, E) with each vertex having an int
// find a shortest path from s to t (s, t in V) : values of vertices included in
// this path is always increasing and report the total weight
// you may assume that each int is unique and distinct

// modify dijkstra algorithm
fun WeightedGraph<Int, Int>.shortestPathInc(s: Vertex<Int>,
//                                            t: CVertex<Int>,
                                            checkIdentity: Boolean = true)
		: Map<Vertex<Int>, Int> {
	val dist = HashMap<Vertex<Int>, Int>()
	vertices.forEach {
		dist[it] = INF
	}
	dist[s] = 0

	val minHeap = PriorityQueue<Vertex<Int>>(Comparator { u, v -> dist[u]!! - dist[v]!! })
	minHeap.add(s)

	while (minHeap.isNotEmpty()) {
		val v = minHeap.remove()
		getEdgesOf(v, checkIdentity).forEach { (start, end, isDirected, weight) ->
			val u = (if (isDirected || start == v) end else start)
			if (dist[v]!! + weight!! < dist[u]!! && u.data > v.data) {
				dist[u] = dist[v]!! + weight
				minHeap.add(u)
			}
		}
	}
//	println(dist)

	return dist
}

// modify graph : there exists an edge u -> v iff. u -> v in E,
// v.data > u.data, for all u, v in V

fun WeightedGraph<Int, Int>.shortestPathInc2(s: Vertex<Int>,
                                             checkIdentity: Boolean = true)
		: Tuple2<Map<Vertex<Int>, Int>, Map<Vertex<Int>, Vertex<Int>?>> {
	val newEdges = weightedEdges.mapNotNull { (s, e, d, w) ->
		if (d) {
			if (e.data > s.data) {
				WeightedEdge(s, e, true, w)
			} else {
				null
			}
		} else {
			if (e.data > s.data) {
				WeightedEdge(s, e, true, w)
			} else {
				WeightedEdge(e, s, true, w)
			}
		}
	}
	val newGraph = WeightedGraph(vertices, newEdges)
	return newGraph.dijkstra(s, checkIdentity)
}

fun main(args: Array<String>) {
	val vertices = setOf(
			Vertex(5),
			Vertex(6),
			Vertex(7),
			Vertex(4),
			Vertex(9))
	val edges = setOf(
			WeightedEdge(Vertex(5), Vertex(6), weight = 1),
			WeightedEdge(Vertex(5), Vertex(7), weight = 3),
			WeightedEdge(Vertex(6), Vertex(7), weight = 1),
			WeightedEdge(Vertex(6), Vertex(4), weight = 1),
			WeightedEdge(Vertex(7), Vertex(4), weight = 5),
			WeightedEdge(Vertex(7), Vertex(9), weight = 7),
			WeightedEdge(Vertex(4), Vertex(9), weight = 2))
	val graph = WeightedGraph(vertices, edges)
	println(graph.shortestPathInc(Vertex(5), false))
	println(graph.shortestPathInc2(Vertex(5), false))
}