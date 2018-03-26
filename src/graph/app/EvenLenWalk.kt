package graph.app

import graph.abstract.Edge
import graph.abstract.Graph
import graph.abstract.Vertex
import graph.abstract.bfs
import util.Tuple2
import util.tu

// given an undirected graph G, find a shortest even length walk from s to e
// or report that such walk does not exist
fun main(args: Array<String>) {
	val vertices = (0 until 9).map { Vertex(it) }
	val edges = setOf(
			Edge(vertices[0], vertices[1]),
			Edge(vertices[1], vertices[2]),
			Edge(vertices[1], vertices[5]),
			Edge(vertices[2], vertices[5]),
			Edge(vertices[3], vertices[4]),
			Edge(vertices[4], vertices[7]),
			Edge(vertices[5], vertices[7]),
			Edge(vertices[6], vertices[8]),
			Edge(vertices[7], vertices[6]))
	val graph = Graph(vertices, edges)
	println(graph.shortestEvenLen(vertices[0], vertices[8]))
}

fun Graph<Int>.shortestEvenLen(s: Vertex<Int>, e: Vertex<Int>): List<Vertex<Int>> {
	// given G = (V, E), construct a new graph G' = (V', E') :
	// V' = { (v, i) : v in V, i in { true, false } indicating whether the walk
	//         is either odd, i.e. true or even, i.e. false o/w }
	// E' = { (v, i) -> (u, i') : v -> u in E, i' = !i }
	// now we want to find the minimum path from (s, false) to (e, false)
	val newVertices = vertices.map { listOf(Vertex(it tu false), Vertex(it tu true)) }
			.flatten()
	val newEdges = HashSet<Edge<Tuple2<Vertex<Int>, Boolean>>>()
	vertices.forEach { v ->
		getEdgesOf(v).forEach { (es, ee) ->
			val u = if (es === v) ee else es
			newEdges.add(Edge(Vertex(v tu false), Vertex(u tu true)))
			newEdges.add(Edge(Vertex(v tu true), Vertex(u tu false)))
		}
	}

	val g = Graph(newVertices, newEdges)

	val start = Vertex(s tu false)
	val map = g.bfs(start)
	val list = ArrayList<Vertex<Int>>()
	var curr: Vertex<Tuple2<Vertex<Int>, Boolean>>? = Vertex(e tu false)
	while (curr != null && curr != start) {
		list.add(curr!!.data.first)
		curr = map[curr]!!
	}
	return list.reversed()
}