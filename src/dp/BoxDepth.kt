package dp

import util.*

typealias Square = Tuple4<Int, Int, Int, Int>
// given n squares of size 10 as (X, Y) where X is the x-coordinate of its bottom
// left corner and Y is the y-coordinate of its bottom left corner

// find the largest subset of such squares : there is a common point in them
// and report the size of such set
fun OneArray<Tuple2<Int, Int>>.commonPoint(): Int {
	val S = this
	val n = size
	// consider the input as a stack of squares piled from bottom to top (even
	// though they don't overlap between each other, but just floating around)

	// dp(i): largest such set that starts @ i-th square
	val dp = OneArray(n) { 0 tu 0 tu 0 tu 0 tu 0 }
	dp[n] = 1 tu S[n].toSquare()

	for (i in n - 1 downTo 1) {
		dp[i] = 1 tu S[i].toSquare()

		for (j in i + 1..n) {
			val area = dp[j].second tu dp[j].third tu dp[j].fourth tu dp[j].fifth
			val overlap = S[i].overlap(area)
			if (overlap.isValid() && dp[j].first + 1 > dp[i].first) {
				dp[i] = dp[j].first + 1 tu overlap
			}
		}
	}

	return dp.maxBy { it.first }?.first ?: 0
}

private fun Tuple2<Int, Int>.toSquare() = this tu first + 10 tu second + 10

private fun Tuple2<Int, Int>.overlap(s: Square): Square {
	// O(1) comparing
	// r1 is the rectangle 1 that has smaller bottom right x coordinate
	val (r1brx, r1bry, r1tlx, r1tly) = if (first < s.first) toSquare() else s
	val (r2brx, r2bry, r2tlx, r2tly) = if (first < s.first) s else toSquare()

	if (r1tlx < r2brx || r2tlx < r1brx || r1tly < r2bry || r2tly < r1bry) {
		return 0 tu 0 tu 0 tu 0
	}

	return max(r1brx, r2brx) tu max(r1bry, r2bry) tu min(r1tlx, r2tlx) tu min(r1tly, r2tly)
}

private fun Square.isValid() = third > first && fourth > second

fun main(args: Array<String>) {
	val s = oneArrayOf(
			0 tu 0,
			1 tu 1,
			9 tu 9,
			10 tu 10,
			100 tu 200,
			105 tu 190)
	println(s.commonPoint())
}