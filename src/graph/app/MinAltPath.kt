package graph.app

import graph.abstract.ComparableVertex
import graph.abstract.Edge
import graph.abstract.Graph
import util.INF
import util.Tuple2
import util.tu
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.List
import kotlin.collections.flatten
import kotlin.collections.forEach
import kotlin.collections.hashMapOf
import kotlin.collections.listOf
import kotlin.collections.map
import kotlin.collections.set
import kotlin.collections.setOf

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

	val up = newGraph.minUpPath(s, t, checkIdentity)
	println(up)
	val down = newGraph.minDownPath(s, t, checkIdentity)
	println(down)

	val path = ArrayList<ComparableVertex<Int>>()
	var curr = t
	if (up.first < down.first) {
		while ((checkIdentity && curr !== s) || (!checkIdentity && curr != s)) {
			path.add(0, curr)
			curr = up.second[curr]!!
		}
	} else {
		while ((checkIdentity && curr !== s) || (!checkIdentity && curr != s)) {
			path.add(0, curr)
			curr = down.second[curr]!!
		}
	}
	path.add(0, s)

	return path
}

// assume a directed graph
fun Graph<Int>.minUpPath(s: ComparableVertex<Int>,
                         t: ComparableVertex<Int>,
                         checkIdentity: Boolean = true,
                         marked: HashMap<ComparableVertex<Int>, Boolean> = HashMap())
		: Tuple2<Int, HashMap<ComparableVertex<Int>, ComparableVertex<Int>?>> {
	marked[s] = true
	if ((checkIdentity && s === t) || (!checkIdentity && s == t)) {
		return 0 tu hashMapOf(t to s as ComparableVertex<Int>?)
	}

	var min = INF
	var map: HashMap<ComparableVertex<Int>, ComparableVertex<Int>?>? = null
	getEdgesFrom(s, checkIdentity).forEach {
		val e = it.vertex2 as ComparableVertex<Int>
		if (!marked.containsKey(e) || marked[e] == false) {
			marked[e] = true
			if (e.data > s.data) {
				val (upCount, upMap) = minUpPath(e, t, checkIdentity, marked)
				if (upCount < min) {
					min = upCount
					upMap[e] = s
					map = upMap
				}
			} else {
				val (downCount, downMap) = minDownPath(e, t, checkIdentity, marked)
				if (downCount + 1 < min) {
					min = downCount + 1
					downMap[e] = s
					map = downMap
				}
			}
		}
		marked[e] = false
	}

	return min tu (map ?: hashMapOf())
}

fun Graph<Int>.minDownPath(s: ComparableVertex<Int>,
                           t: ComparableVertex<Int>,
                           checkIdentity: Boolean = true,
                           marked: HashMap<ComparableVertex<Int>, Boolean> = HashMap())
		: Tuple2<Int, HashMap<ComparableVertex<Int>, ComparableVertex<Int>?>> {
	marked[s] = true
	if ((checkIdentity && s === t) || (!checkIdentity && s == t)) {
		return 0 tu hashMapOf(t to s as ComparableVertex<Int>?)
	}

	var min = INF
	var map: HashMap<ComparableVertex<Int>, ComparableVertex<Int>?>? = null
	getEdgesFrom(s, checkIdentity).forEach {
		val e = it.vertex2 as ComparableVertex<Int>
		if (!marked.containsKey(e) || marked[e] == false) {
			marked[e] = true
			if (e.data < s.data) {
				val (downCount, downMap) = minDownPath(e, t, checkIdentity, marked)
				if (downCount < min) {
					min = downCount
					downMap[e] = s
					map = downMap
				}
			} else {
				val (upCount, upMap) = minUpPath(e, t, checkIdentity, marked)
				if (upCount + 1 < min) {
					min = upCount + 1
					upMap[e] = s
					map = upMap
				}
			}
		}
		marked[e] = false
	}

	return min tu (map ?: hashMapOf())
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