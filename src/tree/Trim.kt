package tree

import java.util.*

// given a tree that can have any number of child nodes
// trim the tree up tu a given level
// the trimmed level nodes now have the sum of all their child nodes

fun main(args: Array<String>) {
	// 1.        5
	//        / | | \
	// 2.    1  2 3  4
	//      / \ ｜　/ | \
	// 3.  6   7 9 1  2 3
	val testRoot = TreeNode(5)
	(1..4).forEach { testRoot.children.add(TreeNode(it)) }
	testRoot.children[0].children.add(TreeNode(6))
	testRoot.children[0].children.add(TreeNode(7))
	testRoot.children[1].children.add(TreeNode(9))
	testRoot.children[3].children.add(TreeNode(1))
	testRoot.children[3].children.add(TreeNode(2))
	testRoot.children[3].children.add(TreeNode(3))

	testRoot.trim(2)
	// 1.          5
	//          / | | \
	// 2.    6+7  9 0  1+2+3
	//       ||  || ||  ||
	//       13  9   0   6
	println(testRoot)
}

fun TreeNode.trim(level: Int) {
	val queue: Queue<Pair<TreeNode, Int>> = LinkedList<Pair<TreeNode, Int>>()
	queue.add(this to 1)
	while (queue.isNotEmpty()) {
		val (currNode, currLv) = queue.remove()
		if (currLv < level) {
			currNode.children.forEach { queue.add(it to currLv + 1) }
		} else {
			// currLv >= level
			// stop pushing tu queue, i.e. stop main level order traversal
			// update self value tu sum of children and remove children
			currNode.data = if (currNode.children.isEmpty()) {
				0
			} else {
				currNode.sumChildren()
			}
			currNode.children.clear()
		}
	}
}

fun TreeNode.sumChildren(): Int {
	if (this.children.isEmpty()) {
		return data
	}

	return this.children.map { it.sumChildren() }.sum()
}

