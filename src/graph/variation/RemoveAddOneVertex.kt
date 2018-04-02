package graph.variation

import graph.core.Vertex
import graph.core.WeightedEdge
import graph.core.WeightedGraph
import graph.core.warshall
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
			if (!(checkIdentity && u === w) && !(!checkIdentity && u == w)) {
				val ori = newEdges.firstOrNull { (s, e, _, _) -> s === u && e === w }
				if (ori == null) {
					newEdges.add(WeightedEdge(u, w, true, weight = w1!! + w2!!))
				} else {
					ori.weight = min(w1!! + w2!!, ori.weight!!)
				}
			}
		}
	}
	newEdges.removeAll(getEdgesTo(v, checkIdentity))
	newEdges.removeAll(getEdgesFrom(v, checkIdentity))
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
				getEdgesTo(v, checkIdentity).forEach { (r, _, _, w) ->
					// u -> r -> v
					toV[u] = min(toV[u]!!, dist[u, r]!! + w!!)
				}

				getEdgesFrom(v, checkIdentity).forEach { (_, r, _, w) ->
					// v -> r -> u
					fromV[u] = min(fromV[u]!!, dist[r, u]!! + w!!)
				}
			}

	return toV tu fromV
}

// 3. now combining ideas from 1 and 2, find a O(V^3) algorithm that computes
// all pairs shortest paths in a given directed, weighted graph G
// it should look similar to warshall's algorithm
fun <V> WeightedGraph<V, Int>.apsp(): Map<Vertex<V>, Map<Vertex<V>, Int>> {
	val v = vertices.toList()
	var g = this
	for (i in 0 until v.size - 2) {
		g = g.removeVertex(v[i])
		println(g)
	}
	val dist = HashMap<Vertex<V>, HashMap<Vertex<V>, Int>>()
	for (i in 0 until v.size - 2) {

	}
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
	val newGraph = graph.removeVertex(vertices[3])

	val gDist = graph.warshall()
	val nDist = newGraph.warshall()

	println(gDist)

	// 1.
//	println(nDist)

	// 2.
//	println(graph.addVertex(vertices[3], nDist))

	// 3.
	println(graph.apsp())
}