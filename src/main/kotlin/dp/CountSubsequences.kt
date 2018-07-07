package dp

fun main(args: Array<String>) {
	// "", "a", "b", "ab", "ba", "aa", "aba" -> 7
	println("aba".countSubsequences())
}

// return the number of distinct subsequences of that string
fun String.countSubsequences(): Int {
	val dup = HashMap<Char, Int>()
	val dp = IntArray(length + 1)
	dp[0] = 1
	(1..length).forEach {
		dp[it] = 2 * dp[it - 1]
		val idx = it - 1
		dup[this[idx]]?.run { dp[it] -= dp[this] }
		dup[this[idx]] = idx
	}
	return dp[length]
}