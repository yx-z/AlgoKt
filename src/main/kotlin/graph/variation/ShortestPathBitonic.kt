package graph.variation

import graph.core.Vertex
import graph.core.WeightedEdge
import graph.core.WeightedGraph
import util.INF

// similar to ShortestPathInc,
// find a shortest path that values in vertices first increases then decreases
// note that such path can still be monotonic (only decreases/increases)
// which is the case when the changing vertex is at the start/end
fun WeightedGraph<Int, Int>.shortestPathBitonic(s: Vertex<Int>,
                                                t: Vertex<Int>,
                                                checkIdentity: Boolean = true): Int {
	// idea is similar to longest bitonic sequence
	val inc = shortestPathInc(s, checkIdentity)
	val dec = shortestPathInc(t, checkIdentity)
	return vertices.map { inc[it]!! + dec[it]!! }.min() ?: INF
}

fun main(args: Array<String>) {
	val vertices = (1..3).map { Vertex(it) }
	val edges = setOf(
			WeightedEdge(Vertex(1), Vertex(3), weight = 1),
			WeightedEdge(Vertex(3), Vertex(2), weight = 1))
	val graph = WeightedGraph(vertices, edges)
	println(graph.shortestPathBitonic(Vertex(1), Vertex(2), false))
}