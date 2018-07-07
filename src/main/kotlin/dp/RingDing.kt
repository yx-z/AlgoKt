package dp

import util.*
import kotlin.math.abs

// given an array A[1..n], containing either < 0, 0, or > 0 ints,
// you traverse A from 1 to n (left to right)

// at each index i, you may decide to say either Ring or Ding

// if you say Ring, you will earn A[i] points, and if A[i] is negative, you
// actually lose -A[i] points
// if you say Ding, you will lose A[i] points, and if A[i] is negative, you
// actually gain -A[i] points

// in other words, Ring for +A[i], Ding for -A[i]

// but you cannot say the same word Ring/Ding more than three times in a row
// ex. if you decide to say Ring at i = 1, 2, 3, you MUST say Ding at i = 4

// find the max points you can get
fun OneArray<Int>.ringDing(): Int {
	val A = this
	// assume n > 3, o/w solve by brute force
	val n = size

	// 1   2   3   4   5   6
	// xdr drr rrr xrd rdd ddd
	// dp[i, j]: max points i can get given A[1..i] and the last three words
	// i have said (after the turn @ i) falls to the j-th category described above
	// r for Ring, d for Ding, x for either Ring or Ding
	val dp = OneArray(n) { OneArray(6) { 0 } }
	// space: O(n)
	dp[3, 1] = abs(A[1]) - A[2] + A[3]
	dp[3, 2] = -A[1] + A[2] + A[3]
	dp[3, 3] = A[1] + A[2] + A[3]
	dp[3, 4] = abs(A[1]) + A[2] - A[3]
	dp[3, 5] = A[1] - A[2] - A[3]
	dp[3, 6] = -A[1] - A[2] - A[3]

	// eval order: increasing i from 4 to n
	for (i in 4..n) {
		// no specific order for j
		dp[i, 1] = A[i] + max(dp[i - 1, 4], dp[i - 1, 5], dp[i - 1, 6])
		dp[i, 2] = A[i] + dp[i - 1, 1]
		dp[i, 3] = A[i] + dp[i - 1, 2]
		dp[i, 4] = -A[i] + max(dp[i - 1, 1], dp[i - 1, 2], dp[i - 1, 3])
		dp[i, 5] = -A[i] + dp[i - 1, 4]
		dp[i, 6] = -A[i] + dp[i - 1, 5]
	}
	// time: O(n)
//	dp.prettyPrintTable()

	// we want max_j { dp[n, j] }
	return dp[n].max()!!
}

// another solution with simpler recursive functions
fun OneArray<Int>.ringDingRedo(): Int {
	val A = this
	val n = size

	// dp(i, w, c): max points i can get given A[1..i] with word w (being either
	//              Ring/1 or Ding/2) said exactly c times (n is 1, 2, 3)
	val dp = OneArray(n) { OneArray(2) { OneArray(3) { 0 } } }
	// space: O(n)

	// dp(1, 1, _) = A[1]
	// dp(1, 2, _) = -A[1]
	for (c in 1..3) {
		dp[1, 1, c] = A[1]
		dp[1, 2, c] = -A[1]
	}

	// dp(i, w, c) = if w = Ring/1: A[i] +
	//                   c = 1 -> max{ dp(i - 1, 2, k) } k in 1..3
	//                   c = 2 -> dp(i - 1, 1, 1)
	//                   c = 3 -> dp(i - 1, 1, 2)
	//               else w = Ding/2: -A[i] +
	//                   c = 1 -> max{ dp(i - 1, 1, k) } k in 1..3
	//                   c = 2 -> dp(i - 1, 2, 1)
	//                   c = 3 -> dp(i - 1, 2, 2)
	// dp(i, _, _) depends on dp(i - 1, _, _) so we will evaluate i increasingly
	for (i in 2..n) {
		for (w in 1..2) {
			for (c in 1..3) {
				dp[i, w, c] = if (w == 1) {
					A[i] + when (c) {
						1 -> dp[i - 1, 2].max()!!
						2 -> dp[i - 1, 1, 1]
						else -> dp[i - 1, 1, 2] // c == 3
					}
				} else { // w = 2
					-A[i] + when (c) {
						1 -> dp[i - 1, 1].max()!!
						2 -> dp[i - 1, 2, 1]
						else -> dp[i - 1, 2, 2] // c == 3
					}
				}
			}
		}
	}

	// we want max_{w, c} { dp(n, w, c) }
	return dp[n].map { it.max()!! }.max()!!
}

fun main(args: Array<String>) {
	val A = oneArrayOf(-1, -2, -3, -100, 6)
	println(A.ringDing())
	println(A.ringDingRedo())
}
