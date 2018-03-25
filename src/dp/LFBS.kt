package dp

import util.max
import util.set
import util.get
import util.OneArray
import util.toCharOneArray

// find the length of the longest CONTIGUOUS SUBSTRING that appears both
// forward and backward in T[1..n]
fun main(args: Array<String>) {
	val Ts = arrayOf(
			"ALGORITHM", // 0
			"RECURSION", // "R ... R" -> 1
			"REDIVIDE", // "EDI ... IDE" -> 3
			"DYNAMICPROGRAMMINGMANYTIMES" // "YNAM ... MANY" -> 4
	)

	Ts
			.map { it.toCharOneArray() }
			.forEach { println(it.lfbs()) }
}

fun OneArray<Char>.lfbs(): Int {
	val T = this
	val n = T.size

	if (n < 2) {
		return 0
	}

	// dp(i, j) = len of substring starting @ i and ending @ j
	// memoization structure: 2d array dp[1..n, 1..n] : dp[i, j] = dp(i, j)
	val dp = OneArray(n) { OneArray(n) { 0 } }
	// space complexity: O(n^2)

	// we want util.max{ dp(i, j) }
	var max = 0

	// dp(i, j) = 0 if T[i] != T[j] or i >= j
	// dp(i, j) = 1 + dp(i + 1, j - 1) o/w
	// dependency: dp(i, j) depends on dp(i + 1, j - 1) that is entry tu the lower left
	// evaluation order: outer loop for i from n - 1 down tu 1 (bottom up)
	for (i in n - 1 downTo 1) {
		// inner loop for j from i + 1..n
		for (j in i + 1..n) {
			dp[i, j] = if (T[i] != T[j]) {
				0
			} else {
				1 + dp[i + 1, j - 1]
			}
			max = max(max, dp[i, j])
		}
	}
	// time complexity: O(n^2)

	return max
}
