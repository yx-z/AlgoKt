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

// binary tree
fun BinTreeNode<Int>.btLCA(t1: BinTreeNode<Int>, t2: BinTreeNode<Int>): BinTreeNode<Int>? {
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

// binary search tree
fun BinTreeNode<Int>.bstLCARecur(t1: BinTreeNode<Int>, t2: BinTreeNode<Int>): BinTreeNode<Int>? {
	if (this.data > t1.data && this.data > t2.data) {
		return this.left?.bstLCARecur(t1, t2)
	}

	if (this.data < t1.data && this.data < t2.data) {
		return this.right?.bstLCARecur(t1, t2)
	}

	return this
}

fun BinTreeNode<Int>.bstLCAIter(t1: BinTreeNode<Int>, t2: BinTreeNode<Int>): BinTreeNode<Int>? {
	var node: BinTreeNode<Int>? = this
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
