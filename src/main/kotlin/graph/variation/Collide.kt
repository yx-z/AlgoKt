package graph.variation

import graph.core.Edge
import graph.core.Graph
import graph.core.Vertex
import graph.core.bfs
import util.Tuple2
import util.tu

// given a connected, undirected graph G = (V, E), suppose there are k tokens
// at some starting vertex and every step, ALL k tokens must move
// find if it is possible for these 42 vertices can collide on the same vertex
fun <V> Graph<V>.collide(vararg vs: Vertex<V> /* an array of vertices of size k */)
		: Boolean {
	val V = vertices
			.map { listOf(Vertex(it tu false), Vertex(it tu true)) }
			.flatten()
	val E = HashSet<Edge<Tuple2<Vertex<V>, Boolean>>>()
	edges.forEach { (u, v) ->
		E.add(Edge(Vertex(u tu false), Vertex(v tu true)))
		E.add(Edge(Vertex(u tu true), Vertex(v tu false)))
	}
	val G = Graph(V, E)
//	println(G)

	val wfs = vs.map { G.bfs(Vertex(it tu false), false) }
	// find a vertex v :
	// either: ALL vertices in vs can reach v in even length walks
	// or: ALL vertices in vs can reach v in odd length walks
	return vertices.any { v ->
		wfs.all { map -> map[Vertex(v tu false)] != null } ||
				wfs.all { map -> map[Vertex(v tu true)] != null }
	}
	// O(V + E)
}

fun main(args: Array<String>) {
	val V = (0..2).map { Vertex(it) }
	val E = (0 until 2).map { Edge(V[it], V[it + 1]) }
	val G = Graph(V, E)
	println(G.collide(V[0], V[2]))
}