package dp

import util.get

// given an array of look-up frequencies of some sorted array
// find the minimum cost for all look-ups
// where cost = sum over all nodes (# of ancestors * freq)  [one's ancestor includes itself]
fun main(args: Array<String>) {
	val freq = intArrayOf(5, 8, 2, 1, 9, 5)
	println(freq.bstCost())
}

fun IntArray.bstCost(): Int {
	// dp[i][j] = optimal cost for this[i..j]
	// dp[i][j] = 0, if i > j
	//            sum(this[i..j]) + util.min(dp[i][r - 1] + dp[r + 1][j]): i <= r <= j, o/w
	val dp = Array(size) { IntArray(size) }
	for (j in 0 until size) {
		for (i in j downTo 0) {
			dp[i][j] = this[i..j].sum() + ((i..j).map {
				when {
					it - 1 < 0 -> dp[it + 1][j]
					it + 1 >= size -> dp[i][it - 1]
					else -> dp[i][it - 1] + dp[it + 1][j]
				}
			}.min() ?: 0)
		}
	}
	return dp[0][size - 1]
}
