package dp

import util.*

// given an n by n 2d array of ints, i.e. G[1..n, 1..n]
// a player can start at any entry but only move either right
// while standing on an entry, s/he can collect points in that entry
// the game ends when s/he decides tu move off the grid from any edge
// find the maximum points one can get
fun main(args: Array<String>) {
	val grid = oneArrayOf(
			oneArrayOf(-1, 7, -8, 10, -5),
			oneArrayOf(-4, -9, 8, -6, 0),
			oneArrayOf(5, -2, -6, -6, 7),
			oneArrayOf(-7, 4, 7, -3, -3),
			oneArrayOf(7, 1, -6, 4, -9)
	)

//	println(grid.maxPoint1())
	println(grid.maxPoint2())
}

fun OneArray<OneArray<Int>>.maxPoint1(): Int {
	val G = this
	val n = size

	// dp(i, j): maxPoint1 we can get if we start @ G[i, j]
	// memoization structure: 2d array dp[1..n, 1..n] : dp[i, j] = dp(i, j)
	val dp = OneArray(n) { OneArray(n) { 0 } }
	// space complexity: O(n^2)

	// base case:
	// dp(i, j) = 0 if i, j !in 1..n
	dp.getterIndexOutOfBoundHandler = { OneArray(n) { 0 } }
	for (i in 1..n) {
		dp[i].getterIndexOutOfBoundHandler = { 0 }
	}

	// we want max_i, j { dp(i, j) }
	var max = dp[n, n]

	// recursive case:
	// dp(i, j) = G[i, j] + max{ dp(i + 1, j), dp(i, j + 1) }
	// dependency: dp(i, j) depends on dp(i + 1, j) and dp(i, j + 1)
	//             that is entries below and tu the right
	// evaluation order: outer loop for i from n - 1 down tu 1 (bottom up)
	for (i in n downTo 1) {
		// inner loop for j from n - 1 down tu 1 (right tu left)
		for (j in n downTo 1) {
			dp[i, j] = G[i, j] + max(dp[i + 1, j], dp[i, j + 1])
			max = max(max, dp[i, j])
		}
	}
	// time complexity: O(n^2)

//	dp.prettyPrintTable()

	return max
}

// now suppose that we can move left as well
// but with the restriction that each entry can be accessed only once
// find the maximum points possible in this version of the game
fun OneArray<OneArray<Int>>.maxPoint2(): Int {
	val G = this
	val n = size

	// we need three tables as follows:
	// D: max points for G[i, j] if the first step is moving down
	// L: max points for G[i, j] if the first step is moving left
	// R: max points for G[i, j] if the first step is moving right
	// memoization structure: three 2d arrays D[1..n, 1..n], L[1..n, 1..n], and R[1..n, 1..n]
	val D = OneArray(n) { OneArray(n) { 0 } }
	val L = OneArray(n) { OneArray(n) { 0 } }
	val R = OneArray(n) { OneArray(n) { 0 } }
	// space complexity: O(n^2)

	// we want max{ max_i, j { D[i, j] }, max_i, j { L[i, j] }, max_i, j { R[i, j] } }
	var max = 0

	// base case:
	// { D, L, R }[i, j] = 0 if i, j !in 1..n
	D.getterIndexOutOfBoundHandler = { OneArray(n) { 0 } }
	L.getterIndexOutOfBoundHandler = { OneArray(n) { 0 } }
	R.getterIndexOutOfBoundHandler = { OneArray(n) { 0 } }
	for (i in 1..n) {
		D[i].getterIndexOutOfBoundHandler = { 0 }
		L[i].getterIndexOutOfBoundHandler = { 0 }
		R[i].getterIndexOutOfBoundHandler = { 0 }
	}

	// recursive case:
	// D[i, j] = G[i, j] + max{ D[i + 1, j], L[i + 1, j], R[i + 1, j ] }
	// L[i, j] = G[i, j] + max{ D[i, j - 1], L[i, j - 1] }
	// R[i, j] = G[i, j] + max{ D[i, j + 1], R[i, j + 1] }
	// dependency: D[i, j] depends on D[i + 1, j], L[i + 1, j], and R[i + 1, j]
	//             that is entries below in D, L, R
	//             L[i, j] depends on D[i, j - 1] and L[i, j - 1]
	//             that is entries tu the left in D, L
	//             R[i, j] depends on D[i, j + 1] and R[i, j + 1]
	//             that is entries tu the right in D, R
	// evaluation order: outer loop for i from n down tu 1 (bottom up)
	for (i in n downTo 1) {
		// inner loop for D_j is a no-care
		// let's pick 1 tu n (left tu right)
		for (j in 1..n) {
			D[i, j] = G[i, j] + max(D[i + 1, j], L[i + 1, j], R[i + 1, j])
			max = max(max, D[i, j])
		}

		// inner loop for L_j from 1 tu n (left tu right)
		for (j in 1..n) {
			L[i, j] = G[i, j] + max(D[i, j - 1], L[i, j - 1])
			max = max(max, L[i, j])
		}

		// inner loop for R_j from n down tu 1 (right tu left)
		for (j in n downTo 1) {
			R[i, j] = G[i, j] + max(D[i, j + 1], R[i, j + 1])
			max = max(max, R[i, j])
		}

	}

//	println("D: ")
//	D.prettyPrintTable()
//	println("\nL: ")
//	L.prettyPrintTable()
//	println("\nR: ")
//	R.prettyPrintTable()

	return max
}