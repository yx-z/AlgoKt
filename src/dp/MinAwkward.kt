package dp

import tree.bintree.BinTreeNode
import tree.bintree.prettyPrintTree
import util.to

// given a binary tree representing company hierarchy, with each edge containing
// a number, either positive, 0, or negative, also remembering that
// there is an edge between two nodes iff. one is a direct parent of the other

// find a subset of exactly k nodes with the smallest sum of awkwardness

fun main(args: Array<String>) {
	val root = BinTreeNode(2 as Int? to 4 as Int?)
	val l = BinTreeNode(-1 as Int? to -2 as Int?)
	val ll = BinTreeNode(null as Int? to null as Int?)
	val lr = BinTreeNode(null as Int? to null as Int?)
	val r = BinTreeNode(null as Int? to 3 as Int?)
	val rr = BinTreeNode(null as Int? to null as Int?)

	root.left = l
	root.left!!.left = ll
	root.left!!.right = lr
	root.right = r
	root.right!!.right = rr

	root.prettyPrintTree()
}
