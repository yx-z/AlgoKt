package graph.app

import graph.abstract.ComparableVertex
import graph.abstract.Vertex
import graph.abstract.WeightedEdge
import graph.abstract.WeightedGraph
import util.Tuple2
import util.tu
import java.util.*
import kotlin.collections.HashMap

// given a looped binary tree - non-negatively weighted, directed graph,
// built from a binary tree by adding an edge from every leaf back to the root
// find a shortest path algorithm from s to t that is faster than Dijkstra's O(E log V)

typealias LoopedBinTree<V> = WeightedGraph<V, Int>

// report the length of such path
fun <V> LoopedBinTree<V>.shortestPath(s: Vertex<V>, t: Vertex<V>): Int {
	// our observation is that given such graph:
	// 1. there is only one path from parent to any children -> DFS
	// 2. there is a shortest path from child to leaf then to root -> DP strategy
	//    and there is one path from root to some parent of the child -> DFS

	val root = vertices.first { edges.filter { (_, v) -> v == it }.count() > 1 }

	// down[v]: distance from root -> v
	val down = HashMap<Vertex<V>, Int>()
	down[root] = 0
	// bfs version
	// you may get dfs with a stack instead
	val queue1: Queue<Vertex<V>> = LinkedList()
	queue1.add(root)
	while (queue1.isNotEmpty()) {
		val v = queue1.remove()
		getEdgesOf(v).forEach { (_, e, _, w) ->
			if (e !== root) {
				down[e] = w!! + down[v]!!
				queue1.add(e)
			}
		}
	}
	// O(V + E)

	// up[v]: distance from v to leaf to root
	val up = HashMap<Vertex<V>, Int>()
	up[root] = 0
	getEdgesOf(root).forEach { (_, v) ->
		getUp(root, v, up)
	}
	// O(V + E)

	// check if s is a parent of t
	val queue2: Queue<Tuple2<Vertex<V>, Int>> = LinkedList()
	queue2.add(s tu 0)
	while (queue2.isNotEmpty()) {
		val (v, d) = queue2.remove()
		// if s is a parent of t indeed, return the weight of their unique path
		if (v === t) {
			return d
		}

		getEdgesOf(v).forEach { (_, e, _, w) ->
			if (e !== root) {
				queue2.add(e tu (d + w!!))
			}
		}
	}
	// O(V + E)

	// s is NOT a parent of t -> shortest path from s to t is the shortest
	// path from s to leaf to root and then from root to t
	return up[s]!! + down[t]!!
}

fun <V> WeightedGraph<V, Int>.getUp(root: Vertex<V>,
                                    vertex: Vertex<V>,
                                    map: HashMap<Vertex<V>, Int>) {
	val edge = getEdgesOf(vertex).first()
	val isLeaf = edge.vertex2 == root
	if (isLeaf) {
		map[vertex] = edge.weight!!
	} else {
		getEdgesOf(vertex).forEach { (_, e) -> getUp(root, e, map) }
		map[vertex] = getEdgesOf(vertex)
				.map { (_, e, _, d) -> map[e]!! + d!! }
				.min()!!
	}
}

fun main(args: Array<String>) {
	val vertices = (0..8).map { ComparableVertex(it) }
	val edges = setOf(
			WeightedEdge(vertices[0], vertices[1], true, 5),
			WeightedEdge(vertices[0], vertices[2], true, 8),
			WeightedEdge(vertices[1], vertices[3], true, 17),
			WeightedEdge(vertices[1], vertices[4], true, 0),
			WeightedEdge(vertices[2], vertices[5], true, 1),
			WeightedEdge(vertices[3], vertices[6], true, 23),
			WeightedEdge(vertices[4], vertices[0], true, 16),
			WeightedEdge(vertices[5], vertices[7], true, 9),
			WeightedEdge(vertices[5], vertices[8], true, 14),
			WeightedEdge(vertices[6], vertices[0], true, 4),
			WeightedEdge(vertices[7], vertices[0], true, 7),
			WeightedEdge(vertices[8], vertices[0], true, 42))
	val graph = WeightedGraph(vertices, edges)

	// parent to child
	println(graph.shortestPath(vertices[0], vertices[5]))
	// child to parent
	println(graph.shortestPath(vertices[1], vertices[0]))
}