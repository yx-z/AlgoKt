package graph.variation

import graph.core.Vertex
import graph.core.WeightedEdge
import graph.core.WeightedGraph
import graph.core.dijkstra
import util.max

// given an undirected weighted graph, a starting point s, and another starting
// point t, find a vertex v that minimize the total cost from s to v and t to v
fun <V> WeightedGraph<V, Int>.shortestMidPoint(s: Vertex<V>, t: Vertex<V>): Int {
	// our strategy is:
	// distS = run a dijkstra from s, distT = run another dijkstra from t
	val (distS, _) = dijkstra(s)
	val (distT, _) = dijkstra(t)
	println(distS)
	println(distT)
	// find the min_v { max{ distS[v], distT[v] } }
	return vertices.map { max(distS[it]!!, distT[it]!!) }.min()!!
}

fun main(args: Array<String>) {
	val vertices = (0..4).map { Vertex(it) }
	val edges = setOf(
			WeightedEdge(vertices[0], vertices[1], weight = 3),
			WeightedEdge(vertices[0], vertices[3], weight = 1),
			WeightedEdge(vertices[1], vertices[2], weight = 1),
			WeightedEdge(vertices[2], vertices[3], weight = 2),
			WeightedEdge(vertices[2], vertices[4], weight = 3),
			WeightedEdge(vertices[3], vertices[4], weight = 1))
	val graph = WeightedGraph(vertices, edges)
	println(graph.shortestMidPoint(vertices[0], vertices[4]))
}
