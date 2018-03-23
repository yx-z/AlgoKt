package tree.bintree

class BinTreeNode<T>(var data: T) {
	var left: BinTreeNode<T>? = null
	var right: BinTreeNode<T>? = null

	override fun toString() = parse(this).toString()
}

