package dp

import dp.Candy.*
import util.*

// suppose you are given three types of candies: A, B, C
enum class Candy(val v: Int) {
	A(1), B(2), C(3);
}

// and also a row of such candies R[1..n]
// you start with one candy A in hand and traverse through the row R from 1 to n

// at each index, you may either swap your candy in hand or not
// if you don't swap the candy, your total score won't change
// if you choose to swap the candy: if the candy is the same, you earn one point
// o/w, you will lose one point (yes your score can be negative)

// find the max score you can earn after traversing R[1..n] only once

fun main(args: Array<String>) {
	val R = oneArrayOf(A, B, C, C, B, A)
	println(R.maxScore())
}

fun OneArray<Candy>.maxScore(): Int {
	val R = this
	val TYPES = Candy.values().size // # of different candies
	val n = size

	// dp[i, j]: max score I can earn if I am given a row R[i..n] and currently
	// have a candy of type j in hand (assume 1 for A, 2 for B, 3 for C)
	val dp = OneArray(n) { OneArray(TYPES) { 0 } }
	// space: O(n)

	// base case:
	// dp[n, T] = 1 if R[n] = T
	//          = 0 o/w
	dp[n, R[n].v] = 1

	// recursive case:
	// dp[i, j] = max { I swap with R[i], NOT swap with R[i] }
	//          = max { (if R[i] = j: 1 else: 0) + dp[i + 1, R[i]], dp[i + 1, j] }
	// dependency: dp[i, j] depends on dp[i + 1, k], that is entries to the right
	// eval order: decreasing i from n - 1 down to 1
	for (i in n - 1 downTo 1) {
		// any order for j
		for (j in 1..TYPES) {
			dp[i, j] = max(dp[i + 1, j], (if (R[i].v == j) 1 else 0) + dp[i + 1, R[i].v])
		}
	}
	// time: O(n)
//	dp.prettyPrintTable()

	// we want to know given R[1..n] and a candy of type A at first
	return dp[1, 1]
}