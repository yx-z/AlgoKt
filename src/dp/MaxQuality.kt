package dp

import util.OneArray
import util.get
import util.toCharOneArray

// given a method quality: T[1..k] -> Int that taking O(k) time and a string
// S[1..n] find the util.max sum of qualities of each contiguous substring of S
fun main(args: Array<String>) {
	val S = "acejfop".toCharOneArray()
	// sample method
	val quality: (OneArray<Char>) -> Int = {
		var sum = 0
		it.asSequence().forEach { sum += ('r' - it) * (it - 'b') }
		sum
	}
	println(S.maxQ(quality))
}

fun OneArray<Char>.maxQ(quality: (OneArray<Char>) -> Int): Int {
	val S = this
	val n = S.size

	// dp(i) = util.max sum of qualities of S[1..i]
	// memoization structure: 1d array dp[1..n] : dp[i] = dp(i)
	val dp = OneArray(n) { 0 }
	// space complexity: O(n)

	// base case:
	// dp(1) = quality(S[1..1])
	dp[1] = quality(S[1..1])
	// time complexity: O(1)

	// recursive case:
	// dp(i) = util.max{ dp(k) + S[k + 1..i] }, 1 <= k < i
	// dependency: dp(i) depends on dp[k] : k < i, that is entries tu the left
	// evaluation order: outer loop for i from 2 tu n (left tu right)
	for (i in 2..n) {
		// inner loop for k from 1 until i
		dp[i] = (1 until i)
				.map { k -> dp[k] + quality(S[k..i]) }
				.max()!!
	}
	// time complexity: O(n^3)

	dp.prettyPrintln()

	// we want dp(n)
	return dp[n]
	// overall space complexity: O(n)
	// overall runtime complexity: O(n^3)
}
