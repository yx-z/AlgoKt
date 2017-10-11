package tree

fun main(args: Array<String>) {
	// 5 -> {2 -> {null, null}, 6 -> {null, 14}}
	val root = TreeNode(5)
	root.left = TreeNode(2)
	root.right = TreeNode(6)
	root.right!!.right = TreeNode(14
	)
	println(root.btLCA(root.left!!, root.right!!.right!!)!!.data)
	println(root.bstLCARecur(root.left!!, root.right!!.right!!)!!.data)
	println(root.bstLCAIter(root.left!!, root.right!!.right!!)!!.data)
}

fun TreeNode.btLCA(t1: TreeNode, t2: TreeNode): TreeNode? {
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

fun TreeNode.bstLCARecur(t1: TreeNode, t2: TreeNode): TreeNode? {
	if (this.data > t1.data && this.data > t2.data) {
		return this.left?.bstLCARecur(t1, t2)
	}

	if (this.data < t1.data && this.data < t2.data) {
		return this.right?.bstLCARecur(t1, t2)
	}

	return this
}

fun TreeNode.bstLCAIter(t1: TreeNode, t2: TreeNode): TreeNode? {
	var node: TreeNode? = this
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