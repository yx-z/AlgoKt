package tree

fun main(args: Array<String>) {
	val root = BinaryTreeNode(3)
	root.left = BinaryTreeNode(5)
	root.right = BinaryTreeNode(5)
	println(root.isBST())
}

fun BinaryTreeNode?.isBST(): Boolean {
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