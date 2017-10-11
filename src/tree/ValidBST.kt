package tree

fun main(args: Array<String>) {
	val root = TreeNode(3)
	root.left = TreeNode(5)
	root.right = TreeNode(5)
	println(root.isBST())
}

fun TreeNode?.isBST(): Boolean {
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