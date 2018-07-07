package tree.bintree

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
	val root = BinTreeNode(5)
	root.left = BinTreeNode(2)
	root.left!!.left = BinTreeNode(1)
	root.left!!.right = BinTreeNode(4)
	root.left!!.right!!.left = BinTreeNode(3)

	root.right = BinTreeNode(10)
	root.right!!.right = BinTreeNode(20)
	root.right!!.left = BinTreeNode(7)

	val times = 10
	for (i in 1..times) {
		println(root.random().data)
	}
}

fun <T> BinTreeNode<T>.random(size: Int = 0): BinTreeNode<T> {
	// idx from 0 .. (size - 1), still #size nodes
	val randIdx = if (size != 0) {
		(Math.random() * size).toInt()
	} else {
		(Math.random() * getSize()).toInt()
	}

	val queue: Queue<BinTreeNode<T>> = LinkedList<BinTreeNode<T>>()
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

fun <T> BinTreeNode<T>?.getSize(): Int {
	if (this == null) {
		return 0
	}

	return 1 + left.getSize() + right.getSize()
}