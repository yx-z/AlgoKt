package misc

import java.util.*

fun main(args: Array<String>) {
	// [0, 1, 2, ..., 9]
	val node = Node(10)
	node.union(0, 1)
	node.union(2, 3)
	node.union(4, 5)
	node.union(0, 2)
	node.union(0, 4)

	println(Arrays.toString((node.parent)))

	println(node.find(5))
	println(Arrays.toString(node.parent))
}

class Node(var size: Int) {
	val parent = Array(size) { it }
	private val rank = Array(size) { 0 }

	fun find(i: Int): Int {
		if (i >= size || i < 0) {
			return -1
		}

		if (i != parent[i]) {
			parent[i] = find(parent[i])
		}

		return parent[i]
	}

	fun union(i1: Int, i2: Int) {
		if (i1 < 0 || i2 < 0 || i1 >= size || i2 >= size) {
			return
		}

		val f1 = find(i1)
		val f2 = find(i2)

		if (rank[f1] > rank[f2]) {
			parent[f2] = f1
		} else {
			if (rank[f1] == rank[f2]) {
				rank[f1]++
			}
			parent[f2] = f1
		}
	}
}