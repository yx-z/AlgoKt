package dp

import util.*

// given an n digit int as X[1..n] where X[1] is the most significant bit,
// you are allowed to remove one digit from either end of this number until
// no digits are left
// we define X's square depth as max # of perfect squares you can get during
// the process described above
// ex. 32492 has square depth 3 since 3249, 324, 4, are all perfect squares
fun main(args: Array<String>) {
	val X = oneArrayOf(3, 2, 4, 9, 2)
	println(X.squareDepth()) // 3
}

fun OneArray<Int>.squareDepth(): Int {
	val X = this
	val n = size

	val dp = OneArray(n) { OneArray(n) { 0 } }
	indices.forEach { dp[it, it] = if (X[it..it].isPerfectSquare()) 1 else 0 }

	for (i in n - 1 downTo 1) {
		for (j in i + 1..n) {
			dp[i, j] = max(dp[i + 1, j], dp[i, j - 1]) + (if (X[i..j].isPerfectSquare()) 1 else 0)
		}
	}
//	dp.prettyPrintTable()

	return dp[1, n]
}

// given a k digit int as Y[1..k], assume that you can determine if it is a
// perfect square in O(k^2) time
fun OneArray<Int>.isPerfectSquare(): Boolean {
	var num = 0
	forEach {
		num *= 10
		num += it
	}

	val sqrt = Math.sqrt(num.toDouble()).toInt()
	return sqrt * sqrt == num
}