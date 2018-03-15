package dp

import max

// longest double increaasing subsequence

// X[1..n] is double increasing if X[i] > X[i - 2] for all i > 2
// intuitively, X is a perfect shuffle of two increasing sequences

// find the length of the ldis of A[1..n]

fun main(args: Array<String>) {
	val A = intArrayOf(1, 2, 5, 8, 6)
	println(ldis(A)) // [1, 2, 5, 8, 6] -> 5, the normal lis is [1, 2, 5, 8] -> 4
}

fun ldis(A: IntArray): Int {
	val n = A.size
	// trivial case
	if (n <= 2) {
		return n
	}

	// dp(i): (len of ldis starting @ A[i], second element in that sequence)
	// use a 1d array dp[1..n] : dp[i] = dp(i)
	val dp = Array(n) { 0 to 0 }
	// space complexity: O(n)

	// we want max_i{ dp[i].first }
	var max = Int.MIN_VALUE

	// base cases:
	// dp(i) = (0, 0) if i > n
	//       = (1, A[n]) if i = n
	//       = (2, A[n - 1]) if i = n - 1
	dp[n - 1] = 1 to A[n - 1]
	dp[n - 2] = 2 to A[n - 1]

	// assume max{ } = 0
	// recursive case:
	// dp(i) = 1 + max{ dp(k)_1 } where k in i + 1..n and A[i] < dp(k)_2
	// dependency: dp(i) depends on dp(k) for all k > i, i.e. entries to the right
	// evaluation order: i from right to left, i.e. n down to 1
	for (i in n - 3 downTo 0) {
		var len = Int.MIN_VALUE
		var elem = A[i]
		for (k in i + 1 until n) {
			if (A[i] < dp[k].second) {
				if (len < 1 + dp[k].first) {
					len = 1 + dp[k].first
					elem = dp[k].second
				}
			}
		}
		dp[i] = len to elem
		max = max(max, dp[i].first)
	}
	// time complexity: O(n^2)

	return max
}