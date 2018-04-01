package graph.app

import graph.abstract.ComparableVertex
import graph.abstract.WeightedEdge
import graph.abstract.WeightedGraph
import util.INF

// similar to ShortestPathInc,
// find a shortest path that values in vertices first increases then decreases
// note that such path can still be monotonic (only decreases/increases)
// which is the case when the changing vertex is at the start/end
fun WeightedGraph<Int, Int>.shortestPathBitonic(s: ComparableVertex<Int>,
                                                t: ComparableVertex<Int>,
                                                checkIdentity: Boolean = true): Int {
	// idea is similar to longest bitonic sequence
	val inc = shortestPathInc(s, checkIdentity)
	val dec = shortestPathInc(t, checkIdentity)
	return vertices.map { inc[it]!! + dec[it]!! }.min() ?: INF
}

fun main(args: Array<String>) {
	val vertices = (1..3).map { ComparableVertex(it) }
	val edges = setOf(
			WeightedEdge(ComparableVertex(1), ComparableVertex(3), data = 1),
			WeightedEdge(ComparableVertex(3), ComparableVertex(2), data = 1))
	val graph = WeightedGraph(vertices, edges)
	println(graph.shortestPathBitonic(ComparableVertex(1), ComparableVertex(2), false))
}