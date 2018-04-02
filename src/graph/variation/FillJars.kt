package graph.variation

import graph.core.*
import util.*
import java.util.*

// given three jars of capacity, A, B, C gallons respectively,
// a lake with infinite amount of water, and a target number k gallons of water
// you may either:
// 1. pour water into a jar until it's full
// 2. pour water out of a jar until it's empty
// 3. pour water from one jar to another, until the other jar is full

// find the minimum steps to get k gallons of water in either of the jars
// or report -1 if this is not possible

fun main(args: Array<String>) {
	val A = 6
	val B = 10
	val C = 15
	val k = 13
	println(minSteps(A, B, C, k)) // 6
}

fun minSteps(A: Int, B: Int, C: Int, k: Int): Int {
	val vertices = HashSet<Vertex<Tuple3<Int, Int, Int>>>()
	for (a in 0..A) {
		for (b in 0..B) {
			for (c in 0..C) {
				vertices.add(Vertex(a tu b tu c))
			}
		}
	}

	val edges = HashSet<Edge<Tuple3<Int, Int, Int>>>()
	vertices.forEach {
		val (a, b, c) = it.data
		// empty one jar
		edges.add(Edge(it, Vertex(0 tu b tu c), true))
		edges.add(Edge(it, Vertex(a tu 0 tu c), true))
		edges.add(Edge(it, Vertex(a tu b tu 0), true))

		// fill one jar
		edges.add(Edge(it, Vertex(A tu b tu c), true))
		edges.add(Edge(it, Vertex(a tu B tu c), true))
		edges.add(Edge(it, Vertex(a tu b tu C), true))

		// pour from one jar to another
		// A -> B
		edges.add(Edge(it, Vertex(max(0, a + b - B) tu min(B, a + b) tu c), true))
		// B -> A
		edges.add(Edge(it, Vertex(min(A, a + b) tu max(0, a + b - A) tu c), true))
		// A -> C
		edges.add(Edge(it, Vertex(max(0, a + c - C) tu b tu min(C, a + c)), true))
		// C -> A
		edges.add(Edge(it, Vertex(min(A, a + c) tu b tu max(0, a + c - A)), true))
		// B -> C
		edges.add(Edge(it, Vertex(a tu max(0, b + c - C) tu min(C, b + c)), true))
		// C -> B
		edges.add(Edge(it, Vertex(a tu min(B, b + c) tu max(0, b + c - B)), true))
	}

	val graph = Graph(vertices, edges)
	val start = Vertex(0 tu 0 tu 0)

	// pre-computed bfs subroutine is really slow
	// we should do bfs ourselves and return early (shown below these comments)
//	val parentMap = graph.bfs(start)
//
//	for (a in 0..A) {
//		for (b in 0..B) {
//			var maybe: Vertex<Tuple3<Int, Int, Int>>? = Vertex(a to b to k)
//			var count = 0
//			while (parentMap[maybe] != null && parentMap[maybe] != start) {
//				maybe = parentMap[maybe]!!
//				count++
//			}
//
//			if (maybe != null) {
//				return count
//			}
//		}
//	}
//
//	for (b in 0..B) {
//		for (c in 0..C) {
//			var maybe: Vertex<Tuple3<Int, Int, Int>>? = Vertex(k to b to c)
//			var count = 0
//			while (parentMap[maybe] != null && parentMap[maybe] != start) {
//				maybe = parentMap[maybe]!!
//				count++
//			}
//
//			if (maybe != null) {
//				return count
//			}
//		}
//	}
//
//	for (a in 0..A) {
//		for (c in 0..C) {
//			var maybe: Vertex<Tuple3<Int, Int, Int>>? = Vertex(a to k to c)
//			var count = 0
//			while (parentMap[maybe] != null && parentMap[maybe] != start) {
//				maybe = parentMap[maybe]!!
//				count++
//			}
//
//			if (maybe != null) {
//				return count
//			}
//		}
//	}

	val marked = HashMap<Vertex<Tuple3<Int, Int, Int>>, Boolean>()
	vertices.forEach {
		marked[it] = false
	}

	val queue: Queue<Tuple2<Vertex<Tuple3<Int, Int, Int>>, Int>> = LinkedList<Tuple2<Vertex<Tuple3<Int, Int, Int>>, Int>>()
	queue.add(start tu 0)

	while (queue.isNotEmpty()) {
		val (vertex, level) = queue.remove()

		val (a, b, c) = vertex.data
		if (a == k || b == k || c == k) {
			return level
		}

		if (marked[vertex] == false) {
			marked[vertex] = true
			graph.getEdgesOf(vertex).forEach {
				queue.add(it.vertex2 tu level + 1)
			}
		}
	}

	return -1
}