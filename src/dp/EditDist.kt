// Edit Distance between 2 Strings
// count of add/remove/replace modifications to match 2 Strings
fun main(args: Array<String>) {
	// test strings
	val s1 = "algorithm"
	val s2 = "altruistic"

	println(editDistRecursion(s1, s2))
	println(editDistDP(s1, s2))
}

// Recursive Implementation
fun editDistRecursion(s1: String, s2: String): Int {
	if (s1.isEmpty()) {
		return s2.length
	}
	if (s2.isEmpty()) {
		return s1.length
	}

	return if (s1[0] == s2[0]) {
		editDistRecursion(s1.substring(1), s2.substring(1))
	} else {
		1 + min(editDistRecursion(s1.substring(1), s2),
				editDistRecursion(s1, s2.substring(1)),
				editDistRecursion(s1.substring(1), s2.substring(1)))
	}
}

// DP Implementation
fun editDistDP(s1: String, s2: String): Int {
	if (s1.isEmpty()) {
		return s2.length
	}
	if (s2.isEmpty()) {
		return s1.length
	}

	val l1 = s1.length
	val l2 = s2.length

	// dp[i][j] = edit distance between s1[1..i] and s2[1..j]
	//            *here strings are one-indexed
	// dp[i][j] = i, if j == 0, i.e. insert i characters
	//          = j, if i == 0
	//          = min(dp[i - 1][j], dp[j - 1][i]) + 1, if s1[i] != s2[j]
	//          = min(dp[i - 1][j] + 1, dp[j - 1][i] + 1, dp[i - 1][j - 1]), if s1[i] == s2[j]
	//            *this case can be optimized to just dp[i - 1][j - 1] in that if two characters are
	//            the same, we can leave them unchanged
	val dp = Array(l1 + 1) { Array(l2 + 1) { 0 } }

	// base case for emptys strings
	for (i in 0..l1) {
		dp[i][0] = i
	}
	for (i in 0..l2) {
		dp[0][i] = i
	}

	for (i1 in 1..l1) {
		for (i2 in 1..l2) {
			dp[i1][i2] = if (s1[i1 - 1] == s2[i2 - 1]) {
				dp[i1 - 1][i2 - 1]
			} else {
				1 + min(dp[i1 - 1][i2], dp[i1][i2 - 1], dp[i1 - 1][i2 - 1])
			}
		}
	}

	return dp[l1][l2]
}
