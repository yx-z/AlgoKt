package tree.bintree

import java.util.*
import kotlin.collections.ArrayList

// return the vertical order of a binary tree
fun main(args: Array<String>) {
	//    5
	//   / \
	//  3   4
	//   \   \
	//    7   8
	val root = BinTreeNode(5)
	root.left = BinTreeNode(3)
	root.left!!.right = BinTreeNode(7)
	root.right = BinTreeNode(4)
	root.right!!.right = BinTreeNode(8)

	// [
	//    [3],
	//    [5, 7],
	//    [4],
	//    [8]
	// ]
	println(root.vertical())
}

fun BinTreeNode.vertical(): List<List<Int>> {
	val list = ArrayList<List<Int>>()
	val map = getVerticalOrder()
	map.forEach {
		val col = ArrayList<Int>()
		it.value.forEach { col.add(it.data) }
		list.add(col)
	}

	return list
}

fun BinTreeNode.getVerticalOrder(map: TreeMap<Int, ArrayList<BinTreeNode>> = TreeMap(),
                                 curr: Int = 0): TreeMap<Int, ArrayList<BinTreeNode>> {
	map[curr]?.add(this) ?: map.put(curr, arrayListOf(this))
	this.left?.getVerticalOrder(map, curr - 1)
	this.right?.getVerticalOrder(map, curr + 1)
	return map
}