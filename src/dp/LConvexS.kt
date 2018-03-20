package dp

import util.set

// longest convex subsequence
// a convex sequence satisfies a[i] - a[i - 1] > a[i - 1] - a[i - 2]
fun main(args: Array<String>) {
	val arr = intArrayOf(1, 4, 8, 13, 14, 18, 19)
	println(lcs(arr)) // [1, 4, 8, 13, 19] -> 5
}

fun lcs(arr: IntArray): Int {
	val len = arr.size

	// trivial case
	if (len < 3) {
		return len
	}

	// dp[i][j] = length of lcs with first two elements arr[i] and arr[j], i < j
	// dp[i][j] = 1 + util.max(dp[j][k]) : k in j + 1 until len && arr[k] - arr[j] > arr[j] - arr[i]
	val dp = Array(len) { IntArray(len) }
	for (i in 0 until len) {
		dp[i, len - 1] = 2
	}

	// keep track of util.max(dp[i][j])
	var max = 0

	for (i in len - 2 downTo 0) {
		for (j in i + 1 until len - 1) {
			for (k in j + 1 until len) {
				if (arr[k] - arr[j] > arr[j] - arr[i]) {
					dp[i][j] = maxOf(dp[i][j], 1 + dp[j][k])
				}
			}
			max = maxOf(max, dp[i][j])
		}
	}
	return max
}