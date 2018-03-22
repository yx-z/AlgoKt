package dp

import tree.GenericTreeNode
import util.OneArray
import util.min

// given a tree as a company hierarchy
// you may assign values to nodes as 1, 2, 3
// however one node cannot have the same value as its parent
// a node will be fired if it has a smaller value than its parent
// find the minimum possible # of nodes to be fired

fun main(args: Array<String>) {
	val manager1 = GenericTreeNode(OneArray(3) { 0 })
	manager1.children.addAll(listOf(GenericTreeNode(OneArray(3) { 0 }), GenericTreeNode(OneArray(3) { 0 })))

	val manager2 = GenericTreeNode(OneArray(3) { 0 })
	manager2.children.addAll(listOf(GenericTreeNode(OneArray(3) { 0 }), GenericTreeNode(OneArray(3) { 0 })))

	val manager3 = GenericTreeNode(OneArray(3) { 0 })
	manager3.children.add(GenericTreeNode(OneArray(3) { 0 }))

	val manager4 = GenericTreeNode(OneArray(3) { 0 })
	manager4.children.add(GenericTreeNode(OneArray(3) { 0 }))

	val manager5 = GenericTreeNode(OneArray(3) { 0 })
	manager5.children.add(GenericTreeNode(OneArray(3) { 0 }))

	val vp1 = GenericTreeNode(OneArray(3) { 0 })
	vp1.children.add(manager1)

	val vp2 = GenericTreeNode(OneArray(3) { 0 })
	vp1.children.add(manager2)

	val vp3 = GenericTreeNode(OneArray(3) { 0 })
	vp3.children.addAll(listOf(GenericTreeNode(OneArray(3) { 0 }), manager3))

	val vp4 = GenericTreeNode(OneArray(3) { 0 })
	vp4.children.addAll(listOf(manager4, manager5))

	val president = GenericTreeNode(OneArray(3) { 0 })
	president.children.addAll(listOf(vp1, vp2, vp3, vp4))

	//          0
	//       / | | \
	//      0  0 0  0
	//    /   / / | | \
	//   0   0 0  0 0  0
	//  /|  / \   | |   \
	// 0 0 0   0  0 0    0

	println(president.minFired())
//	println(president)
}

fun GenericTreeNode<OneArray<Int>>.minFired(): Int {
	// let data[i] be the min # of the fired in the subtree rooted in this node
	// if it is assigned with value i, i in 1..3
	// we want min_i { data[i] } for the current sub-problem

	// base case:
	// leaf nodes have 0 for all data[1..3]
	if (children.isEmpty()) {
		return 0
	}

	//
	children.forEach { it.minFired() }

	data[1] = children.map { min(it.data[2], it.data[3]) }.sum()
	data[2] = children.map { min(it.data[3], it.data[2] + 1) }.sum()
	data[3] = children.map { min(it.data[1] + 1, it.data[2] + 1) }.sum()

	return data.min() ?: 0
}