package dp

import graph.core.*
import util.max

// given an unrooted tree T, that is, connected acyclic undirected graphs,

// 1. let the edges in T be weighed (either > 0, 0, or < 0)
//    find a path with largest weight
fun WeightedGraph<Int, Int>.maxWeight() =
		vertices.map { maxWeight(it, init()) }.max() ?: 0

fun WeightedGraph<Int, Int>.init(): HashMap<Vertex<Int>, Boolean> {
	val map = HashMap<Vertex<Int>, Boolean>()
	vertices.forEach { map[it] = false }
	return map
}

fun WeightedGraph<Int, Int>.maxWeight(v: Vertex<Int>,
                                      map: HashMap<Vertex<Int>, Boolean>): Int {
	map[v] = true
	var max = 0
	getEdgesOf(v).forEach { (s, e, _, data) ->
		val u = if (s === v) e else s
		if (map[u] == false) {
			max = max(max, this.maxWeight(u, map) + (data ?: 0))
		}
	}
	return max
}

// 2. let the weight be in vertices (unweighted edges)
//    find a path with max weight
fun Graph<Int>.maxWeight() =
		vertices.map { maxWeight(it, init()) }.max() ?: 0

fun Graph<Int>.init(): HashMap<Vertex<Int>, Boolean> {
	val map = HashMap<Vertex<Int>, Boolean>()
	vertices.forEach { map[it] = false }
	return map
}

fun Graph<Int>.maxWeight(v: Vertex<Int>, map: HashMap<Vertex<Int>, Boolean>): Int {
	map[v] = true
	var max = 0
	getEdgesOf(v).forEach { (s, e) ->
		val u = if (s === v) e else s
		if (map[u] == false) {
			max = max(max, this.maxWeight(u, map))
		}
	}
	return max + v.data
}

fun main(args: Array<String>) {
	val vertices = (1..6).map { Vertex(it) }
	val edges = setOf(
			WeightedEdge(vertices[0], vertices[1], weight = -1),
			WeightedEdge(vertices[1], vertices[2], weight = 3),
			WeightedEdge(vertices[2], vertices[3], weight = -2),
			WeightedEdge(vertices[3], vertices[4], weight = 3),
			WeightedEdge(vertices[3], vertices[5], weight = 0))
	val T = WeightedGraph(vertices, edges)
//	println(T.maxWeight())

	val v = (1..3).map { Vertex(it - 2) }
	val e = setOf(
			Edge(v[0], v[1])
	)
	val g = Graph(v, e)
	println(g.maxWeight())
}