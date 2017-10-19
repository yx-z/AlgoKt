package dp

fun main(args: Array<String>) {
	// "ra*bbit", "rab*bit", "rabb*it" -> 3
	println("rabbbit" countSubsequencesEqualsTo "rabbit")
}

infix fun String.countSubsequencesEqualsTo(sub: String): Int {
	val lenBig = this.length
	val lenSmall = sub.length

	// dp[i][j]: # of subsequences of this.substring(0, i) = sub.substring(0, j)
	val dp = Array(lenBig + 1) { IntArray(lenSmall + 1) { 0 } }

	// consider sub.substring(0, 0) == "", it is the subsequence of any string
	// i.e. dp[*][0] = 1
	(0..lenBig).forEach { dp[it][0] = 1 }

	(1..lenBig).forEach { row ->
		(1..lenSmall).forEach { col ->
			// ignore current character from big
			// we have at least the same # of substrings from the substring one character shorter
			dp[row][col] = dp[row - 1][col]

			if (this[row - 1] == sub[col - 1]) {
				// if the ending character is the same
				// we can add more
				dp[row][col] += dp[row - 1][col - 1]
			}
		}
	}

	return dp[lenBig][lenSmall]
}