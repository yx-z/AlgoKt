package dp

import util.OneArray
import util.get
import util.toOneArray

// a metapalindrome is a decomposition of string into palindromes,
// such that the sequence of palindrome lengths itself is a palindrome

fun main(args: Array<String>) {
	val str = "BUBBLESSEESABANANA".toCharArray().toList().toOneArray()
	println(str.metaPal()) // BUB B L E S SEES A B A N ANA -> [3, 1, 1, 1, 1, 4, 1, 1, 1, 1, 3].size == 11
}

// given S[1..n], find the minimum length of the sequence obtained by metapalindrome
fun OneArray<Char>.metaPal(): Int {
	val S = this
//	S.prettyPrintln()
	val n = S.size

	// isPal[i, j]: if S[i..j] is palindromic
	// imported from SplitInPal
	// time complexity: O(n^2)
	// space complexity: O(n^2)
	val isPal = isPal()

	// dp(i): min len of metapal of S[i..n - i + 1]
	val lM = (n + 1) / 2
	val rM = n - lM + 1
	// memoization structure: 1d array dp[1..lM] : dp[i] = dp(i)
	val dp = OneArray(lM) { 0 }
	// space complexity: O(n)

	// base case:
	// dp(lM) = 1 if S[lM..rM] is palindromic (lM = rM in the case of odd n)
	//        = 2 o/w
	dp[lM] = if (isPal[lM, rM]) 1 else 2

	// recursive case:
	// dp(i) = 1 if S[i..j] is palindromic
	//         min{ dp(k + 1) + 2 } : i <= k < lM,
	//         S[i..k] and S[s..j] are both palindromic
	//         where lM - i = j - rM and k - i = j - s
	// dependency: dp(i) depends on dp(k) : k > i, that is entries to the right
	// evaluation order: outer loop for i from lM - 1 down to 1 (right to left)
	for (i in lM - 1 downTo 1) {
		// lM - i = j - rM -> j = lM - i + rM
		val j = lM - i + rM
		dp[i] = if (isPal[i, j]) {
			1
		} else {
			(i until lM)
					.filter { k ->
						// k - i = j - s -> s = j - (k - i) = j - k + i
						val s = j - k + i
						isPal[i, k] && isPal[s, j]
					}
					.map { k -> dp[k + 1] + 2 }
					.min()!!
		}
	}
	// time complexity: O(n^2)

//	dp.prettyPrintln()

	// we want dp(1)
	return dp[1]
	// overall space complexity: O(n^2)
	// overall time complexity: O(n^2)
}