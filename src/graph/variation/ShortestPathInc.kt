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
fun WeightedGraph<Int, Int>.shortestPathInc(s: ComparableVertex<Int>,
//                                            t: ComparableVertex<Int>,
                                            checkIdentity: Boolean = true)
		: Map<ComparableVertex<Int>, Int> {
	val dist = HashMap<ComparableVertex<Int>, Int>()
	vertices.forEach {
		dist[it as ComparableVertex<Int>] = INF
	}
	dist[s] = 0

	val minHeap = PriorityQueue<ComparableVertex<Int>>()
	minHeap.add(s)

	while (minHeap.isNotEmpty()) {
		val v = minHeap.remove()
		getEdgesOf(v, checkIdentity).forEach { (start, end, isDirected, weight) ->
			val u = (if (isDirected || start == v) end else start) as ComparableVertex<Int>
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

fun WeightedGraph<Int, Int>.shortestPathInc2(s: ComparableVertex<Int>,
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
			ComparableVertex(5),
			ComparableVertex(6),
			ComparableVertex(7),
			ComparableVertex(4),
			ComparableVertex(9))
	val edges = setOf(
			WeightedEdge(ComparableVertex(5), ComparableVertex(6), weight = 1),
			WeightedEdge(ComparableVertex(5), ComparableVertex(7), weight = 3),
			WeightedEdge(ComparableVertex(6), ComparableVertex(7), weight = 1),
			WeightedEdge(ComparableVertex(6), ComparableVertex(4), weight = 1),
			WeightedEdge(ComparableVertex(7), ComparableVertex(4), weight = 5),
			WeightedEdge(ComparableVertex(7), ComparableVertex(9), weight = 7),
			WeightedEdge(ComparableVertex(4), ComparableVertex(9), weight = 2))
	val graph = WeightedGraph(vertices, edges)
	println(graph.shortestPathInc(ComparableVertex(5), false))
	println(graph.shortestPathInc2(ComparableVertex(5), false))
}