package arr

import sort.swap

// returns the k-th smallest element in the array
fun main(args: Array<String>) {
	val testArr = (1..5).toList().toIntArray()
	println(testArr.kthSmallest(1))
}

fun IntArray.kthSmallest(k: Int): Int {
	val index = k - 1
	var start = 0
	var end = this.size - 1

	while (start < end) {
		val pivot = partition(start, end)
		when {
			pivot < index -> start = pivot + 1
			pivot > index -> end = pivot - 1
			else -> return this[pivot]
		}
	}
	return this[start]
}

private fun IntArray.partition(start: Int, end: Int): Int {
	var left = start
	var right = end

	val pivot = left

	while (left <= right) {
		while (left <= right && this[left] <= this[pivot]) {
			left++
		}
		while (left <= right && this[right] > this[pivot]) {
			right--
		}

		if (left > right) {
			break
		}

		swap(left, right)
	}
	swap(right, pivot)

	return right
}
