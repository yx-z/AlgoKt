package recur

import tree.bintree.BinTreeNode
import util.OneArray
import util.get
import util.oneArrayOf
import util.prettyPrintTree
import kotlin.collections.set

// given two arrays Pre[1..n] and In[1..n] representing the preorder and inorder
// sequences for a binary tree, reconstruct such tree or throw exceptions

// first we can preprocess in O(n) time : In[PreToIn[i]] = Pre[i], i.e.
// PreToIn[i] gives the index in the inorder array In that represents the same
// element in the preorder array Pre
fun <T> preprocess(Pre: OneArray<T>, In: OneArray<T>): OneArray<Int> {
	val n = Pre.size
	val dict = HashMap<T, Int>()
	for (i in 1..n) {
		dict[In[i]] = i
	}
	val PreToIn = OneArray(n) { 0 }
	for (i in 1..n) {
		PreToIn[i] = dict[Pre[i]]!! // <- throws exceptions if we cannot find Pre[i] in In[i]
	}
	return PreToIn
}

fun <T> binTreeFrom(Pre: OneArray<T>,
                    In: OneArray<T>,
                    PreToIn: OneArray<Int> = preprocess(Pre, In))
		: BinTreeNode<T>? {
	val n = Pre.size // == In.size since it's given in the problem statement

	if (n == 0) {
		return null
	}

	val r = PreToIn[1]

	val root = BinTreeNode(Pre[1])
	root.left = binTreeFrom(Pre[2..r], In[1 until r])
	root.right = binTreeFrom(Pre[r + 1..n], In[r + 1..n])
	return root
}
// time complexity: O(n log n)

fun main(args: Array<String>) {
	val Pre = oneArrayOf('a', 'b', 'd', 'e', 'c', 'f')
	val In = oneArrayOf('d', 'b', 'e', 'a', 'f', 'c')
	binTreeFrom(Pre, In)?.prettyPrintTree()
}