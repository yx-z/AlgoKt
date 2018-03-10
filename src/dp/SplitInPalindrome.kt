package dp

import get

// given a string, determine the smallest number of palindromes that make up the string
fun main(args: Array<String>) {
	val s = "bubabaab" // bub a baab -> 3
	println(s.splitInPal())
}

fun String.splitInPal(): Int {
	// dp[i] = # of palindromes that make up this[i until length]
	// dp[i] = 1, if i == length - 1
	//       = 1 + min(dp[j + 1]) where j > i && this[i..j].isPal(), o/w
	val dp = IntArray(length) { Int.MAX_VALUE }
	dp[length - 1] = 1
	for (i in length - 2 downTo 0) {
		dp[i] = 1 + (dp.filterIndexed { idx, _ -> idx - 1 > i && this[i until idx].isPal() }
				.min() ?: 0)
	}

	return dp[0]
}

fun String.isPal() = this == this.reversed()
