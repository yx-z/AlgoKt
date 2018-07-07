package dp

import math.isPrime
import util.*

// given a bitmap as a 2d array, identify the largest rectangular pattern that appear more than once
// the copies of a pattern may overlap but cannot coincide
// report its size
fun main(args: Array<String>) {
	val bitmap = arrayOf(
			intArrayOf(0, 1, 0, 0, 0),
			intArrayOf(1, 0, 1, 0, 0),
			intArrayOf(0, 1, 0, 1, 0),
			intArrayOf(0, 0, 1, 0, 1),
			intArrayOf(0, 0, 0, 1, 0))

//	println(bitmap.largestPattern())
	println(bitmap.map { it.toOneArray() }.toOneArray().largestPattern())
}

fun OneArray<OneArray<Int>>.largestPattern(): Int {
	val M = this
	val n = size

	// dp(i, j, p, q, h): max width w : h * w subarrays are identical
	// memoization structure: 5d array dp[1..n, 1..n, 1..n, 1..n, 1..n]
	val dp = OneArray(n + 1) { OneArray(n + 1) { OneArray(n + 1) { OneArray(n + 1) { OneArray(n) { 0 } } } } }
	// space complexity: O(n^5)

	var max = 0
	// evaluation order: h has to be 1 to n since entries in h - 1 are required
	// there is no specific evaluation order for i, j, p, q
	for (h in 1..n) {
		for (i in 1..n) {
			for (j in 1..n + 1) {
				for (p in 1..n) {
					for (q in 1..n + 1) {
						// different cases
						val curr = when {
							i == p && j == q -> N_INF
							j > n || q > n -> 0
							M[i, j] != M[p, q] -> 0
							h == 1 -> 1 + dp[i, j + 1, p, q + 1, 1]
							else -> min(dp[i, j, p, q, 1], dp[i + 1, j, p + 1, q, h - 1])
						}
						dp[i, j, p, q, h] = curr

						// maintain a max here
						if (curr > 0) {
							max = max(max, curr * h)
						}
					}
				}
			}
		}
	}
	// time complexity: O(n^5)
	return max
}

fun Array<IntArray>.largestPattern(): Int {
	val rows = size
	val cols = this[0].size

	val primes = Array(rows) { IntArray(cols) }
	val primesFlat = primes(rows * cols)
	for (row in 0 until rows) {
		for (col in 0 until cols) {
			primes[row, col] = primesFlat[cols * row + col]
		}
	}

	val dp = Array(rows) { Array(cols) { Array(rows) { IntArray(cols) { 1 } } } }
	val dpFlat = Array(rows * rows * cols * cols) { 0 to (0 to (0 to (0 to 1))) }
	var dpFlatIdx = 0
	for (i in 0 until rows) {
		for (j in 0 until cols) {
			for (p in i until rows) {
				for (q in j until cols) {
					dp[i, j, p, q] = if (this[p, q] == 1) {
						primes[p - i, q - j]
					} else {
						1
					}

					if (p - 1 >= 0 && q - 1 >= 0) {
						dp[i, j, p, q] *= dp[i, j, p - 1, q] * dp[i, j, p, q - 1] / dp[i, j, p - 1, q - 1]
					} else if (p - 1 >= 0) {
						dp[i, j, p, q] *= dp[i, j, p - 1, q]
					} else if (q - 1 >= 0) {
						dp[i, j, p, q] *= dp[i, j, p, q - 1]
					}

					dpFlat[dpFlatIdx] = i to (j to (p to (q to dp[i, j, p, q])))
					dpFlatIdx++
				}
			}
		}
	}

	dpFlat.sortBy { it.second.second.second.second }
	var maxSize = Int.MIN_VALUE
	(1 until dpFlat.size).asSequence()
			.filter { dpFlat[it].second.second.second.second == dpFlat[it - 1].second.second.second.second }
			.forEach { maxSize = max(maxSize, (dpFlat[it].second.second.first - dpFlat[it].first + 1) * (dpFlat[it].second.second.second.first - dpFlat[it].second.first + 1)) }

	return maxSize
}

// return the first `n` prime numbers
fun primes(n: Int): IntArray {
	val ans = IntArray(n)
	var num = 2
	for (i in 0 until n) {
		while (!num.isPrime()) {
			num++
		}
		ans[i] = num
		num++
	}
	return ans
}