package dp

import get
import set
import max

// given an array of arbitrary floating point numbers
// find the largest sum of elements in a contiguous subarray
fun main(args: Array<String>) {
	val arr = intArrayOf(-6, 12, -7, 0, 14, -7, 5)
//	println(arr.largestSum())
//	println(arr.largestSumOpt())
	println(arr.largestSumNlogN())
}

// brute force DP: O(N^2)
fun IntArray.largestSum(): Int {
	// dp[i, j] = sum of this[i..j]
	// dp[i, j] = 0, if (i > j || i, j !in 0 until size)
	//          = this[i], if (i == j)
	//          = dp[i, j - 1]  + this[j], o/w
	// we want max(dp[i, j])
	val dp = Array(size) { IntArray(size) }
	var max = Int.MIN_VALUE
	for (i in 0 until size) {
		for (j in i until size) {
			dp[i, j] = if (i == j) {
				this[i]
			} else {
				if (j == 0) {
					this[j]
				} else {
					dp[i, j - 1] + this[j]
				}
			}
			max = max(max, dp[i][j])
		}
	}
	return max
}

// better: O(n log n)
fun IntArray.largestSumNlogN(): Int {
	// the basic idea here is:
	// there are three cases of the resulting subarray A[i..j]
	// 1. j < n / 2 left of the middle line
	// 2. i > n / 2 right of the middle line
	// 3. i < n / 2 < j crossing the middle line
	// we may recursively solve 1 and 2
	// and do O(n) to find 3 that is, A[i..j] : i < n / 2 < j having the largest sum
	// finally compare 1, 2, and 3 and get the biggest one as solution

	// runtime analysis
	// T(n) <= O(n) + 2T(n / 2) -> T ~ O(n log n)


	TODO()
}

// optimized: O(n)
fun IntArray.largestSumOpt(): Int {
	// dp[i] = max sum for this[i until size]
	// dp[i] = 0, if i !in 0 until size
	//       = dp[i + 1], if this[i] < 0
	//       = this[i] + max(0, tmpSum + dp[nex]), o/w
	//       , where nex is the next positive number
	val dp = IntArray(size + 1)
	dp[size] = 0
	var nex = size
	var tmpSum = 0
	for (i in size - 1 downTo 0) {
		if (this[i] <= 0) {
			dp[i] = dp[i + 1]
			tmpSum += this[i]
		} else {
			dp[i] = this[i]
			if (tmpSum + dp[nex] > 0) {
				dp[i] += tmpSum + dp[nex]
			}
			tmpSum = 0
			nex = i
		}
	}
	return dp.max() ?: 0
}