package graph.variation

import graph.core.Vertex
import graph.core.WeightedEdge
import graph.core.WeightedGraph
import graph.core.topoSort
import util.INF
import util.OneArray
import java.util.*
import kotlin.collections.HashMap

// given # of shortest paths (that should have equal length) in a graph G = (V, E)
// starting with vertex v in V
fun <V> WeightedGraph<V, Int>.numShortestPaths(s: Vertex<V>): Int {
	val newEdges = HashSet<WeightedEdge<V, Int>>()
	val dist = HashMap<Vertex<V>, Int>()
	vertices.forEach {
		dist[it] = INF
	}
	dist[s] = 0

	val minHeap = PriorityQueue<Vertex<V>>()
	minHeap.add(s)

	while (minHeap.isNotEmpty()) {
		val v = minHeap.remove()
		getEdgesOf(v, false).forEach { edge ->
			val (start, end, _, weight) = edge
			val u = if (start == v) end else start
			if (dist[v]!! + weight!! < dist[u]!!) {
				dist[u] = dist[v]!! + weight
				minHeap.add(u)
				newEdges.add(edge)
			}
		}
	}

	val newGraph = WeightedGraph(vertices, newEdges)
	val list = newGraph.topoSort()
	val dict = HashMap<Vertex<V>, Int>()
	list.forEachIndexed { i, vertex -> dict[vertex] = i }
	val num = OneArray(list.size) { 0 }
	num[num.size] = 1
	for (i in num.size - 1 downTo 1) {
		num[i] = getEdgesOf(list[i], false).map { num[dict[it.vertex2]!!] }.sum()
	}
	return num[1]
}

fun main(args: Array<String>) {
	val vertices = setOf(Vertex(1), Vertex(2))
	val edges = setOf(WeightedEdge(Vertex(1), Vertex(2), true, 1))
	val graph = WeightedGraph(vertices, edges)
	println(graph.numShortestPaths(Vertex(1)))
}