package graph.variation

import graph.core.*

// given a DFA in the form of a directed graph, determine if it accepts
// infinite number of strings

// our strategy is:
// 1. remove all states unreachable from the starting state
// 2. remove all states unreachable to the accepting states
// 3. check if there is a cycle in the remaining graph

// assumption: v in V has a value: 0 for the ONLY starting state, 1 for some
// intermediate states, and 2 for possibly many accepting states
fun Graph<Int>.acceptInfinite(): Boolean {
	val s = vertices.first { it.data == 0 }
	val A = vertices.filter { it.data == 2 }

	// 1
	val reachableVerticesFromS = whateverFirstSearch(s)
	val reachableEdgesFromS = edges.filter { (u, v) -> reachableVerticesFromS.contains(u) && reachableVerticesFromS.contains(v) }
	var reachableGraphFromS = Graph(reachableVerticesFromS, reachableEdgesFromS)
	// O(V + E)


	// 2
	A.forEach { a ->
		val reachableVerticesFromA = reachableGraphFromS.reversed().whateverFirstSearch(a)
		val reachableEdgesFromA = reachableEdgesFromS.filter { (u, v) -> reachableVerticesFromA.contains(u) && reachableVerticesFromA.contains(v) }
		reachableGraphFromS = Graph(reachableVerticesFromA, reachableEdgesFromA)
	}
	// O(V * (V + E))

	return try {
		reachableGraphFromS.topoSort()
		false // no cycle
	} catch (ce: CycleDetectedException) {
		true // cycle detected -> infinite strings are accepted
	}
	// O(V + E)
}

// we see that we can speed up step 2 by adding a new vertex v and connect all
// a in A to v, i.e. also adding edges a -> v
// this is similar to adding epsilon transitions to merge all accepting states
// then the runtime would be O(V + E) only

// given a directed graph, reverse all directions in its edges
fun <V> Graph<V>.reversed() = Graph(vertices, edges.map { (u, v) -> Edge(v, u, true) })

fun main(args: Array<String>) {

}