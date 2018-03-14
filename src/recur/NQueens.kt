package recur

var count = 0

fun main(args: Array<String>) {
	// should be 92 for the famous 8-Queens Problem
	solveNQueens(8, 0, Array(8) { Pair(0, 0) })
	println(count)
}

private fun solveNQueens(n: Int, row: Int, positions: Array<Pair<Int, Int>>): Boolean {
	for (col in 0 until n) {
		val isValid = (0 until row).all {
			positions[it].second != col &&
					positions[it].first - positions[it].second != row - col &&
					positions[it].first + positions[it].second != row + col
		}

		//check if this row and col is not under attack from any previous queen.
		if (isValid) {
			positions[row] = Pair(row, col)
			if (row == n - 1) {
				count++
			} else {
				solveNQueens(n, row + 1, positions)
			}
		}
	}
	return false
}