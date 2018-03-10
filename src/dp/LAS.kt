package dp

import get
import set
import max

// longest alternating subsequence
fun main(args: Array<String>) {
	val arr = arrayListOf(5, 6, 7, 4, 8, 10, 9)
	println(arr.las())
}

fun ArrayList<Int>.las(): Int {
	// i < j
	// dp[sign, i, j] = length of las that starts with first element greater than this[i]
	//                  and second element smaller than this[j], if sign == 0
	//                  length of las that starts with first element smaller than this[i]
	//                  and second element greater than this[j], if sign == 1
	// dp[sign, i, j] = 0, if j > n
	//                = dp[sign, i, j + 1], if (this[j] <= this[i] && sign == 0) ||
	//                                         (this[j] >= this[i] && sign == 1)
	//                = max(dp[sign, i, j + 1], 1 + dp[!sign, j, j + 1]), o/w
	val dp = Array(2) { Array(size + 1) { IntArray(size + 1) } }

	for (i in size - 1 downTo 0) {
		for (j in size - 1 downTo i + 1) {
			if (this[j] <= this[i]) {
				dp[0, i, j] = dp[0, i, j + 1]
			} else {
				dp[0, i, j] = max(dp[0, i, j + 1], 1 + dp[1, j, j + 1])
			}

			if (this[j] >= this[i]) {
				dp[1, i, j] = dp[1, i, j + 1]
			} else {
				dp[1, i, j] = max(dp[1, i, j + 1], 1 + dp[0, j, j + 1])
			}
		}
	}

	var max = 0
	for (i in 0 until size) {
		max = max(max, dp[0, i, i + 1], dp[1, i, i + 1])
	}
	return max + 1
}
