package graph.variation

import graph.core.*
import util.*
import java.util.*

// given a bitmap as a 2d array of Int A[1..n, 1..n], a pixel P @ A[i, j], and
// another Int i, you goal is to fill all adjacent pixels of P that
// have the same Int as P to value i (assuming valid input)

fun main(args: Array<String>) {
	val A = arrayOf(
			arrayOf(1, 2, 3, 3, 4),
			arrayOf(1, 3, 3, 3, 4),
			arrayOf(2, 2, 2, 3, 1),
			arrayOf(1, 3, 3, 1, 2),
			arrayOf(3, 2, 1, 1, 2))

	A.paintAt(0 tu 2, 5)
	A.prettyPrintLines()

	println()

	A.paintAt(2, 2, 7)
	A.prettyPrintLines()
}

// well the following is actually an example of a "bad use" of graph
// we convert 2d array to a graph, run some graph algorithm, and convert
// it back to 2d array... yuck!

// the only good thing is that we might be able to call core methods
// without modification, but it really depends on the specific question

// what we really should do is to perform the core graph algorithm directly
// onto the 2d array as an implicit graph
fun Array<Array<Int>>.paintAt(P: Tuple2<Int, Int>, i: Int) {
	val A = this
	val n = size

	val indexedVertices = indices.map { row ->
		A[row].indices.map { col ->
			Vertex(row tu col tu A[row, col])
		}
	}
	val edges = HashSet<Edge<Tuple3<Int, Int, Int>>>()
	indices.forEach { row ->
		A[row].indices.forEach { col ->
			val curr = indexedVertices[row, col]
			val num = curr.data.third
			if (row - 1 >= 0) {
				val up = indexedVertices[row - 1, col]
				if (num == up.data.third) {
					edges.add(Edge(curr, up))
				}
			}

			if (row + 1 <= n - 1) {
				val down = indexedVertices[row + 1, col]
				if (num == down.data.third) {
					edges.add(Edge(curr, down))
				}
			}

			if (col - 1 >= 0) {
				val left = indexedVertices[row, col - 1]
				if (num == left.data.third) {
					edges.add(Edge(curr, left))
				}
			}

			if (col + 1 <= n - 1) {
				val right = indexedVertices[row, col + 1]
				if (num == right.data.third) {
					edges.add(Edge(curr, right))
				}
			}
		}
	}

	val graph = Graph(indexedVertices.flatten(), edges)
	val connectVertices = graph.whateverFirstSearch(indexedVertices[P.first, P.second])

	connectVertices.forEach {
		val (row, col, _) = it.data
		A[row, col] = i
	}
}

// implicit graph used here
// i just played around with parameter types for basically the same thing
// because i am bad at coming up with another method name...
fun Array<Array<Int>>.paintAt(r: Int,
                              c: Int,
                              num: Int,
                              visited: Array<Array<Boolean>> = Array(size) { Array(size) { false } }
) {
	val A = this
	val n = size
	val i = A[r, c]

	// not only do we use an implicit graph
	// we also use recursion for an implicit stack and DFS
	if (r - 1 >= 0 && visited[r - 1, c].not()) {
		visited[r - 1, c] = true
		if (A[r - 1, c] == i) {
			paintAt(r - 1, c, num, visited)
		}
	}

	if (r + 1 <= n - 1 && visited[r + 1, c].not()) {
		visited[r + 1, c] = true
		if (A[r + 1, c] == i) {
			paintAt(r + 1, c, num, visited)
		}
	}

	if (c - 1 >= 0 && visited[r, c - 1].not()) {
		visited[r, c - 1] = true
		if (A[r, c - 1] == i) {
			paintAt(r, c - 1, num, visited)
		}
	}

	if (c + 1 <= n - 1 && visited[r, c + 1].not()) {
		visited[r, c + 1] = true
		if (A[r, c + 1] == i) {
			paintAt(r, c + 1, num, visited)
		}
	}

	visited[r, c] = true
	A[r, c] = num
}