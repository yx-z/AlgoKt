package graph.variation

import graph.core.Vertex
import graph.core.WeightedEdge
import graph.core.WeightedGraph
import util.INF
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set

// given a directed, positively weighted graph G = (V, E), two vertices s, t in V,
// find the shortest path from s to t, that not only minimizes the weight,
// but also minimizes the # of edges in the path

fun <V> WeightedGraph<V, Int>.bestShortestPath(s: Vertex<V>,
                                               t: Vertex<V>,
                                               checkIdentity: Boolean = true)
		: List<Vertex<V>> {
	val dist = HashMap<Vertex<V>, Int>()
	val edgeCount = HashMap<Vertex<V>, Int>()
	val parent = HashMap<Vertex<V>, Vertex<V>?>()
	vertices.forEach {
		dist[it] = INF
		edgeCount[it] = INF
		parent[it] = null
	}
	dist[s] = 0
	edgeCount[s] = 0

	val minHeap = PriorityQueue<Vertex<V>>(Comparator { u, v -> dist[u]!! - dist[v]!! })
	minHeap.add(s)

	while (minHeap.isNotEmpty()) {
		val v = minHeap.remove()
		getEdgesOf(v, checkIdentity).forEach { (start, end, isDirected, weight) ->
			val u = if (isDirected || start == v) end else start
			if (dist[v]!! + weight!! < dist[u]!!) {
				dist[u] = dist[v]!! + weight
				edgeCount[u] = edgeCount[v]!! + 1
				parent[u] = v
				minHeap.add(u)
			} else if (dist[v]!! + weight == dist[u]!! && edgeCount[v]!! + 1 < edgeCount[u]!!) {
				parent[u] = v
				edgeCount[u] = edgeCount[v]!! + 1
				minHeap.add(u)
			}
		}
	}

	val path = ArrayList<Vertex<V>>()
	var curr: Vertex<V>? = t
	while (curr != null) {
		path.add(curr)
		curr = parent[curr]
	}

	return path.reversed()
}

fun main(args: Array<String>) {
	val V = (0..4).map { Vertex(it) }
	val E = setOf(
			WeightedEdge(V[0], V[1], true, 100),
			WeightedEdge(V[0], V[2], true, 20),
			WeightedEdge(V[1], V[4], true, 100),
			WeightedEdge(V[2], V[3], true, 20),
			WeightedEdge(V[3], V[4], true, 160))
	val G = WeightedGraph(V, E)
	println(G.bestShortestPath(V[0], V[4]))
}