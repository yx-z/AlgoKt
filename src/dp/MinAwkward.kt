package dp

import tree.bintree.BinTreeNode
import util.*

// given a binary tree representing company hierarchy, with each edge containing
// a number, either positive, 0, or negative, also remembering that
// there is an edge between two nodes iff. one is a direct parent of the other

// find a subset of exactly k nodes with the smallest sum of awkwardness

fun main(args: Array<String>) {
	val root = BinTreeNode(2 as Int? tu 4 as Int?)
	val l = BinTreeNode(-1 as Int? tu -2 as Int?)
	val ll = BinTreeNode(null as Int? tu null as Int?)
	val lr = BinTreeNode(null as Int? tu null as Int?)
	val r = BinTreeNode(null as Int? tu 3 as Int?)
	val rr = BinTreeNode(null as Int? tu null as Int?)

	root.left = l
	root.left!!.left = ll
	root.left!!.right = lr
	root.right = r
	root.right!!.right = rr

	root.prettyPrintTree()
	println(root.minAwkward(3))
}

fun BinTreeNode<Tuple2<Int?, Int?>>.minAwkward(k: Int): Int {
	// dp[node][k][0] = min awkwardness for a tree rooted @ node
	//                  , inviting k people, and NOT including the root
	// dp[node][k][1] = min awkwardness for a tree rooted @ node
	//                  , inviting k people, and including the root
	val dp = HashMap<BinTreeNode<Tuple2<Int?, Int?>>, OneArray<Array<Int>>>()

	init(dp, k)
	minAwkwardRecur(dp, k)
//	for ((k, v) in dp) {
//		println("$k: $v")
//	}

	return dp[this]!![k].min() ?: 0
}

val INF = Int.MAX_VALUE / 100
fun BinTreeNode<Tuple2<Int?, Int?>>.init(
		dp: HashMap<BinTreeNode<Tuple2<Int?, Int?>>, OneArray<Array<Int>>>,
		k: Int) {
	dp[this] = OneArray(k) { Array(2) { INF } }
	dp[this]!!.getterIndexOutOfBoundsHandler = { Array(2) { 0 } }
	left?.init(dp, k)
	right?.init(dp, k)
}


fun BinTreeNode<Tuple2<Int?, Int?>>.minAwkwardRecur(
		dp: HashMap<BinTreeNode<Tuple2<Int?, Int?>>, OneArray<Array<Int>>>,
		k: Int) {
	if (k <= 1) {
		dp[this]!![1][1] = 0
		return
	}

	// k >= 2

	left?.minAwkwardRecur(dp, k - 1)
	right?.minAwkwardRecur(dp, k - 1)
	minAwkwardRecur(dp, k - 1)

	when {
		left == null && right == null -> {
			dp[this]!![k][0] = INF
			dp[this]!![k][1] = INF
		}
		left != null && right == null -> {
			dp[this]!![k][0] = min(dp[left!!]!![k][0], dp[left!!]!![k][1])
			dp[this]!![k][1] = min(dp[left!!]!![k - 1][0], dp[left!!]!![k - 1][1] + data.first!!)
		}
		left == null && right != null -> {
			dp[this]!![k][0] = min(dp[right!!]!![k][0], dp[right!!]!![k][1])
			dp[this]!![k][1] = min(dp[right!!]!![k - 1][0], dp[right!!]!![k - 1][1] + data.second!!)
		}
		else -> { // left != null && right != null
			dp[this]!![k][0] = (0..k).map { j ->
				min(dp[left!!]!![j][0], dp[left!!]!![j][1]) +
						min(dp[right!!]!![k - j][0], dp[right!!]!![k - j][1])
			}.min() ?: INF
			dp[this]!![k][1] = (0 until k).map { j ->
				min(dp[left!!]!![j][0], dp[left!!]!![j][1] + data.first!!) +
						min(dp[right!!]!![k - 1 - j][0], dp[right!!]!![k - 1 - j][1] + data.second!!)
			}.min() ?: INF
		}
	}
}