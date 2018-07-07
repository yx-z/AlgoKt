package recur

import util.OneArray
import util.append
import util.get
import util.oneArrayOf

// given two sorted arrays A[1..n] and B[1..n],
// find the median of sorted(A append B)[1..2n]
// that is the n-th smallest element in their concatenated sorted array
infix fun OneArray<Int>.medianUnion(B: OneArray<Int>): Int {
	val A = this
	val n = size

	if (n < 7) { // or any arbitrary constant
		return (A append B).sorted()[n]
	}

	val m = n / 2

	return if (A[m] > B[m]) {
		// A[m] is bigger than A[1..m] and B[1..m],
		// so the upper half of A cannot be the median of sorted(A append B)
		// similarly, the lower half of B is smaller than A[m + 1..n] and B[m + 1..n]
		// so the lower half of B cannot be the median of sorted(A append B)
		A[1..m] medianUnion B[m + 1..n] // discard upper half of A and lower half of B
	} else {
		A[m + 1..n] medianUnion B[1..m] // discard lower half of A and upper half of B
	}
	// here we assume there is no copying between arrays since we can set
	// lo/hi bounds for indices
}

fun main(args: Array<String>) {
	val A = oneArrayOf(0, 1, 6, 9, 12, 13, 18, 20)
	val B = oneArrayOf(2, 4, 5, 8, 17, 19, 21, 23)
	println(A medianUnion B) // should be 9
}
