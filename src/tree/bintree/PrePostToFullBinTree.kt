package tree.bintree

import util.OneArray
import util.get
import util.oneArrayOf
import util.prettyPrintTree

// reconstruct a full binary tree given its pre-order and post-order traversal
// as arrays pre[1..n] and post[1..n] (assuming inputs are valid)

// i have a question, given such structured binary tree (i.e., being full)
// why do we need its post-order traversal array?
// i can reconstruct the tree only with its pre order...as below
fun reconstructFullBinTree(pre: OneArray<Int>, post: OneArray<Int>): BinTreeNode<Int> {
	val n = pre.size // == post.size by assumption

	val root = BinTreeNode(pre[1])
	if (n == 1) {
		return root
	}

	val leftEnd = 1 + (n - 1) / 2
	val rightStart = leftEnd + 1

	val leftNode = reconstructFullBinTree(pre[2..leftEnd], post)
	val rightNode = reconstructFullBinTree(pre[rightStart..n], post)

	root.left = leftNode
	root.right = rightNode

	return root
}

fun main(args: Array<String>) {
	val pre = oneArrayOf(1, 3, 7, 5, 4, 2, 8)
	val post = oneArrayOf(7, 5, 3, 2, 8, 4, 1)
	reconstructFullBinTree(pre, post).prettyPrintTree()
}