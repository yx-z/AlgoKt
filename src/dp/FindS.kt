package dp

import max
import get
import prettyPrintln
import set
import toOneArray
import OneArray

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
	dp.prettyPrintln(true)

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
	//                 = null o/w
	val dp = Array(k + 1) { Array(n) { arrayOfNulls<Set<Pair<Int, Int>>>(n + 1) } }

	// base case:
	// dp(0, j, l) = (j - 1, l - 1)
	for (j in 1 until n) {
		for (l in j + 1..n) {
			dp[0, j, l] = hashSetOf(j - 1 to l - 1)
		}
	}

	// recursive case:
	// dp(i, j, l) = if X[i] = Y[j] = Y[l]
	//                   (j', l') = dp(i - 1, j, l)
	//                   if j' < l'
	//                       (j, l)
	//             = if X[i] = Y[j] != Y[l]
	//                   dp(i, j, l - 1)
	//             = if X[i] = Y[l] != Y[j]
	//                   dp(i, j - 1, l)
	//             = dp(i, j - 1, l - 1)
	for (i in 1..k) {
		for (j in 1 until n) {
			for (l in j + 1..n) {
				dp[i, j, l] = when {
					X[i] == Y[j] && Y[j] == Y[l] -> {
						if (dp[i - 1, j, l] == null) {
							null
						} else {
							val set = HashSet<Pair<Int, Int>>()
							dp[i, j - 1, l - 1]?.forEach { set.add(it) }
							if (dp[i - 1, j, l] != null && dp[i - 1, j, l]!!.filter { (preJ, preL) -> preJ < j && preL < l }.isNotEmpty()) {
								set.add(i to j)
							}
							set
						}
					}
					X[i] == Y[j] && Y[j] != Y[l] -> {
						val set = HashSet<Pair<Int, Int>>()
						dp[i, j, l - 1]?.forEach { set.add(it) }
						set
					}
					X[i] == Y[l] && Y[l] != Y[j] -> {
						val set = HashSet<Pair<Int, Int>>()
						dp[i, j - 1, l]?.forEach { set.add(it) }
						set
					}
					else -> {
						val set = HashSet<Pair<Int, Int>>()
						dp[i, j - 1, l - 1]?.forEach { set.add(it) }
						set
					}
				}
			}
		}
	}

	for (i in 0 until dp.size) {
		dp[i].prettyPrintln()
		println()
	}

	// we want dp(k, n - 1, n) != null
	return dp[k, n - 1, n] != null && dp[k, n - 1, n]!!.isNotEmpty()
}

fun main(args: Array<String>) {
	val X = intArrayOf(3, 5, 1, 2, 6)
	val Ys = arrayOf(
			intArrayOf(3, 5, 1, 2, 6), // true
			intArrayOf(3, 5, 2, 1, 1, 2, 6), // true
			intArrayOf(3, 5, 1, 6, 2, 2) // false
	)

//	Ys.forEach { println(X isSubsequenceOf it) }
//	Ys.forEach { println(X isSubseqOf it) }

	val strX = "PPAP"
	val strY = "PEN" + "PINEAPPLE" + "APPLEPIE"
	val intArrX = strX.toAlpha()
	val intArrY = strY.toAlpha()
	val oneArrX = intArrX.toOneArray()
	val oneArrY = intArrY.toOneArray()

//	println(oneArrY.size - oneArrY.subseqNotSuperseq(oneArrX))

//	println(oneArrX occurAs2DisjointSubseq oneArrY)

	val test3X = arrayOf(1, 2, 3).toOneArray()
	val test3Y = arrayOf(1, 2, 2, 1, 2, 3, 4, 2, 2).toOneArray()
	println(test3X occurAs2DisjointSubseq test3Y)
}

fun String.toAlpha() = map { it - 'A' + 1 }.toIntArray()
