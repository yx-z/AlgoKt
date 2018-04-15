package tree.bintree

import util.PrintableBinTreeNode

open class BinTreeNode<T>(var data: T) : PrintableBinTreeNode {
	override val text: String
		get() = data.toString()

	override var left: BinTreeNode<T>? = null

	override var right: BinTreeNode<T>? = null

	override fun toString() = text
}
