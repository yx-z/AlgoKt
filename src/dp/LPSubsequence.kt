package dp

import max
import get
import set

// Longest Palindrome Subsequence

fun main(args: Array<String>) {
	val A = intArrayOf(1, 5, 2, 6, 6, 5)
	println(lps(A))
}

fun lps(A: IntArray): Int {
	val n = A.size
	// dp(i, j): len of lps of A[i..j]
	// use a 2d array dp[1..n, 1..n] : dp[i, j] = dp(i, j)
	val dp = Array(n) { IntArray(n) }
	// space complexity: O(n^2)

	// base cases:
	// dp(i, j) = 0 if i > j
	//          = 1 if i = j
	for (i in 0 until n) {
		for (j in 0 until n) {
			if (i == j) {
				dp[i, j] = 1
			}
		}
	}

	// recursive cases:
	// dp(i, j) = max{ 2 + dp(i + 1, j - 1), dp(i + 1, j), dp(i, j - 1)} if A[i] = A[j]
	//          = max{ dp(i + 1, j - 1), dp(i + 1, j), dp(i, j - 1) } o/w
	// dependency: dp(i, j) depends on dp(i + 1, j - 1), dp(i + 1, j), and dp(i, j - 1)
	//             that is, left, lower-left, and lower entry
	// evaluation order: outer loop for i: bottom to top, i.e. n down to 1,
	//                   inner loop for j: left to right, i.e. 1 to n
	for (i in n - 1 downTo 0) {
		// the following has been evaluated as base cases above
		// dp[i, j] = 0 for all j < i
		// dp[i, j] = 1 for all j = i
		for (j in i + 1 until n) {
			dp[i, j] = max(dp[i + 1, j - 1], dp[i + 1, j], dp[i, j - 1])
			if (A[i] == A[j]) {
				dp[i, j] = max(dp[i, j], 2 + dp[i + 1, j - 1])
			}
		}
	}
	// time complexity: O(n^2)

	// we want dp[1, n]
	return dp[0, n - 1]
}
