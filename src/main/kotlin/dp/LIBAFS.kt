package dp

// given an array of (int, boolean) pair, find the length of longest increasing
// back-and-forth subsequence which is defined as
// the subsequence is increasing
// when boolean == true, find the next element to the right
// o/w, find the next element to the left
fun main(args: Array<String>) {
	// ex. length of LIBAFS should be nine in that it is (val @ idx):
	// 0 @ 20, 1 @ 1, 2 @ 16, 3 @ 17, 4 @ 9, 6 @ 8, 7 @ 13, 8 @ 11, 9 @ 12
	val arr = arrayOf(
			1 to true,
			1 to false,
			0 to true,
			2 to false,
			5 to false,
			9 to true,
			6 to false,
			6 to true,
			4 to false,
			5 to true,
			8 to true,
			9 to false,
			7 to false,
			7 to true,
			3 to true,
			2 to true,
			3 to false,
			8 to false,
			4 to true,
			0 to false)
	println(arr.libafs())
}

fun Array<Pair<Int, Boolean>>.libafs(): Int {
	// get the original index in the sorted array
	val idx = IntArray(size) { it }
	val sortedIdx = idx.sortedByDescending { this[it].first }

	// dp[i] = length of libafs that starts @ this[i]
	// dp[i] = 1 + max(dp[k] : this[k] > this[i], k in i + 1 until size), if this[i].second
	//       = 1 + max(dp[k] : this[k] > this[i], k in 0 until i), o/w
	val dp = IntArray(size)
	sortedIdx.forEach { i ->
		dp[i] = 1 + if (this[i].second) {
			dp.filterIndexed { k, _ ->
				k in i + 1 until size && this[k].first > this[i].first
			}.max() ?: 0
		} else {
			dp.filterIndexed { k, _ ->
				k in 0 until i && this[k].first > this[i].first
			}.max() ?: 0
		}
	}

	return dp.max() ?: 0
}

