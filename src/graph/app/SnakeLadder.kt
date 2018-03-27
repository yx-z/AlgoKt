package graph.app

import graph.abstract.Edge
import graph.abstract.Graph
import graph.abstract.Vertex
import graph.abstract.bfs
import util.Tuple2
import util.min
import util.tu

// given an n by n board, each cell is numbered from 1 to n^2
// you start from 1 and goal is to reach n^2, and in each round, you may move
// k steps forward, i.e. 1..k
// and there are also ladders (going up) and snakes (going down) in some cells
// if your move ends in a ladder, you may or may not go up
// but if you ends in a snake, you MUST go down

// find min # of steps to reach n^2

fun main(args: Array<String>) {
	// a 5 by 5 bouard
	val n = 5
	val ladders = arrayOf(
			2 tu 5,
			3 tu 10,
			8 tu 14,
			12 tu 21,
			15 tu 20,
			21 tu 23,
			22 tu 25)
	val snakes = arrayOf(
			14 tu 9,
			24 tu 18)
	val k = 4
	println(snakeLadder(n, ladders, snakes, k))
}

fun snakeLadder(n: Int,
                ladders: Array<Tuple2<Int, Int>>,
                snakes: Array<Tuple2<Int, Int>>,
                k: Int): Int {
	val end = n * n
	// [1, 2, 3, ..., n^2]
	val vertices = (1..end).map { Vertex(it) }
	val edges = HashSet<Edge<Int>>()
	vertices.forEach { v ->
		if (snakes.any { it.first == v.data }) {
			edges.add(Edge(v, vertices[snakes.find { it.first == v.data }!!.second - 1], true))
		} else {
			(v.data + 1..min(v.data + k, end)).forEach { i ->
				edges.add(Edge(v, vertices[i - 1], true))
				if (ladders.any { it.first == i }) {
					edges.add(Edge(v, vertices[ladders.find { it.first == i }!!.second - 1], true))
				}
			}
		}
	}

	val graph = Graph(vertices, edges)
	val map = graph.bfs(vertices[0])
	var count = 0
	var curr: Vertex<Int>? = vertices.last()
	while (curr != null && curr != vertices[0]) {
//		println(curr)
		count++
		curr = map[curr]
	}
	return count
}