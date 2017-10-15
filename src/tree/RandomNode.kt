package tree

import java.util.*

fun main(args: Array<String>) {
	// test tree with 8 nodes
	//       5
	//      /  \
	//     2    10
	//    / \   / \
	//   1   4 7  20
	//      /
	//     3
	val root = BinaryTreeNode(5)
	root.left = BinaryTreeNode(2)
	root.left!!.left = BinaryTreeNode(1)
	root.left!!.right = BinaryTreeNode(4)
	root.left!!.right!!.left = BinaryTreeNode(3)

	root.right = BinaryTreeNode(10)
	root.right!!.right = BinaryTreeNode(20)
	root.right!!.left = BinaryTreeNode(7)

	val times = 10
	for (i in 1..times) {
		println(root.random().data)
	}
}

fun BinaryTreeNode.random(size: Int = 0): BinaryTreeNode {
	// idx from 0 .. (size - 1), still #size nodes
	val randIdx = if (size != 0) {
		(Math.random() * size).toInt()
	} else {
		(Math.random() * getSize()).toInt()
	}

	val queue: Queue<BinaryTreeNode> = LinkedList<BinaryTreeNode>()
	queue.add(this)
	var idx = -1

	// level order traversal
	while (queue.isNotEmpty()) {
		val curr = queue.remove()
		idx++
		if (idx == randIdx) {
			return curr
		} else {
			if (curr.left != null) {
				queue.add(curr.left)
			}

			if (curr.right != null) {
				queue.add(curr.right)
			}
		}
	}

	// should not be here
	return this
}

fun BinaryTreeNode?.getSize(): Int {
	if (this == null) {
		return 0
	}

	return 1 + left.getSize() + right.getSize()
}