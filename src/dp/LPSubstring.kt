package dp

import util.*

fun main(args: Array<String>) {
	val s = "abbaaa"
	// should be abba -> 4
	println(lpsubstr(s))
	println(s.toCharOneArray().lpsubstr())
}

// Longest Palindrome Substring (consecutive chars), NOT LPS
fun lpsubstr(s: String): Int {
	var sb = StringBuilder(s)
	for (i in s.length - 1 downTo 1) {
		sb = sb.insert(i, '_')
	}
	// max of the length of palindrome substring centered at s[i]
	var max = Int.MIN_VALUE
	sb.indices.forEach {
		var len = 1
		while (it - len >= 0 && it + len < sb.length && sb[it - len] == sb[it + len]) {
			len++
		}
		max = max(max, len)
	}

	return max
}

fun OneArray<Char>.lpsubstr(): Int {
	val A = this
	val n = size
	// dp[i, j] = (start index, end index) of the lpsubstr in A[i..j]
	val dp = OneArray(n) { OneArray(n) { 0 tu 0 } }
	// space: O(n^2)

	// dp[i, j] = (0, 0) if i, j !in 1..n or 1 > j
	//          = (i, j) if i = j, i, j in 1..n
	for (i in 1..n) {
		dp[i, i] = i tu i
	}

	// dp[i, j] = if A[i] != A[j]:
	//                max length among dp[i + 1, j], dp[i + 1, j - 1], dp[i, j - 1]
	//            else A[i] = A[j]:
	//                if dp[i + 1, j - 1] = (i + 1, j - 1)
	//                    (i, j)
}