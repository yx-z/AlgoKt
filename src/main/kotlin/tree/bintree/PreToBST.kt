package tree.bintree

import util.OneArray
import util.oneArrayOf
import util.prettyPrintTree

// given a pre-order traversal of a binary search tree as an array A[1..n]
// reconstruct its corresponding bst

fun OneArray<Int>.reconstrctBST(): BinTreeNode<Int> {
	val pre = this
	val inorder = pre.sorted()
	return binTreeFrom(pre, inorder)!!
}

fun main(args: Array<String>) {
	val A = oneArrayOf(5, 3, 2, 1, 4, 10, 8, 19)
	println(A.reconstrctBST().prettyPrintTree())
}