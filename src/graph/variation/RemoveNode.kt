package graph.variation

import graph.core.*
import util.*

// given a directed and weighted graph G = (V, E), with weights being either
// > 0, 0, or < 0

// 1. now given a specific vertex v in V, construct another directed and weighted
// graph G' = (V', E') from G : V' = V \ { v }, that is removing v in V
// and you have to make sure all other pairs of shortest path distance u -> w in
// G' is the same of that in G
fun <V> WeightedGraph<V, Int>.removeNode(v: Vertex<V>,
                                         checkIdentity: Boolean = true): WeightedGraph<V, Int> {
	val newVertices = vertices.filterNot { if (checkIdentity) v === it else v == it }
	val newEdges = weightedEdges.toMutableList()
	getEdgesTo(v, checkIdentity).forEach { (u, _, _, w1) ->
		getEdgesFrom(v, checkIdentity).forEach { (_, w, _, w2) ->
			// for each u -> v -> w
			// perform an add or replace
			val ori = newEdges.firstOrNull { (s, e, _, _) -> s === u && e === w }
			if (ori == null) {
				newEdges.add(WeightedEdge(u, w, weight = w1!! + w2!!))
			} else {
				ori.weight = min(w1!! + w2!!, ori.weight!!)
			}
		}
	}
	return WeightedGraph(newVertices, newEdges)
}

fun main(args: Array<String>) {
	val vertices = (0..4).map { Vertex(it) }
	val edges = setOf(
			WeightedEdge(vertices[0], vertices[1], true, 10),
			WeightedEdge(vertices[0], vertices[2], true, 3),
			WeightedEdge(vertices[1], vertices[4], true, 12),
			WeightedEdge(vertices[2], vertices[3], true, 12),
			WeightedEdge(vertices[2], vertices[4], true, 4),
			WeightedEdge(vertices[3], vertices[4], true, 2),
			WeightedEdge(vertices[4], vertices[2], true, 3))
	val graph = WeightedGraph(vertices, edges)
	println(graph.warshall())
	val newGraph = graph.removeNode(vertices[3])
	println(newGraph.warshall())
}