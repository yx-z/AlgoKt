package recur

import util.OneArray
import util.oneArrayOf

// my Oath/Yahoo phone interview question:
// given A[1..n] that guarantees to have an element e : it appears more than
// n / 2 times in A, find such e

// algorithms below are all O(n)

// 1. hash and count: maintain a table <element, # of occurrences>
fun OneArray<Int>.majorEleHash(): Int {
	val map = HashMap<Int, Int>()
	forEach { map[it] = (map[it] ?: 0) + 1 }
	return map.entries.first { it.value > size / 2 }.key
}

// 2. use quick select to find the median
fun OneArray<Int>.majorEleMedian() = quickSelect((size + 1) / 2)

// 3. moore's voting algorithm
fun OneArray<Int>.majorEleMoore(): Int {
	val A = this

	var majorEleIdx = 1
	count = 0
	for (i in indices) {
		if (A[majorEleIdx] == A[i]) {
			count++
		} else {
			count--
		}

		if (count == 0) {
			majorEleIdx = i
			count = 1
		}
	}

	return A[majorEleIdx]
}

fun main(args: Array<String>) {
	val A = oneArrayOf(1, 2, 4, 5, 5, 5, 5, 3, 5)
	println(A.majorEleHash())
	println(A.majorEleMedian())
	println(A.majorEleMoore())
}