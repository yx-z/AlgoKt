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
fun OneArray<Int>.maxPoints(): Int {
	val C = this
	val n = C.size

	// dp(i, j): max points I can get if I play first given C[i..j]
	// memoization strucuture: 2d array dp[1..n, 1..n] : dp[i, j] = dp(i, j)
	val dp = OneArray(n) { OneArray(n) { 0 } }
	// space complexity: O(n^2)

	// base case:
	// dp(i, j) = 0 if i > j or i, j !in 1..n
//	dp.getterIndexOutOfBoundHandler = { OneArray(n) { 0 } }
	for (i in 1..n) {
		dp[i].getterIndexOutOfBoundHandler = { 0 }
	}
	// dp(i, i) = C[i]
	for (i in 1..n) {
		dp[i, i] = C[i]
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

// 2. given another perfect player O just as you, find the max points you can get
// assume you play first
fun OneArray<Int>.maxPointsPerfect(): Int {
	val C = this
	val n = C.size

	// dp(i, j): (index I will choose if I play first, given C[i..j], max sum of points for C[i..j])
	// dp(i, j): (index O will choose if I play first, given C[i..j], max sum of points for C[i..j])
	// since we both are perfect players, given the same condition
	// we would always do the same choice
	// so oppo(i, j) = dp(i, j)
	// memoization structure: 2d array dp[1..n, 1..n] : dp[i, j] = dp(i, j)
	val dp = OneArray(n) { OneArray(n) { 0 to 0 } }
	// space complexity: O(n^2)

	// base case:
	// dp(i, j) = (0, 0) if i > j or i, j !in 1..n
	dp.getterIndexOutOfBoundHandler = { OneArray(n) { 0 to 0 } }
	// dp(i, i) = (i, C[i])
	for (i in 1..n) {
		dp[i, i] = i to C[i]
		dp[i].getterIndexOutOfBoundHandler = { 0 to 0 }
	}
	// time complextiy: O(n)

	// recursive case:
	// dp(i, j) = if (pI > pJ) (i, pI) else (j, pJ)
	// where pI = C[i] + if (dp(i + 1, j)_1 == i + 1) dp(i + 2, j)_2 else dp(i + 1, j - 1)_2
	//       pJ = C[j] + if (dp(i, j - 1)_1 == i) dp(i + 1, j - 1)_2 else dp(i, j - 2)_2
	// dependency: dp(i, j) depends on dp(i + 1, j), dp(i + 2, j), dp(i + 1, j - 1),
	//             dp(i, j - 1) and dp(i, j - 2)
	// evaluation order: outer loop for i from n - 1 down to 1 (bottom up)
	for (i in n - 1 downTo 1) {
		// inner loop for j from i + 1 to n (left to right)
		for (j in i + 1..n) {
			val pI = C[i] + if (dp[i + 1, j].first == i + 1) {
				dp[i + 2, j].second
			} else {
				dp[i + 1, j - 1].second
			}

			val pJ = C[j] + if (dp[i, j - 1].first == i) {
				dp[i + 1, j - 1].second
			} else {
				dp[i, j - 2].second
			}

			dp[i, j] = if (pI > pJ) {
				i to pI
			} else {
				j to pJ
			}
		}
	}
	// time complexity: O(n^2)

	dp.prettyPrintTable()

	// we want points(1, n)_2
	return dp[1, n].second
}

// an alternative solution to 2
fun OneArray<Int>.maxPointsPerfect2(): Int {
	val C = this
	val n = C.size

	// self(i, j): max score I can get if I play first, given C[i..j]
	// memoization structure: 2d array self[1..n, 1..n] : self[i, j] = self(i, j)
	val self = OneArray(n) { OneArray(n) { 0 } }
	// oppo(i, j): max score I can get if O play first, given C[i..j]
	// memoization structure: 2d array oppo[1..n, 1..n] : oppo[i, j] = oppo(i, j)
	val oppo = OneArray(n) { OneArray(n) { 0 } }
	// space complexity: O(n^2)

	// base case:
	// self(i, j), oppo(i, j) = 0 if i, j !in 1..n or i > j
	self.getterIndexOutOfBoundHandler = { OneArray(n) { 0 } }
	oppo.getterIndexOutOfBoundHandler = { OneArray(n) { 0 } }
	// self(i, i) = C[i]
	// oppo(i, i) = 0
	for (i in 1..n) {
		self[i, i] = C[i]
		oppo[i, i] = 0
		self[i].getterIndexOutOfBoundHandler = { 0 }
		oppo[i].getterIndexOutOfBoundHandler = { 0 }
	}
	// time complexity: O(n)

	// recursive case:
	// self(i, j) = max{ C[i] + oppo(i + 1, j), C[j] + oppo(i, j - 1) }
	// oppo(i, j) = min{ self(i + 1, j ), self(i, j - 1) }
	// dependency: self(i, j) depends on oppo(i + 1, j) and oppo(i, j - 1)
	//             oppo(i, j) depends on self(i + 1, j) and self(i, j - 1)
	//             that is entries below and to the left
	// evaluation order: outer loop for i from n - 1 down to 1 (bottom up)
	for (i in n - 1 downTo 1) {
		// inner loop for j from i + 1..n (left to right)
		for (j in i + 1..n) {
			self[i, j] = max(C[i] + oppo[i + 1, j], C[j] + oppo[i, j - 1])
			oppo[i, j] = min(self[i + 1, j], C[j] + self[i, j - 1])
		}
	}
	// time complexity: O(n^2)

	// we want self(1, n) assuming I play first
	return self[1, n]
	// we may return oppo(1, n) if we know that O play first
}

fun main(args: Array<String>) {
	val C = oneArrayOf(10, 20, 5, 5)
//	println(C.maxPoints())
//	println(C.maxPointsPerfect())
	println(C.maxPointsPerfect2())
}