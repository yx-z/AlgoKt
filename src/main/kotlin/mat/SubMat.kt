package mat

import java.util.*

fun main(args: Array<String>) {
	val testMatrix = arrayOf(
			intArrayOf(1, 2, 3, 4),
			intArrayOf(5, 6, 7, 8),
			intArrayOf(9, 10, 11, 12),
			intArrayOf(13, 14, 15, 16)
	)

	println(Arrays.deepToString(testMatrix.sub(1, 1)))
}

fun Array<IntArray>.sub(startRow: Int, startCol: Int) = toMutableList()
		.filterIndexed { idx, _ -> idx >= startRow }
		.map { it.asSequence().filterIndexed { idx, _ -> idx >= startCol }.toList().toIntArray() }
		.toTypedArray()
