package dp

// given an array of positive integers and an integer, determine if any subset of the array can sum
// up to the integer
fun main(args: Array<String>) {
	val arr = intArrayOf(1, 6, 4)
	val ints = intArrayOf(7, // true
			5, // true
			8, // false
			3, // false
			11, // true
			9, // false
			10) // true
	ints.forEach { println(arr.subsetSum(it)) }
}

// O(size * n)
fun IntArray.subsetSum(n: Int): Boolean {
	// dp[i][j] = whether any subset of this[i..size - 1] can sum up to j
	// dp[i][j] = false, if j <= 0 or i >= size
	//          = true, if arr[i] == j
	//          = dp[i + 1][j - this[i]] || dp[i + 1][j], o/w
	val dp = Array(size + 1) { BooleanArray(n + 1) }
	for (i in size - 1 downTo 0) {
		for (j in 1..n) {
			dp[i][j] = when {
				this[i] == j -> true
				j - this[i] < 0 -> dp[i + 1][j]
				else -> dp[i + 1][j - this[i]] || dp[i + 1][j]
			}
		}
	}
	return dp[0][n]
}

