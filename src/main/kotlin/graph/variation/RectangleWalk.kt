package graph.variation

import graph.core.Edge
import graph.core.Graph
import graph.core.Vertex
import graph.core.bfs
import util.*

// given an n by n bitmap of 0s and 1s M,

// you start as a 1 by 1 square in the top left and want to reach the 1 by 1
// square in the bottom right with min # of steps

// in each step you can expand or shrink to an m by n rect, provided that
// all pixels in this rect are all 0s and such square is either contained in
// your current rect (in this case, you have done a shrink), or your current rect
// is contained in the new rect (in this case, you have done an expansion)

// find min # of steps to solve the puzzle (assuming that it must be solvable)

fun main(args: Array<String>) {
	// OCR magic below
	val M = oneArrayOf(
			oneArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
			oneArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0),
			oneArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0),
			oneArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0),
			oneArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1),
			oneArrayOf(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0),
			oneArrayOf(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0),
			oneArrayOf(0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
			oneArrayOf(0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1),
			oneArrayOf(0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0),
			oneArrayOf(0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0),
			oneArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
			oneArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
			oneArrayOf(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0),
			oneArrayOf(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
			oneArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
			oneArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0),
			oneArrayOf(0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0),
			oneArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0),
			oneArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1),
			oneArrayOf(0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0),
			oneArrayOf(0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0),
			oneArrayOf(0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0),
			oneArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0))

	println(M.minSteps()) // should be 18 for the puzzle above
}

// we can prove that this game is nothing more than transforming 1 by 1 squares
// from (i, j) to (p, q) and each transformation is a two-step operation, i.e.
// expand (to some bigger rect) and then shrink (to another 1 by 1 square)

// so our strategy is constructing a graph G = (V, E)
// vertices V is the set of white pixels in M
// and there exists an edge E from v1 to v2 (v1, v2 in V) iff. you can do a two-
// step operation described above

// and finding such edges requires us to find a table dp[1..n, 1..n, 1..n, 1..n] :
// dp[i, j, p, q] is a rect with all 0s and having corners @ (i, j) as well as (p, q)

// finally, we want the shortest path in this graph from (1, 1) to (n, n)
fun OneArray<OneArray<Int>>.minSteps(): Int {
	// follow naming convention in the problem (not Java/Kotlin's)
	val M = this
	val n = size

	// define vertices
	val vertices = HashSet<Vertex<Tuple2<Int, Int>>>()
	// space complexity: O(n^2)
	for (i in 1..n) {
		for (j in 1..n) {
			if (M[i, j] == 0) {
				val v = Vertex(i tu j)
				vertices.add(v)
			}
		}
	}
	// time complexity: O(n^2)

	// define edges
	val edges = HashSet<Edge<Tuple2<Int, Int>>>()
	// dp[t, b, l, r]: true if M[t..b, l..r] is white, false o/w
	// memoization structure: 4d array: dp[1..n, 1..n, 1..n, 1..n]
	val dp = OneArray(n) { OneArray(n) { OneArray(n) { OneArray(n) { false } } } }
	// space complexity: O(n^4)

	for (t in n downTo 1) {
		for (l in n downTo 1) {
			for (b in 1..n) {
				for (r in 1..n) {
					val curr = when {
						t == b && l == r -> M[t, l] == 0
						t == b && l < r -> M[t, l] == 0 && dp[t, t, l + 1, r]
						else -> dp[t, t, l, r] && dp[t + 1, b, l, r]
					}
					dp[t, b, l, r] = curr
					if (curr) {
						edges.add(Edge(Vertex(t tu l), Vertex(b tu r)))
						edges.add(Edge(Vertex(b tu l), Vertex(t tu r)))
					}
				}
			}
		}
	}
	// time complexity: O(n^4)

	// define graph
	val graph = Graph(vertices, edges)
	// run BFS to find the shortest path from start
	val map = graph.bfs(Vertex(0 tu 0), false)
	// time complexity: O(n^4)

	// count # of edges from end to start
	var end = Vertex(n tu n)
	var count = 0
	while (end !== Vertex(0 tu 0)) {
		end = map[end]!!
		count++
	}
	// tricky part: remember that each edge actually represents two steps!
	return count * 2
}
