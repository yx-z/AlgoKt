package dp

// given a bitmap as a 2d array, identify the largest rectangular pattern that appear more than once
// the copies of a pattern may overlap but cannot coincide
fun main(args: Array<String>) {
	// ex. here, the pattern
	// 1, 0, 0, ...
	// 1, 1, 1, ...
	// 0, 1, 0, ...
	// 0, 0, 0, ...
	// appears twice (on the left side of this bitmap)
	val bitmap = arrayOf(
			intArrayOf(1, 0, 0, 0, 1),
			intArrayOf(1, 1, 1, 0, 1),
			intArrayOf(0, 1, 0, 0, 0),
			intArrayOf(0, 0, 0, 0, 0),
			intArrayOf(1, 0, 0, 0, 0),
			intArrayOf(1, 1, 1, 0, 0),
			intArrayOf(0, 1, 0, 1, 1),
			intArrayOf(0, 0, 0, 1, 1))

}

fun Array<IntArray>.largestPattern(): Array<IntArray> {
	TODO()
}