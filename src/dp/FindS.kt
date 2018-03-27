package dp

import util.max
import util.min
import util.get
import util.set
import util.toOneArray
import util.OneArray
import util.oneArrayOf

// given X[1..k] and Y[1..n] : k <= n

// 1. determine if X is a subsequence of Y
// ex. PPAP is a subsequence of PenPineappleApplePie
infix fun IntArray.isSubsequenceOf(Y: IntArray): Boolean {
	val X = this
	val k = X.size
	val n = Y.size

	// isSub(i, j): whether X[1..i] is a subsequence of Y[1..j]
	// memoization structure: 2d array dp[0..k, 0..n] : dp[i, j] = isSub(i, j)
	val dp = Array(k + 1) { Array(n + 1) { false } }
	// space complexity: O(k * n)

	// base case:
	// isSub(i, j) = true if i = 0 and j in 0..n
	//             = false if i > j >= 0 || i !in 0..k || j !in 0..n
	for (j in 0..n) {
		dp[0, j] = true
	}

	// recursive case:
	// isSub(i, j) = isSub(i - 1, j - 1) if X[i] = Y[j]
	//             = isSub(i, j - 1) o/w
	// dependency: isSub(i, j) depends on isSub(i, j - 1) and isSub(i - 1, j - 1)
	//             that is, entries to the left and to the upper-left
	// evaluation order: outer loop for i from 1 to k (top down)
	for (i in 1..k) {
		// inner loop for j from 1 to n (left to right)
		for (j in 1..n) {
			dp[i, j] = if (X[i - 1] == Y[j - 1]) {
				dp[i - 1, j - 1]
			} else {
				dp[i, j - 1]
			}
		}
	}
	// time complexity: O(k * n)

	// we want isSub(k, n)
	return dp[k, n]
}

// O(n) non-DP solution
infix fun IntArray.isSubseqOf(Y: IntArray): Boolean {
	val X = this
	val k = X.size
	val n = Y.size

	var i = 0
	var j = 0
	while (i < k && j < n) {
		if (X[i] == Y[j]) {
			i++
		}
		j++
	}

	return i == k
}

// 2. determine the minimum number of elements in Y : after removing those elements
//    , X is no longer a subsequence of Y
//    , you can also find the longest subsequence of Y that is not a supersequence of X
// let me find the length of such subsequence = l
// and we can know the minimum number of elements to be removed as n - l
fun OneArray<Int>.subseqNotSuperseq(X: OneArray<Int>): Int {
	val Y = this
	val k = X.size
	val n = Y.size

	// dp[i, j] = (len of longest subseq of Y[1..j] that only contains X[0..i] in the order but not X[i + 1 until k],
	//             last idx of subseq X[0..i])
	// 2d array dp[0..k, 0..n] taking space O(kn)
	// base case:
	// when i = 0, every element is followed by an empty element
	// so we keep updating the last index of seeing the empty element
	val dp = Array(k + 1) { Array(n + 1) { 0 to it } }

	// we want max_i{ dp[i, n]_1 }
	var max = 0

	for (i in 1..k) {
		for (j in 1..n) {
			dp[i, j] = if (Y[j] == X[i]) {
				max(j - dp[i - 1, j - 1].second - 1, dp[i, j - 1].first) to j
			} else {
				1 + dp[i, j - 1].first to dp[i, j - 1].second
			}

			if (j == n) {
				max = max(max, dp[i, n].first)
			}
		}
	}
	// time complexity: O(kn)
//	dp.prettyPrintln(true)

	return max
}

