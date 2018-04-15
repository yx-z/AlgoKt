package tree.bintree

import util.max

class AVLTree<K : Comparable<K>, V>() {
	private var root: AVLTreeNode<K, V>? = null

	constructor(rootKey: K, rootValue: V) : this() {
		root = AVLTreeNode(rootKey, rootValue)
	}

	operator fun set(key: K, value: V) {
		if (root == null) {
			root = AVLTreeNode(key, value)
		} else {
			root!![key] = value
		}
	}

	operator fun get(key: K) = root?.get(key)

	fun remove(k: K) = root?.remove(k) ?: false

	private class AVLTreeNode<K : Comparable<K>, V>(val key: K, var value: V) {
		var left: AVLTreeNode<K, V>? = null
		var right: AVLTreeNode<K, V>? = null
		var height: Int = 0

		operator fun set(k: K, v: V) {
			if (k == key) {
				value = v
				return
			}

			if (k < key) {
				if (left == null) {
					left = AVLTreeNode(k, v)
				} else {
					left!![k] = v
				}
			} else { // k > key
				if (right == null) {
					right = AVLTreeNode(k, v)
				} else {
					right!![k] = v
				}
			}

			rebalance()
			updateHeight()
		}

		operator fun get(k: K): V? {
			if (k == key) {
				return value
			}

			return if (k < key) {
				if (left == null) {
					null
				} else {
					left!![k]
				}
			} else { // k > key
				if (right == null) {
					null
				} else {
					right!![k]
				}
			}
		}

		fun remove(k: K): Boolean {
			TODO()
		}

		fun rebalance() {
			when (getBalance()) {
				-2 -> when (left!!.getBalance()) {
					-1 -> rotateRight()
					1 -> rotateLeftRight()
				}
				2 -> when (right!!.getBalance()) {
					-1 -> rotateRightLeft()
					1 -> rotateLeft()
				}
			}
			updateHeight()
		}

		fun rotateLeft() {

		}

		fun rotateLeftRight() {

		}

		fun rotateRight() {

		}

		fun rotateRightLeft() {

		}

		fun getBalance() = (right?.height ?: -1) - (left?.height ?: -1)

		fun updateHeight() {
			left?.updateHeight()
			right?.updateHeight()

			height = max(left?.height ?: -1, right?.height ?: -1) + 1
		}
	}
}

fun main(args: Array<String>) {
	val avlTree = AVLTree<Int, String>()
	avlTree[3] = "hello"
	avlTree[6] = "world"
	avlTree[2] = "welcome"
}