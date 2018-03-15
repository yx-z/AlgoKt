package dp

import min
import max
import get
import set

// longest common increasing subsequence

// given two sequences A[1..m], and B[1..n]
// find the length of longest common sequence that is also increasing

fun main(args: Array<String>) {
	val A = intArrayOf(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5, 8, 9, 7, 9, 3)
	val B = intArrayOf(1, 4, 1, 4, 2, 1, 3, 5, 6, 2, 3, 7, 3, 0, 9, 5)
	println(lcis(A, B)) // [1, 4, 5, 6, 7, 9] -> 6
}

fun lcis(A: IntArray, B: IntArray): Int {
	val m = A.size
	val n = B.size

	// let C[1..c] be the sorted array of common elements of A and B
	// this step takes O((m + n) log (m + n)) time and O(m + n) space
	val common = A.toSet().intersect(B.toSet())
	val C = common.toMutableList()
	C.sort()
	val c = C.size

	// dp(i, j, k): len of lcis for A[0..i] and B[0..j] with the largest number = C[k]
	// memoization structure: 3d array dp[0..m, 0..n, 1..c] : dp[i, j, k] = dp(i, j, k)
	val dp = Array(m + 1) { Array(n + 1) { IntArray(c) } }
	// space complexity: O(mn(m + n))

	// we want max_k{ dp(m, n, k) }
	var max = 0

	// base case:
	// dp(i, j, k) = 0 if i = 0 || j = 0
	// this step takes O(m + n) time
	for (i in 0..m) {
		for (k in 0 until c) {
			dp[i, 0, k] = 0
		}
	}
	for (j in 0..n) {
		for (k in 0 until c) {
			dp[0, j, k] = 0
		}
	}

	// recursive case:
	// dp(i, j, k) = max{ 1 + dp(i - 1, j - 1, k - 1),
	//                    dp(i, j - 1, k),
	//                    dp(i + 1, j , k) } if A[i] = B[j] = C[k]
	//             = max{ dp(i, j - 1, k), dp(i + 1, j, k) } o/w
	// dependency: dp(i, j, k) depends on entries in table k - 1,
	//             , entries to the left and entries one row below
	// evaluation order: outermost loop for k from 1..c
	//                   middle loop for i from bottom to top (m down to 1)
	//                   innermost loop for j from left to right (1 to n)
	for (k in 0 until c) {
		// we have covered i = 0 and j = 0 in base case
		for (i in m downTo 1) {
			for (j in n downTo 1) {
				dp[i, j, k] = if ()
			}
		}
	}
	// time complexity: O(mn(m + n))

	return max
}
