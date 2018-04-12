package tree.bintree

import util.*
import kotlin.collections.HashMap
import kotlin.collections.Map
import kotlin.collections.get
import kotlin.collections.set

// given a binary tree, a central node n is a node : after deleting the node,
// the tree is split into three parts: left child of n (and its subtree), right
// child of n (and its subtree), and parent of n (and its parent as well as other child)
// and these three parts all having at most half of the total nodes in this tree
// find one central node

fun <T> BinTreeNode<T>.centralNode(): BinTreeNode<T> {
	// O(n) for these two maps :
	// childrenMap[node]: (# of nodes in the left subtree, # of nodes in the right subtree)
	// parentMap[node]: # of nodes in the parent (and its parent as well as its other child)
	val childrenMap = countChildren()
	val parentMap = countParent(childrenMap, null)

//	println(childrenMap)
//	println(parentMap)

	val n = childrenMap[this]!!.first + childrenMap[this]!!.second + 1
	val limit = (n + 1) / 2
	var curr = this

	while (childrenMap[curr]!!.first > limit || childrenMap[curr]!!.second > limit) {
		val (l, r) = childrenMap[curr]!!
		curr = if (l > limit) {
			left!!
		} else {
			right!!
		}
	}

	return curr
}

private fun <T> BinTreeNode<T>.countChildren(map: HashMap<BinTreeNode<T>, Tuple2<Int, Int>> = HashMap())
		: Map<BinTreeNode<T>, Tuple2<Int, Int>> {
	left?.countChildren(map)
	right?.countChildren(map)
	map[this] = (map[left]?.run { first + second + 1 } ?: 0) tu
			(map[right]?.run { first + second + 1 } ?: 0)
	return map
}

private fun <T> BinTreeNode<T>.countParent(childrenMap: Map<BinTreeNode<T>, Tuple2<Int, Int>>,
                                           parent: BinTreeNode<T>?,
                                           parentMap: HashMap<BinTreeNode<T>, Int> = HashMap())
		: Map<BinTreeNode<T>, Int> {
	if (parent == null) {
		parentMap[this] = 0
	} else {
		if (this === parent.left) { // left child
			parentMap[this] = parentMap[parent]!! + childrenMap[parent]!!.second + 1
		} else { // right child
			parentMap[this] = parentMap[parent]!! + childrenMap[parent]!!.first + 1
		}
	}

	left?.countParent(childrenMap, this, parentMap)
	right?.countParent(childrenMap, this, parentMap)

	return parentMap
}

fun main(args: Array<String>) {
	val root = BinTreeNode(5)
	root.left = BinTreeNode(3)
	root.right = BinTreeNode(1)
	root.left!!.left = BinTreeNode(4)
	root.left!!.left!!.left = BinTreeNode(10)
	root.left!!.left!!.right = BinTreeNode(20)
	root.left!!.right = BinTreeNode(2)
	root.right!!.right = BinTreeNode(8)
	root.prettyPrintTree()

	println(root.centralNode())
}