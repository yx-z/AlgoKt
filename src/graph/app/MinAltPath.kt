package graph.app

import graph.abstract.ComparableVertex
import graph.abstract.WeightedEdge
import graph.abstract.WeightedGraph
import util.INF
import util.min

// given a weighted graph, with each vertex associated with an int
// you start from s and want to reach t in a path : # of alternations in the
// path is as small as possible
// an alternation happens in u -> v -> w if the value of v is greater/less
// than both that of u and that of v
// note that the path might not be shortest in total weight

// report this path
fun WeightedGraph<Int, Int>.minAltPath(s: ComparableVertex<Int>,
                                       t: ComparableVertex<Int>,
                                       checkIdentity: Boolean = true): Int {
	val newGraph = toDirectedGraph()

	val upCount = newGraph.minUpPath(s, t, checkIdentity)
	val downCount = newGraph.minDownPath(s, t, checkIdentity)

	return min(upCount, downCount)
}

// assume a directed graph
fun WeightedGraph<Int, Int>.minUpPath(s: ComparableVertex<Int>,
                                      t: ComparableVertex<Int>,
                                      checkIdentity: Boolean = true,
                                      marked: HashMap<ComparableVertex<Int>, Boolean> = HashMap())
		: Int {
	marked[s] = true
	if ((checkIdentity && s === t) || (!checkIdentity && s == t)) {
		return 0
	}

	var min = INF
	getEdgesFrom(s).forEach { (_, e, _, _) ->
		if (e.data > s.data) {
			min = min(min, minDownPath(e as ComparableVertex<Int>, t, checkIdentity, marked))
			marked[e] = false
		}
	}

	return min + 1
}

fun WeightedGraph<Int, Int>.minDownPath(s: ComparableVertex<Int>,
                                        t: ComparableVertex<Int>,
                                        checkIdentity: Boolean = true,
                                        marked: HashMap<ComparableVertex<Int>, Boolean> = HashMap())
		: Int {
	marked[s] = true
	if ((checkIdentity && s === t) || (!checkIdentity && s == t)) {
		return 0
	}

	var min = INF
	getEdgesFrom(s).forEach { (_, e, _, _) ->
		if (e.data > s.data) {
			min = min(min, minUpPath(e as ComparableVertex<Int>, t, checkIdentity, marked))
			marked[e] = false
		}
	}

	return min + 1
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
	val vertices = (0..2).map { ComparableVertex(it) }
	val edges = setOf(
			WeightedEdge(vertices[0], vertices[1], true, 10),
			WeightedEdge(vertices[1], vertices[2], true, 2))
	val graph = WeightedGraph(vertices, edges)
	println(graph.minAltPath(vertices[0], vertices[2]))
}