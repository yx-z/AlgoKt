package tree.bintree

import java.util.*


// Traversal of a Binary Tree
fun main(args: Array<String>) {
	// sample tree
	//       3
	//     /   \
	//   2       5
	//    \     / \
	//     1   3   6
	val root = BinTreeNode(3)
	root.left = BinTreeNode(2)
	root.right = BinTreeNode(5)
	root.left!!.right = BinTreeNode(1)
	root.right!!.left = BinTreeNode(3)
	root.right!!.right = BinTreeNode(6)

	root.inOrderRecursive()
	println()
	println("======")

	inOrderIterative(root)
	println("======")

	root.postOrderIterative2Stacks()
	println("======")

	root.postOrderIterative1Stack()
	println("======")

	root.levelOrder()
	println("======")
}

// In-Order Recursive Traversal
fun BinTreeNode?.inOrderRecursive() {
	if (this === null) {
		return
	}

	this.left.inOrderRecursive()
	print(this.data)
	this.right.inOrderRecursive()
}

// In-Order Iterative Traversal
fun inOrderIterative(root: BinTreeNode) {
	var node: BinTreeNode? = root

	val stack = Stack<BinTreeNode>()
	stack.push(node)

	while (node!!.left !== null) {
		node = node!!.left
		stack.push(node)
	}

	while (stack.isNotEmpty()) {
		node = stack.pop()
		print(node.data)

		if (node.right !== null) {
			node = node.right
			while (node !== null) {
				stack.push(node)
				node = node.left
			}
		}
	}

	println()
}

// Post-Order Iterative Traversal w/ 2 Stacks
fun BinTreeNode.postOrderIterative2Stacks() {
	var node = this

	val s1 = Stack<BinTreeNode>()
	s1.push(node)

	val s2 = Stack<BinTreeNode>()

	while (s1.isNotEmpty()) {
		node = s1.pop()
		s2.push(node)

		if (node.left !== null) {
			s1.push(node.left)
		}
		if (node.right !== null) {
			s1.push(node.right)
		}
	}

	while (s2.isNotEmpty()) {
		print(s2.pop().data)
	}

	println()
}

// Post-Order Iterative w/ 1 Stack
fun BinTreeNode.postOrderIterative1Stack() {
	var node: BinTreeNode? = this
	val stack = Stack<BinTreeNode>()

	while (node !== null || stack.isNotEmpty()) {
		if (node !== null) {
			stack.push(node)
			node = node.left
		} else {
			var tmp = stack.peek().right
			if (tmp === null) {
				tmp = stack.pop()
				print(tmp!!.data)

				while (stack.isNotEmpty() && tmp === stack.peek().right) {
					tmp = stack.pop()
					print(tmp!!.data)
				}
			} else {
				node = tmp
			}
		}
	}

	println()
}

// Level-Order Traversal (Always Iterative)
fun BinTreeNode.levelOrder() {
	val queue: Queue<BinTreeNode> = LinkedList<BinTreeNode>()
	queue.add(this)

	while (queue.isNotEmpty()) {
		val curr = queue.remove()

		print(curr.data)

		if (curr.left !== null) {
			queue.add(curr.left)
		}
		if (curr.right !== null) {
			queue.add(curr.right)
		}
	}
	println()
}