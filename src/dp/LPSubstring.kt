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
//	A.prettyPrintln()
	val n = size
	// dp[i, j] = (start index, end index) of the lpsubstr in A[i..j]
	val dp = OneArray(n) { i -> OneArray(n) { j -> i tu j } }
	// space: O(n^2)

	// dp[i, j] = (i, j) if i = j, i, j in 1..n
	for (i in 1..n) {
		dp[i, i] = i tu i
	}

	// dp[i, j] = if A[i] != A[j]:
	//                max length among dp[i + 1, j], dp[i + 1, j - 1], dp[i, j - 1]
	//            else A[i] = A[j]:
	//                if dp[i + 1, j - 1] = (i + 1, j - 1)
	//                    (i, j)
	//                else
	//                    max length among dp[i + 1, j], dp[i + 1, j - 1], dp[i, j - 1]
	// dp[i, j] depends on entries below and to the left
	// eval order: outer loop for i from n - 1 down to 1
	for (i in n - 1 downTo 1) {
		// inner loop for j from i + 1 to n
		for (j in i + 1..n) {
			dp[i, j] = if (A[i] == A[j] && dp[i + 1, j - 1] == (i + 1 tu j - 1)) {
				i tu j
			} else {
				arrayOf(dp[i, j - 1], dp[i + 1, j - 1], dp[i + 1, j])
						.maxBy { (s, e) ->
							e - s + 1
						} ?: 0 tu 0
			}
		}
	}
	// time: O(n^2)
//	dp.prettyPrintTable()

	val (start, end) = dp[1, n]
	return end - start + 1
}