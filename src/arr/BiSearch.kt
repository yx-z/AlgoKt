package arr

// Binary Search
fun main(args: Array<String>) {
	val arr = arrayOf(1, 2, 3, 4, 5, 6)

	println(biSearchIterative(arr, 3))
	println(biSearchRecursive(arr, 3))
}

// Iterative Binary Search
fun biSearchIterative(arr: Array<Int>, target: Int): Int {
	var lo = 0
	var hi = arr.size

	while (lo <= hi) {
		val mid = lo + (hi - lo) / 2
		val value = arr[mid]

		when {
			value == target -> return mid
			value > target -> hi = mid - 1
			else -> lo = mid + 1
		}
	}

	return -1
}

// Recursive Binary Search
fun biSearchRecursive(arr: Array<Int>, target: Int, lo: Int = 0, hi: Int = arr.size): Int {
	if (hi < lo) {
		return -1
	}

	val mid = lo + (hi - lo) / 2
	val value = arr[mid]

	return when {
		value == target -> mid
		value > target -> biSearchRecursive(arr, target, lo, mid - 1)
		else -> biSearchRecursive(arr, target, mid + 1, hi)
	}
}
