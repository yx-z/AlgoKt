package stack

import java.util.*
import kotlin.collections.HashMap

fun main(args: Array<String>) {
	val arr = arrayOf(1, 4, 2, 3, 6, 9, 5, 8, 7)
	print(arr.getNextGreaterNum())
}

fun Array<Int>.getNextGreaterNum(): Map<Int, Int> {
	val map = HashMap<Int, Int>(this.size)
	val stack = Stack<Int>()
	this.forEach {
		while (stack.isNotEmpty() && stack.peek() < it) {
			map.put(stack.pop(), it)
		}
		stack.push(it)
	}
	this.forEach { map.putIfAbsent(it, -1) }
	return map
}