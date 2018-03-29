package graph.abstract

import util.Tuple2
import util.min
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

// if we use a queue instead of minHeap in the above classic Dijkstra's Algorithm,
// it will be called Shimbel's or Bellman-Ford's Alogrithm

// but we can also do a DP version of Shimbel's algorithm
fun <V> WeightedGraph<V, Int>.shimbelDP(s: Vertex<V>, t: Vertex<V>): Int {
	val V = vertices.size
	val INF = Int.MAX_VALUE / 2

	// dp(i, v): shortest distance from s to v consisting of at most i edges
	// memoization structure: array of maps dp[1 until V, vertices ] : dp[i, v] = dp(i, v)
	val dp = Array(V) { HashMap<Vertex<V>, Int>() }

	// base case:
	// dp(0, s) = 0
	// dp(0, v) = inf for all v != s
	vertices.forEach { vertex ->
		dp[0][vertex] = INF
	}
	dp[0][s] = 0
	// space complexity: O(V^2)

	// recursive case:
	// dp(i, v) = min { dp(i - 1, v), min { dp(i - 1, u) + w(u -> v) : u -> v in E } }
	// dependency: dp(i, v) depends on dp(i - 1, u)
	// evaluation order: outer loop for i from 1 until V
	for (i in 1 until V) {
		// inner loop for every vertex v
		vertices.forEach { v ->
			dp[i][v] = dp[i - 1][v]!!
			weightedEdges
					.filter { (s, e) -> e === v } // get all edges to v
					.forEach { (u, _, _, d) ->
						dp[i][v] = min(dp[i][v]!!, dp[i - 1][u]!! + d!!)
					}
		}
	}
	// time complexity: O(VE)

	// we want dp(V - 1, t)
	return dp[V - 1][t]!!
}

// we can optimize the above algorithm by observing that we only need dp[i - 1]
// to compute dp[i]
// so we don't need dp[1 until V] instead, a single map containing previous
// results is enough


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
	println(graph.dijkstra(vertices[0]).first[vertices[4]])
	println(graph.shimbelDP(vertices[0], vertices[4]))
}