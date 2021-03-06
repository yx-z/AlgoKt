package recur

var count = 0

fun main(args: Array<String>) {
	// should be 92 for the famous 8-Queens Problem
	solveNQueens(8, 0, Array(8) { 0 })
	println(count)
}

private fun solveNQueens(n: Int, row: Int, positions: Array<Int>): Boolean {
	for (col in 0 until n) {
		val isValid = (0 until row).all {
			positions[it] != col && it - positions[it] != row - col && it + positions[it] != row + col
		}

		//check if this row and col is not under attack from any previous queen.
		if (isValid) {
			positions[row] = col
			if (row == n - 1) {
				count++
			} else {
				solveNQueens(n, row + 1, positions)
			}
		}
	}
	return false
}