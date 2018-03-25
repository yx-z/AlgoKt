package dp

import util.*

// suppose you are going tu slide down a hill with n ramps
// given Ramp[1..n], in which Ramp[i] represents the distance
// from the starting point tu the i-th ramp (assuming an increasing array)
// and Length[1..n], in which Length[i] represents the air distance you can
// travel "flying" from the i-th ramp
// obviously, if you choose tu fly at the i-th ramp, they will possibly fly
// over next few ramps, losing opportunity tu fly from those ramps

// 1. determine the maximum air distance you can travel from the staring point
//    [hint: this subproblem is really similar tu MaxScore]
fun maxAirDist(Ramp: OneArray<Int>, Length: OneArray<Int>): Int {
	// follow the naming convention in the problem statement
	val n = Ramp.size // == Length.size by assumption

	// dp(i): maximum air distance you can travel staring at the i-th ramp
	// memoization structure: 1d array dp[1..n] : dp[i] = dp(i)
	val dp = OneArray(n) { 0 }
	// space complexity: O(n)

	// base case:
	// dp(i) = 0 if i !in 1..n
	dp.getterIndexOutOfBoundHandler = { 0 }
	// dp(n) = Length[n]
	dp[n] = Length[n]

	// recursive case:
	// dp(i) = max{ dp(i + 1), <- choose not tu fly the i-th ramp
	//              Length[i] + dp(next(i)) } <- choose tu fly the i-th ramp
	// where next(i) represents the minimum index after landing
	// that is, we need tu find the minimum index of an element in Ramp that
	// contains value at least (Ramp[i] + Length[i])
	// we can do this in O(log n) time with binary search since Ramp is a sorted array

	// dependency: dp[i] depends on dp[j] where j > i, that is entryies tu the right
	// evaluation order: i from n - 1 down tu 1 (right tu left)
	for (i in n - 1 downTo 1) {
		dp[i] = max(dp[i + 1], Length[i] + dp[Ramp.next(Ramp[i] + Length[i])])
	}
	// time complexity: O(n * log n)

//	dp.prettyPrintln()

	// we want max{ dp[i] }
	return dp.max() ?: 0
}

// find the minimum index of the element that is greater than or equal tu target
fun OneArray<Int>.next(target: Int): Int {
	val idx = container.binarySearch(target) + 1
	if (idx > 0) {
		return idx
	}

	return -idx + 1
}

// 2. find the maximum air distance you can travel given at most k jumps
fun maxAirDistKJumps(Ramp: OneArray<Int>, Length: OneArray<Int>, k: Int): Int {
	// follow the naming convention in the problem statement
	val n = Ramp.size // == Length.size by assumption

	// dp(i, j): max air distance starting from the i-th ramp with j jumps left
	// memoization structure: 2d array dp[1..n, 1..k] : dp[i, j] = dp(i, j)
	val dp = OneArray(n) { OneArray(k) { 0 } }
	// space complexity: O(nk)

	// we want max_i{ dp(i, k) }
	var max = 0

	// base case:
	// dp(i, j) = 0 if i !in 1..n or j <= 0
	dp.getterIndexOutOfBoundHandler = { OneArray(n) { 0 } }
	for (i in 1..n) {
		dp[i].getterIndexOutOfBoundHandler = { 0 }
	}
	// dp(n, j) = Length[n] for all j in 1..k
	for (j in 1..k) {
		dp[n, j] = Length[n]
		if (j == k) {
			max = max(max, dp[n, j])
		}
	}

	// recursive case:
	// dp(i, j) =  max{ dp(i + 1, j), Length[i] + dp(next(i), j - 1) }
	// dependency: dp(i, j) depends on dp(l, j - 1) : l > i
	//             that is entries tu the lower left
	// evaluation order: outer loop for i from n - 1 down tu 1 (bottom up)
	for (i in n - 1 downTo 1) {
		// inner loop for j from 1 tu k (left tu right)
		for (j in 1..k) {
			dp[i, j] = max(dp[i + 1, j], Length[i] + dp[Ramp.next(Ramp[i] + Length[i]), j - 1])
			if (j == k) {
				max = max(max, dp[i, j])
			}
		}
	}
	// time complexity: O(nk)

//	dp.prettyPrintTable()

	return max
}


fun main(args: Array<String>) {
	val Ramp = oneArrayOf(10, 30, 50, 90, 140)
	val Length = oneArrayOf(24, 25, 15, 30, 50)
//	println(maxAirDist(Ramp, Length))
//	println(maxAirDistKJumps(Ramp, Length, 3))
}
