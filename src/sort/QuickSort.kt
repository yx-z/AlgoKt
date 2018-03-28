package sort

import util.prettyPrintln
import util.swap
import java.util.*

fun IntArray.quickSort() {
	quickSort(0, size - 1)
}

fun IntArray.quickSort(start: Int, end: Int) {
	if (end > start) {
		val mid = partition(1)
		prettyPrintln()
		quickSort(start, mid - 1)
		quickSort(mid + 1, end)
	}
}

fun IntArray.partition(idx: Int): Int {
	val A = this
	val n = size - 1

	swap(idx, n)
	var i = -1
	var j = n
	while (i < j) {
		do {
			i++
		} while (i < j && A[i] < A[n])

		do {
			j--
		} while (i < j && A[j] > A[n])

		if (i < j) {
			swap(i, j)
		}
	}

	// now idx >= j
	swap(i, n)
	return i
}

fun main(args: Array<String>) {
	println(testCorrectness { quickSort() })
}