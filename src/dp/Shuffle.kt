package dp

import get
import set

// given two strings X and Y, a shuffle of them is interspersing characters of X and Y
// while keeping them in the same order
// ex. "abc" shuffle "123" -> "abc123", "a1b2c3", "1ab23c", ...

// 1. given A[1..m], B[1..n], and C[1..m + n] determine if C is a shuffle of A and B
fun main(args: Array<String>) {
	val A = "abc"
	val B = "123"
	val Cs = arrayOf(
			"ab132c", // false
			"ab123c", // true
			"ab1c23") // true
	Cs.forEach {
		println(it.isShuffle(A, B))
	}
}

fun String.isShuffle(A: String, B: String): Boolean {
	val C = this // follow the naming convention in the problem
	val m = A.length
	val n = B.length

	// dp(a, b): whether C[1..a + b] is a shuffle of A[1..a] and B[1..b]
	// memoization structure: 2d array dp[0..m, 0..n] : dp[a, b] = dp(a, b)
	val dp = Array(m + 1) { Array(n + 1) { false } }
	// space complexity: O(mn)

	// base case:
	// dp(a, b) = true if a = 0 and b = 0 (empty string is the shuffle of two empty strings)
	//          = false if a !in 0..m || b !in 0..n
	//          = dp(0, b - 1) && C[b] == B[b] if a = 0
	//          = dp(a - 1, 0) && C[a] == A[a] if b = 0
	dp[0, 0] = true
	for (a in 1..m) {
		dp[a, 0] = dp[a - 1, 0] && C[a - 1] == A[a - 1]
	}
	for (b in 1..n) {
		dp[0, b] = dp[0, b - 1] && C[b - 1] == B[b - 1]
	}
	// time complexity here: O(m + n)


	// recursive case
	// dp(a, b) = dp(a - 1, b) or dp(a, b - 1) if C[a + b] = A[a] = B[b]
	//          = dp(a - 1, b) if C[a + b] = A[a]
	//          = dp(a, b - 1) if C[a + b] = B[b]
	//          = false o/w
	// dependency: dp(a, b) depends on dp(a - 1, b) and dp(a, b - 1)
	//             that is the entry above and entry to the left
	// evaluation order: outer loop for a from 1 to m (top down)
	for (a in 1..m) {
		// inner loop for b from 1 to n (left to right)
		for (b in 1..n) {
			dp[a, b] = when {
				C[a + b - 1] == A[a - 1] && A[a - 1] == B[b - 1] -> {
					when {
						a - 1 >= 0 && b - 1 >= 0 -> dp[a - 1, b] || dp[a, b - 1]
						a - 1 >= 0 -> dp[a - 1, b]
						b - 1 >= 0 -> dp[a, b - 1]
						else -> false
					}
				}
				C[a + b - 1] == A[a - 1] -> {
					if (a - 1 >= 0) {
						dp[a - 1, b]
					} else {
						false
					}
				}
				C[a + b - 1] == B[b - 1] -> {
					if (b - 1 >= 0) {
						dp[a, b - 1]
					} else {
						false
					}
				}
				else -> false
			}
		}
	}
	// time complexity: O(mn)

	// we want dp(m, n)
	return dp[m, n]
}