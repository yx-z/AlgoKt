package graph.variation

import graph.core.Edge
import graph.core.Graph
import graph.core.Vertex

// check if an undirected graph G = (V, E) is bipartite, that is,
// let V be partitioned into two sets A and B, and every edge e in E satisfies
// that one endpoint is in A and the other is in B
fun <V> Graph<V>.isBipartite(checkIdentity: Boolean = true): Boolean {
	val marked = HashMap<Vertex<V>, Boolean>()
	val color = HashMap<Vertex<V>, Boolean>()
	vertices.forEach { marked[it] = false }

	val bag = ArrayList<Vertex<V>>()
	val start = vertices.toList()[0]
	var curr = true
	color[start] = curr
	bag.add(start)
	// wfs traversal with marking vertices with either TRUE or FALSE
	while (bag.isNotEmpty()) {
		val v = bag.removeAt(0)
		marked[v] = true
		curr = !curr
		getEdgesOf(v, checkIdentity).forEach { (s, e) ->
			// check for all marked vertices, if this edge has to be marked
			// with the same value (T/F), then G must NOT be bipartite
			if (color[s] == color[e]) {
				return false
			}
			val u = if ((checkIdentity && s === v) || (!checkIdentity && s == v)) e else s
			if (marked[u] == false) {
				bag.add(u)
				color[u] = curr
			}
		}
	}
	// O(V + E)

	return true
}

fun main(args: Array<String>) {
	val V = (0..2).map { Vertex(it) }
	val E = setOf(Edge(V[0], V[2]), Edge(V[1], V[2]), Edge(V[0], V[1]))
	val G = Graph(V, E)
	println(G.isBipartite())
}
