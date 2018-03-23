package tree.bintree

import java.util.*

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

fun <T> BinTreeNode<T>.vertical(): List<List<T>> {
	val list = ArrayList<List<T>>()
	val map = getVerticalOrder()
	map.forEach {
		val col = ArrayList<T>()
		it.value.forEach { col.add(it.data) }
		list.add(col)
	}

	return list
}

fun <T> BinTreeNode<T>.getVerticalOrder(map: TreeMap<Int, ArrayList<BinTreeNode<T>>> = TreeMap(),
                                 curr: Int = 0): TreeMap<Int, ArrayList<BinTreeNode<T>>> {
	map[curr]?.add(this) ?: map.put(curr, arrayListOf(this))
	this.left?.getVerticalOrder(map, curr - 1)
	this.right?.getVerticalOrder(map, curr + 1)
	return map
}