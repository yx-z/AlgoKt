package dp

import util.*

// vogue vogue revolution
// suppose you are playing a game, vvr, originated from a dance machine
// in each round, someone gives you an instruction as one of the 4-way arrows
// for example the instruction would be <, ^, v, >
// your two feet initially stand on arrow < and >
// if you already stand on an arrow which is the same as the current instruction
// you can earn a point if you choose to not move either of your feet in this round
// but if neither of your feet is standing on the correct arrow in the round
// you lose immediately
// and in each round, you can move either of your feet, but only once (in total)
// given all instructions of one game as an array Arrows[1..n]
// find the maximum number of points you can get in such vvr game
fun main(args: Array<String>) {
	val Arrows = oneArrayOf(Arrow.U, Arrow.U, Arrow.D, Arrow.D, Arrow.L, Arrow.R, Arrow.L, Arrow.R)
	println(Arrows.maxPoint())
}

fun OneArray<Arrow>.maxPoint(): Int {
	val Arrows = this
	val n = Arrows.size

	// dp(i): given Arrows[1..i], (max point I can get, [set of possible ending position])
	// ex. dp(i) = (12, [(L, R), (R, D)]) represents given Arrows[1..i], I can
	//     earn 12 points at most and my feet are standing on either (L and R) or (R and D)
	// memoization structure: 1d array dp[1..n] : dp[i] = dp(i)
	val dp = Array(n + 1) { 0 to HashSet<Pair<Arrow, Arrow>>() }
	// the length of set is at most 4 * 3 so the space complexity: O(2 * 4 * 3 * n) = O(n)

	// base case:
	// dp(i) = (0, []) if i !in 1..n
	// dp(1) = (0, [(L, R)]
	dp[0] = 0 to hashSetOf(Arrow.L to Arrow.R)

	// recursive case:
	// consider dp(i), we need to find dp(i)_1 and dp(i)_2:
	// dp(i)_1 = if any pair in dp(i - 1)_2 is standing on Arrows[i]
	//               mark move as false
	//               1 + dp(i - 1)
	//           else
	//               mark move as true
	//               dp(i - 1)
	// dp(i)_2 = if move is false
	//               copy dp[i - 1]_2 and remove pairs that don't contain Arrows[i]
	//           else
	//               copy dp[i - 1]_2 and transform all pairs (p1, p2) to (Arrows[i], p2), (p1, Arrows[i])
	// dependency: dp(i) depends on dp(i - 1), that is entry to the left
	// evaluation order: i from 2 to n (left to right)
	for (i in 1..n) {
		val add = dp[i - 1].second.any { (p1, p2) -> p1 == Arrows[i] || p2 == Arrows[i] }
		val first = dp[i - 1].first + if (add) 1 else 0
		if (add) {
			dp[i - 1].second
					.filter { (p1, p2) -> p1 == Arrows[i] || p2 == Arrows[i] }
					.toCollection(dp[i].second)
		} else {
			dp[i - 1].second
					.forEach { (p1, p2) ->
						dp[i].second.add(p1 to Arrows[i])
						dp[i].second.add(Arrows[i] to p2)
					}
		}
		dp[i] = first to dp[i].second
	}
	// time complexity: O(n)

	// we want dp(n)_1
	return dp[n].first
}

enum class Arrow {
	L, // < Left
	R, // > Right
	U, // ^ Up
	D; // v Down
}

