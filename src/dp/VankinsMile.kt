package dp

import util.*

// given an n by n 2d array of ints, i.e. G[1..n, 1..n]
// a player can start at any entry but only move either right
// while standing on an entry, s/he can collect points in that entry
// the game ends when s/he decides to move off the grid from any edge
// find the maximum points one can get
fun main(args: Array<String>) {
	val grid = oneArrayOf(
			oneArrayOf(-1, 7, -8, 10, -5),
			oneArrayOf(-4, -9, 8, -6, 0),
			oneArrayOf(5, -2, -6, -6, 7),
			oneArrayOf(-7, 4, 7, -3, -3),
			oneArrayOf(7, 1, -6, 4, -9)
	)

//	println(grid.maxPoint())
}

fun OneArray<OneArray<Int>>.maxPoint(): Int {
	val G = this
	val n = size

	// dp(i, j): maxPoint we can get if we start @ G[i, j]
	// memoization structure: 2d array dp[1..n, 1..n] : dp[i, j] = dp(i, j)
	val dp = OneArray(n) { OneArray(n) { 0 } }
	// space complexity: O(n^2)

	// base case:
	// dp(i, j) = 0 if i, j !in 1..n
	dp.getterIndexOutOfBoundHandler = { OneArray(0) }
	for (i in 1..n) {
		dp[i].getterIndexOutOfBoundHandler = { 0 }
	}
	// dp(n, n) = G[n, n]
	dp[n, n] = G[n, n]

	// we want max_i, j { dp(i, j) }
	var max = dp[n, n]

	// dp(i, n) = G[i, n] + max{0, dp(i + 1, n) }
	for (i in n - 1 downTo 1) {
		dp[i, n] = G[i, n] + max(0, dp[i + 1, n])
		max = max(max, dp[i, n])
	}
	// dp(n, j) = G[n, j] + max{0, dp(n, j + 1) }
	for (j in n - 1 downTo 1) {
		dp[n, j] = G[n, j] + max(0, dp[n, j + 1])
		max = max(max, dp[n, j])
	}

	// recursive case:
	// dp(i, j) = G[i, j] + max{ dp(i + 1, j), dp(i, j + 1) }
	// dependency: dp(i, j) depends on dp(i + 1, j) and dp(i, j + 1)
	//             that is entries below and to the right
	// evaluation order: outer loop for i from n - 1 down to 1 (bottom up)
	for (i in n - 1 downTo 1) {
		// inner loop for j from n - 1 down to 1 (right to left)
		for (j in n - 1 downTo 1) {
			dp[i, j] = G[i, j] + max(dp[i + 1, j], dp[i, j + 1])
			max = max(max, dp[i, j])
		}
	}
	// time complexity: O(n^2)

//	dp.prettyPrintTable()

	return max
}