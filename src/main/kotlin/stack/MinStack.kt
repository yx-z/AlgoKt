package stack

import java.util.*

class MinStack {
	private var min: Int = Int.MAX_VALUE
	private val stack = Stack<Int>()

	fun getMin() = min

	fun pop(): Int {
		val popped = stack.pop()
		if (popped == min) {
			min = stack.pop()
		}

		return popped
	}

	fun push(i: Int) {
		if (i < min) {
			stack.push(min)
			min = i
		}
		stack.push(i)
	}
}

fun main(args: Array<String>) {
	val minStack = MinStack()
	(5 downTo 1).forEach { minStack.push(it) }
	minStack.getMin()
	(1..5).forEach { println(minStack.pop()) }
}