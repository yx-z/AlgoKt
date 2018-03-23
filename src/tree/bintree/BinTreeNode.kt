package tree.bintree

import util.PrintableBinTreeNode

class BinTreeNode<T>(var data: T) : PrintableBinTreeNode {
	override val text: String
		get() = data.toString()
	override var left: BinTreeNode<T>? = null
	override var right: BinTreeNode<T>? = null
}

fun <T> BinTreeNode<T>.prettyPrintTree() = util.prettyPrintBinTree(this)
