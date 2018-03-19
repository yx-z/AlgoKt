package dp

import get
import set
import min
import OneArray
import prettyPrintTable
import toOneArray

// given a String S[1..n],
// determine the smallest number of palindromes that make up the string
fun main(args: Array<String>) {
	val str = "BUBBASEESABANANA".toCharArray().toList().toOneArray()
	println(str.splitInPal())
}

fun OneArray<Char>.splitInPal(): Int {
	val S = this
	val n = S.size
//	S.prettyPrintln(true)

	// first we may preprocess to get isPal[i, j] = isPal(S[i..j]), i <= j
	val isPal = OneArray(n) { OneArray(n) { false } }
	// space complexity: O(n^2)
	// base case:
	// isPai(i, j) = true if i >= j
	for (i in 1..n) {
		for (j in 1..i) {
			isPal[i, j] = true
		}
	}
	// time complexity: O(n^2)
	// recursive case:
	// isPal(i, j) = isPal(i + 1, j - 1) && S[i] == S[j]
	// dependency: isPal[i, j] depends on isPal[i + 1, j - 1]
	//             , that is entry to the lower-left
	// evaluation order: outer loop for i from n down to 1 (bottom up)
	for (i in n downTo 1) {
		// inner loop for j from i + 1 to n (left to right)
		for (j in i + 1..n) {
			if (i != j) {
				isPal[i, j] = isPal[i + 1, j - 1] && S[i] == S[j]
			}
		}
	}
	// time complexity: O(n^2)
//	isPal.prettyPrintTable(true)

	// dp(i): # of pal that can separate S[1..i]
	// memoization structure: 1d array dp[1..n] : dp[i] = dp(i)
	val dp = OneArray(n) { 0 }
	// space complexity: O(n)

	// base case:
	// dp(i) = 0 if i !in 1..n
	dp.assignGetOverflowHandler { 0 }
	// dp(1) = 1
	dp[1] = 1

	// recursive case:
	// dp(i) = min_k{ dp(k - 1) } + 1 where S[k..i] is a palindrome, 1 <= k <= i
	// dependency: dp[i] depends on dp(j) where j < i
	//             , that is entries to the left
	// evaluation order: outer loop for i from 2 to n (top down)
	for (i in 2..n) {
		// inner loop for k from 1 until i (left to right)
		dp[i] = (1..i)
				.filter { k -> isPal[k, i] }
				.map { k -> dp[k - 1] }.min()!! + 1
	}
	// time complexity: O(n^2)

//	dp.prettyPrintln(true)

	// we want dp(n)
	return dp[n]
	// overall space complexity: O(n^2)
	// overall time complexity: O(n^2)
}
