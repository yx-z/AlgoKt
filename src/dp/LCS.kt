import java.lang.Math.max

// Longest Common Subsequence
fun main(args: Array<String>) {
	// test strings
	val s1 = "abcde"
	val s2 = "abce"

	println(lenLCS(s1, s2))
}

/**
 * @param s1 first string to be compared with
 * @param s2 second string to be compared with
 * @return length of the longest common subsequence between s1 and s2
 *
 * dynamic programing method:
 * dp[i][j] = LCS for s1.substring(0, i + 1) & s2.substring(0, j + 1)
 * dp[i][j] = when {
 *                  s1[i] == s2[j] -> dp[i - 1][j - 1] + 1 // previous LCS length + 1
 *                  else -> max(dp[i - 1][j], dp[i][j - 1]) //
 *            }
 * dp[0][*] = dp[*][0] = 0, any LCS with empty string is 0
 */
fun lenLCS(s1: String, s2: String): Int {
	val l1 = s1.length
	val l2 = s2.length
	val dp = Array(l1 + 1) { Array(l2 + 1) { 0 } }

	for (i1 in 1 .. l1) {
		for (i2 in 1 .. l2) {
			dp[i1][i2] = if (s1[i1 - 1] == s2[i2 - 1]) {
				dp[i1 - 1][i2 - 1] + 1
			} else {
				max(dp[i1 - 1][i2], dp[i1][i2 - 1])
			}
		}
	}

	return dp[l1][l2]
}