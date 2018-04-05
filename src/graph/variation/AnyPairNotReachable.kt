package graph.variation

import util.*
import graph.core.*

// Given a directed graph G, describe a linear time algorithm that computes
// a pair of vertices u, v : there is no path from u to v.
// If no such pair exists, then the algorithm should output null

fun <V> Graph<V>.notReachable(): Tuple2<Vertex<V>, Vertex<V>>? {
	TODO()
}

fun main(args: Array<String>) {
	val V = (0..2).map { Vertex(it) }
	val E = setOf(Edge(V[0], V[1], true), Edge(V[0], V[2], true))
	val G = Graph(V, E)
	println(G.notReachable())
}
