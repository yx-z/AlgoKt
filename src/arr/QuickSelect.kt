package arr

import arr.sort.swap

// returns the k-th smallest element in the array
fun main(args: Array<String>) {
	val testArr = (1..5).toList().toIntArray()
	println(testArr.findKthLargest(5))
}

fun IntArray.findKthLargest(k: Int): Int {
	var start = 0
	var end = this.size - 1
	val index = this.size - k

	while (start < end) {
		val pivot = partion(start, end)
		when {
			pivot < index -> start = pivot + 1
			pivot > index -> end = pivot - 1
			else -> return this[pivot]
		}
	}
	return this[start]
}

private fun IntArray.partion(start: Int, end: Int): Int {
	var start = start
	var end = end

	val pivot = start

	while (start <= end) {
		while (start <= end && this[start] <= this[pivot]) {
			start++
		}
		while (start <= end && this[end] > this[pivot]) {
			end--
		}

		if (start > end) {
			break
		}

		swap(start, end)
	}
	swap(end, pivot)

	return end
}
