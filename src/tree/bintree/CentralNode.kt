package tree.bintree

import util.Tuple2
import util.tu
import kotlin.collections.HashMap
import kotlin.collections.Map
import kotlin.collections.get
import kotlin.collections.set

// given a binary tree, a central node n is a node : after deleting the node,
// the tree is split into three parts: left child of n (and its subtree), right
// child of n (and its subtree), and parent of n (and its other child)
// and these three parts all having at most half of the total nodes in this tree
// find one central node

fun <T> BinTreeNode<T>.centralNode(): BinTreeNode<T> {
	val map = countChildren()
	val (l, r) = map[this]!!
	if (l == r) {
		return this
	}

	val n = l + r + 1

	if (l > r) {
		var parent = r + 1
		var curr = left!!
		while (parent > n / 2) {
			parent++
		}
		return curr
	} else { // l < r
		var parent = l + 1
		var curr = right!!
		while (parent > n / 2) {
			parent++
		}
		return curr
	}
}

private fun <T> BinTreeNode<T>.countChildren(map: HashMap<BinTreeNode<T>, Tuple2<Int, Int>> = HashMap())
		: Map<BinTreeNode<T>, Tuple2<Int, Int>> {
	left?.countChildren(map)
	right?.countChildren(map)
	map[this] = (map[left]?.run { first + second + 1 } ?: 0) tu
			(map[right]?.run { first + second + 1 } ?: 0)
	return map
}

fun main(args: Array<String>) {
	val root = BinTreeNode(5)
	root.left = BinTreeNode(3)
	root.right = BinTreeNode(1)
	root.left!!.left = BinTreeNode(4)
	root.left!!.left!!.left = BinTreeNode(10)
	root.left!!.right = BinTreeNode(2)
	root.right!!.right = BinTreeNode(8)
	root.right!!.right!!.left = BinTreeNode(9)
//	root.prettyPrintTree()

	println(root.centralNode())
}