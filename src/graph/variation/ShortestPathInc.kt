package graph.variation

import graph.core.*
import util.INF
import util.Tuple2
import java.util.*
import kotlin.collections.set

// given a weighted graph G = (V, E) with each vertex having an int
// find a shortest path from s to t (s, t in V) : values of vertices included in
// this path is always increasing and report the total weight
// you may assume that each int is unique and distinct

// modify dijkstra algorithm
fun WeightedGraph<Int, Int>.shortestPathInc(s: CVertex<Int>,
//                                            t: CVertex<Int>,
                                            checkIdentity: Boolean = true)
		: Map<CVertex<Int>, Int> {
	val dist = HashMap<CVertex<Int>, Int>()
	vertices.forEach {
		dist[it as CVertex<Int>] = INF
	}
	dist[s] = 0

	val minHeap = PriorityQueue<CVertex<Int>>()
	minHeap.add(s)

	while (minHeap.isNotEmpty()) {
		val v = minHeap.remove()
		getEdgesOf(v, checkIdentity).forEach { (start, end, isDirected, weight) ->
			val u = (if (isDirected || start == v) end else start) as CVertex<Int>
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

fun WeightedGraph<Int, Int>.shortestPathInc2(s: CVertex<Int>,
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
			CVertex(5),
			CVertex(6),
			CVertex(7),
			CVertex(4),
			CVertex(9))
	val edges = setOf(
			WeightedEdge(CVertex(5), CVertex(6), weight = 1),
			WeightedEdge(CVertex(5), CVertex(7), weight = 3),
			WeightedEdge(CVertex(6), CVertex(7), weight = 1),
			WeightedEdge(CVertex(6), CVertex(4), weight = 1),
			WeightedEdge(CVertex(7), CVertex(4), weight = 5),
			WeightedEdge(CVertex(7), CVertex(9), weight = 7),
			WeightedEdge(CVertex(4), CVertex(9), weight = 2))
	val graph = WeightedGraph(vertices, edges)
	println(graph.shortestPathInc(CVertex(5), false))
	println(graph.shortestPathInc2(CVertex(5), false))
}