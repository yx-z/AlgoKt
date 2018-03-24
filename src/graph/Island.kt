package graph

import java.util.*

fun main(args: Array<String>) {
	// given an m * n array representing a map
	// mark 1 as island, 0 as ocean
	// and given that islands are four-directionally connected
	val testMap = arrayOf(
			intArrayOf(1, 1, 0, 0, 1),
			intArrayOf(1, 0, 0, 1, 0),
			intArrayOf(0, 1, 1, 1, 0),
			intArrayOf(1, 0, 0, 0, 1)
	)
	println(testMap.countIslands())
	println(testMap.largestIslandArea())
}

// find the number of the islands
// e.x. should return 5 for `testMap`
fun Array<IntArray>.countIslands(): Int {
	val m = this.size
	val n = this[0].size

	var count = 0

	val hasVisited = Array(m, { Array(n) { false } })
	for (row in 0 until m) {
		for (col in 0 until n) {
			// new island!
			if (this[row][col] == 1 && !hasVisited[row][col]) {
				count++
				// this.dfs(row, col, hasVisited)
				this.bfs(row, col, hasVisited)
			}
		}
	}

	return count
}

// find the area of the largest island
// ex. should return 4 for `testMap`
fun Array<IntArray>.largestIslandArea(): Int {
	val m = this.size
	val n = this[0].size

	var maxArea = 0

	val hasVisited = Array(m, { Array(n) { false } })

	for (row in 0 until m) {
		for (col in 0 until n) {
			// new island!
			if (this[row][col] == 1 && !hasVisited[row][col]) {
				maxArea = maxOf(this.dfsArea(row, col, hasVisited), maxArea)
			}
		}
	}

	return maxArea
}

fun Array<IntArray>.dfs(row: Int, col: Int, hasVisited: Array<Array<Boolean>>) {
	if (this[row][col] == 0 || hasVisited[row][col]) {
		return
	}
	hasVisited[row][col] = true
	// up
	if (row - 1 >= 0) {
		this.dfs(row - 1, col, hasVisited)
	}
	// down
	if (row + 1 < this.size) {
		this.dfs(row + 1, col, hasVisited)
	}
	// left
	if (col - 1 >= 0) {
		this.dfs(row, col - 1, hasVisited)
	}
	// right
	if (col + 1 < this[row].size) {
		this.dfs(row, col + 1, hasVisited)
	}
}

fun Array<IntArray>.bfs(row: Int, col: Int, hasVisited: Array<Array<Boolean>>) {
	if (this[row][col] == 0 || hasVisited[row][col]) {
		return
	}
	val q: Queue<Pair<Int, Int>> = LinkedList()
	q.add(row to col)
	while (q.isNotEmpty()) {
		val (currRow, currCol) = q.remove()
		if (this[currRow][currCol] == 1 && !hasVisited[currRow][currCol]) {
			hasVisited[currRow][currCol] = true
			// up
			if (currRow - 1 >= 0) {
				q.add((currRow - 1) to currCol)
			}
			// down
			if (currRow + 1 < this.size) {
				q.add((currRow + 1) to currCol)
			}
			// left
			if (currCol - 1 >= 0) {
				q.add(currRow to (currCol - 1))
			}
			// right
			if (currCol + 1 < this[currRow].size) {
				q.add(currRow to (currCol + 1))
			}
		}
	}
}

fun Array<IntArray>.dfsArea(row: Int, col: Int, hasVisited: Array<Array<Boolean>>, area: Int = 0): Int {
	if (this[row][col] == 0 || hasVisited[row][col]) {
		return area
	}
	hasVisited[row][col] = true
	var sumArea = area + 1

	// up
	if (row - 1 >= 0) {
		sumArea = this.dfsArea(row - 1, col, hasVisited, area = sumArea)
	}
	// down
	if (row + 1 < this.size) {
		sumArea = this.dfsArea(row + 1, col, hasVisited, area = sumArea)
	}
	// left
	if (col - 1 >= 0) {
		sumArea = this.dfsArea(row, col - 1, hasVisited, area = sumArea)
	}
	// right
	if (col + 1 < this[row].size) {
		sumArea = this.dfsArea(row, col + 1, hasVisited, area = sumArea)
	}

	return sumArea
}