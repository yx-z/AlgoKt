package dp

import get
import println
import set

// given two strings X and Y, a shuffle of them is interspersing characters of X and Y
// while keeping them in the same order
// ex. "abc" shuffle "123" -> "abc123", "a1b2c3", "1ab23c", ...

// 1. given A[1..m], B[1..n], and C[1..m + n] determine if C is a shuffle of A and B
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
				C[a + b - 1] == A[a - 1] && A[a - 1] == B[b - 1] -> dp[a - 1, b] || dp[a, b - 1]
				C[a + b - 1] == A[a - 1] -> dp[a - 1, b]
				C[a + b - 1] == B[b - 1] -> dp[a, b - 1]
				else -> false
			}
		}
	}
	// time complexity: O(mn)

	// we want dp(m, n)
	return dp[m, n]
}

// a smooth shuffle of X and Y is a shuffle of X and Y
// that never uses more than two consecutive strings of either string
// ex. "abcd" smoothShuffle "1234" -> "ab12cd34", "a12b3cd4", ...
//     but not "abc12d34" since "abc", has length more than two
//     neither is "ab1234cd" (due to "1234")
// 2. given X[1..m], Y[1..n], and Z[1..m + n], determine if Z is a smooth shuffle of X and Y
fun String.isSmoothShuffle(X: String, Y: String): Boolean {
	val Z = this
	val m = X.length
	val n = Y.length

	// ss(i, j): whether Z[1..i + j] is a smooth shuffle of X[1..i] and Y[1..j]
	// ss(i, j) = 0 if Z[1..i + j] is NOT a smooth shuffle of X[1..i] and Y[1..j]
	//          = 1 if Z[1..i + j] is a smooth shuffle of X[1..i] and Y[1..j] AND Z[i + j] represents X[i]
	//          = 2 if Z[1..i + j] is a smooth shuffle of X[1..i] and Y[1..j] And Z[i + j] represents Y[j]

	// memoization structure: 2d array dp[0..m, 0..n] : dp[i, j] = ss(i, j)
	val dp = Array(m + 1) { Array(n + 1) { 0 } }
	// space complexity: O(mn)

	// base case:
	// ss(i, j) = 1 if i = 0 and j = 0
	//          = Z[1..i] == X[1..i] ? 1 : 0 if j = 0 and 1 <= i <= 2
	//          = Z[1..j] == Y[1..j] ? 2 : 0 if i = 0 and 1 <= j <= 2
	//          = 0 if (i = 0 and j > 2) or (j = 0 and i > 2)
	//          = 0 if i !in 0..m || j !in 0..n
	dp[0, 0] = 1
	dp[1, 0] = if (Z[0] == X[0]) 1 else 0
	dp[2, 0] = if (Z[0] == X[0] && Z[1] == X[1]) 1 else 0
	dp[0, 1] = if (Z[0] == Y[0]) 2 else 0
	dp[0, 2] = if (Z[0] == Y[0] && Z[1] == Y[1]) 2 else 0

	// recursive case:
	// ss(i, j) = when Z[i + j] = X[i] = Y[j]: ORing the following cases
	//            when Z[i + j] = X[i]: ss(i - 1, j) != 0 && if (ss(i - 1, j) == 1) then (ss(i - 2, j) == 2 || i - 2 == 0) ? 1 : 0
	//            when Z[i + j] = Y[j]: ss(i, j - 1) != 0 && if (ss(i, j - 1) == 2) then ss(i, j - 2) == 1 ? 2 : 0
	//            else: 0
	// dependency: ss(i, j) depends on ss(i - 1, j), ss(i, j - 1),
	//                                 ss(i - 2, j ), and ss(i, j - 2)
	//             , that is entries above and entries to the left
	// evaluation order: outer loop for i from 1 to m (top down)
	for (i in 1..m) {
		// inner loop for j from 1 to n (left to right)
		for (j in 1..n) {
			dp[i, j] = when {
//				Z[i + j - 1] == X[i - 1] && X[i - 1] == Y[j - 1] -> {
//				}
				Z[i + j - 1] == X[i - 1] -> {
					if (dp[i - 1, j] == 0) {
						0
					} else {
						if (dp[i - 1, j] == 1) {
							if (i - 2 == 0) {
								1
							} else {
								if (i - 2 > 0 && dp[i - 2, j] == 2) {
									1
								} else {
									0
								}
							}
						} else {
							1
						}
					}
				}
				Z[i + j - 1] == Y[j - 1] ->
					if (dp[i, j - 1] == 0) {
						0
					} else {
						if (dp[i, j - 1] == 2) {
							if (j - 2 >= 0 && dp[i, j - 2] == 1) {
								2
							} else {
								0
							}
						} else {
							2
						}
					}
				else -> 0
			}
		}
	}
	// time complexity: O(mn)
	dp.println(true)

	// we want ss(m, n) = 1 or 2 (simply != 0)
	return dp[m, n] != 0
}

fun main(args: Array<String>) {
	// 1.
	val A = "abc"
	val B = "123"
	val Cs = arrayOf(
			"ab132c", // false
			"ab123c", // true
			"ab1c23") // true
	Cs.forEach {
		//		println(it.isShuffle(A, B))
	}

	val X = "abcd"
	val Y = "12345"
	val Zs = arrayOf(
			"ab123cd45", // false
			"ab12c34d5", // true
			"1ab23c4d5" // true
	)
	Zs.forEach {
		println(it.isSmoothShuffle(X, Y))
	}
}

