package recur

import util.INF
import util.OneArray
import util.oneArrayOf

// given k SORTED arrays A1, A2,... Ak, w/ a total of n distinct numbers
// and another number x, find the smallest number y : y > x in these arrays

fun OneArray<OneArray<Int>>.find(x: Int): Int {
	val A = this
	val k = size
	val n = map { size }.sum()

	// find the closest number in each array w/ binary search
	// now we have k numbers, traverse through them to find the best one
	return A.map { it.binSearch(x) }.min() ?: -1

	// let si represent the size of the i-th array
	// then s1 + s2 + s3 + ... + sk = n
	// and we will spend log(s1) + log(s2) + ... + log(sk) time
	// since si < n for all i, it is at most O(k log n)
	// finally, we will do O(k) work to find the best one
	// therefore the total time would be O(k log n + k) = O (k log n)
}

// variant of binary search that returns the smallest number larger than x
// in this sorted array A of size n
fun OneArray<Int>.binSearch(x: Int): Int {
	val A = this
	val n = size

	var l = 1
	var h = n
	while (l < h) {
		val m = l + (h - l) / 2
		if (A[m] == x) {
			return A[m]
		}

		if (A[m] > x) {
			h = m - 1
		} else { // A[m] < x
			l = m + 1
		}
	}

	return if (A[h] > x) A[h] else INF
}

fun main(args: Array<String>) {
	val A = oneArrayOf(
			oneArrayOf(1, 5, 9, 10, 20),
			oneArrayOf(3, 23, 25, 30),
			oneArrayOf(11, 15, 22)
	)

	println(A.find(17)) // should find 20
}