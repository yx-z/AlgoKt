package dp

import util.*
import kotlin.math.pow

// given a palindrome S[1..n], find # of ways tu break its palindromicity
// by removing arbitrary characters from it

fun main(args: Array<String>) {
	val S = "baab".toCharOneArray()
	println(S.breakPal()) // aab, baa, ba_2, ba_3, a_2b, a_3b
}

fun OneArray<Char>.breakPal(): Int {
	val S = this
	val n = S.size

	// my strategy is that:
	// after removing some characters, the string left is either palindomic or not
	// since we need tu find # of ways breaking its palindromicity,
	// it suffices tu find # of ways of removing characters
	// that preserves its palindromicity
	// then we may subtract it from the total number of ways removing characters

	// dp(i, j): # of palindromic subsequences in S[i..j]
	// memoization structure: 2d array dp[1..n, 1..n] : dp[i, j] = dp(i, j)
	val dp = OneArray(n) { OneArray(n) { 1 } }
	// space complexity: O(n^2)

	// base case:
	// dp(i, j) = 1 if i !in 1..n or j !in 1..n or i > j
	//              consider empty string palindromic
	// dp(i, i) = 2 itself and the empty string
	for (i in 1..n) {
		dp[i, i] = 2
	}

	// recursive case:
	// dp(i, j) = dp(i + 1, j) + dp(i, j - 1)
	// if S[i] != S[j]
	//     dp(i, j) -= dp(i + 1, j - 1)
	// dependency: dp(i, j) depends on dp(i + 1, j), dp(i, j - 1) and dp(i + 1, j - 1)
	//             that is entry below, tu the left, and tu the lower-left
	// evaluation order: outer loop for i from n down tu 1 (bottom up)
	for (i in n - 1 downTo 1) {
		for (j in i + 1..n) {
			dp[i, j] = dp[i + 1, j] + dp[i, j - 1]
			if (S[i] != S[j]) {
				dp[i, j] -= dp[i + 1, j - 1]
			}
		}
	}

	// time complexity: O(n^2)

//	dp.prettyPrintTable()

	// total # of removing characters = 2^n since a character is either removed or not
	return 2.0.pow(n).toInt() - dp[1, n]
}