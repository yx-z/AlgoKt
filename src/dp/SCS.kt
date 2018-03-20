package dp

import util.get
import util.set
import util.min

// shortest common supersequence

// given A[1..m] and B[1..n], their common supersequence is a sequence that
// contains both A and B as subsequences

// find the minimum length of such supersequence

fun main(args: Array<String>) {
	val A = intArrayOf(5, 6, 2)
	val B = intArrayOf(1, 5, 2, 5)
	println(A scs B) // [1, 5, 6, 2, 5] -> 5
}

infix fun IntArray.scs(that: IntArray): Int {
	// dp(i, j): length of shortest common supersequence for A[1..i] and B[1..j]
	// dp(i, j) = j if i = 0
	//          = i if j = 0
	//          = util.min{ 1 + dp(i - 1, j - 1), 1 + dp(i, j - 1), 1 + dp(i - 1, j) } if A[i] = B[j]
	//          = util.min{ 2 + dp(i - 1, j - 1), 1 + dp(i, j - 1), 1 + dp(i - 1, j) } o/w
	// memoization structure: 2d array dp[1..m][1..n]
	// space complexity: O(mn)
	val dp = Array(size + 1) { IntArray(that.size + 1) }

	// dependencies: dp[i, j] depends on dp[i - 1, j], dp[i, j - 1] and dp[i - 1, j - 1]
	// evaluation order: i from 0..m, j from 0..n
	// time complexity: O(n^2)
	for (i in 1..size) {
		for (j in 1..that.size) {
			dp[i, j] = when {
				i - 1 == 0 -> j - 1
				j - 1 == 0 -> i - 1
				this[i - 1] == that[j - 1] -> min(1 + dp[i - 1, j - 1], dp[i, j - 1], 1 + dp[i - 1, j])
				else -> min(2 + dp[i - 1, j - 1], 1 + dp[i, j - 1] + 1 + dp[i - 1, j])
			}
		}
	}

	// we want dp(m, n)
	return dp[size, that.size]
}