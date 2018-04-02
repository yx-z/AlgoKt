package graph.app

import graph.abstract.ComparableVertex
import graph.abstract.Edge
import graph.abstract.Graph
import util.INF
import util.Tuple2
import util.tu
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

// given a graph, with each vertex associated with an int
// you start from s and want to reach t in a path : # of alternations in the
// path is as small as possible
// an alternation happens in u -> v -> w if the value of v is greater/less
// than both that of u and that of v

// assume a solution always exists
// report this path
fun Graph<Int>.minAltPath(s: ComparableVertex<Int>,
                          t: ComparableVertex<Int>,
                          checkIdentity: Boolean = true)
		: List<ComparableVertex<Int>> {
	val newGraph = toDirectedGraph()

	val (upCount, upPath) = newGraph.minUpPath(s, t, checkIdentity)
	upPath.add(0, s)
	val (downCount, downPath) = newGraph.minDownPath(s, t, checkIdentity)
	downPath.add(0, s)

	return if (upCount < downCount) upPath else downPath
}

fun Graph<Int>.minUpPath(s: ComparableVertex<Int>,
                         t: ComparableVertex<Int>,
                         checkIdentity: Boolean = true,
                         marked: HashMap<ComparableVertex<Int>, Boolean> = HashMap())
		: Tuple2<Int, MutableList<ComparableVertex<Int>>> {
	marked[s] = true
	if ((checkIdentity && s === t) || (!checkIdentity && s == t)) {
		return 0 tu mutableListOf()
	}

	var minCount = INF
	val minPath: MutableList<ComparableVertex<Int>> = ArrayList(vertices.size)
	getEdgesFrom(s, checkIdentity).forEach {
		val e = it.vertex2 as ComparableVertex<Int>
		if (!marked.containsKey(e) || marked[e] == false) {
			if (e.data > s.data) {
				val (upCount, upPath) = minUpPath(e, t, checkIdentity, marked)
				if (upCount < minCount) {
					minCount = upCount
					minPath.clear()
					minPath.addAll(upPath)
					minPath.add(0, e)
				}
			} else {
				val (downCount, downPath) = minDownPath(e, t, checkIdentity, marked)
				if (downCount + 1 < minCount) {
					minCount = downCount + 1
					minPath.clear()
					minPath.addAll(downPath)
					minPath.add(0, e)
				}
			}
			marked[e] = false
		}
	}

	return minCount tu minPath
}

fun Graph<Int>.minDownPath(s: ComparableVertex<Int>,
                           t: ComparableVertex<Int>,
                           checkIdentity: Boolean = true,
                           marked: HashMap<ComparableVertex<Int>, Boolean> = HashMap())
		: Tuple2<Int, MutableList<ComparableVertex<Int>>> {
	marked[s] = true
	if ((checkIdentity && s === t) || (!checkIdentity && s == t)) {
		return 0 tu mutableListOf()
	}

	var minCount = INF
	val minPath: MutableList<ComparableVertex<Int>> = ArrayList(vertices.size)
	getEdgesFrom(s, checkIdentity).forEach {
		val e = it.vertex2 as ComparableVertex<Int>
		if (!marked.containsKey(e) || marked[e] == false) {
			if (e.data > s.data) {
				val (upCount, upPath) = minUpPath(e, t, checkIdentity, marked)
				if (upCount + 1 < minCount) {
					minCount = upCount + 1
					minPath.clear()
					minPath.addAll(upPath)
					minPath.add(0, e)
				}
			} else {
				val (downCount, downPath) = minDownPath(e, t, checkIdentity, marked)
				if (downCount < minCount) {
					minCount = downCount
					minPath.clear()
					minPath.addAll(downPath)
					minPath.add(0, e)
				}
			}
			marked[e] = false
		}
	}

	return minCount tu minPath
}

// transform a weighted undirected graph to a directed graph
fun <V> Graph<V>.toDirectedGraph(): Graph<V> {
	val newEdges = edges.map { edge ->
		val (s, e, d) = edge
		if (d) {
			listOf(edge)
		} else {
			listOf(Edge(s, e, true), Edge(e, s, true))
		}
	}.flatten()
	return Graph(vertices, newEdges)
}

fun main(args: Array<String>) {
	val vertices = setOf(
			ComparableVertex(5),
			ComparableVertex(2),
			ComparableVertex(7),
			ComparableVertex(3),
			ComparableVertex(10))
	val edges = setOf(
			Edge(ComparableVertex(5), ComparableVertex(2)),
			Edge(ComparableVertex(5), ComparableVertex(7)),
			Edge(ComparableVertex(7), ComparableVertex(3)),
			Edge(ComparableVertex(2), ComparableVertex(3)),
			Edge(ComparableVertex(2), ComparableVertex(10)),
			Edge(ComparableVertex(3), ComparableVertex(10)))
	val graph = Graph(vertices, edges)
	println(graph.minAltPath(ComparableVertex(5), ComparableVertex(10), false))
}