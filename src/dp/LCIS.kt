package dp

import get
import set
import max

// longest common increasing subsequence

// given two sequences A[1..m], and B[1..n]
// find the length of longest common sequence that is also increasing

fun main(args: Array<String>) {
	val A = intArrayOf(0, 1, 1, 2, 4, 3, 5)
	val B = intArrayOf(1, 0, 1, 2, 4, 3, 5, 4)
	println(lcis(A, B))
}

fun lcis(A: IntArray, B: IntArray): Int {
	val m = A.size
	val n = B.size

	// let M[1..l] be the sorted sequence of A and B intersected
	// M is strictly increasing
	val M = (A.toSet().intersect(B.toSet())).toMutableList()
	// time cost here is O( min(m, n) * log min(m, n) )
	M.sort()
	// add a sentinel value for comparing
	M.add(0, Int.MIN_VALUE)
	// l is O(min(m, n))
	val l = M.size

	// dp(i, j, k): len of lcis for A[1..i] and B[1..j] : every element <= M[k]
	// we want dp(m, n, l)

	// memoization structure: 3d array dp[0..m, 0..n, 1..l] : dp[i, j, k] = dp(i, j, k)
	val dp = Array(m + 1) { Array(n + 1) { IntArray(l) } }
	// space complexity: O(m * n * l)

	// dp(i, j, k) = 0 if i !in 1 to m or j !in 1 to n or k !in 1 to l
	//             = max{ dp(i - 1, j - 1, k - 1) + 1,
	//                    dp(i - 1, j, k),
	//                    dp(i, j - 1, k) } if M[k - 1] < A[i] = B[j] <= M[k]
	//             = max { dp(i - 1, j, k), dp(i, j - 1, k), dp(i, j, k - 1) } o/w

	// dependency: dp(i, j, k) depends on dp(i - 1, j - 1, k - 1),
	//                                    dp(i - 1, j, k),
	//                                    dp(i - 1, j - 1, k - 1)
	//                                    , and dp(i, j - 1, k)
	//             that is, entries in the previous table, entries to the left and to the upper-left

	// evaluation order: outermost loop for k from 1 to l
	for (k in 1 until l) {
		// no need to touch i = 0 or j = 0 since they are covered in the base case
		// middle loop for i from 1 to m (top to down)
		for (i in 1..m) {
			//i nnermost loop for i from 1 to n (left to right)
			for (j in 1..n) {
				dp[i, j, k] = if (M[k - 1] < A[i - 1] && A[i - 1] == B[j - 1] && B[j - 1] <= M[k]) {
					max(1 + if (k - 1 >= 0) dp[i - 1, j - 1, k - 1] else 0,
							dp[i - 1, j, k],
							dp[i, j - 1, k])
				} else {
					max(dp[i - 1, j, k],
							dp[i, j - 1, k],
							if (k - 1 >= 0) dp[i, j, k - 1] else 0)
				}
			}
		}
	}
	// time complexity: O(m * n * l) = O(m * n * min(m, n))

	return dp[m, n, l - 1]
}
