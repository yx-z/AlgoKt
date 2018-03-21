package dp

import util.OneArray
import util.max
import util.oneArrayOf

// suppose you are in a dancing competition
// you can dance multiple songs in a total number of n songs
// score for each song is stored in Score[1..n]
// but if you dance the i-th song, you cannot dance the next Wait[i](s) songs
// find the maximum score you can get after cleverly deciding which song to dance

fun main(args: Array<String>) {
	// ignore standard Java naming conventions here
	// let's follow the naming convention in the problem statement
	val Score = oneArrayOf(10, 8, 5, 10, 9, 4)
	val Wait = oneArrayOf(1, 4, 1, 2, 3, 1)
	println(maxScore(Score, Wait))
}

fun maxScore(Score: OneArray<Int>, Wait: OneArray<Int>): Int {
	val n = Score.size // == Wait.size by assumption

	// dp(i): max score I can get if I start dancing at the i-th song
	// memoization structure: 1d array dp[1..n] : dp[i] = dp(i)
	val dp = OneArray(n) { Score[it] }
	// space complexity: O(n)

	// base case:
	// dp(i) = 0 if i !in 1..n
	dp.getterIndexOutOfBoundHandler = { 0 }
	// dp(n) = Score[n]
	dp[n] = Score[n]

	// recursive case:
	// dp(i) = max{ dp(i + 1), Score[i] + dp(i + 1 + Wait[i]) }
	// dependency: dp[i] depends on dp[j] where j > i, that is entries to the right
	// evaluation order: i from n - 1 down to 1 (right to left)
	for (i in n - 1 downTo 1) {
		dp[i] = max(dp[i + 1], Score[i] + dp[i + 1 + Wait[i]])
	}
	// time complexity: O(n)

	// we want max{ dp[i] }
	return dp.max() ?: 0
}
