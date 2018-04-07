package graph.variation

import graph.core.*
import util.OneArray
import java.util.*
import kotlin.collections.HashMap

// given # of shortest paths (that should have equal length) in a graph G = (V, E)
// starting with vertex v in V
fun <V> WeightedGraph<V, Int>.numShortestPaths(s: Vertex<V>,
                                               t: Vertex<V>,
                                               checkIdentity: Boolean = true): Int {
	val (dist, _) = dijkstra(s, checkIdentity)
	// O(E log V)

	val newEdges = HashSet<WeightedEdge<V, Int>>()
	weightedEdges.forEach { edge ->
		val (start, end, _, w) = edge
		if (dist[end]!! == dist[start]!! + w!!) {
			newEdges.add(edge)
		}
	}

	val newGraph = WeightedGraph(vertices, newEdges)
	val list = newGraph.topoSort()
	// O(V + E)
	val dict = HashMap<Vertex<V>, Int>()
	list.forEachIndexed { i, vertex ->
		dict[vertex] = i
	}

	val num = OneArray(list.size) { 0 }
	num[dict[t]!!] = 1
	for (i in dict[t]!! - 1 downTo dict[s]!!) {
		num[i] = getEdgesFrom(list[i], checkIdentity)
				.map { num[dict[it.vertex2]!!] }
				.sum()
	}

//	num.prettyPrintln()
	return num[dict[s]!!]
}

fun main(args: Array<String>) {
	val V = (0..2).map { CVertex(it) }
	val E = setOf(
			WeightedEdge(V[0], V[1], true, 1),
			WeightedEdge(V[1], V[2], true, 2),
			WeightedEdge(V[0], V[2], true, 3))
	val G = WeightedGraph(V, E)
	println(G.numShortestPaths(V[0], V[2]))
}