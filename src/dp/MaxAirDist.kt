package dp

import util.OneArray
import util.max
import util.oneArrayOf

// suppose you are going to slide down a hill with n ramps
// given Ramp[1..n], in which Ramp[i] represents the distance
// from the starting point to the i-th ramp (assuming an increasing array)
// and Length[1..n], in which Length[i] represents the air distance you can
// travel "flying" from the i-th ramp
// obviously, if you choose to fly at the i-th ramp, they will possibly fly
// over next few ramps, losing opportunity to fly from those ramps

// 1. determine the maximum air distance you can travel from the staring point
//    [hint: this subproblem is really similar to MaxScore]
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
	// dp(i) = max{ dp(i + 1), <- choose not to fly the i-th ramp
	//              Length[i] + dp(next(i)) } <- choose to fly the i-th ramp
	// where next(i) represents the minimum index after landing
	// that is, we need to find the minimum index of an element in Ramp that
	// contains value at least (Ramp[i] + Length[i])
	// we can do this in O(log n) time with binary search since Ramp is a sorted array

	// dependency: dp[i] depends on dp[j] where j > i, that is entryies to the right
	// evaluation order: i from n - 1 down to 1 (right to left)
	for (i in n - 1 downTo 1) {
		dp[i] = max(dp[i + 1], Length[i] + dp[Ramp.next(Ramp[i] + Length[i])])
	}
	// time complexity: O(n * log n)

//	dp.prettyPrintln()

	// we want max{ dp[i] }
	return dp.max() ?: 0
}

// find the minimum index of the element that is greater than or equal to target
fun OneArray<Int>.next(target: Int): Int {
	val idx = container.binarySearch(target) + 1
	if (idx > 0) {
		return idx
	}

	return -idx + 1
}

fun main(args: Array<String>) {
	val Ramp = oneArrayOf(10, 30, 50, 90, 140)
	val Length = oneArrayOf(24, 25, 15, 30, 50)
	println(maxAirDist(Ramp, Length))
}
