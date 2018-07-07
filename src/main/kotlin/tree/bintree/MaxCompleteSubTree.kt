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
	if (left == null) { // and right != null
		return right.maxCompleteSubTree()
	}
	if (right == null) { // and left != null
		return left.maxCompleteSubTree()
	}

	// left, right != null
	val (leftMaxCompleteSubTree, leftDepth) = left.maxCompleteSubTree()
	val (rightMaxCompleteSubTree, rightDepth) = right.maxCompleteSubTree()

	// first find the larger subtree between these two
	val childrenMaxDepth = if (leftDepth > rightDepth) leftDepth else rightDepth
	val childrenMaxCompleteSubTree = if (leftDepth > rightDepth) leftMaxCompleteSubTree else rightMaxCompleteSubTree

	// then consider the maxCompleteSubTree rooted at this (current root)
	val currMaxDepth = maxCompleteSubTreeRootedAtCurrent()
	return if (currMaxDepth >= childrenMaxDepth) {
		this tu currMaxDepth
	} else {
		childrenMaxCompleteSubTree tu childrenMaxDepth
	}
}

fun <T> BinTreeNode<T>.maxCompleteSubTreeRootedAtCurrent(): Int {
	if (left == null && right == null) {
		return 0
	}
	if (left == null) { // and right != null
		return right.maxCompleteSubTreeRootedAtCurrent()
	}
	if (right == null) { // and left != null
		return left.maxCompleteSubTreeRootedAtCurrent()
	}

	// left, right != null
	return min(left.maxCompleteSubTreeRootedAtCurrent(), right.maxCompleteSubTreeRootedAtCurrent()) + 1
}

fun main(args: Array<String>) {
	// duh ... a huge tree
	val root = BinTreeNode(1)
	root.left = BinTreeNode(2)
	root.right = BinTreeNode(3)
	root.left!!.left = BinTreeNode(4)
	root.left!!.right = BinTreeNode(5)
	root.right!!.left = BinTreeNode(6)
	root.right!!.right = BinTreeNode(7)
	root.left!!.left!!.left = BinTreeNode(8)
	root.left!!.left!!.right = BinTreeNode(9)
	root.left!!.right!!.left = BinTreeNode(10)
	root.left!!.right!!.right = BinTreeNode(11)
	root.right!!.left!!.left = BinTreeNode(12)
	root.right!!.left!!.right = BinTreeNode(13)
	root.right!!.right!!.left = BinTreeNode(14)
	root.right!!.right!!.right = BinTreeNode(15)
	root.left!!.left!!.right!!.left = BinTreeNode(16)
	root.left!!.left!!.right!!.right = BinTreeNode(17)
	root.left!!.left!!.right!!.left!!.left = BinTreeNode(18)
	root.left!!.left!!.right!!.left!!.right = BinTreeNode(19)
	root.left!!.left!!.right!!.right!!.left = BinTreeNode(20)
	root.left!!.left!!.right!!.right!!.right = BinTreeNode(21)
	root.prettyPrintTree()

	val (maxCompleteTree, depth) = root.maxCompleteSubTree()
	maxCompleteTree.prettyPrintTree()
	println(depth)
}