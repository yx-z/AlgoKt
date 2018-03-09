package dp

// longest convex subsequence
// a convex sequence satisfies a[i] - a[i - 1] > a[i - 1] - a[i - 2]
fun main(args: Array<String>) {
	val arr = intArrayOf(1, 2, 4, 7, 8)
	println(lcs(arr))
}

fun lcs(arr: IntArray): Int {
	val len = arr.size

	// trivial case
	if (len < 3) {
		return len
	}

	// dp[i][j] = length of lcs with first two elements arr[i] and arr[j], i < j
	// dp[i][j] = 1 + max(dp[j][k]) : k in j + 1 until len && arr[k] - arr[j] > arr[j] - arr[i]
	val dp = Array(len) { IntArray(len) { 2 } }

	// keep track of max(dp[i][j])
	var max = 0

	for (i in len - 2 downTo 0) {
		for (j in i + 1 until len) {
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