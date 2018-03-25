package greedy

// arr[i] is the util.max length we can jump from index i
// starting from arr[0], see if we can jump tu the end index
fun main(args: Array<String>) {
	val testArr1 = intArrayOf(1, 3, 2, 0, 3, 0)
	println(testArr1.canJumpToEnd())
	println(testArr1.minStepsToEnd())

	val testArr2 = intArrayOf(2, 4, 0, 0)
	println(testArr2.canJumpToEnd())
	println(testArr2.minStepsToEnd())

	val testArr3 = intArrayOf(1, 2, 0, 0, 1)
	println(testArr3.canJumpToEnd())
	println(testArr3.minStepsToEnd())
}

fun IntArray.canJumpToEnd(): Boolean {
	var max = 0
	var idx = 0
	while (idx <= minOf(size - 1, max)) {
		max = maxOf(max, idx + this[idx])
		idx++
	}

	return max >= size - 1
}

fun IntArray.minStepsToEnd(): Int {
	var currMax = 0
	var nextMax = 0
	var steps = 0

	indices
			.filter { it < size - 1 }
			.forEach {
				nextMax = maxOf(nextMax, this[it] + it)
				if (it == currMax) {
					currMax = nextMax
					steps++
				}
			}

	return steps
}