package mat

import util.*
import java.lang.Math.abs
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

// given a grid G[1..n, 1..n], where G[i, j] = 1 represents an obstacle and 0
// o/w, and a starting point s = (u, v), and finally an ending point t = (p, q)
// find the shortest path from s to t, i.e. w/ min # of cells in the path
// you may assume that inputs are valid and such path exists

// A* search algorithm
// for more shortest path algorithms used in a graph with traditional G = (V, E)
// representation, please see src/graph/core/ShortestPath*.kt)
fun OneArray<OneArray<Int>>.aStar(start: Tuple2<Int, Int>, goal: Tuple2<Int, Int>):
		List<Tuple2<Int, Int>> {
	val path = ArrayList<Tuple2<Int, Int>>()
	val f = HashMap<Tuple2<Int, Int>, Int>()
	val g = HashMap<Tuple2<Int, Int>, Int>()
	val parent = HashMap<Tuple2<Int, Int>, Tuple2<Int, Int>?>()
	val closed = HashSet<Tuple2<Int, Int>>()
	indices.forEach { i ->
		indices.forEach { j ->
			f[i tu j] = INF
			g[i tu j] = INF
			parent[i tu j] = null
		}
	}
	g[start] = 0
	f[start] = g[start]!! + heuristic(start, goal)

	val open = PriorityQueue<Tuple2<Int, Int>>(Comparator { c1, c2 -> f[c1]!! - f[c2]!! })
	open.add(start)

	trav@ while (open.isNotEmpty()) {
		val curr = open.remove()
		val (i, j) = curr
		if (curr == goal) {
			break@trav
		}

		closed.add(curr)

		val neighbors = HashSet<Tuple2<Int, Int>>()
		// finding neighbors in all eight directions
		(-1..1).forEach { deltaX ->
			(-1..1).forEach { deltaY ->
				if (deltaX != 0 || deltaY != 0) { // ensuring != curr
					val neighbor = i + deltaX tu j + deltaY
					if (isValid(neighbor)) {
						neighbors.add(neighbor)
					}
				}
			}
		}

		neighbor@ for (neighbor in neighbors) {
			if (closed.contains(neighbor)) {
				continue@neighbor
			}

			if (!open.contains(neighbor)) {
				open.add(neighbor)
			}

			// here the idea is basically the same as finding a tense edge and
			// relax if necessary in dijkstra's algorithm
			val tmpG = g[curr]!! + dist(curr, neighbor)
			if (tmpG >= g[neighbor]!!) {
				continue@neighbor
			}

			parent[neighbor] = curr
			g[neighbor] = tmpG
			f[neighbor] = g[neighbor]!! + heuristic(neighbor, goal)
		}
	}

	// reconstruct path from parent map
	var c: Tuple2<Int, Int>? = goal
	while (c != null) {
		path.add(0, c)
		c = parent[c]
	}
	return path
}

private fun OneArray<OneArray<Int>>.isValid(curr: Tuple2<Int, Int>): Boolean {
	val (i, j) = curr
	return i in indices && j in indices && this[i, j] == 0
}

private fun dist(curr: Tuple2<Int, Int>, neighbor: Tuple2<Int, Int>): Int {
	val (i, j) = curr
	val (p, q) = neighbor
	return abs(i - j) + abs(p - q) // counting the manhattan distance
}

// heuristic function based on manhattan distance
private fun heuristic(curr: Tuple2<Int, Int>, goal: Tuple2<Int, Int>): Int {
	val (i, j) = curr
	val (p, q) = goal
	return (p - i) + (q - j)
}

fun main(args: Array<String>) {
	val G = oneArrayOf(
			oneArrayOf(0, 0, 1, 0),
			oneArrayOf(0, 0, 1, 0),
			oneArrayOf(0, 1, 0, 0),
			oneArrayOf(0, 0, 0, 1))
	println(G.aStar(1 tu 1, 1 tu 4))
}