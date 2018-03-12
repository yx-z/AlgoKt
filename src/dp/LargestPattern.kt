package dp

import math.isPrime
import get
import set
import max

// given a bitmap as a 2d array, identify the largest rectangular pattern that appear more than once
// the copies of a pattern may overlap but cannot coincide
// report its size
fun main(args: Array<String>) {
	val bitmap = arrayOf(
			intArrayOf(0, 1, 0),
			intArrayOf(1, 0, 1),
			intArrayOf(0, 1, 0))

	println(bitmap.largestPattern())
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
	for (i in 1 until dpFlat.size) {
		if (dpFlat[i].second.second.second.second == dpFlat[i - 1].second.second.second.second) {
			maxSize = max(maxSize,
					(dpFlat[i].second.second.first - dpFlat[i].first + 1) *
							(dpFlat[i].second.second.second.first - dpFlat[i].second.first + 1))
		}
	}

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