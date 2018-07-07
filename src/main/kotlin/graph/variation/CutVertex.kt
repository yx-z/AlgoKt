package graph.variation

import graph.core.Edge
import graph.core.Graph
import graph.core.Vertex
import graph.core.topoSort
import util.OneArray
import util.max
import util.min
import kotlin.collections.set

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

// 2. find all cut vertices in a general directed graph
//    google articulation point (another name for cut vertex) would help
fun <V> Graph<V>.cutVertex(checkIdentity: Boolean = true): Set<Vertex<V>> {
	// so the basic idea is, run a DFS for G, v in V is a cut vertex iff. either
	// - v is the root of the DFS tree and has at least two children
	//   OR
	// - v is NOT the root of the DFS tree and there is a child w : no back edge
	//   from the subtree of w to v's parent
	// reference: https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/

	val cutVertices = HashSet<Vertex<V>>()
	val marked = HashMap<Vertex<V>, Boolean>()
	val parent = HashMap<Vertex<V>, Vertex<V>?>()
	val disc = HashMap<Vertex<V>, Int>()
	val low = HashMap<Vertex<V>, Int>()
	vertices.forEach { v ->
		marked[v] = false
		parent[v] = null
		disc[v] = 0
		low[v] = 0
	}

	var discTime = 0
	vertices.forEach { v ->
		if (marked[v]!! == false) {
			discTime = cutVertexRecur(
					v,
					marked,
					parent,
					disc,
					low,
					cutVertices,
					discTime,
					checkIdentity)
		}
	}

	return cutVertices
}

private fun <V> Graph<V>.cutVertexRecur(v: Vertex<V>,
                                        marked: HashMap<Vertex<V>, Boolean>,
                                        parent: HashMap<Vertex<V>, Vertex<V>?>,
                                        disc: HashMap<Vertex<V>, Int>,
                                        low: HashMap<Vertex<V>, Int>,
                                        cutVertices: HashSet<Vertex<V>>,
                                        pDiscTime: Int,
                                        checkIdentity: Boolean = true): Int {
	var discTime = pDiscTime
	var children = 0
	marked[v] = true
	disc[v] = discTime
	low[v] = discTime
	getEdgesFrom(v, checkIdentity).forEach { (_, w) ->
		if (marked[w] == false) {
			children++
			parent[w] = v
			discTime = cutVertexRecur(
					w,
					marked,
					parent,
					disc,
					low,
					cutVertices,
					discTime,
					checkIdentity)
			low[w] = min(low[w]!!, low[v]!!)
			if (parent[v] == null && children > 1) {
				cutVertices.add(v)
			}

			if (parent[v] != null && low[w]!! >= disc[v]!!) {
				cutVertices.add(w)
			}
		}
	}
	return discTime + 1
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
	println(G.cutVertex())
}
