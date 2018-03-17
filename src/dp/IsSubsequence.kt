package dp

import get
import set

// given X[1..k] and Y[1..n] : k <= n

// 1. determine if X is a subsequence of Y
// ex. PPAP is a subsequence of PenPineappleApplePie
infix fun IntArray.isSubsequenceOf(Y: IntArray): Boolean {
	val X = this
	val k = X.size
	val n = Y.size

	// isSub(i, j): whether X[1..i] is a subsequence of Y[1..j]
	// memoization structure: 2d array dp[0..k, 0..n] : dp[i, j] = isSub(i, j)
	val dp = Array(k + 1) { Array(n + 1) { false } }
	// space complexity: O(k * n)

	// base case:
	// isSub(i, j) = true if i = 0 and j in 0..n
	//             = false if i > j >= 0 || i !in 0..k || j !in 0..n
	for (j in 0..n) {
		dp[0, j] = true
	}

	// recursive case:
	// isSub(i, j) = isSub(i - 1, j - 1) if X[i] = Y[j]
	//             = isSub(i, j - 1) o/w
	// dependency: isSub(i, j) depends on isSub(i, j - 1) and isSub(i - 1, j - 1)
	//             that is, entries to the left and to the upper-left
	// evaluation order: outer loop for i from 1 to k (top down)
	for (i in 1..k) {
		// inner loop for j from 1 to n (left to right)
		for (j in 1..n) {
			dp[i, j] = if (X[i - 1] == Y[j - 1]) {
				dp[i - 1, j - 1]
			} else {
				dp[i, j - 1]
			}
		}
	}
	// time complexity: O(k * n)

	// we want isSub(k, n)
	return dp[k, n]
}

// 2. determine the minimum number of elements in Y : after removing those elements
//    , X is no longer a subsequence of Y
//    , you can also find the longest subsequence of Y that is not a supersequence of X
// let me find the length of such subsequence = l
// and we can know the minimum number of elements to be removed as n - l
fun IntArray.subseqNotSuperseq(X: IntArray): Int {
	val Y = this
	val k = X.size
	val n = Y.size

	// sns(i, j) = len of subseq of Y[1..j] which is not a supersequnce of X[1..i]
	// memoization structure: 2d array dp[0..k, 0..n] : dp[i, j] = sns(i, j)
	val dp = Array(k + 1) { IntArray(n + 1) }
	// space complexity: O(n * k)

	// base case:
	// sns(i, 0) = 0
	// sns(0, j) = j
	for (j in 0..n) {
		dp[0, j] = j
	}
	// sns(i, j) = j if j < i
	for (i in 0..k) {
		for (j in 0 until i) {
			dp[i, j] = j
		}
	}

	// recursive case:
	// sns(i, j) = sns(i, j - 1) if X[i] = Y[j]
	//           = 1 + sns(i, j - 1) if X[i] != Y[j]
	// dependency: sns(i, j) depends on sns(i, j - 1) that is entry to the left
	// evaluation order: outer loop for i from 1 to k (top down)
	for (i in 1..k) {
		// inner loop for j from i to n (left to right)
		for (j in i..n) {
			dp[i, j] = dp[i, j - 1]
			if (X[i - 1] != Y[j - 1]) {
				dp[i, j] += 1
			}
		}
	}
	// time complexity: O(n * k)

	// we want sns(k, n)
	return dp[k, n]
}

fun main(args: Array<String>) {
	val X = intArrayOf(3, 5, 1, 2, 6)
	val Ys = arrayOf(
			intArrayOf(3, 5, 1, 2, 6), // true
			intArrayOf(3, 5, 2, 1, 1, 2, 6), // true
			intArrayOf(3, 5, 1, 6, 2, 2) // false
	)

	Ys.forEach { println(X isSubsequenceOf it) }

	// output should be 4, 6, 6
	Ys.forEach { println(it.subseqNotSuperseq(X)) }
}
