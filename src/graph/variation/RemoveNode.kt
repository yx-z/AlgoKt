package graph.variation

import graph.core.*
import util.*

// given a directed and weighted graph G = (V, E), with weights being either
// > 0, 0, or < 0

// 1. now given a specific vertex v in V, construct another directed and weighted
// graph G' = (V', E') from G : V' = V \ { v }, that is removing v in V
// and you have to make sure all other pairs of shortest path distance u -> w in
// G' is the same of that in G

// do this in O(V^2) time
fun <V> WeightedGraph<V, Int>.removeVertex(v: Vertex<V>,
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

// 2. now suppose you have computed all pairs shortest paths for G' (in which
// we have removed v from V), and are given such table dist[u, w]: u, w in V'
// report two tables toV[1..V] and fromV[1..V} : toV[u] is the shortest distance
// from u to v in G and fromV[1..V] is the shortest distance from v to w in G

// also do this in O(V^2) time
fun <V> WeightedGraph<V, Int>.addVertex(v: Vertex<V>,
                                        dist: Map<Vertex<V>, Map<Vertex<V>, Int>>,
                                        checkIdentity: Boolean = true)
		: Tuple2<Map<Vertex<V>, Int>, Map<Vertex<V>, Int>> {
	val toV = HashMap<Vertex<V>, Int>()
	val fromV = HashMap<Vertex<V>, Int>()
	vertices.forEach {
		toV[it] = INF
		fromV[it] = INF
	}
	toV[v] = 0
	fromV[v] = 0

	vertices
			.filterNot { if (checkIdentity) it === v else it == v }
			.forEach { u ->

			}

	return toV tu fromV
}

// 3. now combining ideas from 1 and 2, find a O(V^3) algorithm that computes
// all pairs shortest paths in a given directed, weighted graph G
// it should look similar to warshall's algorithm
fun <V> WeightedGraph<V, Int>.apsp(): Map<Vertex<V>, Map<Vertex<V>, Int>> {
	TODO()
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
	val newGraph = graph.removeVertex(vertices[3])
	println(newGraph.warshall())

	val dist = newGraph.warshall()
	println(newGraph.addVertex(vertices[3], dist))

	println(graph.apsp())
}