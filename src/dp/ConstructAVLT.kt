package dp

import get
import max
import kotlin.math.abs

// similar to 'ConstructBST'
// construct a balanced BST (AVL Tree) that must satisfy |h(left) - h(right)| < 2
fun main(args: Array<String>) {
	val freq = intArrayOf(5)
	println(freq.avltCost())
}

fun IntArray.avltCost(): Int {
	// dp[i][j] = (height in optical avlt, optimal cost for this[i..j])
	// dp[i][j] = (0, 0), if i > j
	//          = (1 + max(dp[i, r - 1].first, dp[r + 1, j].first),
	//            sum(this[i..j] + dp[i, r - 1].second + dp[r + 1, j].second), o/w
	//            , where r satisfies i <= r <= j, |dp[i, r - 1].first - dp[r + 1, j].first| < 2
	//            , and minimizes dp[i, r - 1].second + dp[r + 1, j].second
	val dp = Array(size) { Array(size) { 0 to 0 } }
	for (j in 0 until size) {
		for (i in j downTo 0) {
			var minCost = Int.MAX_VALUE
			var minH = 0
			for (r in i..j) {
				var cost = this[i..j].sum()
				var h = 0
				when {
					r - 1 < 0 && r + 1 >= size -> {
						// do nothing
					}
					r - 1 < 0 -> {
						if (dp[r + 1][j].first < 2) {
							cost += dp[r + 1][j].second
							h = 1 + dp[r + 1][j].first
						}
					}
					r + 1 >= size -> {
						if (dp[i][r - 1].first < 2) {
							cost += dp[i][r - 1].second
							h = 1 + dp[i][r - 1].first
						}
					}
					else -> {
						if (abs(dp[i][r - 1].first - dp[r + 1][j].first) < 2) {
							cost += dp[i][r - 1].second + dp[r + 1][j].second
							h = 1 + max(dp[i][r - 1].first, dp[r + 1][j].first)
						}
					}
				}

				if (cost < minCost) {
					minCost = cost
					minH = h
				}
			}

			dp[i][j] = minH to minCost
		}
	}
	return dp[0][size - 1].second
}
