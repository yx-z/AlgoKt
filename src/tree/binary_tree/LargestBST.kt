package tree.binary_tree

fun main(args: Array<String>) {
	// sample tree
	//       3
	//     /   \
	//   2       5
	//    \     / \
	//     1   3   6
	val root = BinaryTreeNode(3)
	root.left = BinaryTreeNode(2)
	root.right = BinaryTreeNode(5)
	root.left!!.right = BinaryTreeNode(1)
	root.right!!.left = BinaryTreeNode(3)
	root.right!!.right = BinaryTreeNode(6)

	// should return 3 (a subtree consisting of 5 -> {3, 6})
	println(root.largestBSTSize())
}

// given a Binary Tree find the size of the largest Binary Search Tree among all its subtrees
fun BinaryTreeNode.largestBSTSize(): Int = largestBST().size

fun BinaryTreeNode?.largestBST(): Ret {
	if (this === null) {
		return Ret(true, 0)
	}

	val lBST = this.left.largestBST()
	val rBST = this.right.largestBST()

	var isValid = lBST.isValid && rBST.isValid

	if ((this.left !== null && this.left!!.data > this.data) ||
			(this.right !== null && this.right!!.data < this.data)) {
		isValid = false
	}

	if (!isValid) {
		return Ret(false, Math.max(lBST.size, rBST.size))
	}

	return Ret(true, lBST.size + rBST.size + 1, lBST.min, rBST.max)

}

class Ret(var isValid: Boolean, var size: Int, var min: Int = 0, var max: Int = 0)

