package sort

import util.swap

fun IntArray.quickSort() {
	quickSort(0, size - 1)
}

fun IntArray.quickSort(start: Int, end: Int) {
	if (start < end) {
		val mid = partition(start, end)
		quickSort(start, mid - 1)
		quickSort(mid + 1, end)
	}
}

// pick A[end] as pivot naively
fun IntArray.partition(start: Int, end: Int): Int {
	val A = this

	val pivot = A[end]
	var i = start - 1

	for (j in start until end) {
		if (this[j] <= pivot) {
			i++
			swap(i, j)
		}
	}

	swap(i + 1, end)
	return i + 1
}

fun main(args: Array<String>) {
	println(testCorrectness { quickSort() })
}