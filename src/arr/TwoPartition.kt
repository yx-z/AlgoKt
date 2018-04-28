package arr

import util.OneArray
import util.oneArrayOf

// given a list of 2n numbers A[1..2n]
// determine whether it can be split into n pairs : sum of each
// pair is the same, assuming that numbers are all distinct in polynomial time
fun OneArray<Int>.twoPartition(): Boolean {
	val A = this
	val n = size / 2

	for (i in 2..2 * n) {
		val target = A[1] + A[i]
		if (canPartition(target)) {
			return true
		}
	}
	// O(n^3)

	return false
}

fun OneArray<Int>.canPartition(target: Int): Boolean {
	forEach {
		// possible easy improvement using hash map for up to O(n)
		// but the problem only asks for polynomial time (since this is derived
		// from an np-hard problem 3partition)
		if (!contains(target - it)) {
			return false
		}
	}
	// O(n^2)

	return true
}

fun main(args: Array<String>) {
	val A = oneArrayOf(1, 99, 2, 98, 3, 91)
	println(A.twoPartition())
}