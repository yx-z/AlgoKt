package other

import java.util.*

fun main(args: Array<String>) {
	val minHeap = MinHeap(7)
	for (i in arrayOf(1, 3, 5, 8, 2, 4, 6)) {
		minHeap.insert(i)
		minHeap.run()
	}

	println(Arrays.toString(minHeap.mArr))
}

interface PriorityQueue {
	var mArr: Array<Int>

	fun insert(i: Int)
	fun remove(): Int
	fun peek(): Int
}

class MinHeap(capacity: Int) : PriorityQueue {
	var size: Int = 0
	override var mArr = Array(capacity + 1) { 0 }

	private fun parent(idx: Int) = idx / 2

	private fun left(idx: Int) = 2 * idx

	private fun right(idx: Int) = left(idx) + 1

	private fun isLeaf(idx: Int) = idx >= size / 2 && idx <= size

	private fun swap(idx1: Int, idx2: Int) {
		val tmp = mArr[idx1]
		mArr[idx1] = mArr[idx2]
		mArr[idx2] = tmp
	}

	private fun heapify(idx: Int) {
		if (!isLeaf(idx)) {
			val l = left(idx)
			val r = right(idx)

			if (mArr[idx] > mArr[l] || mArr[idx] > mArr[r]) {
				if (mArr[l] < mArr[r]) {
					swap(idx, l)
					heapify(l)
				} else {
					swap(idx, r)
					heapify(r)
				}
			}
		}
	}

	fun run() {
		for (i in size / 2 downTo 1) {
			heapify(i)
		}
	}

	override fun insert(i: Int) {
		size++
		mArr[size] = i

		var curr = size
		while (mArr[curr] < mArr[parent(curr)]) {
			swap(curr, parent(curr))
			curr = parent(curr)
		}
	}

	override fun remove(): Int {
		val ret = peek()
		mArr[1] = mArr[size]
		size--
		heapify(1)
		return ret
	}

	override fun peek() = mArr[1]
}
