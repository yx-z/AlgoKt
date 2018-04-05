package recur

import util.OneArray
import util.get
import util.oneArrayOf

// given an array A[1..n], we know that at some index k, k in 2 until n:
// A[1..k] is sorted in increasing order, A[k + 1..n] is also sorted in
// increasing order, but A[n] < A[1]
// find such k
fun OneArray<Int>.arrRotateIdx(): Int {
	val A = this
	val n = size

	if (n < 5) { // O(1) brute force
		for (k in 2 until n) {
			if (A[k - 1] < A[k] && A[k] > A[k + 1]) {
				return k
			}
		}
		return -1 // report such k is not found
	}

	val m = n / 2
	if (A[m - 1] < A[m] && A[m] > A[m + 1]) {
		return m
	}

	// O(log n) in total
	val l = A[1 until m].arrRotateIdx()
	val r = A[m + 1..n].arrRotateIdx()
	if (l == -1 && r == -1) {
		return -1
	}

	if (r == -1) {
		return l
	}

	// note that indices in the right half should be shifted
	return r + m
}

fun main(args: Array<String>) {
	val A = oneArrayOf(9, 13, 16, 18, 19, 23, 28, 31, 37, 42, -4, 0, 2, 5, 7, 8)
	println(A.arrRotateIdx()) // 10
}