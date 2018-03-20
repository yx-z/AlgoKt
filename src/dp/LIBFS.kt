package dp

import util.oneArrayOf
import util.OneArray
import util.max
import util.toOneArray

// longest increasing back and forth subsequence
// given A[1..n], an array of (Int, Color) pair
// its ibfs, I[1..l] follows:
// 1 <= I[j] <= n
// A[I[j]] < A[I[j + 1]]
// If A[I[j]] is Red, then I[j + 1] > I[j]
// If A[I[j]] is Blue, then I[j + 1] < I[j]
// find the length of longest such sequence

fun main(args: Array<String>) {
	val A = oneArrayOf(
			1 to Color.Red,
			1 to Color.Blue,
			0 to Color.Red,
			2 to Color.Blue,
			5 to Color.Blue,
			9 to Color.Red,
			6 to Color.Blue,
			6 to Color.Red,
			4 to Color.Blue,
			5 to Color.Red,
			8 to Color.Red,
			9 to Color.Blue,
			7 to Color.Blue,
			7 to Color.Red,
			3 to Color.Red,
			2 to Color.Red,
			3 to Color.Blue,
			8 to Color.Blue,
			4 to Color.Red,
			0 to Color.Blue
	)
	println(A.lbfs())
}

fun OneArray<Pair<Int, Color>>.lbfs(): Int {
	val A = this
	val n = A.size

	// sort A to util.get an array of (index, value, Color) that descends in value
	val sortedA = A.indices
			.map { it to A[it] }
			.toList()
			.sortedByDescending { it.second.first }
			.toOneArray()
//	sortedA.prettyPrintln()

	// dp(i) = len of longest such sequence starting @ A[i]
	val dp = OneArray(n) { 0 }
	// space complexity: O(n)

	// base case:
	// dp(i) = 1 if i is the index A having the maximum value
	dp[sortedA[1].first] = 1

	// recursive case:
	// assume util.max{ } = 0
	// dp(i) = 1 + util.max{ dp(k) : A[k] > A[i], k > i } if A[i] is Red, i in 1..n
	//       = 1 + util.max{ dp(k) : A[k] > A[i], k < i } if A[i] is Blue, i in 1..n
	//       = 0 o/w
	// dependency: dp(i) depends on dp(k) where k is an index in A : A[i] > A[k]
	// evaluation order: outer loop for i from sortedA[2]_1 to sortedA[n]_1
	//                   that is a loop for idx from 2 to n in sortedA
	for (idx in 2..n) {
		val i = sortedA[idx].first
		dp[i] = 1 + when (A[i].second) {
			Color.Red -> (i + 1..n)
					.filter { k -> A[k].first > A[i].first }
					.map { k -> dp[k] }
					.max() ?: 0
			Color.Blue -> (1 until i)
					.filter { k -> A[k].first > A[i].first }
					.map { k -> dp[k] }
					.max() ?: 0
		}
	}
	// time complexity: O(n^2)
//	dp.prettyPrintln()

	// we want max_i{ dp(i) }
	return dp.max() ?: 0
}

enum class Color {
	Red, Blue;
}