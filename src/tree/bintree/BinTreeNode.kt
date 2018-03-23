package tree.bintree

class BinTreeNode<T>(var data: T) : PrintableNode {
	override val text: String
		get() = data.toString()
	override var left: BinTreeNode<T>? = null
	override var right: BinTreeNode<T>? = null
}

fun <T> BinTreeNode<T>.prettyPrintTree() = prettyPrintTree(this)
