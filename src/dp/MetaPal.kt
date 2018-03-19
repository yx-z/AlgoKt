package dp

import min
import set
import get
import toOneArray
import OneArray

// a metapalindrome is a decomposition of string into palindromes,
// such that the sequence of palindrome lengths itself is a palindrome

fun main(args: Array<String>) {
	val str = "BUBBLESSEESABANANA".toCharArray().toList().toOneArray()
	println(str.metaPal()) // BUB B L E S SEES A B A N ANA -> [3, 1, 1, 1, 1, 4, 1, 1, 1, 1, 3].size == 11
}

// given S[1..n], find the minimum length of the sequence obtained by metapalindrome
fun OneArray<Char>.metaPal(): Int {
	val S = this
	val n = S.size

	// isPal[i, j]: if S[i..j] is palindromic
	// imported from SplitInPal
	// time complexity: O(n^2)
	// space complexity: O(n^2)
	val isPal = isPal()

	// dp(i): min len of metapal of S[i..n - i + 1]
	val m = (n + 1) / 2
	// memoization structure: 1d array dp[1..m : dp[i] = dp(i)
	val dp = OneArray(m) { 0 }
	// space complexity: O(n)

	// base case:
	// dp(m) = 1 if S[m..n - m + 1] is palindromic (which is always the case for odd n)
	//       = 0 o/w
	dp[m] = if (isPal[m, n - m + 1]) 1 else 0

	// recursive case:
	// dp(i) =
	// dependency: dp(i) depends on dp(k) : k > i, that is entries to the right
	// evaluation order: outer loop for i from m - 1 down to 1 (right to left)
	for (i in m downTo 1) {

	}
	// time complexity: O(n^2)

	dp.prettyPrintln()

	// we want dp(1)
	return dp[1]
}