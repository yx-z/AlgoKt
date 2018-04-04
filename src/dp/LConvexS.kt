package dp

import util.*

// longest convex subsequence
// a convex sequence satisfies a[i] - a[i - 1] > a[i - 1] - a[i - 2]
fun main(args: Array<String>) {
	val A = oneArrayOf(1, 4, 8, 13, 14, 18, 19)
	println(A.lcs())
}

fun OneArray<Int>.lcs(): Int {
	val A = this
	val n = size

	// dp[i, j] = len of lcs w/ 1st 2 elem as A[i] and A[j]
	// memoization structure: 2d arr dp[1..n, 1..n]
	val dp = OneArray(n) { OneArray(n) { 0 } }
	// space: O(n^2)

	// base case:
	// dp[i, j] = 0 if i, j !in 1..n OR i >= j
	//          = 2 if j = n
	for (i in 1 until n) {
		dp[i, n] = 2
	}

	// recursive case:
	// assume max { } = 0
	// dp[i, j] = 1 + max_k { dp[j, k] } where k in j + 1..n : A[k] - A[j] > A[j] - A[i]
	// dependency: dp[i, j] depends on dp[j, k] where k > j > i
	//             so it is entries to the lower right
	// eval order: outer loop for i from n - 2 down to 1
	for (i in n - 2 downTo 1) {
		// inner loop for j is a no care, say i + 1..n
		for (j in i + 1 until n) {
			dp[i, j] = 1 + ((j + 1..n)
					.filter { k -> A[k] - A[j] > A[j] - A[i] }
					.map { k -> dp[j, k] }
					.max() ?: 0)
		}
	}
	// time: O(n^2)

//	dp.prettyPrintTable()

	// we want max_{i, j} { dp[i, j] }
	return dp.maxBy { it.max() ?: 0 }?.max() ?: 0
}