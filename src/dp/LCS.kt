package dp

import util.*

// Longest Common Subsequence
fun main(args: Array<String>) {
	val a1 = intArrayOf(1, 5, 0, 12, 9)
	val a2 = intArrayOf(5, 12, 9, 1)
//	println(a1 lcs a2) // [5, 12, 9] -> 3

	// how about three arrays?
	val A = oneArrayOf(1, 2, 3, 5, 4, 1)
	val B = oneArrayOf(2, 5, 4, 7, 2, 3)
	val C = oneArrayOf(3, 5, 4, 1, 3, 2)
	println(lcs3(A, B, C))
}

infix fun IntArray.lcs(that: IntArray): Int {
	// dp[i, j]: length of lcs for this[0 until i] & that[0 until j]
	// dp[i, j] = 0, if (i == 0 || j == 0)
	//          = if (this[i] == that[j]) {
	//                  max(1 + dp[i - 1, j - 1], dp[i - 1, j], dp[i, j - 1])
	//            } else {
	//                  max(dp[i - 1, j], dp[i, j - 1], dp[i - 1, j - 1])
	//            }
	// we want dp[size, that.size]
	val dp = Array(size + 1) { IntArray(that.size + 1) }
	for (i in 1..size) {
		for (j in 1..that.size) {
			dp[i, j] = if (this[i - 1] == that[j - 1]) {
				max(1 + dp[i - 1, j - 1], dp[i - 1, j], dp[i, j - 1])
			} else {
				max(dp[i - 1, j], dp[i, j - 1], dp[i - 1, j - 1])
			}
		}
	}
	return dp[size, that.size]
}

// A[1..n], B[1..n], C[1..n]
fun lcs3(A: OneArray<Int>, B: OneArray<Int>, C: OneArray<Int>): Int {
	val n = A.size // == B.size == C.size
	val dp = Array(n + 1) { Array(n + 1) { Array(n + 1) { 0 } } }
	for (i in 1..n) {
		for (j in 1..n) {
			for (k in 1..n) {
				dp[i, j, k] = if (A[i] == B[j] && B[j] == C[k]) {
					1 + dp[i - 1, j - 1, k - 1]
				} else {
					max(dp[i - 1, j, k], dp[i, j - 1, k], dp[i, j, k - 1])
				}

			}
		}
	}
	return dp[n, n, n]
}
