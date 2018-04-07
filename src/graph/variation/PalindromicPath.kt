package graph.variation

import graph.core.Graph
import graph.core.topoSort
import util.*

// given a DAG G = (V, E), with each vertex associated with a character,
// find the longest path from in V : the string composed of characters
// from corresponding vertices in this path is palindromic

// report the length of such path
fun Graph<Char>.palindrome(checkIdentity: Boolean = true): Int {
	val G = this
	// topological order of vertices
	val V = G.topoSort(checkIdentity)
	// O(V + E)
	val E = edges

	// dp[i, j]: len of longest path from V[i] to V[j] : string described above is palindromic
	val dp = OneArray(V.size) { OneArray(V.size) { 0 } }
	// space: O(V^2)

	// base case:
	// dp[i, j] = 1 if i = j (G is a DAG so there won't be a cycle)
	//          = 0 if i > j or i, j !in 1..V.size
	for (i in 1..V.size) {
		dp[i, i] = 1
	}

	// we want max_{i, j} { dp[i, j] }
	var max = 1 // init. to base case

	// recursive case:
	// dp[i, j] = max_{u, v} { dp[u, v] : i -> u, v -> j in E, v is reachable from u, i < u < v < j } if V[i] != V[j]


	return max
}

fun main(args: Array<String>) {

}