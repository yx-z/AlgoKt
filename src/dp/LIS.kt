import java.util.*

// Longest Increasing Subsequence
fun main(args: Array<String>) {
	// test array
	val arr = arrayOf(4, 2, 3, 5, 7)

	println(lenLISByLCS(arr))
	println(lenLISDP(arr))
	println(lenLISDP2(arr))
	println(lenLISDP3(arr))
	println(lenLISDPOpt(arr))
}

// arr.sort the copied array and then solve by using LCS
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

// another DP with O(N^2)
fun lenLISDP2(arr: Array<Int>): Int {
	val len = arr.size

	// dp[i] = length of LIS that starts at arr[i]
	// dp[i] = 0, if i > n
	//         1 + max(dp[j]) where j > i && arr[j] > arr[i], o/w
	val dp = IntArray(len)
	var lenMax = 0
	for (i in (len - 1) downTo 0) {
		dp[i] = 1 + (dp.filterIndexed { j, _ -> j > i && arr[j] > arr[i] }.max() ?: 0)
		lenMax = maxOf(dp[i], lenMax)
	}
	return lenMax
}

// yet another DP with O(N^2)
fun lenLISDP3(arr: Array<Int>): Int {
	val len = arr.size
	val copy = IntArray(len + 1)
	copy[0] = Int.MIN_VALUE
	arr.forEachIndexed { i, v -> copy[i + 1] = v }
	val hold = Array(len + 1) { IntArray(len + 1) }
	for (col in len downTo 0) {
		for (row in 0..col) {
			if (col < len) {
				hold[row][col] = if (copy[row] > copy[col]) {
					hold[row][col + 1]
				} else {
					maxOf(hold[row][col + 1], 1 + hold[col][col + 1])
				}
			}
		}
	}
	return hold[0][0]
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