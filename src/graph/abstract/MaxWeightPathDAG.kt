package graph.abstract

import util.max

// find a path in a directed acyclic graph that has max weight from s to t
// report the total weight of such path
fun <V> WeightedGraph<V, Int>.maxWeightedPath(s: Vertex<V>, t: Vertex<V>): Int {
	val dp = HashMap<Vertex<V>, Int>()
	val sortedVertices = topoSort()
	sortedVertices.reversed().forEach { v ->
		if (v == t) {
			dp[v] = 0
		} else {
			dp[v] = Int.MIN_VALUE
			getWeightedEdgesOf(v).forEach { edge ->
				val (_, w) = edge
				dp[v] = max(dp[v]!!, (edge.data ?: 0) + dp[w]!!)
			}
		}
	}

	return dp[s]!!
}

fun main(args: Array<String>) {
	val vertices = (1..5).map { Vertex(it) }
	val edges = setOf(
			WeightedEdge(vertices[0], vertices[1], true, 1),
			WeightedEdge(vertices[0], vertices[2], true, 4),
			WeightedEdge(vertices[1], vertices[2], true, 2),
			WeightedEdge(vertices[1], vertices[3], true, 5),
			WeightedEdge(vertices[2], vertices[3], true, -1),
			WeightedEdge(vertices[2], vertices[4], true, 3),
			WeightedEdge(vertices[3], vertices[4], true, 1))
	val graph = WeightedGraph(vertices, edges)
	println(graph.maxWeightedPath(vertices[0], vertices[4]))
}
