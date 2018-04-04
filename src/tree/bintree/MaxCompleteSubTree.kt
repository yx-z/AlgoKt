package tree.bintree

import util.Tuple2
import util.min
import util.prettyPrintTree
import util.tu

// assume now a subtree of a binary tree is any connected subgraph (that is,
// the subtree may terminate early even if there are still children)
// now define a complete binary tree as every internal (as well as root) node
// has two children, and every leaf has exactly the same depth
// find the root as well as the depth of the largest subtree of a given binary tree
fun <T> BinTreeNode<T>.maxCompleteSubTree(): Tuple2<BinTreeNode<T>, Int> {
	if (left == null && right == null) {
		return this tu 0
	}
	if (left == null) {
		val (rightMaxCompleteSubTree, rightDepth) = right!!.maxCompleteSubTree()
		return if (rightDepth > 0) {
			rightMaxCompleteSubTree tu rightDepth
		} else {
			this tu 0
		}
	}
	if (right == null) {
		val (leftMaxCompleteSubTree, leftDepth) = left!!.maxCompleteSubTree()
		return if (leftDepth > 0) {
			leftMaxCompleteSubTree tu leftDepth
		} else {
			this tu 0
		}
	}

	// left != null && right != null
	val (leftMaxCompleteSubTree, leftDepth) = left!!.maxCompleteSubTree()
	val (rightMaxCompleteSubTree, rightDepth) = right!!.maxCompleteSubTree()

	if (leftMaxCompleteSubTree === left && rightMaxCompleteSubTree === right) {
		return this tu min(leftDepth, rightDepth) + 1
	}

	return if (leftDepth > rightDepth) {
		leftMaxCompleteSubTree tu leftDepth
	} else {
		rightMaxCompleteSubTree tu rightDepth
	}
}

fun main(args: Array<String>) {
	// duh ... a huge tree
	val root = BinTreeNode(1)
	root.left = BinTreeNode(2)
	root.right = BinTreeNode(3)
	root.left!!.left = BinTreeNode(4)
	root.left!!.right = BinTreeNode(5)
	root.right!!.left = BinTreeNode(6)
	root.left!!.left!!.left = BinTreeNode(7)
	root.left!!.left!!.right = BinTreeNode(8)
	root.left!!.right!!.left = BinTreeNode(9)
	root.left!!.right!!.right = BinTreeNode(10)
	root.right!!.left!!.left = BinTreeNode(11)
	root.right!!.left!!.right = BinTreeNode(12)
	root.left!!.left!!.right!!.left = BinTreeNode(13)
	root.left!!.left!!.right!!.right = BinTreeNode(14)
	root.left!!.right!!.left!!.left = BinTreeNode(15)
	root.left!!.right!!.left!!.right = BinTreeNode(16)
	root.right!!.left!!.right!!.left = BinTreeNode(17)
	root.right!!.left!!.right!!.right = BinTreeNode(18)
	root.left!!.left!!.right!!.left!!.right = BinTreeNode(19)
	root.prettyPrintTree()

	val (maxCompleteTree, depth) = root.maxCompleteSubTree()
	maxCompleteTree.prettyPrintTree()
	println(depth)
}