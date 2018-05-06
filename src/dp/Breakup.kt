package dp

import util.OneArray
import util.get
import util.set
import util.toCharOneArray
import kotlin.math.abs

// given a String w in (0+1)^* of length m, two natural numbers t and g,
// determine if there exists a breakup of w :
// w is split into at most t parts
// each part satisfies |# of 0s - # of 1s| < g

fun OneArray<Char>.breakup(t: Int, g: Int): Boolean {
	val w = this
	val m = size

	// dp(i, j): w[i..m] can have a breakup of at most j parts w/ same g
	val dp = OneArray(m) { OneArray(t) { false } } // space: O(mt)

	// dp(i, 1) = w[i..m] satisfies constraint on g
	for (i in 1..m) {
		dp[i, 1] = abs(w[i..m].count { it == '0' } - w[i..m].count { it == '1' }) < g
	}
	// time: O(m^2)

	// dp(i, j) = dp(i, j - 1) OR
	// Union_{k in i + 1..m} { dp(k, j) AND w[i..k] satisfies constraint on g }
	// dependency: dp(i, j) depends on dp(i, j - 1) and dp(k, j)
	// eval order: outer loop for j from 2 to t
	for (j in 2..t) {
		// inner loop for i from m down to 1
		for (i in m downTo 1) {
			dp[i, j] = dp[i, j - 1] ||
					(i + 1..m).any { k ->
						dp[k, j] && abs(w[i..k].count { it == '0' } - w[i..k].count { it == '1' }) < g
					}
		}
	}
	// time: O(m^2t)

	// we want dp(1, t)
	return dp[1, t]
}


fun main(args: Array<String>) {
	val w = "01110010".toCharOneArray()
	println(w.breakup(3, 2))
}