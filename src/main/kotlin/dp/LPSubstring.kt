package dp

import util.*

fun main(args: Array<String>) {
	val s = "abbaaa"
	// should be abba -> 4
//	println(lpsubstr(s))
//	println(s.toCharOneArray().lpsubstr())
//	println(s.toCharOneArray().lpsubstring())
	println(s.toCharOneArray().lpsubstrRedo())
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

fun OneArray<Char>.lpsubstring(): Int {
	val A = this
//	A.prettyPrintln()
	val n = size

	// dp[i]: set of start indices of a palindromic substring of length i
	//        will be empty if there is no such string
	val dp = OneArray(n) { HashSet<Int>() }
	// space: O(n^2)
	// we want the index of last non-empty list

	// dp[1] = every index in A
	dp[1].addAll(indices)
	// dp[2] = O(n) brute force check
	dp[2].addAll((1 until n).filter { A[it] == A[it + 1] })

	for (i in 3..n) {
		dp[i].addAll(
				dp[i - 2]
						.filter {
							it - 1 >= 1 && it + i - 2 <= n &&
									A[it - 1] == A[it + i - 2]
						}
						.map { it - 1 })
	}
	// time: O(n^2)
//	dp.prettyPrintln()

	return dp.indexOfLast { it.isNotEmpty() }
}

fun OneArray<Char>.lpsubstrRedo(): Int {
	val A = this
	val n = size

	// dp(i, j): len of lpsubstr : that MUST start @ i and end @ j
	val dp = OneArray(n) { OneArray(n) { 0 } }
	// space: O(n^2)

	// base case:
	// dp(i, j) = 1 if i = j
	//          = 2 if j = i + 1 and A[i] = A[j]
	//          = 0 if i > j or i, j !in 1..n
	for (i in indices) {
		dp[i, i] = 1
	}
	for (i in 1 until n) {
		if (A[i + 1] == A[i]) {
			dp[i, i + 1] = 2
		}
	}

	// we want max len of such substr
	var max = 1 // as base case

	// recursive case:
	// dp(i, j) = 0 if A[i] != A[j]
	//          = 2 + dp(i + 1, j - 1) o/w
	// dependency: dp(i, j) depends on dp(i + 1, j - 1)
	//             , that is, entry to the lower-left
	// eval order: outer loop for i decreasing from n - 1 down to 1
	for (i in n - 2 downTo 1) {
		// inner loop for j increasing from i + 1 to n
		for (j in i + 2..n) {
			dp[i, j] = if (A[i] != A[j]) 0 else 2 + dp[i + 1, j - 1]
			max = max(max, dp[i, j])
		}
	}
	// time: O(n^2)

//	dp.prettyPrintTable()

	return max
}