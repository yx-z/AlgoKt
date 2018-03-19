package dp

import oneArrayOf
import OneArray

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

	val sortA = A.sortedByDescending { it.first }
	sortA.prettyPrintln()

	// dp(i) = len of longest such sequence starting @ A[i]
	val dp = OneArray(n) { 0 }
	// space complexity: O(n)

	// we want max_i{ dp(i) }
	var max = 1

	return max
}

enum class Color {
	Red, Blue;
}