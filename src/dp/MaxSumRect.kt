package dp

import util.*

/*
Suppose you are given an array M[1..n, 1..n] of numbers, which may be positive,
negative, or zero, and which are not necessarily integers.

Describe an algorithm to find the largest sum of elements in any rectangular
subarray of the form M[i..i', j..j'].

Try to solve it in O(n^3) time
*/

fun OneArray<OneArray<Double>>.maxSumRect(): Int {
	val M = this
	val n = size // assuming M is n by n as the problem stated

	// row(i, j): max entry sum starting @ (i, j) (and move right in the same row)


	// dp(r, i, j): max rect sum in rectangles spanning r rows
	//              with the top-left corner @ (i, j)
	// defined for r, i, j in 1..n
	val dp = OneArray(n) { OneArray(n) { OneArray(n) { 0.0 } } } // space: O(n^3)
	// dp(1, i, j)

	// we want max_{r, i, j} { dp(r, i, j) }
	TODO()
}

fun main(args: Array<String>) {
	val M = oneArrayOf(
			oneArrayOf(1.0, 3.0, -1.0, 0.5),
			oneArrayOf(2.5, -2.5, 1.5, 0.25),
			oneArrayOf(1.5, -2.0, 2.5, 3.5),
			oneArrayOf(3.5, 2.0, -1.5, 2.0))
	println(M.maxSumRect())
}
