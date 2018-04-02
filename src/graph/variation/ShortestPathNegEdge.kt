package graph.variation

import graph.core.*
import util.min

// in the standard Dijkstra's algorithm, we assume all edges are positive
// what will happen if there is exactly one negative edge in the graph?
// you only need to report the length of such path
fun <V> WeightedGraph<V, Int>.shortestPathNegEdge(s: Vertex<V>, t: Vertex<V>): Int {
	// given a negative weighted edge u -> v
	// our strategy is:
	// the shortest path from s to t either includes the negative edge or not
	// if it doesn't, then remove it and find the shortest path from s to t
	// if it does, then remove it, find the shortest path from s to u, add the
	// negative edge u -> v, and finally find the shortest path from v to t

	val newVertices = vertices
	val newEdges = weightedEdges.filter { it.weight!! > 0 }
	val newGraph = WeightedGraph(newVertices, newEdges)

	val (u, v, _, d) = weightedEdges.first { it.weight!! < 0 }
	val sMap = newGraph.dijkstra(s)

	val notInclude = sMap.first[t]!!
	val include = sMap.first[u]!! + d!! + newGraph.dijkstra(v).first[t]!!

	return min(notInclude, include)
}

fun main(args: Array<String>) {
	val vertices = (1..5).map { ComparableVertex(it) }
	val edges = setOf(
			WeightedEdge(vertices[0], vertices[1], true, 3),
			WeightedEdge(vertices[0], vertices[3], true, 1),
			WeightedEdge(vertices[1], vertices[2], true, 1),
			WeightedEdge(vertices[2], vertices[3], true, 2),
			WeightedEdge(vertices[2], vertices[4], true, -3),
			WeightedEdge(vertices[3], vertices[4], true, 1))
	val graph = WeightedGraph(vertices, edges)
	println(graph.shortestPathNegEdge(vertices[0], vertices[4]))
}