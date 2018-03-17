package dp

import get
import set

// given X[1..k] and Y[1..n] : k <= n
// determine if X is a subsequence of Y

// ex. PPAP is a subsequence of PenPineappleApplePie

infix fun IntArray.isSubsequenceOf(Y: IntArray): Boolean {
	val X = this
	val k = X.size
	val n = Y.size

	// isSub(i, j): whether X[1..i] is a subsequence of Y[1..j]
	// memoization structure: 2d array dp[0..k, 0..n] : dp[i, j] = isSub(i, j)
	val dp = Array(k + 1) { Array(n + 1) { false } }
	// space complexity: O(k * n)

	// base case:
	// isSub(i, j) = true if i = 0 and j = 0
	//             = false if i > j || i !in 0..k || j !in 0..n
	//             = true if i = 0 and j in 1..n
	dp[0, 0] = true
	for (j in 1..n) {
		dp[0, j] = true
	}

	// recursive case:
	// isSub(i, j) = isSub(i - 1, j - 1) if X[i] = Y[j]
	//             = isSub(i, j - 1) o/w
	// dependency: isSub(i, j) depends on isSub(i, j - 1) and isSub(i - 1, j - 1)
	//             that is, entries to the left and to the upper-left
	// evaluation order: outer loop for i from 1 to k (top down)
	for (i in 1..k) {
		// inner loop for j from 1 to n (left to right)
		for (j in 1..n) {
			dp[i, j] = if (X[i - 1] == Y[j - 1]) {
				dp[i - 1, j - 1]
			} else {
				dp[i, j - 1]
			}
		}
	}
	// time complexity: O(k * n)

	// we want isSub(k, n)
	return dp[k, n]
}

fun main(args: Array<String>) {
	val X = intArrayOf(3, 5, 1, 2, 6)
	val Ys = arrayOf(
			intArrayOf(3, 5, 1, 2, 6), // true
			intArrayOf(3, 5, 2, 1, 1, 2, 6), // true
			intArrayOf(3, 5, 1, 6, 2, 2) // false
	)

	Ys.forEach { println(X isSubsequenceOf it) }
}
