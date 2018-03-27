package dp

import graph.abstract.Vertex
import graph.abstract.WeighedEdge
import graph.abstract.WeighedGraph
import util.max

// given an unrroted tree T, that is, connected acylic undirected graphs,

// 1. let the edges in T be weighed (either > 0, 0, or < 0)
//    find a path with largest weight
fun WeighedGraph<Int, Int>.maxWeight() =
		vertices.map { dfs(it, init()) }.max() ?: 0

fun WeighedGraph<Int, Int>.init(): HashMap<Vertex<Int>, Boolean> {
	val map = HashMap<Vertex<Int>, Boolean>()
	vertices.forEach { map[it] = false }
	return map
}

fun WeighedGraph<Int, Int>.dfs(v: Vertex<Int>,
                               map: HashMap<Vertex<Int>, Boolean>): Int {
	map[v] = true
	var max = 0
	getWeigedEdgesOf(v).forEach { edge ->
		val (s, e) = edge
		val u = if (s === v) e else s
		if (map[u] == false) {
			max = max(max, dfs(u, map) + (edge.data ?: 0))
		}
	}
	return max
}

fun main(args: Array<String>) {
	val vertices = (1..6).map { Vertex(it) }
	val edges = setOf(
			WeighedEdge(vertices[0], vertices[1], data = -1),
			WeighedEdge(vertices[1], vertices[2], data = 3),
			WeighedEdge(vertices[2], vertices[3], data = -2),
			WeighedEdge(vertices[3], vertices[4], data = 3),
			WeighedEdge(vertices[3], vertices[5], data = 0))
	val T = WeighedGraph(vertices, edges)
	println(T.maxWeight())
}