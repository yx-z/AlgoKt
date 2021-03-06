package graph.core

import util.INF
import util.Tuple2
import util.min
import util.tu
import java.util.*
import kotlin.collections.HashMap

// given a weighted graph, a starting vertex s
// find the shortest path with min weight from s to all other vertices
// assume the weights are all positive ints

fun <V> WeightedGraph<V, Int>.dijkstra(s: Vertex<V>, checkIdentity: Boolean = true)
		: Tuple2<Map<Vertex<V>, Int>, Map<Vertex<V>, Vertex<V>?>> {
	val dist = HashMap<Vertex<V>, Int>()
	val parent = HashMap<Vertex<V>, Vertex<V>?>()
	vertices.forEach {
		dist[it] = INF
		parent[it] = null
	}
	dist[s] = 0
	parent[s] = s

	val minHeap = PriorityQueue<Vertex<V>>(Comparator { u, v -> dist[u]!! - dist[v]!! })
	minHeap.add(s)

	while (minHeap.isNotEmpty()) {
		val v = minHeap.remove()
		getEdgesOf(v, checkIdentity).forEach { (start, end, isDirected, weight) ->
			val u = if (isDirected || start == v) end else start
			if (dist[v]!! + weight!! < dist[u]!!) {
				dist[u] = dist[v]!! + weight
				parent[u] = v
				minHeap.add(u)
			}
		}
	}

	return dist tu parent
}
// time complexity: O(E log V)

// if we use a queue instead of minHeap in the above classic Dijkstra's Algorithm,
// it will be called Shimbel's or Bellman-Ford's Algorithm

// but we can also do a DP version of Shimbel's i.e. Bellman-Ford's algorithm
fun <V> WeightedGraph<V, Int>.bellmanFordDp(s: Vertex<V>,
                                            t: Vertex<V>,
                                            checkIdentity: Boolean = true): Int {
	val V = vertices.size

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
					.filter { (s, e, isDirected) ->
						if (isDirected) {
							if (checkIdentity) e === v else e == v
						} else {
							if (checkIdentity) s === v || e === v else s == v || e == v
						}
					} // get all edges to v
					.forEach { (u, _, _, d) ->
						dp[i][v] = min(dp[i][v]!!, dp[i - 1][u]!! + d!!)
					}
		}
	}
	// time complexity: O(VE)

	// we want dp(V - 1, t)
	return dp[V - 1][t]!!
}

// and we can do even better as follows
// (not faster but more succinct)
fun <V> WeightedGraph<V, Int>.bellmanFord(s: Vertex<V>, t: Vertex<V>): Int {
	val V = vertices.size

	val dist = HashMap<Vertex<V>, Int>()
	vertices.forEach { dist[it] = INF }
	dist[s] = 0

	for (i in 1 until V) {
		weightedEdges.forEach { (u, v, isDirected, d) ->
			if (dist[v]!! > dist[u]!! + d!!) { // if edge is tense
				dist[v] = dist[u]!! + d // relax the edge
			}
			if (!isDirected) {
				// if the graph is undirected
				// relax the other edge if necessary
				if (dist[u]!! > dist[v]!! + d) {
					dist[u] = dist[v]!! + d
				}
			}
		}
	}

	return dist[t]!!
}

// note that i have also implemented an A* (A Star) algorithm in src/mat/ShortestPath.kt
// it is a generalized dijkstra's algorithm for the single source shortest path
// problem with heuristic functions for better performance
// but it might not ALWAYS find the shortest path depending on the specific
// heuristic function being used

// since by convention, A* is used in a grid/table/bitmap/2d arr, with no explicit
// graph structures (although it can be easily turned into so), i choose not to
// put it under this graph package which mainly uses traditional G = (V, E) graph
// representation/implementation here

fun main(args: Array<String>) {
	val vertices = (0..4).map { Vertex('a' + it) }
	val edges = setOf(
			WeightedEdge(vertices[0], vertices[1], true, 10),
			WeightedEdge(vertices[0], vertices[2], true, 2),
			WeightedEdge(vertices[0], vertices[4], true, 100),
			WeightedEdge(vertices[1], vertices[3], true, 2),
			WeightedEdge(vertices[2], vertices[4], true, 10),
			WeightedEdge(vertices[3], vertices[4], true, 1))
	val graph = WeightedGraph(vertices, edges)
	println(graph.dijkstra(vertices[0]).first[vertices[4]])
	println(graph.bellmanFordDp(vertices[0], vertices[4]))
	println(graph.bellmanFord(vertices[0], vertices[4]))
}