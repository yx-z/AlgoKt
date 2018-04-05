package graph.core

import util.min
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

// Given a directed graph G, we want to build the SCC (Strongly Connected Components)
// of G, or say the meta-graph, or say the condensation of G in linear time O(V + E) :
// the resulting graph G' has vertices as sets of vertices in G : vertices in each
// set can reach other vertices in the same set. And G' has edges as directions among these sets
// assume that graph is unweighted, o/w we will lose information while forming SCCs
fun <V> Graph<V>.kosaraju(checkIdentity: Boolean = true): Graph<Set<Vertex<V>>> {
	// init.:
	val stack = Stack<Vertex<V>>()
	val root = HashMap<Vertex<V>, Vertex<V>?>()
	val marked = HashMap<Vertex<V>, Boolean>()
	vertices.forEach {
		marked[it] = false
		root[it] = null
	}

	// phase 1: push in post-order in the reversed graph
	vertices.forEach {
		if (marked[it] == false) {
			pushPostRevDFS(it, stack, marked, checkIdentity)
		}
	}

	// phase 2: DFS again in stack order
	while (stack.isNotEmpty()) {
		val v = stack.pop()
		if (root[v] == null) {
			labelOneDFS(v, v, root, checkIdentity)
		}
	}

	// phase 3: group/merge vertices having the same root (as new vertices)
	// construct intermediate edges and build the new graph
	return sccFromMap(root)
	// overall time complexity: O(V + E)
}

private fun <V> Graph<V>.pushPostRevDFS(v: Vertex<V>,
                                        stack: Stack<Vertex<V>>,
                                        marked: HashMap<Vertex<V>, Boolean>,
                                        checkIdentity: Boolean = true) {
	marked[v] = true
	getEdgesTo(v, checkIdentity).forEach { (u, _) ->
		if (marked[u] == false) {
			pushPostRevDFS(u, stack, marked)
		}
	}
	stack.push(v)
}

private fun <V> Graph<V>.labelOneDFS(v: Vertex<V>,
                                     r: Vertex<V>,
                                     root: HashMap<Vertex<V>, Vertex<V>?>,
                                     checkIdentity: Boolean = true) {
	root[v] = r
	getEdgesFrom(v, checkIdentity).forEach { (_, w) ->
		if (root[w] == null) {
			labelOneDFS(w, r, root)
		}
	}
}

fun <V> Graph<V>.tarjan(checkIdentity: Boolean = true): Graph<Set<Vertex<V>>> {
	var clock = 0
	val stack = Stack<Vertex<V>>()
	val marked = HashMap<Vertex<V>, Boolean>()
	val root = HashMap<Vertex<V>, Vertex<V>?>()
	val low = HashMap<Vertex<V>, Int>()
	val pre = HashMap<Vertex<V>, Int>()
	vertices.forEach {
		marked[it] = false
		root[it] = null
		low[it] = 0
		pre[it] = 0
	}

	vertices.forEach {
		if (marked[it] == false) {
			clock = tarjanDFS(it, clock, marked, pre, low, stack, root, checkIdentity)
		}
	}

	return sccFromMap(root)
	// still O(V + E)
}

private fun <V> Graph<V>.tarjanDFS(v: Vertex<V>,
                                   pClock: Int,
                                   marked: HashMap<Vertex<V>, Boolean>,
                                   pre: HashMap<Vertex<V>, Int>,
                                   low: HashMap<Vertex<V>, Int>,
                                   stack: Stack<Vertex<V>>,
                                   root: HashMap<Vertex<V>, Vertex<V>?>,
                                   checkIdentity: Boolean = true): Int {
	marked[v] = true
	val clock = pClock + 1
	pre[v] = clock
	low[v] = pre[v]!!
	stack.push(v)
	getEdgesFrom(v).forEach { (_, w) ->
		when {
			marked[w] == false -> {
				tarjanDFS(w, clock, marked, pre, low, stack, root, checkIdentity)
				low[v] = min(low[v]!!, low[w]!!)
			}
			root[w] == null -> low[v] = min(low[v]!!, pre[w]!!)
		}
	}
	if (low[v] == pre[v]) {
		do {
			val w = stack.pop()
			root[w] = v
		} while ((checkIdentity && w !== v) || (!checkIdentity && w != v))
	}
	return clock
}

private fun <V> Graph<V>.sccFromMap(root: Map<Vertex<V>, Vertex<V>?>): Graph<Set<Vertex<V>>> {
	val scc = root.entries.groupBy { it.value }
	val newVertices = scc.map { Vertex(it.value.map { it.key }.toSet()) }

	val newEdges = HashSet<Edge<Set<Vertex<V>>>>()
	edges.forEach { (u, v, _) ->
		// speed up is possible if we have built a <Vertex, Set<Vertex<V>>> map
		// to find which set u/v belongs to in O(1) time
		// but we'll ignore details for simplicity and expressiveness here
		val uSet = newVertices.first { it.data.contains(u) }
		val vSet = newVertices.first { it.data.contains(v) }
		if (uSet != vSet) {
			newEdges.add(Edge(uSet, vSet, true))
		}
	}

	return Graph(newVertices, newEdges)
}

fun main(args: Array<String>) {
	val V = (0..5).map { Vertex(it) }
	val E = setOf(
			Edge(V[3], V[4], true),
			Edge(V[0], V[1], true),
			Edge(V[1], V[2], true),
			Edge(V[1], V[3], true),
			Edge(V[2], V[0], true),
			Edge(V[4], V[5], true),
			Edge(V[5], V[4], true))
	val G = Graph(V, E)
//	println(G.kosaraju())
	println(G.tarjan())
}
