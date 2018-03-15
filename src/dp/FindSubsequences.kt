package dp

import set
import get

fun main(args: Array<String>) {
	// given two arrays L(ong)[1..m], S(hort)[1..n]
	// find the number of subsequences in L that equals to S
	val L = intArrayOf(3, 1, 2, 2, 3, 3, 4, 4)
	val S = intArrayOf(2, 3, 4)
	println(findSubseq(L, S)) // 8 since there are 2 ways of picking 2, 2 of 4, and 2 of 3
}

fun findSubseq(L: IntArray, S: IntArray): Int {
	val m = L.size
	val n = S.size

	// dp(i, j): # of subsequences in L[1..i] that equals to S[1..j]
	// use 2d array dp[0..m, 0..n] : dp[i, j] = dp(i, j)
	// dp(i, j) = 0 if i < j
	val dp = Array(m + 1) { IntArray(n + 1) }
	// space complexity: O(mn)

	// dp(i, 0) = 1 since empty can be a subsequence for any sequence including empty sequence itself
	for (i in 0..m) {
		dp[i, 0] = 1
	}

	// dp(i, j) = dp(i - 1, j) + dp(i - 1, j - 1) if L[i] = S[j]
	//          = dp(i - 1, j) o/w
	// dependency: dp[i, j] depends on dp[i - 1, j], and dp[i - 1, j - 1], i.e.
	//             dp[i, j] depends on the upper entry and the upper-left entry
	// evaluation order: outer loop for j from left to right (1..n)
	//                   and inner loop for i from top to bottom (1..m)
	for (j in 1..n) {
		for (i in 1..m) {
			dp[i, j] = dp[i - 1, j]
			if (L[i - 1] == S[j - 1]) {
				dp[i, j] += dp[i - 1, j - 1]
			}
		}
	}
	// time complexity: O(mn)

	// we want dp(m, n)
	return dp[m][n]
}