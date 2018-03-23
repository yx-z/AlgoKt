package tree.bintree

class BinTreeNode<T>(var data: T) {
	var left: BinTreeNode<T>? = null
	var right: BinTreeNode<T>? = null

	override fun toString() = if (left == null && right == null) {
		data.toString()
	} else {
		"$data -> [${left ?: ""}, ${right ?: ""}]"
	}
}

fun main(args: Array<String>) {
	val root = BinTreeNode(2)
	root.left = BinTreeNode(3)
	root.right = BinTreeNode(4)
	root.right!!.right = BinTreeNode(5)
	BinTreePrinter.printNode(root)
}
