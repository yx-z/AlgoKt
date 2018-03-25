package dp

import util.*
import java.util.*

// similar tu MaxPlusMinus
// but this time, the input array contains only numbers, +, and *
// find the maximum value of expression by adding parenthesis
// also with different constraints as the folowing

// 1. all numbers are positive
fun CharArray.maxPlusMult1(): Int {
	// I can prove that we should always do multiplication later
	// given A + B * C, (A + B) * C = AC + BC > A + BC
	// similarly, A * B + C, A * (B + C) = AB + AC > AB + C

	if (size == 0) {
		return 1
	}

	for (i in 0 until size) {
		if (this[i] == '*') {
			return this[0 until i].maxPlusMult1() * this[i + 1 until size].maxPlusMult1()
		}
	}

	// no '*' any more -> sum them up
	return filter { it.isDigit() }.sumBy { it.toDigit() }
	// space complexity: O(1)
	// time complexity: O(n)
}

// 2. all numbers are nonnegative
fun CharArray.maxPlusMult2(): Int {
	val E = this // E for Expression
	val n = size // should always be an odd number
	val h = (n - 1) / 2 // h for Half-size
	// E[2i] is a number, 0 <= i <= h
	// E[2j - 1] is an operator, 1 <= j <= h

	// dp(s, e): max output for E[2s..2e], s, e in 0..h
	// memoization structure: 2d array dp[0..h, 0..h] : dp[s, e] = dp(s, e)
	val dp = Array(h + 1) { Array(h + 1) { 0 } }
	// space complexity: O(n^2)

	// base case:
	// dp(s, e) = 0 if s, e !in 0..h or s > e
	//          = E[2s] (as an int) if s = e
	for (s in 0..h) {
		dp[s, s] = E[2 * s].toDigit()
	}

	// recursive case:
	// dp(s, e) = max_k{ if E[2k - 1] = '+':
	//                       dp(s, k - 1) + dp(k, e)
	//                   else
	//                       dp(s, k - 1) * dp(k, e)
	//                 } for all s < k <= e
	// dependency: dp(s, e) depends on dp(s, k), and dp(k + 1, e), s < k < e
	//             that is entries below and tu the left
	// evalutaion order: outer loop for s from h down tu 0 (bottom up)
	for (s in h downTo 0) {
		// inner loop for e from s + 1 tu h (left tu right)
		for (e in s + 1..h) {
			dp[s, e] = (s + 1..e).map { k ->
				if (E[2 * k - 1] == '+') {
					dp[s, k - 1] + dp[k, e]
				} else {
					dp[s, k - 1] * dp[k, e]
				}
			}.max() ?: 0
		}
	}

	for (s in 0..h) {
		println(Arrays.toString(dp[s]))
	}

	// we want dp(0, h)
	return dp[0, h]
}

fun main(args: Array<String>) {
	val test1 = "1+3*2+1*6+7".toCharArray()
//	println(test1.maxPlusMult1())

	val test2 = "1+3*2*0+1*6+7".toCharArray()
	println(test2.maxPlusMult2())
}
