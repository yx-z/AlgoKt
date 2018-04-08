package tree

import util.max

// find the max size of the independent set of a tree, that is a set of nodes
// that has no edges between them
fun TreeNode.mis(sizeMap: HashMap<TreeNode, Int> = HashMap()): Int {
	val withOutRoot = children.map { it.mis(sizeMap) }.sum()
	val withRoot = 1 + children.map { it.children }.flatten().map { it.mis(sizeMap) }.sum()
	sizeMap[this] = max(withOutRoot, withRoot)
	return sizeMap[this]!!
}

fun main(args: Array<String>) {
	val root = TreeNode(0)
	root.children.add(TreeNode(0))
	root.children.add(TreeNode(0))
	root.children.add(TreeNode(0))
	root.children[0].children.add(TreeNode(0))
	root.children[0].children[0].children.add(TreeNode(0))
	root.children[0].children[0].children.add(TreeNode(0))
	root.children[1].children.add(TreeNode(0))
	root.children[1].children.add(TreeNode(0))
	root.children[1].children[0].children.add(TreeNode(0))
	root.children[2].children.add(TreeNode(0))
	root.children[2].children.add(TreeNode(0))
	root.children[2].children.add(TreeNode(0))
	root.children[2].children[0].children.add(TreeNode(0))

	println(root.mis())
}