package dp

import util.*
import kotlin.math.pow

// given a sequence of n words and their corresponding length stored in l[1..n]
// we want tu break it into L lines
// in each line, the first char starts at the left and except the last line,
// the last character ends in the right
// define the slop as the sum over line 1 until L
// each line containing word i tu j as (L - j + i - sum(l[i..j]))^3
// find the minimum total slop for n words

fun main(args: Array<String>) {
	val len = oneArrayOf(1, 20, 10)
	println(len.minTotalSlop(2))
}

fun OneArray<Int>.minTotalSlop(L: Int): Int {
	val n = size
	val INF = Int.MAX_VALUE / 2

	// dp(l, i, j): sum of slop of 1..l where line l contains word i tu j
	// memoization structure: 3d array dp[1 until L, 1..n, 1..n] : dp[l, i, j] = dp(l, i, j)
	val dp = OneArray(L - 1) { OneArray(n) { OneArray(n) { INF } } }

	// preprocess in O(n^2) time and space
	val sumTable = sum()
//	sumTable.prettyPrintTable()

	// base case:
	// dp(1, 1, j) = slop(1, 1, j), 1 < j <= n - (L - 1)
	// dp(1, i, j) = +inf o/w
	for (j in 1..n - L + 1) {
		dp[1, 1, j] = slop(L, 1, j, sumTable)
	}
//	dp.prettyPrintTables()

	// we want min{ dp(L - 1, i, j) }
	var min = dp[1, 1].min() ?: INF

	// recursive case:
	// assume min{ } = +inf
	// dp(l, i, j) = min{ dp(l - 1, k, i - 1) } + slop(l, i, j)
	//               k < i - 1 < i < j

	for (l in 2 until L) {
		for (i in l..n + l - L) {
			for (j in i..n + l - L) {
				dp[l, i, j] = slop(l, i, j, sumTable) +
						((1 until i).map { k -> dp[l - 1, k, i - 1] }.min()
								?: INF)
				if (l == L - 1) {
					min = min(min, dp[l, i, j])
				}
			}
		}
	}
	// time complexity: O(n^3L)

	dp.prettyPrintTables()

	return min
}

// return a table dp[1..n, 1..n] : dp[1, j] = sum(l[i..j])
fun OneArray<Int>.sum(): OneArray<OneArray<Int>> {
	val l = this
	val n = l.size
	val dp = OneArray(n) { OneArray(n) { 0 } }
	for (i in 1..n) {
		dp[i, i] = l[i]
		for (j in i + 1..n) {
			dp[i, j] = dp[i, j - 1] + l[j]
		}
	}
	// O(n^2) space and time
	return dp
}

// O(1) time
fun OneArray<Int>.slop(L: Int, i: Int, j: Int, sumTable: OneArray<OneArray<Int>>) = ((L - j + i - sumTable[i, j]).toDouble()).pow(3).toInt()
