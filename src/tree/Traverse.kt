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
	val root = TreeNode(3)
	root.left = TreeNode(2)
	root.right = TreeNode(5)
	root.left!!.right = TreeNode(1)
	root.right!!.left = TreeNode(3)
	root.right!!.right = TreeNode(6)

	inOrderRecursive(root)
	println()
	println("======")

	inOrderIterative(root)
	println("======")

	postOrderIterative2Stacks(root)
	println("======")

	postOrderIterative1Stack(root)
	println("======")

	levelOrder(root)
	println("======")
}

// In-Order Recursive Traversal
fun inOrderRecursive(root: TreeNode?) {
	if (root === null) {
		return
	}

	inOrderRecursive(root.left)
	print(root.data)
	inOrderRecursive(root.right)
}

// In-Order Iterative Traversal
fun inOrderIterative(root: TreeNode) {
	var node: TreeNode? = root

	val stack = Stack<TreeNode>()
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
fun postOrderIterative2Stacks(root: TreeNode) {
	var node = root

	val s1 = Stack<TreeNode>()
	s1.push(node)

	val s2 = Stack<TreeNode>()

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
fun postOrderIterative1Stack(root: TreeNode) {
	var node: TreeNode? = root
	val stack = Stack<TreeNode>()

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
fun levelOrder(root: TreeNode) {
	val queue: Queue<TreeNode> = LinkedList<TreeNode>()
	queue.add(root)

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