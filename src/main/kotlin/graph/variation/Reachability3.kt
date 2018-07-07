package graph.variation

import graph.core.Edge
import graph.core.Graph
import graph.core.Vertex
import graph.core.whateverFirstSearch
import util.Tuple3
import util.tu

// given a connected undirected graph G with three tokens at vertex a, b, and c
// in each turn you must move ALL three tokens to an adjacent vertex
// at the end of each turn, three tokens must lie on three DIFFERENT vertices
// your goal is to move them to x, y, z with no restriction on correspondence

// your algorithm should return whether such puzzle is solvable

fun main(args: Array<String>) {
	val vertices = (1..9).map { Vertex(it) }
	val edges = setOf(
			Edge(vertices[0], vertices[1]),
			Edge(vertices[1], vertices[2]),
			Edge(vertices[0], vertices[3]),
			Edge(vertices[1], vertices[4]),
			Edge(vertices[3], vertices[4]),
			Edge(vertices[4], vertices[5]),
			Edge(vertices[4], vertices[6]),
			Edge(vertices[4], vertices[7]),
			Edge(vertices[5], vertices[8]))
	val G = Graph(vertices, edges)
	println(G.reachability3(
			vertices[0],
			vertices[1],
			vertices[2],
			vertices[6],
			vertices[7],
			vertices[8]))
}

fun Graph<Int>.reachability3(a: Vertex<Int>,
                             b: Vertex<Int>,
                             c: Vertex<Int>,
                             x: Vertex<Int>,
                             y: Vertex<Int>,
                             z: Vertex<Int>): Boolean {
	// our strategy is that transforming G = (V, E) into a new graph G' = (V', E')
	// V' = { (v1, v2, v3) : v1, v2, v3 in V }
	// E' = { (v1, v2, v3) -> (u1, u2, u3) : v1 -> u1, v2 -> u2, v3 -> u3 in E
	//        , v1 != v2 != v3 and u1 != u2 != u3 }
	// then run G'.whateverFirstSearch(a, b, c) and check if (x, y, z) is in the
	// spanning tree of G' starting @ (a, b, c)

	val newVertices = HashSet<Vertex<Tuple3<Vertex<Int>, Vertex<Int>, Vertex<Int>>>>()
	vertices.forEach { v1 ->
		vertices.forEach { v2 ->
			vertices.forEach { v3 ->
				newVertices.add(Vertex(v1 tu v2 tu v3))
			}
		}
	}

	val newEdges = HashSet<Edge<Tuple3<Vertex<Int>, Vertex<Int>, Vertex<Int>>>>()
	newVertices.forEach {
		val (v1, v2, v3) = it.data
		if (v1 !== v2 && v2 !== v3 && v1 !== v3) {
			getEdgesOf(v1).forEach { (s1, e1) ->
				val u1 = if (s1 === v1) e1 else s1
				getEdgesOf(v2).forEach { (s2, e2) ->
					val u2 = if (s2 === v2) e2 else s2
					getEdgesOf(v3).forEach { (s3, e3) ->
						val u3 = if (s3 === v3) e3 else s3

						if (u1 !== u2 && u2 !== u3 && u1 !== u3) {
							newEdges.add(Edge(Vertex(v1 tu v2 tu v3), Vertex(u1 tu u2 tu u3)))
						}
					}
				}
			}
		}
	}

	val newGraph = Graph(newVertices, newEdges)
	val spanningTree = newGraph.whateverFirstSearch(Vertex(a tu b tu c))
	return spanningTree.contains(Vertex(x tu y tu z))
}