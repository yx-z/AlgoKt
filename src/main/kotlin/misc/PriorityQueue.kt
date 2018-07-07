package misc

import util.OneArray

interface PriorityQueue<T : Comparable<T>> {
	fun add(t: T)

	fun remove(): T

	fun peek(): T

	fun isEmpty(): Boolean

	fun isNotEmpty() = !isEmpty()
}

abstract class Heap<T : Comparable<T>> : PriorityQueue<T>

class HeapFullException : Exception("heap is currently full")

class MinHeap<T : Comparable<T>>(val capacity: Int) : Heap<T>() {
	private val arr = OneArray<T?>(capacity) { null }
	private var size = 0

	init {
		arr.getterIndexOutOfBoundsHandler = { null }
	}

	fun size() = size

	override fun add(t: T) {
		if (size == capacity) {
			throw HeapFullException()
		}

		size++
		arr[size] = t
		heapifyUp(size)
	}

	override fun remove(): T {
		val min = peek()
		arr[1] = arr[size]
		arr[size] = null
		heapifyDown(1)
		size--
		return min
	}

	override fun peek() = arr[1]!!

	override fun isEmpty() = size == 0

	private fun heapifyUp(i: Int) {
		var parent = i / 2
		var curr = i
		while (arr[parent] != null &&
				arr[curr] != null &&
				arr[parent]!! > arr[curr]!!) {
			swap(curr, parent)
			curr = parent
			parent /= 2
		}
	}

	private fun heapifyDown(i: Int) {
		var smallest = i
		val left = 2 * i
		val right = left + 1
		if (arr[smallest] != null &&
				arr[left] != null &&
				arr[smallest]!! > arr[left]!!) {
			smallest = left
		}
		if (arr[smallest] != null &&
				arr[right] != null &&
				arr[smallest]!! > arr[right]!!) {
			smallest = right
		}

		if (smallest != i) {
			swap(i, smallest)
			heapifyDown(smallest)
		}
	}

	private fun swap(i1: Int, i2: Int) {
		val tmp = arr[i1]
		arr[i1] = arr[i2]
		arr[i2] = tmp
	}

}

fun main(args: Array<String>) {
	val minHeap = MinHeap<Int>(6)
	arrayOf(1, 5, 2, 3, 9, 6).forEach {
		minHeap.add(it)
	}

	while (minHeap.isNotEmpty()) {
		print("${minHeap.remove()} ")
	}
	println()
}
