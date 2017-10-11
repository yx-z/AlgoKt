// Longest Palindrome Subsequence (you can skip some characters)
fun main(args: Array<String>) {
	val s = "a0b1a"

	// aba is the LPS
	println(lenLPS(s))
}

fun lenLPS(s: String): Int {
	val len = s.length

	val dp = Array(len) { Array(len) { 0 } }

	for (i in 0 until len) {
		dp[i][i] = 1
	}

	for (l in 2..len) {
		for (i in 0 until len - l + 1) {
			val j = i + l - 1

			dp[i][j] = if (s[i] == s[j]) {
				if (l == 2) {
					2
				} else {
					dp[i + 1][j - 1] + 2
				}
			} else {
				Math.max(dp[i + 1][j], dp[i][j - 1])
			}
		}
	}

	return dp[0][len - 1]
}

/*
public int LPS(String s) {
	int len = s.length();
	int[][] dp = new int[len][len];
	
	for (int i = 0; i < len; i++) {
		dp[i][i] = 1;
	}
	
	for (int d = 1; d < len; d++) {
		for (int i = 0; i < len - d; i++) {
			int j = i + d;
			if (s.charAt(i) == s.charAt(j)) {
				dp[i][j] = 2 + dp[i + 1][j - 1];
			} else {
				dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
			}
		}
	}
	
	return dp[0][len - 1];
}
*/