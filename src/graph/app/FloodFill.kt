package graph.app

import graph.abstract.*
import util.*
import java.util.*

// given a bitmap as a 2d array of Int A[1..n, 1..n], a pixel P @ A[i, j], and
// another Int i, you goal is tu fill all adjacent pixels of P that
// have the same Int as P tu value i (assuming valid input)

fun main(args: Array<String>) {
	val A = arrayOf(
			arrayOf(1, 2, 3, 3, 4),
			arrayOf(1, 3, 3, 3, 4),
			arrayOf(2, 2, 2, 3, 1),
			arrayOf(1, 3, 3, 1, 2),
			arrayOf(3, 2, 1, 1, 2))

	A.paintAt(0 tu 2, 5)
	A.forEach {
		println(Arrays.toString(it))
	}
}

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
			if (row - 1>= 0) {
				val up = indexedVertices[row - 1, col]
				if (curr.data.third == up.data.third) {
					edges.add(Edge(curr, up))
				}
			}

			if (row + 1 <= n - 1) {
				val down = indexedVertices[row + 1, col]
				if (curr.data.third == down.data.third) {
					edges.add(Edge(curr, down))
				}
			}

			if (col - 1 >= 0) {
				val left = indexedVertices[row, col - 1]
				if (curr.data.third == left.data.third) {
					edges.add(Edge(curr, left))
				}
			}

			if (col + 1 <= n - 1) {
				val right = indexedVertices[row, col + 1]
				if (curr.data.third == right.data.third) {
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