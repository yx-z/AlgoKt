package dp

import get
import lenLISDPOpt

// longest bitonic subsequence

// X[1..n] is bitonic if there exists i: 1 < i < n, X[1..i] is inscreasing and X[i..n] is decreasing

// find the length of lbs of A[1..n]
fun main(args: Array<String>) {
	val a = intArrayOf(1, 11, 2, 10, 4, 5, 2, 1)
	println(a.lbs())
}

fun IntArray.lbs(): Int {
	if (size < 3) {
		return 0
	}
	// dp(i): (length of lbs for A[i..n], leftMin)
	// dp(i) = (-inf, -inf) if i >= n - 1
	val invalid = Int.MIN_VALUE to Int.MIN_VALUE
	//       = (3, A[n - 2]) if i == n - 2 && A[n - 1] > A[n - 2] && A[n - 1] > A[n]
	//       = (-inf, -inf) if i == n - 2 && (A[n - 1] <= A[n - 2] || A[n - 1] <= A[n])
	//       = (max{dp(k)_1 + A[i] < dp(k)_2, l, LDS(A[i + 1]) + A[i + 1] > A[i] ? 1 : +inf}, A[k]) k in i + 1 until n - 1 o/w
	val dp = Array(size) { invalid }
	if (this[size - 2] > this[size - 3] && this[size - 2] > this[size - 1]) {
		dp[size - 3] = 3 to this[size - 2]
	}
	for (i in size - 4 downTo 0) {
		var maxLen = Int.MIN_VALUE
		var minLeft = Int.MIN_VALUE
		for (k in i + 1 until size) {
			val len = dp[k].first + if (this[i] < dp[k].second) {
				1
			} else {
				0
			}
			if (maxLen < len) {
				maxLen = len
				minLeft = this[k]
			}
		}
		if (this[i + 1] > this[i]) {
			val anotherLen = lenLISDPOpt(this[i + 1 until size].reversed().toTypedArray())
			if (anotherLen > maxLen) {
				maxLen = anotherLen + 1
				minLeft = this[i]
			}
		}
		dp[i] = maxLen to minLeft
	}
	return dp[0].first
}

// or even better find the max of (lds@i + lis@i) for every index i
