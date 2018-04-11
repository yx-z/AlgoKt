package recur

import util.OneArray
import util.oneArrayOf

// given an array A[1..n] sorted in increasing order (A[i] can be < 0, 0, or > 0)

// 1. find j : A[j] = j or report 0 if no such j exists in O(log n) time
fun OneArray<Int>.eleIdx(): Int {
	val A = this
	val n = size

	// similar to binary search, this can be implemented recursively
	var l = 1
	var h = n
	while (l <= h) {
		val m = l + (h - l) / 2
		if (A[m] == m) {
			return m
		}

		if (A[m] > m) {
			// A[m] is too big, look in the left subarray
			h = m - 1
		} else { // A[m] < m
			// A[m] is too small, look in the right subarray
			l = h + 1
		}
	}

	return 0
}

// find, i will do the recursive version as well
fun OneArray<Int>.eleIdxRecur(l: Int = 0, h: Int = size): Int {
	if (l > h) {
		return 0
	}

	val A = this

	val m = l + (h - l) / 2
	if (A[m] == m) {
		return m
	}

	return if (A[m] > m) {
		eleIdxRecur(m + 1, h)
	} else {
		eleIdxRecur(l, m - 1)
	}
}

// 2. now assume that A[i] > 0 for all i, find such j in O(1) time
//    you know what, 1 can be the only possible candidate!!
fun OneArray<Int>.eleIdxPos() = if (this[1] == 1) 1 else 0

fun main(args: Array<String>) {
	val A = oneArrayOf(-12, 2, 3, 5)
	println(A.eleIdxRecur())
	println(A.eleIdx())
}

