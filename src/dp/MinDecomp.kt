package dp

import min
import kotlin.math.roundToInt

// given an integer, decompose it with combinations of 1, +, and *
// report the minimum amount of 1s needed
// ex. 14 = (1 + 1) * ((1 + 1 + 1) * (1 + 1) + 1) -> 8
fun main(args: Array<String>) {
	val ints = intArrayOf(14, 21)
	ints.forEach { println(it.minDecomp()) }
}

fun Int.minDecomp(): Int {
	// dp[i] = minimum amount of 1s that make up i
	// dp[i] = min(dp[a] + dp[b], dp[c] + dp[d]), where a * b == i, c + d == i
	val dp = IntArray(this + 1) { Int.MAX_VALUE }
	dp[1] = 1
	for (i in 2..this) {
		for (a in 2..Math.sqrt(i.toDouble()).roundToInt()) {
			if (i % a == 0) {
				val b = i / a
				dp[i] = min(dp[a] + dp[b], dp[i])
			}
		}
		for (c in 1..i / 2) {
			dp[i] = min(dp[c] + dp[i - c], dp[i])
		}
	}

	return dp[this]
}