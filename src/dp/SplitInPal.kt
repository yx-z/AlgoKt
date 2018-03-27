package dp

import util.OneArray
import util.get
import util.set
import util.toOneArray

// given a String S[1..n]

fun main(args: Array<String>) {
	val str = "BUBBASEESABANANA".toCharArray().toList().toOneArray()
//	println(str.minSplitInPal()) // BUB BASEESAB ANANA -> 3

//	println(str.maxSplitLen()) // BUB BASEESAB ANANA -> 3

	println(str.numSplitInPal())
}

// 1. determine the smallest number of palindromes that make up the string
fun OneArray<Char>.minSplitInPal(): Int {
	val S = this
	val n = S.size
//	S.prettyPrintln(true)

	// preprocess to util.get isPal[i..j] that determines if S[i, j] is palindromic
	val isPal = isPal()
//	isPal.util.prettyPrintTable(true)

	// dp(i): # of pal that can separate S[1..i]
	// memoization structure: 1d array dp[1..n] : dp[i] = dp(i)
	val dp = OneArray(n) { 0 }
	// space complexity: O(n)

	// base case:
	// dp(i) = 0 if i !in 1..n
	dp.getterIndexOutOfBoundHandler = { 0 }
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

// 2. find the util.max int k : S can be split into palindromes of len at least k
fun OneArray<Char>.maxSplitLen(): Int {
	val S = this
	val n = S.size

	// see util fun below
	// space complexity: O(n^2)
	// time complexity: O(n^2)
	val isPal = isPal()

	// dp(i): util.max int k : S[1..i] can be split into palindroms of len at least k
	// memoization structure: 1d array dp[1..n] : dp[i] = dp(i)
	val dp = OneArray(n) { 1 }
	// space complexity: O(n)

	// base case:
	// dp(1) = 1

	// recursive case:
	// assume util.max{ } = 1
	// dp(i) = i if S[1..i] is palindromic
	//       = max_k{ dp(k) : 1 <= k < i,
	//                and S[k + 1..i] is palindromic,
	//                and i - k >= dp(k) } o/w
	// dependency: dp(i) depends on dp(k) : k < i, that is entries to the left
	// evaluation order: outer loop for i from 2 to n (top down)
	for (i in 2..n) {
		dp[i] = if (isPal[1, i]) {
			i
		} else {
			// inner loop for k from 1 until i (left to right)
			((1 until i)
					.filter { k -> isPal[k + 1, i] && i - k >= dp[k] }
					.map { k -> dp[k] }
					.max()) ?: 1
		}
	}
	// time complexity: O(n^2)

//	dp.prettyPrintln()

	// we want dp(n)
	return dp[n]
	// overall space complexity: O(n^2)
	// overall time complexity: O(n^2)
}

// 3. find the total number of ways to split S into palindromes
fun OneArray<Char>.numSplitInPal(): Int {
	val S = this
	val n = S.size

	// see util fun below
	// space complexity: O(n^2)
	// time complexity: O(n^2)
	val isPal = isPal()

	// dp(i): # of ways to split S[1..i] into palindromes
	// memoization structure: 1d array dp[0..n] : dp[i] = dp(i)
	val dp = OneArray(n) { 1 }

	// base case:
	// dp(0) = 1
	dp.getterIndexOutOfBoundHandler = { 1 }

	// recursive case:
	// dp(i) = sum{ dp(k) : 0 <= k < i and S[k + 1..i] is palindromic }
	// dependency: dp(i) depends on dp(k) : k < i, that is entries to the left
	// evaluation order: outer loop for i from 2 to n (top down)
	for (i in 1..n) {
		// inner loop for k from 1 until i (left to right)
		dp[i] = (0 until i)
				.filter { k -> isPal[k + 1, i] }
				.map { k -> dp[k] }
				.sum()
	}
	// time complexity: O(n^2)

	dp.prettyPrintln()

	// we want dp(n)
	return dp[n]
	// overall space complexity: O(n^2)
	// overall time complexity: O(n^2)
}

// util fun to return a table isPal[i, j] indicating if S[i..j] is palindromic
fun OneArray<Char>.isPal(): OneArray<OneArray<Boolean>> {
	val S = this
	val n = S.size
	val isPal = OneArray(n) { OneArray(n) { false } }
	// space complexity: O(n^2)
	// base case:
	// isPal(i, j) = true if i >= j
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
	return isPal
}
