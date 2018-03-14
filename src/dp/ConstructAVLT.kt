package dp

import get
import set

// similar to 'ConstructBST'
// construct a balanced BST (AVL Tree) that must satisfy |h(left) - h(right)| < 2
fun main(args: Array<String>) {
	val freq = intArrayOf(1, 1, 1, 1, 10, 1, 1, 100, 1, 1)
	println(freq.avltCost())
}


fun IntArray.avltCost(): Int {
	TODO()
}
