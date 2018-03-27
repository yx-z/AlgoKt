package dp

import util.max
import util.get
import util.set

// longest weakly increasing subsequence

// X[1..n] is weakly increasing if 2 * X[i] > X[i - 1] + X[i - 2], for all i > 2

// find the length of longest weakly increasing subsequence of A[1..n]

fun main(args: Array<String>) {
	val A = intArrayOf(1, 2, 3, 3, 3, 4)
	println(wis(A)) // [1, 2, 3, 3, 4] -> 5
}

fun wis(A: IntArray): Int {
	val n = A.size
	if (n < 3) {
		return n
	}

	// dp(i, j): len of lwis of A with first two elements A[i] and A[j]
	// assume util.max{ } = 0
	// dp(i, j) = 0 if i, j !in 0 until n || i >= j
	//          = 2 if j = n
	//          = 1 + util.max{ dp(j, k) } where k in j + 1 until n and 2 * A[k] > A[j] + A[i] o/w

	// we want util.max{ dp(i, j) }
	var max = Int.MIN_VALUE

	// use a 2d array dp[1..n, 1..n] : dp[i, j] = dp(i, j)
	// space complexity: O(n^2)
	val dp = Array(n) { IntArray(n) }

	// base case, a special case of wis when the length is only 2
	for (i in 0 until n) {
		dp[i, n - 1] = 2
	}

	// dp(i, j) depends on dp(j, k) for all k > j
	// so the evaluation order should be i from bottom up, i.e. n to 1
	// , and j from right to left, i.e. n to i + 1
	// time complexity: O(n^2 * n) = O(n^3)
	for (i in n - 3 downTo 0) {
		for (j in n - 2 downTo i + 1) {
			dp[i, j] = 1 + (dp[j]
					.filterIndexed { k, _ -> k in j + 1 until n && 2 * A[k] > A[j] + A[i] }
					.max() ?: 0)
			max = max(max, dp[i, j])
		}
	}

	return max
}
