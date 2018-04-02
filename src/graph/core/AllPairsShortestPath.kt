package graph.core

import util.*
import java.lang.Math.ceil
import kotlin.math.log

// given a weighted graph with either negative, zero, or positive edges G = (V, E)
// report a table dist[u, v]: the shortest distance between u and v in G

// our first solution is just run Bellman-Ford's Algorithm for every vertex
fun <V> WeightedGraph<V, Int>.bellmanFordAll()
		: Map<Vertex<V>, Map<Vertex<V>, Int>> {
	val ret = HashMap<Vertex<V>, HashMap<Vertex<V>, Int>>()
	vertices.forEach { u ->
		ret[u] = HashMap()
		vertices.forEach { v ->
			ret[u, v] = bellmanFord(u, v)
		}
	}
	return ret
}
// time complexity: O(V^2E)

// now consider dist(i, u, v) which is the length of the shortest path from
// u to v with at most i edges
// we have dist(i, u, v) = min{ dist(i / 2, u, w) + dist(i / 2, w, v) : w in V }
// and then we can report any of dist(i, u, v), i >= V - 1
// for simplicity, let us compute dist(2^k, u, v) : 2^k >= V - 1
// so that i / 2 is always a power of 2
fun <V> WeightedGraph<V, Int>.allPairsShortestPathDivideAndConquer()
		: Map<Vertex<V>, Map<Vertex<V>, Int>> {
	val V = vertices.size

	val k = ceil(log(V.toDouble(), 2.0)).toInt()

	val dist = Array(k + 1) { HashMap<Vertex<V>, HashMap<Vertex<V>, Int>>() }
	dist.forEach { map ->
		vertices.forEach { u ->
			map[u] = HashMap()
			vertices.forEach { v ->
				map[u, v] = when {
					u === v -> 0
					getEdgesOf(u)
							.any { (s, e) -> s === v || e === v } -> {
						getEdgesOf(u)
								.filter { (s, e) -> s === v || e === v }
								.map { it.weight!! }
								.first()
					}
					else -> INF
				}
			}
		}
	}

	for (i in 1..k) {
		vertices.forEach { u ->
			vertices.forEach { v ->
				dist[i][u, v] = vertices.map { w ->
					dist[i - 1][u, w]!! + dist[i - 1][w, v]!!
				}.min() ?: INF
			}
		}
	}

	return dist.last()
}
// time complexity: O(V^3 log V)

// warshall's dp
// given G = (V, E), number all vertices from 1 to |V|
// consider dp[1..V, 1..V, 0 until V] : dp[u, v, r] is the shortest distance from
// u to v with intermediate vertices having index at most r
// for example dp[u, v, 0] is the shortest distance from u to v with NO
// intermediate vertices (since no vertex has an index <= 0)
fun <V> WeightedGraph<V, Int>.warshallDP(): Map<Vertex<V>, Map<Vertex<V>, Int>> {
	val vArr = vertices.toOneArray()
	val V = vertices.size
	val dp = OneArray(V) { OneArray(V) { Array(V) { 0 } } }
	// space complexity: O(V^3)

	for (u in 1..V) {
		for (v in 1..V) {
			dp[u, v][0] = if (u == v) {
				0
			} else {
				getEdgesOf(vArr[u])
						.firstOrNull { (_, e) -> e === vArr[v] }?.weight ?: INF
			}
		}
	}

	for (r in 1 until V) {
		for (u in 1..V) {
			for (v in 1..V) {
				dp[u, v][r] = min(dp[u, v][r - 1], dp[u, r][r - 1] + dp[r, v][r - 1])
			}
		}
	}

	val dist = HashMap<Vertex<V>, HashMap<Vertex<V>, Int>>()
	for (u in 1..V) {
		dist[vArr[u]] = HashMap()
		for (v in 1..V) {
			dist[vArr[u], vArr[v]] = dp[u, v][V - 1]
		}
	}
	return dist
}
// time complexity: O(V^3)

// but we can do better as we have done from bellmanFordDP to bellmanFord!
// warshall's final algorithm
fun <V> WeightedGraph<V, Int>.warshall(): Map<Vertex<V>, Map<Vertex<V>, Int>> {
	val dist = HashMap<Vertex<V>, HashMap<Vertex<V>, Int>>()
	vertices.forEach { u ->
		dist[u] = HashMap()
		vertices.forEach { v ->
			dist[u, v] = if (u === v) {
				0
			} else {
				getEdgesOf(u)
						.firstOrNull { (_, e) -> e === v }?.weight ?: INF
			}
		}
	}

	vertices.forEach { r ->
		vertices.forEach { u ->
			vertices.forEach { v ->
				dist[u, v] = min(dist[u, v]!!, dist[u, r]!! + dist[r, v]!!)
			}
		}
	}

	return dist
}
// time complexity: O(V^3)

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
	println(graph.bellmanFordAll())
	println(graph.allPairsShortestPathDivideAndConquer())
	println(graph.warshallDP())
	println(graph.warshall())
}