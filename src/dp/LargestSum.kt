package dp

import get
import set
import max

// given an array of arbitrary numbers
// find the largest sum of elements in a contiguous subarray
fun main(args: Array<String>) {
	val arr = intArrayOf(-6, 12, -7, 0, 14, -7, 5)
	println(arr.largestSumN2())
	println(arr.largestSumNlogN())
	println(arr.largestSumN())
}

// brute force DP: O(N^2)
fun IntArray.largestSumN2(): Int {
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
fun IntArray.largestSumNlogN(s: Int = 0, e: Int = size - 1): Int {
	// the basic idea here is:
	// there are three cases of the resulting subarray A[i..j]
	// 1. j < n / 2 left of the middle line
	// 2. i > n / 2 right of the middle line
	// 3. i < n / 2 < j crossing the middle line
	// we may recursively solve 1 and 2
	// and do O(n) to find 3: max prefix sum of A[n / 2 + 1..n] + max suffix sum of A[1..n / 2 - 1]
	// finally compare 1, 2, and 3 and get the biggest one as solution

	// runtime analysis
	// T(n) <= O(n) + 2T(n / 2) -> T ~ O(n log n)

	if (s > e) {
		return 0
	}

	if (s == e) {
		return max(0, this[s])
	}

	if (s == e - 1) {
		return max(this[s], this[e], this[s] + this[e], 0)
	}

	val midIdx = s + (e - s) / 2
	val left = largestSumNlogN(s, midIdx - 1)
	val right = largestSumNlogN(midIdx + 1, e)

	var mid = this[midIdx]
	mid += prefixSum(midIdx + 1, e)
	mid += suffixSum(s, midIdx - 1)

	return max(left, right, mid)
}

fun IntArray.prefixSum(s: Int = 0, e: Int = size - 1): Int {
	// dp(i): sum for this[s..s + i]
	// memoization structure: 1d array dp[0..size - 1 - s] : dp[i] = dp(i)
	val dp = IntArray(e - s + 1)
	// space complexity: O(n)

	// base case:
	// dp(i) = this[s] if i = 0
	dp[0] = this[s]

	// we want max_i{ dp(i) } >= 0
	var max = max(dp[0], 0)

	// recursive case:
	// dp(i) = dp(i - 1) + this[s + i] o/w
	// dependency: dp(i) depends on dp(i - 1), that is the entry to the left
	// evaluation order: loop for i from 1 until size - s (left to right)
	for (i in 1..e - s) {
		dp[i] = dp[i - 1] + this[s + i]
		max = max(max, dp[i])
	}
	// time complexity: O(n)

	return max
}

// similarly we have suffixSum and I won't do analysis here
fun IntArray.suffixSum(s: Int = 0, e: Int = size - 1): Int {
	// dp(i): sum for this[s + i..e]
	val dp = IntArray(e - s + 1)
	dp[e - s] = this[e]
	var max = max(dp[e - s], 0)
	for (i in e - s - 1 downTo 0) {
		dp[i] = dp[i + 1] + this[s + i]
		max = max(max, dp[i])
	}
	return max
}

// optimized: O(n)
fun IntArray.largestSumN(): Int {
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