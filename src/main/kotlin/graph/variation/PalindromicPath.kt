package graph.variation

import graph.core.*
import util.OneArray
import util.get
import util.max
import util.set

// given a DAG G = (V, E), with each vertex associated with a character,
// find the longest path from in V : the string composed of characters
// from corresponding vertices in this path is palindromic

// report the length of such path
fun Graph<Char>.palindrome(checkIdentity: Boolean = true): Int {
	val G = this
	// topological order of vertices
	val V = G.topoSort(checkIdentity) // O(V + E)
	// V.prettyPrintln()

	// dp[i, j]: len of longest path from V[i] to V[j] that MUST include V[i]
	//           and V[j] : string described above is palindromic
	val dp = OneArray(V.size) { OneArray(V.size) { 0 } }
	// space: O(V^2)

	// we want max_{i, j} { dp[i, j] }
	var max = 0

	// base case:
	// dp[i, j] = 1 if i = j (G is a DAG so there won't be a cycle)
	//          = 2 if V[i] -> V[j] in E and V[i] = V[j]
	//          = 0 if i > j or i, j !in 1..V.size
	for (i in 1..V.size) {
		dp[i, i] = 1
		max = 1
	}
	edges
			.filter { (u, v) -> u.data == v.data }
			.forEach { (u, v) ->
				dp[V.indexOf(u), V.indexOf(v)] = 2
				max = 2
			}

	val reachable = HashMap<Vertex<Char>, Set<Vertex<Char>>>()
	vertices.forEach {
		reachable[it] = whateverFirstSearch(it, checkIdentity)
	}
//	println(reachable)

	// recursive case:
	// dp[i, j] = 0 if V[i] != V[j]
	//          = 2 + max_{u, v} { dp[u, v] } o/w where i < u <= v < j
	//            , i -> u, j -> v in E, and v is reachable from u
	// dp[i, j] depends on entries to the lower-left
	// eval order: outer loop for i decreasing from V.size - 2 down to 1
	for (i in V.size - 2 downTo 1) {
		// inner loop for j increasing from i + 2 to V.size
		for (j in i + 2..V.size) {
			dp[i, j] = if (V[i].data != V[j].data) {
				0
			} else {
				var innerMax = 0
				getEdgesFrom(V[i], checkIdentity).forEach { (_, u) ->
					val uIdx = V.indexOf(u)
					getEdgesTo(V[j], checkIdentity)
							.filter { (v, _) ->
								val vIdx = V.indexOf(v)
								vIdx >= uIdx && reachable[u]!!.contains(v)
							}
							.forEach { (v, _) ->
								val vIdx = V.indexOf(v)
								innerMax = max(innerMax, dp[uIdx, vIdx] + 2)
							}
				}
				innerMax
			}
			max = max(max, dp[i, j])
		}
	}
	// time: O(V^2 + E^2)

//	dp.prettyPrintTable()

	return max
}

fun main(args: Array<String>) {
	val V = listOf(DVertex('H'), DVertex('A'), DVertex('H'))
	val E = setOf(Edge(V[0], V[1], true), Edge(V[1], V[2], true))
	val G = Graph(V, E)
	println(G.palindrome())
}