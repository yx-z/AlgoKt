package recur

import util.OneArray
import util.oneArrayOf

// classic binary search
// given a sorted array A[1..n], and a target element t
// find the index of t or report -1 if no such element exists
fun <T : Comparable<T>> OneArray<T>.binSearchRecur(t: T,
                                                   lo: Int = 1,
                                                   hi: Int = size): Int {
	val A = this

	if (lo > hi) {
		return -1
	}

	val mid = lo + (hi - lo) / 2 // prevent overflow

	return when {
		A[mid] == t -> mid
		A[mid] > t -> binSearchRecur(t, lo, mid - 1)
		else /* A[mid] < t */ -> binSearchRecur(t, mid + 1, hi)
	}
}

fun <T : Comparable<T>> OneArray<T>.binSearchIter(t: T): Int {
	val A = this
	val n = size

	var lo = 1
	var hi = n

	while (lo <= hi) {
		val mid = lo + (hi - lo) / 2

		if (A[mid] == t) {
			return mid
		}

		if (A[mid] > t) {
			hi = mid - 1
		} else { // A[mid] < t
			lo = mid + 1
		}
	}

	return -1
}

fun main(args: Array<String>) {
	val arr = oneArrayOf(1, 5, 7, 10, 55, 209)
	println(arr.binSearchRecur(10))
	println(arr.binSearchIter(10))
}
