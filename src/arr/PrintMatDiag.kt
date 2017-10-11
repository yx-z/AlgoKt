package arr

fun main(args: Array<String>) {
	val matrix = arrayOf(
			// 3 * 5 matrix
			arrayOf(1, 2, 3, 4, 5),
			arrayOf(6, 7, 8, 9, 10),
			arrayOf(11, 12, 13, 14, 15)
	)
	// 1.  1
	// 2.  6, 2
	// 3.  11, 7, 3
	// 4.  12, 8, 4
	// 5.  13, 9, 5
	// 6.  14, 10
	// 7.  15
	matrix.printDiag()
}

fun Array<Array<Int>>.printDiag() {
	val numRows = this.size
	val numCols = this[0].size
	val totalRows = numRows + numCols - 1

	(0 until totalRows).forEach { sum ->
		(0..minOf(numRows - 1, sum)).forEach { row ->
			if (sum - row < numCols) {
				print("${this[row][sum - row]} ")
			}
		}
		println()
	}
}