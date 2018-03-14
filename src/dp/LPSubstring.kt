package str

fun main(args: Array<String>) {
	// test string
	val s = "abbaaa"
	// should be abba -> 4
	println(lenLPS(s))
}

// Longest Palindrome Substring (consecutive chars) (NOT dp.LPS (Longest Palindrome Subsequence))
// substring must be consecutive
fun lenLPS(s: String): Int {
	val len = s.length
	val dp = Array(len) { Array(len) { false } }
	var max = 1
	for (i in 0 until len) {
		dp[i][i] = true
	}

	for (l in 2..len) {
		for (i in 0..len - l) {
			val j = i + l - 1
			dp[i][j] = if (s[i] == s[j]) {
				if (s[i + 1] == s[j - 1] || l == 2) {
					max = maxOf(max, l)
					true
				} else {
					false
				}
			} else {
				false
			}
		}
	}
	return max
}
