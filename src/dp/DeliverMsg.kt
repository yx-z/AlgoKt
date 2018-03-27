package dp

import tree.bintree.BinTreeNode
import util.*

// given a binary tree, only the root knows a message at first,
// in each round, a node can deliver the message to one of its children
// find min rounds required for this message to be delivered to every node
fun BinTreeNode<Int>.minRounds(): Int {
	// the basic idea is: in each round, should i deliver message to right or left?
	// the optimal way is that i should deliver it to a larger subtree so that
	// they will have more time to finish

	// no children, no message to be delivered
	if (left == null && right == null) {
		return 0
	}

	// compute minRounds for the current node depends on both of its children
	// so we will do a "post-order traversal"
	left?.minRounds()
	right?.minRounds()

	// add 1 to that single child
	if (left == null) {
		data = right!!.data + 1
		return data
	}

	if (right == null) {
		data = left!!.data + 1
		return data
	}

	// this is tricky, when they are equal, we have to do two
	if (left!!.data == right!!.data) {
		data = left!!.data + 2
		return data
	}

	data = max(left!!.data, right!!.data) + 1
	return data
}

// but the above strategy is greedy! a more dp-ish idea is that:
// compute # of rounds if i deliver msg to left first,
// compute # of rounds if i deliver msg to right first,
// compare and take the min between these 2
fun BinTreeNode<Tuple2<Int, Int>>.minRound(): Int {
	if (left == null && right == null) {
		return 0
	}

	// we are still doing post-order traversal
	left?.minRound()
	right?.minRound()

	if (left == null) {
		data.first = 2 + min(right!!.data.first, right!!.data.second)
		data.second = 1 + min(right!!.data.first, right!!.data.second)
		return min(data.first, data.second)
	}

	if (right == null) {
		data.second = 2 + min(left!!.data.first, left!!.data.second)
		data.first = 1 + min(left!!.data.first, left!!.data.second)
		return min(data.first, data.second)
	}

	data.first = max(
			1 + min(left!!.data.first, left!!.data.second),
			2 + min(right!!.data.first, right!!.data.second))
	data.second = max(
			1 + min(right!!.data.first, right!!.data.second),
			2 + min(left!!.data.first, left!!.data.second))

	return min(data.first, data.second)
}

fun main(args: Array<String>) {
	// we want to compute a tree with the same structure as the given one,
	// and set every node value to the minRounds required for that subtree

	// since we will use the tree itself as a given data structure,
	// and we only need its strucutre, not its data, for simplicity,
	// let's just assume we are already given a tree with base case 0s
	val root = BinTreeNode(0)
	root.left = BinTreeNode(0)
	root.left!!.left = BinTreeNode(0)
	root.right = BinTreeNode(0)
	root.right!!.right = BinTreeNode(0)
	root.right!!.left = BinTreeNode(0)
	root.right!!.left!!.left = BinTreeNode(0)
	root.right!!.left!!.right = BinTreeNode(0)
	root.prettyPrintTree()
	println(root.minRounds())
	root.prettyPrintTree()

	println()

	// a Tuple2<Int, Int>, i.e. 0 to 0, version of the tree in the last example
	val root2 = BinTreeNode(0 tu 0)
	root2.left = BinTreeNode(0 tu 0)
	root2.left!!.left = BinTreeNode(0 tu 0)
	root2.right = BinTreeNode(0 tu 0)
	root2.right!!.right = BinTreeNode(0 tu 0)
	root2.right!!.left = BinTreeNode(0 tu 0)
	root2.right!!.left!!.left = BinTreeNode(0 tu 0)
	root2.right!!.left!!.right = BinTreeNode(0 tu 0)
	root2.prettyPrintTree()
	println(root2.minRound())
	root2.prettyPrintTree()
}
