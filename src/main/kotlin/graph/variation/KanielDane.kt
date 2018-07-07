package graph.variation

import graph.core.Edge
import graph.core.Graph
import graph.core.Vertex
import graph.core.whateverFirstSearch
import util.*

// given a 2d array G[1..n, 1..n] as a game board
// G[i, j] = 1 means that this is an obstacle and you cannot move on
// G[i, j] = 0 means that this is a road you you may pass through

// you start with two tokens at [i, j] and [p, q]
// your goal is to move either of the tokens to a target [u, v]

// in each turn, you can move one token in one direction, but the distance is
// "as far as possible", that is, move until it either hits the edge, hits the
// obstacle, or the other token

// find if a given game setting is solvable
fun OneArray<OneArray<Int>>.kanielDane(s1: Tuple2<Int, Int>,
                                       s2: Tuple2<Int, Int>,
                                       t: Tuple2<Int, Int>): Boolean {
	val G = this
	val n = size

	val obstacles = HashSet<Tuple2<Int, Int>>()
	for (i in indices) {
		for (j in indices) {
			if (G[i, j] == 1) {
				obstacles.add(i tu j)
			}
		}
	}

	val vertices = HashSet<Vertex<Tuple4<Int, Int, Int, Int>>>()
	for (i in indices) {
		for (j in indices) {
			for (p in indices) {
				for (q in indices) {
					if (G[i, j] == 0 && G[p, q] == 0 && (i != p || j != q)) {
						vertices.add(Vertex(i tu j tu p tu q))
					}
				}
			}
		}
	}

	val edges = HashSet<Edge<Tuple4<Int, Int, Int, Int>>>()
	val start = Vertex(s1.first tu s1.second tu s2.first tu s2.second)
	vertices.forEach {
		val (i, j, p, q) = it.data

		var jd = j
		while (jd <= n && !obstacles.contains(i tu jd) && (i tu jd != p tu q)) {
			jd++
		}
		jd--
		if (jd != j) {
			edges.add(Edge(it, Vertex(i tu jd - 1 tu p tu q), true))
		}
		var ju = j
		while (ju >= 1 && !obstacles.contains(i tu ju) && (i tu ju != p tu q)) {
			ju--
		}
		ju++
		if (ju != j) {
			edges.add(Edge(it, Vertex(i tu ju tu p tu q), true))
		}
		var ir = i
		while (ir <= n && !obstacles.contains(ir tu j) && (ir tu j != p tu q)) {
			ir++
		}
		ir--
		if (ir != i) {
			edges.add(Edge(it, Vertex(ir tu j tu p tu q), true))
		}
		var il = i
		while (il >= 1 && !obstacles.contains(il tu j) && (il tu j != p tu q)) {
			il--
		}
		il++
		if (il != i) {
			edges.add(Edge(it, Vertex(il tu j tu p tu q), true))
		}

		var qd = q
		while (qd <= n && !obstacles.contains(p tu qd) && (i tu j != p tu qd)) {
			qd++
		}
		qd--
		if (qd != q) {
			edges.add(Edge(it, Vertex(i tu j tu p tu qd), true))
		}
		var qu = q
		while (qu >= 1 && !obstacles.contains(p tu qu) && (i tu j != p tu qu)) {
			qu--
		}
		qu++
		if (qu != q) {
			edges.add(Edge(it, Vertex(i tu j tu p tu qu), true))
		}
		var pr = p
		while (pr <= n && !obstacles.contains(pr tu q) && (i tu j != pr tu q)) {
			pr++
		}
		pr--
		if (pr != p) {
			edges.add(Edge(it, Vertex(i tu j tu pr tu q), true))
		}
		var pl = p
		while (pl >= 1 && !obstacles.contains(pl tu q) && (i tu j != pl tu q)) {
			pl--
		}
		pl++
		if (pl != p) {
			edges.add(Edge(it, Vertex(i tu j tu pl tu q), true))
		}
	}

	val graph = Graph(vertices, edges)

	val solvable = graph.whateverFirstSearch(start, false)
	return solvable.any {
		val (i, j, p, q) = it.data
		return@any (i tu j == t) || (p tu q == t)
	}
	// O(n^4)
}

fun main(args: Array<String>) {
	val G = oneArrayOf(
			oneArrayOf(0, 0, 0, 0, 0),
			oneArrayOf(0, 0, 0, 0, 0),
			oneArrayOf(0, 1, 0, 0, 0),
			oneArrayOf(0, 0, 0, 0, 0),
			oneArrayOf(0, 0, 0, 1, 0))
	println(G.kanielDane(1 tu 2, 2 tu 5, 3 tu 3))
}