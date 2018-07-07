package dp

import util.get
import util.max
import util.set

// longest double increaasing subsequence

// X[1..n] is double increasing if X[i] > X[i - 2] for all i > 2
// intuitively, X is a perfect shuffle of two increasing sequences

// find the length of the ldis of A[1..n]

fun main(args: Array<String>) {
	val A = intArrayOf(0, 7, 1, 4, 6, 5, 3, 2)
	println(ldis(A)) // [0, 1, 4, 6, 5] -> 5
}

fun ldis(A: IntArray): Int {
	val n = A.size
	// trivial case
	if (n < 2) {
		return n
	}

	// dp(i, j): the length of ldis with first two elements A[i] and A[j]
	// memoization structure: 2d array dp[1..n - 1, 1..n] : dp[i, j] = dp(i, j)
	val dp = Array(n) { IntArray(n) }
	// space complexity: O(n^2)

	// base case:
	// dp(i, j) = 0 if i >= j // process only when i < j
	//          = 2 if j = n
	for (i in 0 until n - 1) {
		dp[i, n - 1] = 2
	}

	// we want max dp(i, j)
	// which is at least 2 considering the base case
	var max = 2

	// assume max{ } = 0
	// recursive case:
	// dp(i, j) = max{2, 1 + max{ dp(j, k) } : where i < j < k and A[i] < A[k]}
	// dependency: dp(i, j) depends on dp(j, k) where j > i and k > j
	//             i.e. all entries on a row below and to right of current entry

	// evaluation order: outer loop for i from down to top (n - 2 down to 0)
	for (i in n - 2 downTo 0) {
		// inner loop for j from right to left (n down to i + 1)
		for (j in n - 2 downTo i + 1) {
			var maxLen = 2
			// innermost loop for k from left to right (j + 1..n)
			for (k in j + 1 until n) {
				if (A[i] < A[k]) {
					maxLen = max(maxLen, 1 + dp[j, k])
				}
			}
			dp[i, j] = maxLen
			max = max(max, dp[i, j])
		}
	}
	// time complexity: O(n^2)

	return max
}