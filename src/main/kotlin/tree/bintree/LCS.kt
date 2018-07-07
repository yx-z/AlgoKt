package tree.bintree

import util.get
import util.prettyPrintTree
import util.set
import java.util.*

// given two binary trees A and B, find a largest common subtree between them
// here we only care about their tree structure so no need to compare data
// return the root of this subtree in A
fun BinTreeNode<Int>.lst(B: BinTreeNode<Int>): BinTreeNode<Int> {
	val A = this
	val lvA = A.lvTrav() // level order traversal of A
	// since data is irrelevant, we will override data as the index during the traversal
	// o/w we can use a hashtable/build a separate tree for just storing indices
	val m = lvA.size // # of nodes of A

	// similarly for B
	val lvB = B.lvTrav()
	val n = lvB.size

	// dp(i, j): # of nodes of lcs rooted @ lvA[i] and lvB[j]
	// memoization structure: 2d arr dp[0 until m, 0 until n]
	val dp = Array(m) { Array(n) { 0 } }
	// space: O(mn)

	// dp(i, j) = 1 if lvA[i] is a leaf or lvB[j] is a leaf
	//          = 1 if lvA[i] does/does not have a left child but lvB[j] does not/does OR
	//                 lvA[i] does/does not have a right child but lvB[j] does not/does
	//          = 1 + dp(p, q) if both lvA[i] and lvB[j] only have a left/right child
	//            where lvA[p], lvB[q] is the left/right child
	//          = 1 + dp(p, q) + dp(u, v) if both lvA[i] and lvB[j] have both children
	//            where lvA[p], lvA[u], lvB[q], lvB[v] are corresponding children
	// dependency: since lvA, lvB are level order traversals, children of a node
	//             will always have a larger index than it has
	//             that is to say, dp(i, j) depends on dp(p, q) where p > i, q > j
	// we will keep a maximum size and its corresponding index in A, i.e. i
	var maxSize = -1
	var maxI = -1
	// eval order: outer loop for i decreasing from m - 1 down to 0
	for (i in m - 1 downTo 0) {
		// inner loop for j decreasing from n - 1 down to 0
		for (j in n - 1 downTo 0) {
			dp[i, j] = when {
				lvA[i].isLeaf() || lvB[j].isLeaf() -> 1
				(lvA[i].left == null && lvB[j].left != null) ||
						(lvA[i].left != null && lvB[j].left == null) ||
						(lvA[i].right == null && lvB[j].right != null) ||
						(lvA[i].right != null && lvB[j].right == null) -> 1
				lvA[i].left != null && lvA[i].right == null && lvB[i].left != null && lvB[j].right == null -> 1 + dp[lvA[i].left!!.data, lvB[j].left!!.data]
				lvA[i].left == null && lvA[i].right != null && lvB[j].left == null && lvB[j].right != null -> 1 + dp[lvA[i].right!!.data, lvB[j].right!!.data]
				else -> // both children are non-null for lvA[i] and lvB[j]
					1 + dp[lvA[i].left!!.data, lvB[j].left!!.data] + dp[lvA[i].right!!.data, lvB[j].right!!.data]
			}
			if (dp[i, j] > maxSize) {
				maxSize = dp[i, j]
				maxI = i
			}
		}
	}

	return lvA[maxI]
}

private fun BinTreeNode<Int>.lvTrav(): ArrayList<BinTreeNode<Int>> {
	var index = 0
	val lvTrav = ArrayList<BinTreeNode<Int>>()
	val queue: Queue<BinTreeNode<Int>> = LinkedList<BinTreeNode<Int>>()
	queue.add(this)
	while (queue.isNotEmpty()) {
		val node = queue.remove()
		node.data = index
		index++
		lvTrav.add(node)
		node.left?.run { queue.add(this) }
		node.right?.run { queue.add(this) }
	}
	return lvTrav
}

private fun <T> BinTreeNode<T>.isLeaf() = left == null && right == null

fun main(args: Array<String>) {
	val A = BinTreeNode(0)
	A.left = BinTreeNode(0)
	A.right = BinTreeNode(0)
	A.left!!.left = BinTreeNode(0)
	A.left!!.right = BinTreeNode(0)
	A.right!!.left = BinTreeNode(0)
	A.left!!.left!!.left = BinTreeNode(0)
	A.left!!.left!!.right = BinTreeNode(0)
//	A.prettyPrintTree()

	val B = BinTreeNode(0)
	B.left = BinTreeNode(0)
	B.right = BinTreeNode(0)
	B.right!!.left = BinTreeNode(0)
	B.right!!.right = BinTreeNode(0)
	B.right!!.left!!.left = BinTreeNode(0)
	B.right!!.left!!.right = BinTreeNode(0)
	B.right!!.left!!.left!!.left = BinTreeNode(0)
	B.right!!.right!!.left = BinTreeNode(0)
	B.right!!.right!!.right = BinTreeNode(0)
	B.right!!.right!!.left!!.left = BinTreeNode(0)
	B.right!!.right!!.left!!.right = BinTreeNode(0)
//	B.prettyPrintTree()

	(A.lst(B)).prettyPrintTree()
}