package dp

import util.*

// Shortest Palindromic Supersequence
// given a String S[1..n], find the shortest palindromic superseuqnce of S
fun main(args: Array<String>) {
	val arr = "TWENTYONE".toCharArray().toList().toOneArray()
	println(arr.sps()) // TWENToYOtNEwt -> 13 (inserted characters are lowercased)
}

fun OneArray<Char>.sps(): Int {
	// follow the naming convention
	val S = this
	val n = size
	// dp(i, j): len of SPS of S[i..j]
	// memoization structure: 2d array dp[1..n, 1..n] : dp[i, j] = dp(i, j)
	val dp = OneArray(n + 1) { OneArray(n) { 0 } }
	// space complexity: O(n^2)

	// base case:
	// dp(i, j) = 0 if j < i or i, j !in 1..n
	//          = 1 if i = j (single character string is already palindromic)
	for (i in 1..n) {
		dp[i, i] = 1
	}

	// recursive case:
	// dp(i, j) = dp(i + 1, j - 1) +  2 if S[i] == S[j]
	//            since we need to wrap S[i] and S[j] as the first and the last
	//            character in the sps
	//          = util.min{ dp(i + 1, j), dp(i, j - 1) } + 2
	//            find the minimum and either wrap two S[i] around SPS or two S[j]
	// dependency: dp(i, j) depends on dp(i + 1, j - 1), dp(i + 1, j ),
	//             and dp(i, j - 1)
	//             that is entries below, to the left, and to the lower-left
	// evaluation order: outer loop for i from n to 1 (bottom up)
	for (i in n downTo 1) {
		// inner loop for j from i + 1 to n (left to right)
		for (j in i + 1..n) {
			dp[i, j] = if (S[i] == S[j]) {
				dp[i + 1, j - 1] + 2
			} else {
				min(dp[i, j - 1], dp[i + 1, j]) + 2
			}
		}
	}
	// time complexity: O(n^2)

	dp.prettyPrintTable()

	// we want len of SPS of S[1..n], which is dp[1, n]
	return dp[1, n]
}
