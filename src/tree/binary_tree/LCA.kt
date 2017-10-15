package tree.binary_tree

// Largest Common Ancestor
fun main(args: Array<String>) {
	// 5 -> {2 -> {null, null}, 6 -> {null, 14}}
	val root = BinaryTreeNode(5)
	root.left = BinaryTreeNode(2)
	root.right = BinaryTreeNode(6)
	root.right!!.right = BinaryTreeNode(14)
	println(root.btLCA(root.left!!, root.right!!.right!!)!!.data)
}

fun BinaryTreeNode.btLCA(t1: BinaryTreeNode, t2: BinaryTreeNode): BinaryTreeNode? {
	if (this == t1 || this == t2) {
		return this
	}

	val left = this.left?.btLCA(t1, t2)
	val right = this.right?.btLCA(t1, t2)

	if (left != null && right != null) {
		return this
	}

	return left ?: right
}
