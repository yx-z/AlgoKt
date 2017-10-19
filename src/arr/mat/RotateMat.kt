package arr.mat

import java.util.*

fun main(args: Array<String>) {
	// given an n * n arr.matrix
	// do a clockwise rotation

	val test2 = arrayOf(
			intArrayOf(1, 2),
			intArrayOf(3, 4)
	)
	// -> 3, 1
	// -> 4, 2
	test2.rotate()
	println(Arrays.deepToString(test2))

	val test3 = arrayOf(
			intArrayOf(1, 2, 3),
			intArrayOf(4, 5, 6),
			intArrayOf(7, 8, 9)
	)
	// -> 7, 4, 1
	// -> 8, 5, 2
	// -> 9, 6, 3
	test3.rotate()
	println(Arrays.deepToString(test3))

	val test4 = arrayOf(
			intArrayOf(1, 2, 3, 4),
			intArrayOf(5, 6, 7, 8),
			intArrayOf(9, 10, 11, 12),
			intArrayOf(13, 14, 15, 16)
	)
	test4.rotate()
	println(Arrays.deepToString(test4))
}

fun Array<IntArray>.rotate() {
	val n = this.size
	val layers = n / 2
	for (layer in 0 until layers) {
		for (shift in 0 until n - layer - 1) {
			// cache top
			val tmp = this[layer][layer + shift]
			// top <- left
			this[layer][layer + shift] = this[n - layer - shift - 1][layer]
			// left <- bottom
			this[n - layer - shift - 1][layer] = this[n - layer - 1][n - layer - shift - 1]
			// bottom <- right
			this[n - layer - 1][n - layer - shift - 1] = this[layer + shift][n - layer - 1]
			// right <- top (tmp)
			this[layer + shift][n - layer - 1] = tmp
		}
	}
}
