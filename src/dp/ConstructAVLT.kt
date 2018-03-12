package dp

import min

// similar to 'ConstructBST'
// construct a balanced BST (AVL Tree) that must satisfy |h(left) - h(right)| < 2
fun main(args: Array<String>) {
	val freq = intArrayOf(5, 8, 2, 1, 9, 5)
	println(freq.avltCost())
}

fun IntArray.avltCost(): Int {
	// dp[i][j] = (height in optical avlt, optimal cost for this[i..j])
	// dp[i][j] = (0, 0), if i > j
	//          = (1 + max(dp[i, r - 1].first, dp[r + 1, j].first),
	//            sum(this[i..j] + dp[i, r - 1].second + dp[r + 1, j].second), o/w
	//            , where r satisfies i <= r <= j, |dp[i, r - 1].first - dp[r + 1, j].first| < 2
	//            , and minimizes dp[i, r - 1].second + dp[r + 1, j].second
	val dp = Array(size + 1) { Array(size + 1) { 0 to 0 } }
	for (j in 0 until size) {
		for (i in j downTo 0) {
			var minR = i
			var minCost = Int.MAX_VALUE
			for (r in i..j) {
				var cost = when {
					r - 1 < 0 -> dp[r + 1][j].second
					r + 1 >= size -> dp[i][r - 1].second
					else -> dp[i][r - 1].second + dp[r + 1][j].second
				}
				if (r - 1 >= 0 && r + 1 < size) {
					if (Math.abs(dp[r + 1][j].first - dp[i][r - 1].first) >= 2) {
						cost = Int.MAX_VALUE
					}
				}
				if (cost < minCost) {
					minCost = cost
					minR = r
				}
			}
			dp[i][j] = when {
				minR - 1 < 0 -> 1 + dp[minR + 1][j].first to minCost
				minR + 1 >= size -> 1 + dp[i][minR - 1].first to minCost
				else -> (1 + min(dp[i][minR - 1].first, dp[minR + 1][j].first)) to minCost
			}
		}
	}
	return dp[0][size - 1].second
}
