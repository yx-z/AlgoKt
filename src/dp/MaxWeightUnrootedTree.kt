package dp

import graph.abstract.Graph
import graph.abstract.Vertex
import graph.abstract.WeightedEdge
import graph.abstract.WeightedGraph
import util.max

// given an unroted tree T, that is, connected acyclic undirected graphs,

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
	getWeigedEdgesOf(v).forEach { edge ->
		val (s, e) = edge
		val u = if (s === v) e else s
		if (map[u] == false) {
			max = max(max, this.maxWeight(u, map) + (edge.data ?: 0))
		}
	}
	return max
}

// 2. let the weight be in vertices (unweighed edges)
//    find a path with max weight
fun Graph<Int>.maxWeight(): Int {
	TODO()
}

fun main(args: Array<String>) {
	val vertices = (1..6).map { Vertex(it) }
	val edges = setOf(
			WeightedEdge(vertices[0], vertices[1], data = -1),
			WeightedEdge(vertices[1], vertices[2], data = 3),
			WeightedEdge(vertices[2], vertices[3], data = -2),
			WeightedEdge(vertices[3], vertices[4], data = 3),
			WeightedEdge(vertices[3], vertices[5], data = 0))
	val T = WeightedGraph(vertices, edges)
	println(T.maxWeight())
}