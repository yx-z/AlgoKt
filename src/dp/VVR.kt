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
	// it's so fun to use these symbols instead of enums
	val Arrows = "^^vv<><>".toCharOneArray()
//	println(Arrows.maxPoint())
	println(Arrows.maxPoints())
}

fun OneArray<Char>.maxPoint(): Int {
	val Arrows = map { it.toArrow() }.toOneArray()
	val n = Arrows.size

	// dp(i): given Arrows[1..i], (max point I can get, [set of possible ending position])
	// ex. dp(i) = (12, [(L, R), (R, D)]) represents given Arrows[1..i], I can
	//     earn 12 points at most and my feet are standing on either (L and R) or (R and D)
	// memoization structure: 1d array dp[1..n] : dp[i] = dp(i)
	val dp = Array(n + 1) { 0 tu HashSet<Tuple2<Arrow, Arrow>>() }
	// the size of the set is at most 4 * 3 so the space complexity: O((2 * 4 * 3 + 1) * n) = O(n)

	// base case:
	// dp(i) = (0, []) if i !in 1..n
	// dp(0) = (0, [(L, R)]
	dp[0] = 0 tu hashSetOf(Arrow.L tu Arrow.R)

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
		dp[i].first = dp[i - 1].first + if (add) 1 else 0
		if (add) {
			dp[i - 1].second
					.filter { (p1, p2) -> p1 == Arrows[i] || p2 == Arrows[i] }
					.toCollection(dp[i].second)
		} else {
			dp[i - 1].second
					.forEach { (p1, p2) ->
						dp[i].second.add(p1 tu Arrows[i])
						dp[i].second.add(Arrows[i] tu p2)
					}
		}
	}
	// time complexity: O(n)

	// we want dp(n)_1
	return dp[n].first
}

// an alternative solution without set operation
fun OneArray<Char>.maxPoints(): Int {
	val Arrows = map { it.toArrow().ordinal + 1 }.toOneArray()
	val n = size

	// dp(i, p, q): max points I can get given A[1..i] standing on p, q in the end
	// memoization structure: 3d array dp[1..n, 1..4, 1..4] : dp[i, p, q] = dp(i, p, q)
	val dp = OneArray(n) { OneArray(Arrow.values().size) { OneArray(Arrow.values().size) { 0 } } }

	// base case:
	// when the first input is either < or >, we don't move and earn a point
	// dp(1, p, q) = 0 o/w
	if (Arrows[1] == 1 || Arrows[1] == 2) {
		dp[1, 1, 2] = 1
	}

	// recursive case:
	// dp(i, p, q) = if either p or q = Arrows[i]
	//                   if p = Arrows[i]
	//                       max{ 1 + dp(i - 1, p, q), dp(i - 1, k, q : k != p) }
	//                   else // q = Arrows[i]
	//                       max{ 1 + dp(i - 1, p, q), dp(i - 1, p, k : k != q) }
	//               else if neither p nor q = Arrows[i]
	//                   -inf
	// dependency: dp(i, p , q) depends on dp(i - 1, m, n) that is entries in the table before
	// evaluation order: outer loop for i from 2 to n (left to right)
	for (i in 2..n) {
		// inner loop for p, q is arbitrary
		for (p in 1..4) {
			for (q in 1..4) {
				if (p != q) {
					dp[i, p, q] = if (p != Arrows[i] && q != Arrows[i]) {
						0
					} else {
						val notMove = 1 + dp[i - 1, p, q]
						if (p == Arrows[i]) {
							max(notMove, (1..4).filter { it != p }.map { dp[i - 1, it, q] }.max()
									?: 0)
						} else { // q == Arrows[i]
							max(notMove, (1..4).filter { it != q }.map { dp[i - 1, p, it] }.max()
									?: 0)
						}
					}
				}
			}
		}
	}
	// time complexity: O(n)

//	dp.prettyPrintTables()

	// we want max_(p, q){ dp(n, p, q) }
	return dp[n].asSequence().map { it.max() }.maxBy { it ?: 0 } ?: 0
}

enum class Arrow {
	L, // 1 < Left
	R, // 2 > Right
	U, // 3 ^ Up
	D; // 4 v Down
}

class ArrowConversionException : Exception("cannot be converted to an Arrow")

fun Char.toArrow() = when (this) {
	'<' -> Arrow.L
	'>' -> Arrow.R
	'^' -> Arrow.U
	'v' -> Arrow.D
	else -> throw ArrowConversionException()
}