// 3. determine whether X occurs as two disjoint subsequence of Y
// ex. PPAP appears as two disjoint subsequences is [P]en[P]in[A](P)(P)le(A)[P](P)LEPIE
infix fun OneArray<Int>.occurAs2DisjointSubseq(Y: OneArray<Int>): Boolean {
	val X = this
	val k = size
	val n = Y.size
	// dp(i, j, l): idx of the last elements of 2 disjoint X[1..i] in Y[1..j] and Y[2..l]
	// ex. dp(i, j, l) = (a, b) if Y[a] = Y[b] = X[i] and there are two disjoint subsequences
	//                   Y[1..a] and Y[2..b] that equals X[1..i]
	//                   a <= j, b <= l, 1 <= j < l <= n
	//                 = [] o/w
	val dp = OneArray<OneArray<OneArray<HashSet<Pair<Int, Int>>>>>(k)
	for (i in 1..k) {
		dp[i] = OneArray(n - 1)
		for (j in 1 until n) {
			dp[i][j] = OneArray(n)
			for (l in 1..n) {
				dp[i][j][l] = HashSet(0)
			}
		}
	}

	for (i in 1..k) {
		for (j in 1 until n) {
			for (l in j + 1..n) {
				if (X[i] == Y[j] && Y[j] == Y[l]) {
					if (i == 1 || dp[max(1, -1)][max(1, j - 1)][max(1, l - 1)].isNotEmpty()) {
						dp[i][j][l].add(j to l)
					}
				} else {
					dp[i][j][l].addAll(dp[i][max(1, j - 1)][max(1, l - 1)])
					dp[i][j][l].addAll(dp[i][max(1, j - 1)][l])
					dp[i][j][l].addAll(dp[i][j][max(1, l - 1)])
				}
			}
		}
	}

//	for (i in 1..k) {
//		for (j in 1 until n) {
//			dp[i][j].prettyPrintln()
//		}
//		println()
//	}

	return dp[k][n - 1][n].isNotEmpty()
}

// 4. weighed subseq
// given X[1..k], Y[1..n], and C[1..n] as the cost of Y
fun OneArray<Int>.minWeighedSubseq(Y: OneArray<Int>, C: OneArray<Int>): Int {
	val X = this
	val k = X.size
	val n = Y.size

	// dp(i, j): minimum cost of a subseq in Y[1..j] that ends in Y[j] and equals to X[1..i]
	// memoization structure: dp[1..k, 1..n] : dp[i, j] = dp(i, j)
	val dp = OneArray<OneArray<Int>>(k)
	// space complexity: O(k * n)

	// we want min_j { dp(k, j) }
	var min = Int.MAX_VALUE / 2

	// base case:
	// dp(i, j) = 0 if i !in 1..k or j !in 1..n
	for (i in 1..k) {
		dp[i] = OneArray(n)
		for (j in 1..n) {
			// assume +inf = Int.MAX_VALUE / 2 (so as to avoid overflow)
			dp[i, j] = Int.MAX_VALUE / 2
		}
	}

	// recursive case:
	// assume util.min{ } = +inf
	// dp(i, j) = min_l{ dp(i - 1, l) : l < j } + C[j] if X[i] = Y[j]
	//          = +inf o/w
	// dependency: dp(i, j) depends on entries on the row above and to the left
	// evaluation order: outer loop for i from 1 to k (top down)
	for (i in 1..k) {
		// inner loop for j from 1 to n (left to right)
		for (j in 1..n) {
			if (X[i] == Y[j]) {
				dp[i, j] = if (i == 1) {
					C[j]
				} else {
					(1 until j).map { dp[i - 1, it] + C[j] }.min()
							?: (Int.MAX_VALUE / 2)
				}
			}
			if (i == k) {
				min = min(min, dp[i, j])
			}
		}
	}
	// time complexity: O(k * n)
//	dp.util.prettyPrintTable(true)

	return min
}


fun main(args: Array<String>) {
	val test1X = intArrayOf(3, 5, 1, 2, 6)
	val test1Ys = arrayOf(
			intArrayOf(3, 5, 1, 2, 6), // true
			intArrayOf(3, 5, 2, 1, 1, 2, 6), // true
			intArrayOf(3, 5, 1, 6, 2, 2) // false
	)

//	test1Ys.forEach { println(test1X isSubsequenceOf it) }
//	test1Ys.forEach { println(test1X isSubseqOf it) }

	val test2X = "PPAP".toAlpha().toOneArray()
	val test2Y = ("PEN" + "PINEAPPLE" + "APPLEPIE").toAlpha().toOneArray()

//	println(test2Y.size - test2Y.subseqNotSuperseq(test2X))

//	println(test2X occurAs2DisjointSubseq test2Y)

	val test3X = arrayOf(1, 2, 3).toOneArray()
	val test3Y = arrayOf(1, 2, 2, 1, 2, 3, 4, 2, 3).toOneArray()
//	println(test3X occurAs2DisjointSubseq test3Y)

	val test4X = oneArrayOf(1, 2)
	val test4Y = oneArrayOf(1, 2, 3, 1, 1, 2)
	val test4C = oneArrayOf(-10, 20, 0, 10, 20, 25)

	println(test4X.minWeighedSubseq(test4Y, test4C))
}

fun String.toAlpha() = map { it - 'A' + 1 }.toIntArray()
