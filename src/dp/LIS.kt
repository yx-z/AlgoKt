import java.util.*

// Longest Increasing Subsequence
fun main(args: Array<String>) {
	// test array
	val arr = arrayOf(4, 2, 3, 5, 7)

	println(lenLISByLCS(arr))
	println(lenLISDP(arr))
	println(lenLISDPOpt(arr))
}

// sort the copied array and then solve by using LCS
fun lenLISByLCS(arr: Array<Int>): Int {
	val copy = Arrays.copyOf(arr, arr.size)
	Arrays.sort(copy)

	val s1 = StringBuilder(arr.size)
	val s2 = StringBuilder(copy.size)

	arr.forEach { s1.append(it) }
	copy.forEach { s2.append(it) }

	return lenLCS(s1.toString(), s2.toString())
}

// DP with O(N^2)
fun lenLISDP(arr: Array<Int>): Int {
	val len = arr.size
	var max = 1

	val dp = Array(len) { 1 }

	for (i in 1 until len) {
		for (j in 0 until i) {
			if (arr[i] > arr[j]) {
				dp[i] = Math.max(dp[i], dp[j] + 1)
			}
		}
		max = Math.max(max, dp[i])
	}

	return max
}

// Optimized DP with O(N log N)
fun lenLISDPOpt(arr: Array<Int>): Int {
	val dp = ArrayList<Int>()

	arr.forEach {
		var idx = dp.binarySearch(it)
		if (idx < 0) {
			idx = -(idx + 1)
		}

		if (idx == dp.size) {
			dp.add(it)
		} else {
			dp[idx] = it
		}
	}

	return dp.size
}