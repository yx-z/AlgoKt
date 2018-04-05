package dp

import util.*

// similar to `MaxSum`,
// given an array of numbers,
// find the largest product in any contiguous subarray.
// note that two negative numbers produce a positive result, ex. (-2) * (-4) = 8
fun main(args: Array<String>) {
	val arr = intArrayOf(-6, 12, -7, 0, 14, -7, 5)
//	println(arr.maxProdInt())

	val fracArray = oneArrayOf(-2.0, 8.0, 0.25, -4.0, 0.5, -8.0)
//	println(fracArray.maxProd())
//	println(fracArray.maxProdShort())
	println(fracArray.maxProdKadane())
	println(fracArray.maxProdRedo())
}

// this solution assumes that input array are all integers
// but cannot handle fractions that may decrease the product
fun IntArray.maxProdInt(): Int {
	if (size == 0) {
		return 0
	}
	if (size == 1) {
		return max(0, this[0])
	}

	// 0 cancels all our current progress! yuck!
	// so we will recursively call ourself
	for (i in 0 until size) {
		if (this[i] == 0) {
			return max(this[0 until i].maxProdInt(), this[i + 1 until size].maxProdInt())
		}
	}

	// since we are here, there is no 0 in the array
	// multiply all numbers up!
	val total = this.reduce { acc, it -> acc * it }
	// extra work to be done if the result is negative
	if (total < 0) {
		var leftMax = total
		var leftIdx = 0
		while (leftIdx < size && this[leftIdx] > 0) {
			leftMax /= this[leftIdx]
			leftIdx++
		}
		// first negative number from left to right
		leftMax /= this[leftIdx]

		var rightMax = total
		var rightIdx = size - 1
		while (rightIdx >= 0 && this[rightIdx] > 0) {
			rightMax /= this[rightIdx]
			rightIdx--
		}
		// first negative number from right to left
		rightMax /= this[rightIdx]
		return max(leftMax, rightMax)
	}
	return total
	// we can do this in O(n)
}

// given A[1..n] containing possibly negative, and possibly non-integer, numbers
// find the largest prod
fun OneArray<Double>.maxProd(): Double {
	val A = this
	val n = size
	// i like my idea from the above
	if (n == 0) {
		return 0.0
	}
	if (n == 1) {
		return max(0.0, this[1])
	}

	// 0 cancels all our current progress! yuck!
	// so we will recursively call itself before and after the 0
	for (i in 1..n) {
		if (this[i] == 0.0) {
			return max(this[1 until i].maxProd(), this[i + 1..n].maxProd())
		}
	}

	// still assumes no 0 in the input array

	// dp(i): (max product of A[1..i] that ends in A[i], min of such product)
	// memoization structure: 1d array dp[1..n] : dp[i] = dp(i)
	val dp = OneArray(n) { 0.0 tu 0.0 }
	// space complexity: O(n)

	// base case:
	// dp(i) = 0 if i !in 1..n
	dp.getterIndexOutOfBoundsHandler = { 0.0 tu 0.0 }
	// dp(1) = max(0, A[1])
	dp[1] = max(0.0, A[1]) tu min(0.0, A[1])

	// recursive case:
	// dp(i) = (max{ dp(k)_1 : k < i } * A[i], min{ dp(k)_2 : k < i } * A[i]) if A[i] > 0
	//       = (min{ dp(k)_2 : k < i } * A[i], max{ dp(k)_1 : k < i } * A[i]) o/w
	for (i in 2..n) {
		val maxPre = (1 until i).map { dp[it].first }.max() ?: 0.0
		val minPre = (1 until i).map { dp[it].second }.min() ?: 0.0
		dp[i] = if (A[i] > 0) {
			maxPre * A[i] tu minPre * A[i]
		} else {
			minPre * A[i] tu maxPre * A[i]
		}
	}
	// time complexity: O(n^2)

	dp.prettyPrintln()

	// we want max{ dp(i)_1 }
	return dp.asSequence().map { it.first }.max() ?: 0.0
}

// while the following program is short, it is still slow O(n^2)
fun OneArray<Double>.maxProdShort(): Double {
	val dp = OneArray(size) { OneArray(size) { 0.0 } }
	for (i in 1..size) dp[i, i] = this[i]
	for (i in 1..size) for (j in i + 1..size) dp[i, j] = dp[i, j - 1] * this[j]
	return dp.asSequence().map { it.max() ?: 0.0 }.max() ?: 0.0
}

// O(n) time
// assume input has no 0s, o/w we can wrap it as the methods above do
fun OneArray<Double>.maxProdKadane(): Double {
	// similar to Kadane's algorithm described in MaxSum
	var maxEndingHere = 1.0
	var minEndingHere = 1.0
	var maxSoFar = 1.0

	asSequence().forEach {
		if (it > 0) {
			maxEndingHere *= it
			minEndingHere = min(minEndingHere * it, 1.0)
		} else {
			val tmp = maxEndingHere
			maxEndingHere = max(minEndingHere * it, 1.0)
			minEndingHere = tmp * it
		}

		maxSoFar = max(maxSoFar, maxEndingHere)
	}

	return maxSoFar
}

fun OneArray<Double>.maxProdRedo(): Double {
	val A = this
	val n = size
	// max[i]: max prod subarray of A[i..n] that MUST include A[i]
	val max = OneArray(n) { A[it] }
	// min[i]: min prod subarray of A[i..n] that MUST include A[i]
	val min = OneArray(n) { A[it] }
	// space: O(n)

	for (i in n - 1 downTo 1) {
		when {
		// 0 is the easiest case since everything multiply with 0 is 0
			A[i] == 0.0 -> {
				max[i] = 0.0
				min[i] = 0.0
			}
		// then we can do > 0 since it's not that tricky
			A[i] > 0 -> {
				// we can get bigger when the scaling factor > 1
				if (max[i + 1] > 1.0) {
					max[i] *= max[i + 1]
				}
				// we will be smaller if we are multiplied with anything < 1
				// ex. 0.7, 0.1, 0, and even better, negative numbers!
				if (min[i + 1] < 1.0) {
					min[i] *= min[i + 1]
				}
			}
		// we are now < 0
			A[i] < 0 -> {
				// we can get bigger if we can either scaled to a smaller
				// magnitude (but still negative), or better, become positive!
				if (min[i + 1] < 1.0) {
					max[i] *= min[i + 1]
				}
				// we will get even smaller (more negative) when we are scaled up
				if (max[i + 1] > 1.0) {
					min[i] *= max[i + 1]
				}
			}
		}
	}
	// time: O(n)

	max.prettyPrintln()

	return max.max()!!
}