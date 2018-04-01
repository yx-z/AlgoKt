package graph.app

import graph.abstract.ComparableVertex
import graph.abstract.WeightedEdge
import graph.abstract.WeightedGraph
import util.INF
import java.util.*
import kotlin.collections.set

// given a weighted graph G = (V, E) with each vertex having an int
// find a shortest path from s to t (s, t in V) : values of vertices included in
// this path is always increasing and report the total weight
// you may assume that each int is unique and distinct

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
		getWeightedEdgesOf(v, checkIdentity).forEach { (start, end, isDirected, weight) ->
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

fun main(args: Array<String>) {
	val vertices = setOf(
			ComparableVertex(5),
			ComparableVertex(6),
			ComparableVertex(7),
			ComparableVertex(4),
			ComparableVertex(9))
	val edges = setOf(
			WeightedEdge(ComparableVertex(5), ComparableVertex(6), data = 1),
			WeightedEdge(ComparableVertex(5), ComparableVertex(7), data = 3),
			WeightedEdge(ComparableVertex(6), ComparableVertex(7), data = 1),
			WeightedEdge(ComparableVertex(6), ComparableVertex(4), data = 1),
			WeightedEdge(ComparableVertex(7), ComparableVertex(4), data = 5),
			WeightedEdge(ComparableVertex(7), ComparableVertex(9), data = 7),
			WeightedEdge(ComparableVertex(4), ComparableVertex(9), data = 2))
	val graph = WeightedGraph(vertices, edges)
	println(graph.shortestPathInc(ComparableVertex(5), false))
}