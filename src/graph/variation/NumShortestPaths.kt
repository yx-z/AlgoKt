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
	val newEdges = HashSet<WeightedEdge<V, Int>>()
	val (dist, _) = dijkstra(s, checkIdentity)
	weightedEdges.forEach { edge ->
		val (s, e, _, w) = edge
		if (dist[e]!! == dist[s]!! + w!!) {
			newEdges.add(edge)
		}
	}
	val newGraph = WeightedGraph(vertices, newEdges)
	val list = newGraph.topoSort()
	val dict = HashMap<Vertex<V>, Int>()
	list.forEachIndexed { i, vertex -> dict[vertex] = i }
	val num = OneArray(list.size) { 0 }
	num[dict[t]!!] = 1
	for (i in dict[t]!! downTo 1) {
		num[i] = getEdgesOf(list[i], false).map { num[dict[it.vertex2]!!] }.sum()
	}
	return num[1]
}

fun main(args: Array<String>) {
	val vertices = setOf(Vertex(1), Vertex(2))
	val edges = setOf(WeightedEdge(Vertex(1), Vertex(2), true, 1))
	val graph = WeightedGraph(vertices, edges)
	println(graph.numShortestPaths(Vertex(1), Vertex(2), false))
}