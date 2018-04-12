package graph.variation

import graph.core.*
import util.*

// cut vertex is a vertex : after deleting it, the graph will be disconnected

// 1. given a DAG G = (V, E) with only one source and one sink
//    determine whether G has a cut vertex or not
fun <V> Graph<V>.dagCutVertex(checkIdentity: Boolean = true): Boolean {
	// we should ALWAYS sort the DAG in EVERY DAG problem
	// it's like
	// - "Suppose you are given a DAG, and you want to ..."
	// - "Hold on, a DAG, so topological sort it first...
	//    Hmm, I'm sorry, what's your question again?"
	val V = topoSort(checkIdentity)

	// dp(i): biggest index in V : V[1..i] can get (by edges)
	val dp = OneArray(V.size) { 0 }
	// space: O(V)

	// dp(i) = 0 if i <= 0
	dp.getterIndexOutOfBoundsHandler = { 0 }
	// dp(i) = max{ dp(i - 1), j } for all V[i] -> V[j] is in E
	// dependency: dp(i) depends on dp(i - 1), i.e. entry to the left
	// eval order: increasing i from 1 to V
	for (i in V.indices) {
		dp[i] = dp[i - 1]
		getEdgesFrom(V[i], checkIdentity).forEach { (_, w) ->
			dp[i] = max(dp[i], V.indexOf(w))
		}
	}
	// time: O(V + E)
//	dp.prettyPrintln()

	// we want to find if there exists a vertex V[j] : dp(j - 1) <= j, j != 1 or V
	return V.indices.any { it in 2 until V.size && dp[it - 1] <= it }
}

// 2. find a cut vertex in a general graph
fun <V> Graph<V>.cutVertex(checkIdentity: Boolean = true): Boolean {
	TODO()
}

fun main(args: Array<String>) {
	val V = (0..3).map { Vertex(it) }
	val E = setOf(
			Edge(V[0], V[1], true),
			Edge(V[0], V[2], true),
			Edge(V[1], V[2], true),
			Edge(V[2], V[3], true))
	val G = Graph(V, E)
	println(G.dagCutVertex())
}
