package recur

import util.OneArray
import util.toOneArray

// A[i] is a local minimum in A[1..n] iff. A[i - 1] >= A[i] and A[i] <= A[i + 1]
// given an array : A[1] >= A[2], and A[n - 1] <= A[n]
// find any local minimum A[i] in O(log n) time
fun <T : Comparable<T>> OneArray<T>.localMin(lo: Int = 1, hi: Int = size): T {
	val A = this
	val n = hi - lo + 1
	if (n <= 100) { // still O(1) since n is bounded by 100
		for (i in lo + 1 until hi) {
			if (A[i - 1] >= A[i] && A[i] <= A[i + 1]) {
				return A[i]
			}
		}
	}

	val m = n / 2 + lo
	if (A[m - 1] >= A[m] && A[m] <= A[m + 1]) {
		return A[m]
	}

	// O(log n)
	return if (A[m - 1] >= A[m]) {
		localMin(m, hi)
	} else { // A[m - 1] < A[m]
		localMin(lo, m + 1)
	}
}

fun main(args: Array<String>) {
	val A = (1..1000).toList().toOneArray()
	A[1] = 3
	// 3, 2, 3, 4, ..., 999, 1000
	//    ^
	//    |
	// local min
	println(A.localMin())
}