package graph.variation

import graph.core.Edge
import graph.core.Graph
import graph.core.Vertex
import graph.core.tarjan
import util.*

// describe a polynomial time algorithm for 2sat:
// given a boolean formula B in 2cnf (conjunctive normal form)
// determine if it is satisfiable
// note that 3sat is np-complete, but not 2sat

// here we will use a graph algorithm (scc, strongly connected component)
// to solve this problem (that's why we are in the graph package)
fun OneArray<Tuple2<String, String>>.twoSat(): Boolean {
	val literals = HashSet<String>()
	forEach { (v1, v2) ->
		if (v1[0] == '!') {
			literals.add(v1[1..1])
		} else {
			literals.add(v1[0..0])
		}

		if (v2[0] == '!') {
			literals.add(v2[1..1])
		} else {
			literals.add(v2[0..0])
		}
	}

	val vertices = HashSet<Vertex<String>>()
	literals.forEach {
		vertices.add(Vertex((it)))
		vertices.add(Vertex("!$it"))
	}

	val edges = HashSet<Edge<String>>()
	forEach { (v1, v2) ->
		if (v1[0] == '!') {
			edges.add(Edge(Vertex(v1[1..1]), Vertex(v2), true))
		} else {
			edges.add(Edge(Vertex("!$v1"), Vertex(v2), true))
		}

		if (v2[0] == '!') {
			edges.add(Edge(Vertex(v2[1..1]), Vertex(v1), true))
		} else {
			edges.add(Edge(Vertex("!$v2"), Vertex(v1), true))
		}
	}

	val graph = Graph(vertices, edges)
	val scc = graph.tarjan(false)
//	println(scc.vertices)

	return scc.vertices.none {
		val component = it.data
		return@none component.any {
			val literal = it.data
			if (literal[0] == '!') {
				component.contains(Vertex(literal[1..1]))
			} else {
				component.contains(Vertex("!$literal"))
			}
		}
	}
	// polynomial time for building the graph, building the scc (meta graph),
	// and checking if !x and x are contained in the same component
	// references: https://www.geeksforgeeks.org/2-satisfiability-2-sat-problem
}

fun main(args: Array<String>) {
	// (!x | y) & (x | !z) & (w & !y)
	// this is satisfiable: ex. x = F, y = T, z = F, w = T
	// one clause per tuple, assuming literals are single characters
	val B = oneArrayOf(
			"!x" tu "y",
			"x" tu "!z",
			"w" tu "!y")
	println(B.twoSat())
}