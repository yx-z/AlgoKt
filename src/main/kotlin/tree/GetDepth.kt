package tree

fun main(args: Array<String>) {
	//     1
	//    / \
	//   2   4
	//  /     \
	// 3       5
	//          \
	//           6
	val root = TreeNode(1)
	root.children.add(TreeNode(2))
	root.children[0].children.add(TreeNode(3))
	root.children.add(TreeNode(4))
	root.children[1].children.add(TreeNode(5))
	root.children[1].children[0].children.add(TreeNode(6))

	// 3
	println(root.getDepth())
}

fun TreeNode.getDepth(): Int = if (children.isEmpty()) {
	0
} else {
	children.map { it.getDepth() }.max()!! + 1
}
