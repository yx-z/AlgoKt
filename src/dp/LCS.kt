package dp

import get
import set
import max

// Longest Common Subsequence
fun main(args: Array<String>) {
	val a1 = intArrayOf(1, 5, 0, 12, 9)
	val a2 = intArrayOf(5, 12, 9, 1)
	println(a1 lcs a2) // [5, 12, 9] -> 3
}

infix fun IntArray.lcs(that: IntArray): Int {
	// dp[i, j] = length of lcs for this[0 until i] & that[0 until j]
	// dp[i, j] = 0, if (i == 0 || j == 0)
	//          = if (this[i] == that[j]) {
	//                  max(1 + dp[i - 1, j - 1], dp[i - 1, j], dp[i, j - 1])
	//            } else {
	//                  max(dp[i - 1, j], dp[i, j - 1], dp[i - 1, j - 1])
	//            }
	// we want dp[size, that.size]
	val dp = Array(size + 1) { IntArray(that.size + 1) }
	for (i in 1..size) {
		for (j in 1..that.size) {
			dp[i, j] = if (this[i - 1] == that[j - 1]) {
				max(1 + dp[i - 1, j - 1], dp[i - 1, j], dp[i, j - 1])
			} else {
				max(dp[i - 1, j], dp[i, j - 1], dp[i - 1, j - 1])
			}
		}
	}
	return dp[size, that.size]
}
