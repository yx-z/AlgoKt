package recur

import util.*
import java.util.*

// Suppose you have an integer array A[1..n] that used to be sorted
// but k entries of A have been overwritten with random numbers
// Describe an algorithm to determine whether k-corrupted A contains an int x
// Report its index or return 0 for no such x

fun OneArray<Int>.idxOf(x: Int, k: Int): Int {
	val A = this
	val n = size

	if (k == 0) {
		return Arrays.binarySearch(A.toArray(), x)
	}

	if (n < k) {
		// O(1) brute force for small n
		return indexOfFirst { it == x }
	}


	val m = n / 2
	if (A[m] == x) {
		return m
	}

	var countSmallerThanX = 0
	var countBiggerThanX = 0
	for (i in max(1, m - k)..min(n, m + k)) {
		when {
			A[i] == x -> return i
			A[i] > x -> countBiggerThanX++
			else -> countSmallerThanX++
		}
	}

	if (countBiggerThanX > k) {
		// recurse on the left
		return A[1 until m - k].idxOf(x, k)
	} else { // countSmallerThanX > k / 2
		// recurse on the right
		return A[m + k + 1..n].idxOf(x, k)
	}
}

fun main(args: Array<String>) {
	val A = oneArrayOf(1, 2, 10, 4, 50, 6, 20, 8, 19, 10)
	println(A.idxOf(2, 4))
}
