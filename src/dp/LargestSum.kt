package dp

import get
import set
import max

// given an array of arbitrary floating point numbers
// find the largest sum of elements in a contiguous subarray
fun main(args: Array<String>) {
	val arr = floatArrayOf(-6F, 12F, -7F, 0F, 14F, -7F, 5F)
//	println(arr.largestSum())
	println(arr.largestSumOpt())
}

// brute force DP: O(N^2)
fun FloatArray.largestSum(): Float {
	// dp[i, j] = sum of this[i..j]
	// dp[i, j] = 0, if (i > j || i, j !in 0 until size)
	//          = this[i], if (i == j)
	//          = dp[i, j - 1]  + this[j], o/w
	// we want max(dp[i, j])
	val dp = Array(size) { FloatArray(size) }
	var max = Float.MIN_VALUE
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

// optimized: O(n)
fun FloatArray.largestSumOpt(): Float {
	// dp[i] = max sum starting at i
	// dp[i] = 0, if i !in 0 until size
	//       = dp[i + 1], if this[i] < 0
	//       = this[i] + max(0, tmpSum + dp[nex]), o/w
	//       , where nex is the next positive number
	val dp = FloatArray(size + 1)
	dp[size] = 0F
	var nex = size
	var tmpSum = 0F
	for (i in size - 1 downTo 0) {
		if (this[i] <= 0) {
			dp[i] = dp[i + 1]
			tmpSum += this[i]
		} else {
			dp[i] = this[i]
			if (tmpSum + dp[nex] > 0) {
				dp[i] += tmpSum + dp[nex]
			}
			tmpSum = 0F
			nex = i
		}
	}
	return dp.max() ?: 0F
}