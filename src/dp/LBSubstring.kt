package dp

import util.*

// given A[1..n] composed of '[', ']', '(', ')', ex. A = ([]])()
// find the length of
// longest balanced subsequence of A, i.e. matching parenthesis/brackets
// ex. ([] )() -> 6 in the example above
fun main(args: Array<String>) {
	val A = "([]])()".toCharOneArray()
	println(A.lbs()) // 6
}

fun OneArray<Char>.lbs(): Int {
	val A = this
	val n = size

	// dp(i, j): len of lbs of A[i..j]
	// memoization structure: 2d arr dp[1..n, 1..n]
	val dp = OneArray(n) { OneArray(n) { 0 } }
	// space: O(n^2)

	// base case:
	// dp(i, j) = 0 if i, j !in indices or i >= j

	// recursive case:
	// dp(i, j) = max_k { 2 + dp(i + 1, j - 1), dp(i, k) + dp(k + 1, j) }
	//            where k in i + 1..j - 1 if A[i] matches A[j]
	//          = max_k { dp(i, k) + dp(k + 1, j) } same k as above o/w
	// dependency: dp(i, j) depends on dp(i + 1, j - 1), dp(i, k), and dp(k + 1, j)
	//             , that is entries below, to the left, and to the lower-left
	// eval order: outer loop for i decreasing from n - 1 down to 1
	for (i in n - 1 downTo 1) {
		// inner loop for j increasing from i + 1 to n
		for (j in i + 1..n) {
			// loop for k doesn't matter, say increasing from i + 1 to j - 1
			dp[i, j] = (i + 1 until j)
					.map { k -> dp[i, k] + dp[k + 1, j] }
					.max() ?: 0
			if (A[i] match A[j]) {
				dp[i, j] = max(dp[i, j], 2 + dp[i + 1, j - 1])
			}
		}
	}
	// time: O(n^3)

//	dp.prettyPrintTable()

	return dp[1, n]
}

infix fun Char.match(that: Char) = when (this) {
	'(' -> that == ')'
	'[' -> that == ']'
	else -> false
}
