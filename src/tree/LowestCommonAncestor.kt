package tree

fun main(args: Array<String>) {
	// 5 -> {2 -> {null, null}, 6 -> {null, 14}}
	val root = BinaryTreeNode(5)
	root.left = BinaryTreeNode(2)
	root.right = BinaryTreeNode(6)
	root.right!!.right = BinaryTreeNode(14
	)
	println(root.btLCA(root.left!!, root.right!!.right!!)!!.data)
	println(root.bstLCARecur(root.left!!, root.right!!.right!!)!!.data)
	println(root.bstLCAIter(root.left!!, root.right!!.right!!)!!.data)
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

fun BinaryTreeNode.bstLCARecur(t1: BinaryTreeNode, t2: BinaryTreeNode): BinaryTreeNode? {
	if (this.data > t1.data && this.data > t2.data) {
		return this.left?.bstLCARecur(t1, t2)
	}

	if (this.data < t1.data && this.data < t2.data) {
		return this.right?.bstLCARecur(t1, t2)
	}

	return this
}

fun BinaryTreeNode.bstLCAIter(t1: BinaryTreeNode, t2: BinaryTreeNode): BinaryTreeNode? {
	var node: BinaryTreeNode? = this
	while (node != null) {
		node = if (node.data < t1.data && node.data < t2.data) {
			node.left
		} else if (node.data > t1.data && node.data > t2.data) {
			node.right
		} else {
			break
		}
	}
	return node
}