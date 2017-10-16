package greedy

// arr[i] is the max length we can jump from index i
// starting from arr[0], see if we can jump to the end index
fun main(args: Array<String>) {
	println(intArrayOf(1, 3, 2, 0, 3).canJumpToEnd())
	println(intArrayOf(1, 2, 0, 1, 0, 1).canJumpToEnd())
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
