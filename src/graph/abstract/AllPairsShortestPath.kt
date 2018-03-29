package graph.abstract

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
			ret[u, v] = bellmanFordOpt(u, v)
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
					getWeightedEdgesOf(u)
							.any { (s, e) -> s === v || e === v} -> {
						getWeightedEdgesOf(u)
								.filter { (s, e) -> s === v || e === v }
								.map { it.data!! }
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

fun main(args: Array<String>) {
	val vertices = (1..5).map { ComparableVertex(it) }
	val edges = setOf(
			WeightedEdge(vertices[0], vertices[1], data = 1),
			WeightedEdge(vertices[0], vertices[3], data = 3),
			WeightedEdge(vertices[1], vertices[2], data = 1),
			WeightedEdge(vertices[2], vertices[3], data = 2),
			WeightedEdge(vertices[2], vertices[4], data = 3),
			WeightedEdge(vertices[3], vertices[4], data = 1))
	val graph = WeightedGraph(vertices, edges)
	println(graph.bellmanFordAll())
	println(graph.allPairsShortestPathDivideAndConquer())
}