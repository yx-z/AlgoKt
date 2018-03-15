package dp

import get
import set
import max

// longest common increasing subsequence

// given two sequences A[1..m], and B[1..n]
// find the length of longest common sequence that is also increasing

fun main(args: Array<String>) {
	val A = intArrayOf(1, 5, 6)
	val B = intArrayOf(1, 5, 3, 6)
	println(lcis(A, B))
}

fun lcis(A: IntArray, B: IntArray): Int {
	val m = A.size
	val n = B.size

	// let M[1..l] be the sorted sequence of A and B intersected
	// M is strictly increasing
	val M = (A.toSet().intersect(B.toSet())).toMutableList()
	// sorting costs O(min(m, n) * log min(m, n)) time
	M.sort()
	// l is O(min(m, n))
	val l = M.size

	// dp(i, j, k): len of lcis for A[1..i] and B[1..j] : last element = M[k]
	// we want max_k{ dp(m, n, k) }
	var max = 0

	// memoization structure: 3d array dp[0..m, 0..n, 1..l] : dp[i, j, k] = dp(i, j, k)
	val dp = Array(m + 1) { Array(n + 1) { IntArray(l) } }
	// space complexity: O(m * n * l)

	// assume max{ } = 0
	// dp(i, j, k) = 0 if i !in 1..m or j !in 1..n or k !in 1..l
	//             = max{ 1 + max_p{ dp(i - 1, j - 1, p) : p < k },
	//                    dp(i - 1, j, k),
	//                    dp(i, j - 1, k) } if A[i] = B[j] = M[k]
	//             = max { dp(i - 1, j, k), dp(i, j - 1, k) } o/w

	// dependency: dp(i, j, k) depends on entries in the previous table,
	//                                    entries to the left and to the upper-left

	// evaluation order: outermost loop for k from 1 to l
	for (k in 0 until l) {
		// no need to touch i = 0 or j = 0 since they are covered in the base case
		// middle loop for i from 1 to m (top to down)
		for (i in 1..m) {
			//innermost loop for i from 1 to n (left to right)
			for (j in 1..n) {
				dp[i, j, k] = max(dp[i - 1, j, k], dp[i, j - 1, k])

				if (A[i - 1] == B[j - 1] && B[j - 1] <= M[k]) {
					dp[i, j, k] = max(dp[i, j, k], 1 + ((0 until k).map { dp[i - 1, j - 1, it] }
							.max() ?: 0))
				}

				if (i == m && j == n) {
					max = max(max, dp[i, j, k])
				}
			}
		}
	}
	// time complexity: O(m * n * l) = O(m * n * min(m, n))

	return max
}
