package tree.bintree

fun main(args: Array<String>) {
	val root = BinTreeNode(3)
	root.left = BinTreeNode(5)
	root.right = BinTreeNode(5)
	println(root.isBST())
}

fun BinTreeNode?.isBST(): Boolean {
	if (this == null || (this.left == null && this.right == null)) {
		return true
	}

	if (this.left != null  && this.data < this.left!!.data) {
		return false
	}

	if (this.right != null && this.data > this.right!!.data) {
		return false
	}

	return this.left.isBST() && this.right.isBST()
}