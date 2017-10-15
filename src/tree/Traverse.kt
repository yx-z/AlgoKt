package tree

import java.util.*


// Traversal of a Binary Tree
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
fun BinaryTreeNode?.inOrderRecursive() {
	if (this === null) {
		return
	}

	this.left.inOrderRecursive()
	print(this.data)
	this.right.inOrderRecursive()
}

// In-Order Iterative Traversal
fun inOrderIterative(root: BinaryTreeNode) {
	var node: BinaryTreeNode? = root

	val stack = Stack<BinaryTreeNode>()
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
fun BinaryTreeNode.postOrderIterative2Stacks() {
	var node = this

	val s1 = Stack<BinaryTreeNode>()
	s1.push(node)

	val s2 = Stack<BinaryTreeNode>()

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
fun BinaryTreeNode.postOrderIterative1Stack() {
	var node: BinaryTreeNode? = this
	val stack = Stack<BinaryTreeNode>()

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
fun BinaryTreeNode.levelOrder() {
	val queue: Queue<BinaryTreeNode> = LinkedList<BinaryTreeNode>()
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