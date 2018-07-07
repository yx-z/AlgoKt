package tree.bintree

// binary tree -> doubly linked list
fun main(args: Array<String>) {
	//        10
	//       /  \
	//      4    15
	//     / \     \
	//    2   7    21
	val testRoot = BinTreeNode(10)
	testRoot.left = BinTreeNode(4)
	testRoot.left!!.left = BinTreeNode(2)
	testRoot.left!!.right = BinTreeNode(7)
	testRoot.right = BinTreeNode(15)
	testRoot.right!!.right = BinTreeNode(21)

	//    head
	//      |
	//      v
	// . <- 2 <-> 4 <-> 7 <-> 10 <-> 15 <-> 21 -> .
	var head: BinTreeNode<Int>? = testRoot.toDLL()
	while (head != null) {
		print("${head.data} <-> ")
		head = head.right
	}
	println(".")
}

fun <T> BinTreeNode<T>.toDLL(): BinTreeNode<T> {
	val head = left?.toDLL() ?: this

	var predecessor = left
	while (predecessor != null && predecessor.right != null) {
		predecessor = predecessor.right
	}
	predecessor?.right = this
	left = predecessor

	val successor = right?.toDLL()
	successor?.left = this
	right = successor

	return head
}