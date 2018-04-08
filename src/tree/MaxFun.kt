package tree

import util.max

// given a tree as a company hierarchy
// each node contains a 'fun' value as an int
// you want to invite people in the company to your party with max 'fun' value
// however if one node is invited, its children cannot be invited
// and the root must be invited

fun main(args: Array<String>) {
	val manager1 = TreeNode(3)
	manager1.children.addAll(listOf(TreeNode(2), TreeNode(2)))

	val manager2 = TreeNode(4)
	manager2.children.addAll(listOf(TreeNode(6), TreeNode(1)))

	val manager3 = TreeNode(3)
	manager3.children.add(TreeNode(2))

	val manager4 = TreeNode(3)
	manager4.children.add(TreeNode(2))

	val manager5 = TreeNode(1)
	manager5.children.add(TreeNode(3))

	val vp1 = TreeNode(5)
	vp1.children.add(manager1)

	val vp2 = TreeNode(2)
	vp2.children.add(manager2)

	val vp3 = TreeNode(2)
	vp3.children.addAll(listOf(TreeNode(2), manager3))

	val vp4 = TreeNode(1)
	vp4.children.addAll(listOf(manager4, manager5))

	val president = TreeNode(-1)
	president.children.addAll(listOf(vp1, vp2, vp3, vp4))

//	println(president)
	//         - 1
	//       / | | \
	//      5  2 2  1
	//    /   / / | | \
	//   3   4 2  3 3  1
	//  /|  / \   | |   \
	// 2 2 6   1  2 2    3

	println(president.maxFunPresident())
	println(president.maxFun())
}

// root must be included
fun TreeNode.maxFunPresident() = data + children.map { it.children.map { it.maxFun() }.sum() }.sum()

// root may or may not be included
fun TreeNode.maxFun(): Int {
	if (children.isEmpty()) {
		return max(0, data)
	}

	val childrenFun = children.map { it.maxFun() }.sum()
	val thisAndGrandchildrenFun = data + children.map { it.children.map { it.maxFun() }.sum() }.sum()

	return max(childrenFun, thisAndGrandchildrenFun)
}
