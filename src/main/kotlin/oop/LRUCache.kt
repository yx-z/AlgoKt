package oop

import java.util.*

fun main(args: Array<String>) {
	// Least Recently Used Cache
	val cache = LRUCache(2)
	cache.set(1, 2)
	cache.set(2, 3)
	cache.set(3, 4)
	println(cache.get(2))
	println(cache.get(1))
	cache.set(4, 5)
	println(cache.get(3))
	println(cache.get(2))
}

class LRUCache(private val cap: Int) {
	private val map = HashMap<Int, Node>()
	private var head: Node? = null
	private var tail: Node? = null

	fun set(key: Int, value: Int) {
		if (map.containsKey(key)) {
			val ori = map[key]!!
			ori.value = value
			remove(ori)
			append(ori)
		} else {
			val new = Node(key, value)
			append(new)
			map.put(key, new)
			if (map.size > cap) {
				map.remove(head!!.key)
				remove(head!!)
			}
		}
	}

	fun get(key: Int) = if (map.containsKey(key)) {
		val query = map[key]!!
		remove(query)
		append(query)
		query.value
	} else {
		-1
	}

	private fun append(n: Node) {
		if (head == null) {
			head = n
		}

		if (tail == null) {
			tail = n
		} else {
			tail!!.nex = n
			n.pre = tail
			tail = n
		}
	}

	private fun remove(n: Node) {
		if (n.pre == null) {
			head = n.nex
		} else {
			n.pre!!.nex = n.nex
		}

		if (n.nex == null) {
			tail = n.pre
		} else {
			n.nex!!.pre = n.pre
		}
	}


	private class Node(val key: Int, var value: Int) {
		var nex: Node? = null
		var pre: Node? = null

		override fun toString() = "($key, $value)"
	}
}