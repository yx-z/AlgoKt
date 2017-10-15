package tree.bintree

// Largest Common Ancestor
fun main(args: Array<String>) {
	// 5 -> {2 -> {null, null}, 6 -> {null, 14}}
	val root = BinTreeNode(5)
	root.left = BinTreeNode(2)
	root.right = BinTreeNode(6)
	root.right!!.right = BinTreeNode(14)
	println(root.btLCA(root.left!!, root.right!!.right!!)!!.data)
	println(root.bstLCARecur(root.left!!, root.right!!.right!!)!!.data)
	println(root.bstLCAIter(root.left!!, root.right!!.right!!)!!.data)
}

fun BinTreeNode.btLCA(t1: BinTreeNode, t2: BinTreeNode): BinTreeNode? {
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

fun BinTreeNode.bstLCARecur(t1: BinTreeNode, t2: BinTreeNode): BinTreeNode? {
	if (this.data > t1.data && this.data > t2.data) {
		return this.left?.bstLCARecur(t1, t2)
	}

	if (this.data < t1.data && this.data < t2.data) {
		return this.right?.bstLCARecur(t1, t2)
	}

	return this
}

fun BinTreeNode.bstLCAIter(t1: BinTreeNode, t2: BinTreeNode): BinTreeNode? {
	var node: BinTreeNode? = this
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
