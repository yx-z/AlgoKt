package graph

import java.util.*
import kotlin.collections.HashSet

// given a directed graph
// and two nodes in the graph
// find if there exists a valid (directed) path between them
fun main(args: Array<String>) {
	// a -> b -> e
	// |    ^    |
	// |    |    |
	// | -> d    |
	// |    ^    |
	// |    |    |
	// |--> c <--|
	val adjList = arrayOf(
			// first -> second
			"a" to "b",
			"a" to "d",
			"a" to "c",
			"b" to "e",
			"e" to "c",
			"c" to "d",
			"d" to "b"
	)

	// true
	println(adjList.pathBFS("e", "b"))
	println(adjList.pathDFS("e", "b"))
	// false
	println(adjList.pathBFS("b", "a"))
	println(adjList.pathDFS("b", "a"))
	// true
	println(adjList.pathBFS("b", "d"))
	println(adjList.pathDFS("b", "d"))
	// false
	println(adjList.pathBFS("e", "a"))
	println(adjList.pathDFS("e", "a"))
}

// bfs to see if start -> end
fun Array<Pair<String, String>>.pathBFS(start: String, end: String): Boolean {
	val visitedSet = HashSet<String>()
	val visitedQueue: Queue<String> = LinkedList<String>()
	visitedQueue.add(start)
	while (visitedQueue.isNotEmpty()) {
		val trav = visitedQueue.remove()
		if (trav == end) {
			return true
		}
		visitedSet.add(trav)

		filter { it.first == trav && !visitedSet.contains(it.second) }
				.forEach { visitedQueue.add(it.second) }
	}
	return false
}

// recursive dfs version (implicit stack)
fun Array<Pair<String, String>>.pathDFS(start: String, end: String, visitedSet: HashSet<String> = HashSet()): Boolean {
	if (start == end) {
		return true
	}

	visitedSet.add(start)

	return filter { it.first == start && !visitedSet.contains(it.second) }
			.any { pathDFS(it.second, end, visitedSet) }
}
