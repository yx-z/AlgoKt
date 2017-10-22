package stack

import java.util.*

class MyQueue {
	private val pushStack = Stack<Int>()
	private val popStack = Stack<Int>()

	fun peek(): Int {
		if (isEmpty()) {
			throw RuntimeException("empty queue")
		}

		if (popStack.isNotEmpty()) {
			return popStack.peek()
		}


		while (pushStack.isNotEmpty()) {
			popStack.push(pushStack.pop())
		}

		return popStack.peek()
	}

	fun push(i: Int) {
		pushStack.push(i)
	}

	fun pop(): Int {
		if (popStack.isEmpty()) {
			while (pushStack.isNotEmpty()) {
				popStack.push(pushStack.pop())
			}
		}
		val popped = popStack.pop()
		return popped
	}

	fun isEmpty() = pushStack.isEmpty() && popStack.isEmpty()
}

fun main(args: Array<String>) {
	val q = MyQueue()
	try {
		q.peek() // Exception
	} catch (e: RuntimeException) {
		e.printStackTrace()
	}
	q.push(0)
	println(q.peek()) // 0
	println(q.pop()) // 0
	q.push(1)
	q.push(2)
	q.push(3)
	println(q.peek()) // 1
	println(q.pop()) // 1
	println(q.peek()) // 2
	println(q.pop()) // 2
	println(q.pop()) // 3
}