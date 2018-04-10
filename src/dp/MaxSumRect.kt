package dp

import util.*

/*
Suppose you are given an array M[1..n, 1..n] of numbers, which may be positive,
negative, or zero, and which are not necessarily integers.

Describe an algorithm to find the largest sum of elements in any rectangular
subarray of the form M[i..i', j..j'].

Try to solve it in O(n^3) time
*/

fun OneArray<OneArray<Double>>.maxSumRect(): Double {
	val M = this
	val n = size // assuming M is n by n as the problem stated

	// dp(i, j): an array of column sums from row i to row j
	//           where i, j in 1..n and defined only for j >= i
	val dp = OneArray(n) { OneArray(n) { OneArray(n) { 0.0 } } } // space: O(n^3)

	// dp(i, j) = M[i] if i = j
	//          = dp(i, j - 1) + M[j] o/w
	//            where we are using python-like array addition w/ broadcasting
	for (i in 1 until n) {
		for (j in i..n) {
			dp[i, j] = if (i == j) M[i].copy() else dp[i, j - 1] + M[j]
		}
	}
	// O(n^3)

	// now we do Kadane's Algorithm as in MaxSum and kep track of the max
	var max = 0.0
	for (i in 1 until n) {
		for (j in i..n) {
			val currMax = dp[i, j].maxSum()
			max = max(max, currMax)
		}
	}
	// O(n^3)

	return max
}

operator fun OneArray<Double>.plus(that: OneArray<Double>) = indices.map { this[it] + that[it] }.toOneArray()

fun OneArray<Double>.maxSum(): Double {
	var maxEndingHere = 0.0
	var maxSoFar = 0.0
	forEach {
		maxEndingHere += it
		maxEndingHere = max(maxEndingHere, 0.0)
		maxSoFar = max(maxSoFar, maxEndingHere)
	}
	return maxSoFar
}

fun main(args: Array<String>) {
	val M = oneArrayOf(
			oneArrayOf(1.0, 3.0, -1.0, 0.5),
			oneArrayOf(2.5, -2.5, 1.5, 0.25),
			oneArrayOf(1.5, -2.0, 2.5, 3.5),
			oneArrayOf(3.5, 2.0, -1.5, 2.0))
	println(M.maxSumRect())
}
