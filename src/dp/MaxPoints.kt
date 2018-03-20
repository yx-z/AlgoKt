package dp

import util.*

// given an array of ints C[1..n], you want to play a game with your brother.
// the rule is in each turn, a player can either take the leftmost or rightmost
// int, earn its point and remove it from the array
// after all ints are taken, the player with more points wins


// 1. suppose there is a player who, in every turn, always takes the larger between
//    the leftmost and rightmost ints
//    call this strategy as greedy

// 1.1 give a sample array that no matter who starts first, you may beat such player
//     only if you don't use the greedy strategy
// ans [4, 5, 2, 2] if I play first
//     if I use greedy: I take 4
//                                 s/he takes 5
//                      I take 2
//                                 s/he takes 2
//                      I get  6 < s/he gets  7
//     if I don't use greedy: I take 2
//                                       s/he takes 4
//                            I take 5
//                                       s/he takes 2
//                            I get  7 > s/he gets 6
//     [0.5, 3, 5, 1, 1] if s/he plays first
//     If I use greedy: s/he takes 1
//                                       I take 1
//                      s/he takes 5
//                                       I take 3
//                      s/he takes 0.5
//                      s/he gets  6.5 > I get  4
//     If I don't use greedy: s/he takes 1
//                                            I take 0.5
//                            s/he takes 3
//                                            I take 5
//                            s/he takes 1
//                            s/he gets  5 <  I get  5.5

// 1.2 find the maximum point you can get given such player
// assume you play first
// since o/w you will be given an array after the other player takes one int
fun OneArray<Int>.maxPoint(): Int {
	val C = this
	val n = C.size

	// dp(i, j): max points possible given C[i..j]
	// memoization strucuture: 2d array dp[1..n, 1..n] : dp[i, j] = dp(i, j)
	val dp = OneArray(n) { OneArray(n) { 0 } }
	// space complexity: O(n^2)

	// base case:
	// dp(i, j) = 0 if i > j or i, j !in 1..n
//	dp.getOverflowHandler = { OneArray(n) { 0 } }
	for (i in 1..n) {
		dp[i].getOverflowHandler = { 0 }
	}
	// dp(i, i) = 1
	for (i in 1..n) {
		dp[i, i] = 1
	}

	// recursive case:
	// dp(i, j) = max{ C[i] + if (C[i + 1] > C[j]) dp(i + 2, j) else dp(i + 1, j - 1),
	//                 C[j] + if (C[i] > C[j - 1]) dp(i + 1, j - 1) else dp(i, j - 2) }
	// dependency: dp(i, j) depends on dp(i + 1, j - 1), dp(i + 2, j) and dp(i, j - 2)
	//             that is entries below, to the left, and to the lower-left
	// evaluation order: outer loop for i from n down to 1 (bottom up)
	for (i in n - 1 downTo 1) {
		// inner loop for j from 1 to n (left to right)
		for (j in i + 1..n) {
			dp[i, j] = max(
					C[i] + if (C[i + 1] > C[j]) dp[i + 2, j] else dp[i + 1, j - 1],
					C[j] + if (C[i] > C[j - 1]) dp[i + 1, j - 1] else dp[i, j - 2]
			)
		}
	}
	// time complexity: O(n^2)

//	dp.prettyPrintTable()

	// we want dp(1, n)
	return dp[1, n]
}

fun main(args: Array<String>) {
	val C = oneArrayOf(1, 6, 10, 2)
	println(C.maxPoint())
}