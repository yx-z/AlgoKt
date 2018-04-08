package dp

import util.*

// shortest common supersequence

// given A[1..m] and B[1..n], their common supersequence is a sequence that
// contains both A and B as subsequences

// find the minimum length of such supersequence

fun main(args: Array<String>) {
	val A = intArrayOf(5, 6, 2)
	val B = intArrayOf(1, 5, 2, 5)
//	println(A scs B) // [1, 5, 6, 2, 5] -> 5

	val X = "AxxBxxCDxEF".toCharOneArray()
	val Y = "yyABCDyEyFy".toCharOneArray()
	val Z = "zAzzBCDzEFz".toCharOneArray()

	println(scs(X, Y, Z)) // 21
}

infix fun IntArray.scs(that: IntArray): Int {
	// dp(i, j): length of shortest common supersequence for A[1..i] and B[1..j]
	// dp(i, j) = j if i = 0
	//          = i if j = 0
	//          = min{ 1 + dp(i - 1, j - 1), 1 + dp(i, j - 1), 1 + dp(i - 1, j) } if A[i] = B[j]
	//          = min{ 2 + dp(i - 1, j - 1), 1 + dp(i, j - 1), 1 + dp(i - 1, j) } o/w
	// memoization structure: 2d array dp[1..m][1..n]
	// space complexity: O(mn)
	val dp = Array(size + 1) { IntArray(that.size + 1) }

	// dependencies: dp[i, j] depends on dp[i - 1, j], dp[i, j - 1] and dp[i - 1, j - 1]
	// evaluation order: i from 0..m, j from 0..n
	// time complexity: O(n^2)
	for (i in 1..size) {
		for (j in 1..that.size) {
			dp[i, j] = when {
				i - 1 == 0 -> j - 1
				j - 1 == 0 -> i - 1
				this[i - 1] == that[j - 1] -> min(1 + dp[i - 1, j - 1], dp[i, j - 1], 1 + dp[i - 1, j])
				else -> min(2 + dp[i - 1, j - 1], 1 + dp[i, j - 1], 1 + dp[i - 1, j])
			}
		}
	}

	// we want dp(m, n)
	return dp[size, that.size]
}

// another version of scs of two sequences merely served as the
// pre-process stage for solving the three sequences version
private infix fun OneArray<Char>.scs(B: OneArray<Char>): Array<Array<Int>> {
	val A = this
	val n = size

	val dp = Array(n + 1) { Array(n + 1) { 0 } }
	for (i in 1..n) {
		dp[i, 0] = i
		dp[0, i] = i
	}

	for (i in 1..n) {
		for (j in 1..n) {
			dp[i, j] = if (A[i] == B[j]) {
				min(1 + dp[i - 1, j - 1], 1 + dp[i, j - 1], 1 + dp[i - 1, j])
			} else {
				min(2 + dp[i - 1, j - 1], 1 + dp[i, j - 1], 1 + dp[i - 1, j])
			}
		}
	}

	return dp
}

// what if we are given three strings X[1..n], Y[1..n], Z[1..n]
fun scs(X: OneArray<Char>, Y: OneArray<Char>, Z: OneArray<Char>): Int {
	val n = X.size // == Y.size == Z.size by assumption

	// dp(i, j, k): len of scs among X[0..i], Y[0..j], Z[0..k]
	// where X[0], Y[0], Z[0] represents the empty character
	// memoization structure: 3d arr dp[0..n, 0..n, 0..n]
	val dp = Array(n + 1) { Array(n + 1) { Array(n + 1) { 0 } } }
	// space: O(n^3)

	// base case:
	val xy = X scs Y
	val yz = Y scs Z
	val xz = X scs Z
	for (i in 0..n) {
		for (j in 0..n) {
			dp[i, j, 0] = xy[i, j]
			dp[0, i, j] = yz[i, j]
			dp[i, 0, j] = xz[i, j]
		}
	}
	// time: O(n^2)

	// recursive case:
	// { too many cases... see below in code }
	// dependency: dp(i, j, k) depends on dp(i - 1, j - 1, k - 1),
	//                                    dp(i, j - 1, k),
	//                                    dp(i - 1, j, k - 1),
	//                                    dp(i - 1, j - 1, k)
	// imagine dp as tables indexed by i, each having row idx j and col idx k
	// then dp[i, j, k] depends on entries in the previous table, and entries
	// in the current table that is above or to the left of the current entry
	// eval order: outermost loop for i increasing from 1 to n
	for (i in 1..n) {
		// middle loop for j increasing from 1 to n
		for (j in 1..n) {
			// innermost loop for k increasing from 1 to n
			for (k in 1..n) {
				dp[i, j, k] = when {
					X[i] == Y[j] && Y[j] == Z[k] -> min(
							dp[i - 1, j - 1, k - 1] + 1,
							dp[i, j - 1, k - 1] + 1,
							dp[i - 1, j, k - 1] + 1,
							dp[i - 1, j - 1, k] + 1,
							dp[i - 1, j, k] + 1,
							dp[i, j - 1, k] + 1,
							dp[i, j, k - 1] + 1)
					X[i] == Y[j] && Y[j] != Z[k] -> min(
							dp[i - 1, j - 1, k - 1] + 2,
							dp[i, j - 1, k - 1] + 2,
							dp[i - 1, j, k - 1] + 2,
							dp[i - 1, j - 1, k] + 1,
							dp[i - 1, j, k] + 1,
							dp[i, j - 1, k] + 1,
							dp[i, j, k - 1] + 1)
					X[i] != Y[j] && Y[j] == Z[k] -> min(
							dp[i - 1, j - 1, k - 1] + 2,
							dp[i, j - 1, k - 1] + 1,
							dp[i - 1, j, k - 1] + 2,
							dp[i - 1, j - 1, k] + 2,
							dp[i - 1, j, k] + 1,
							dp[i, j - 1, k] + 1,
							dp[i, j, k - 1] + 1)
					X[i] == Z[k] && Z[k] != Y[j] -> min(
							dp[i - 1, j - 1, k - 1] + 2,
							dp[i, j - 1, k - 1] + 2,
							dp[i - 1, j, k - 1] + 1,
							dp[i - 1, j - 1, k] + 2,
							dp[i - 1, j, k] + 1,
							dp[i, j - 1, k] + 1,
							dp[i, j, k - 1] + 1)
					else -> /* X[i] != Y[j] != Z[k] */ min(
							dp[i - 1, j - 1, k - 1] + 3,
							dp[i, j - 1, k - 1] + 2,
							dp[i - 1, j, k - 1] + 2,
							dp[i - 1, j - 1, k] + 2,
							dp[i - 1, j, k] + 1,
							dp[i, j - 1, k] + 1,
							dp[i, j, k - 1] + 1)
				}
			}
		}
	}
	// time: O(n^3)

	// we want the len of scs among X[0..n], Y[0..n], Z[0..n]
	return dp[n, n, n]
}