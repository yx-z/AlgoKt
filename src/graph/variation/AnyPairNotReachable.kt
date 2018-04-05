package graph.variation

import util.*
import graph.core.*

// Given a directed graph G, describe a linear time algorithm that computes
// a pair of vertices u, v : there is no path from u to v.
// If no such pair exists, then the algorithm should output null

fun <V> Graph<V>.notReachable(checkIdentity: Boolean = true):
		Tuple2<Vertex<V>, Vertex<V>>? {
	// pick any two vertices in distinct SCCs!
	val scc = kosaraju(checkIdentity)
	return if (scc.vertices.size <= 1) {
		null
	} else {
		val v1 = scc.vertices.toList().first().data.toList().first()
		val v2 = scc.vertices.toList().last().data.toList().first()
		if (whateverFirstSearch(v1, checkIdentity).contains(v2)) {
			// v1 can reach v2, so v2 cannot reach v1
			v2 tu v1
		} else {
			// v1 is exactly the u we want that cannot reach v (= v2)
			v1 tu v2
		}
	}
}

fun main(args: Array<String>) {
	val V = (0..2).map { Vertex(it) }
	val E = setOf(Edge(V[0], V[1], true), Edge(V[0], V[2], true))
	val G = Graph(V, E)
	println(G.notReachable())
}
