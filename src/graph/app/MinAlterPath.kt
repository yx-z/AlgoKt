package graph.app

import graph.abstract.ComparableVertex
import graph.abstract.WeightedEdge
import graph.abstract.WeightedGraph
import util.Tuple2
import util.tu

// given a weighted graph, with each vertex associated with an int
// you start from s and want to reach t in a path : # of alternations in the
// path is as small as possible
// an alternation happens in u -> v -> w if the value of v is greater/less
// than both that of u and that of v
// note that the path might not be shortest in total weight

// report this path
fun WeightedGraph<Int, Int>.minAlterPath(s: ComparableVertex<Int>,
                                         t: ComparableVertex<Int>,
                                         checkIdentity: Boolean = true)
		: Map<ComparableVertex<Int>, ComparableVertex<Int>?> {
	val newGraph = toDirectedGraph()
	val (upCount, upPath) = newGraph.minUpPath(s, t, checkIdentity)
	val (downCount, downPath) = newGraph.minDownPath(s, t, checkIdentity)

	return if (upCount < downCount) {
		upPath
	} else {
		downPath
	}
}

// assume a directed graph
fun WeightedGraph<Int, Int>.minUpPath(s: ComparableVertex<Int>,
                                      t: ComparableVertex<Int>,
                                      checkIdentity: Boolean = true)
		: Tuple2<Int, Map<ComparableVertex<Int>, ComparableVertex<Int>?>> {
	val parent = HashMap<ComparableVertex<Int>, ComparableVertex<Int>?>()
	parent[s] = s
	if ((checkIdentity && s === t) || (!checkIdentity && s == t)) {
		return 0 tu parent
	}

	TODO()
}

fun WeightedGraph<Int, Int>.minDownPath(s: ComparableVertex<Int>,
                                        t: ComparableVertex<Int>,
                                        checkIdentity: Boolean = true)
		: Tuple2<Int, Map<ComparableVertex<Int>, ComparableVertex<Int>?>> {
	TODO()
}

// transform a weighted undirected graph to a directed graph
fun <V, E> WeightedGraph<V, E>.toDirectedGraph(): WeightedGraph<V, E> {
	val newEdges = weightedEdges.map { edge ->
		val (s, e, d, w) = edge
		if (d) {
			listOf(edge)
		} else {
			listOf(WeightedEdge(s, e, true, w), WeightedEdge(e, s, true, w))
		}
	}.flatten()
	return WeightedGraph(vertices, newEdges)
}

fun main(args: Array<String>) {

}