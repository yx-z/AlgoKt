package dp

import util.*
import kotlin.math.ceil
import kotlin.math.log2

// similar to ConstructBST, now we should construct an AVL Tree with its property
// that for every node, the height difference between its left child and right
// child should not differ by more than 1

// here we define a NULL node having height -1,
// and a root node with no children is an AVL Tree with height 0

// your input is still an array of frequencies being accessed (see ConstructBST
// for more details)
fun main(args: Array<String>) {
	val freq = oneArrayOf(5, 8, 2, 1, 9, 5)
	println(freq.avlCost())
}

fun OneArray<Int>.avlCost(): Int {
	val f = this
	val n = size
	val F = OneArray(n) { OneArray(n) { 0 } }
	for (i in 1 until n) {
		for (j in i..n) {
			F[i, j] = if (i == j) f[i] else F[i, j - 1] + f[j]
		}
	}
	// dp(i, k, h): min cost for constructing an AVL Tree of height h and storing
	// values from the subarray i to k
	// memoization structure: 3d array dp[1 until n, 2..n, 1..H] where H is O(log n)
	// representing the max height of such AVL Tree
	val H = ceil(2 * log2(n.toDouble())).toInt()
	val dp = OneArray(n + 1) { OneArray(n + 1) { OneArray(H) { 0 } } }
	// space complexity: O(n^3 log n)
	val handler = { i: Int, k: Int, h: Int ->
		when (h) {
			0 -> if (i == k) f[i] else INF
			-1 -> if (i == k + 1) 0 else INF
			else -> INF
		}
	}

	// we want min_h { dp(h, 1, n) }
	var min = INF

	for (h in 1..H) {
		for (i in 1..n) {
			for (k in i..n) {
				dp[i, k, h] = when {
					h <= -1 && i > k -> 0
					h != -1 && i > k -> INF
					else -> F[i, k] + ((i..k).map { r ->
						min(
								dp[i, r, h - 1, handler] + dp[r + 1, k, h - 2, handler],
								dp[i, r - 1, h - 2, handler] + dp[r + 1, k, h - 1, handler],
								dp[i, r - 1, h - 1, handler] + dp[r + 1, k, h - 1, handler])
					}.min() ?: INF)
				}

				if (i == 1 && k == n) {
					min = min(min, dp[i, k, h])
				}
			}
		}
	}
	// time complexity: O(n^3 log n)

	return min
}
