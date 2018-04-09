package graph.variation

import graph.core.*
import util.OneArray

// given a graph G = (V, E), where we have each vertex v in V associated with
// a value r(v) = 1 OR 0 and we call vertices with r(v) = 1 as routers

// next we define a vertex to be safe when it can reach at least k distinct
// routers from it, assuming k << |V|

// 1. Suppose G is a DAG, find all safe vertices in G
fun Graph<Int>.safeVerticesDAG(k: Int,
                               checkIdentity: Boolean = true)
		: Collection<Vertex<Int>> {
	val V = topoSort(checkIdentity) // O(V + E)

	// dp(i): list of indices of routers V[i] can reach
	val dp = OneArray(V.size) { HashSet<Int>() }
	// space: O(V)

	V.indices
			.filter { V[it].data == 1 }
			.forEach { dp[it].add(it) }
	for (i in V.size - 1 downTo 1) {
		getEdgesFrom(V[i], checkIdentity).forEach { (_, w) ->
			dp[V.indexOf(w)].forEach { dp[i].add(it) }
		}
	}

	return V.filterIndexed { i, _ -> dp[i].size >= k }
}

fun main(args: Array<String>) {
	val V = listOf(
			DVertex(0),
			DVertex(0),
			DVertex(1),
			DVertex(1),
			DVertex(0))
	val E = setOf(
			Edge(V[0], V[1], true),
			Edge(V[0], V[2], true),
			Edge(V[0], V[4], true),
			Edge(V[1], V[2], true),
			Edge(V[1], V[3], true),
			Edge(V[2], V[3], true),
			Edge(V[2], V[4], true),
			Edge(V[3], V[4], true))
	val G = Graph(V, E)
	println(G.safeVerticesDAG(2))
}