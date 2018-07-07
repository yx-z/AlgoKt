package recur

import util.OneArray
import util.get
import kotlin.collections.set

// given an unsorted array A[1..n]
// determine if there exists an element in A : it appears more than n / 4 times in A

// easy! put elements into a hash table : element is the key and count
// their appearances as values
fun OneArray<Int>.nOverFourHash(): Boolean {
	val n = size
	val map = HashMap<Int, Int>()
	// init. all elements with count 0
	forEach {
		if (map.containsKey(it)) {
			map[it] = map[it]!! + 1
		} else {
			map[it] = 1
		}
	}

	return map.values.any { it > n / 4 }
}

// but what if hashing is not allowed and you still want O(n) running time?
fun OneArray<Int>.nOverFour(count: Int = size / 4 + 1): Boolean {
	val A = this
	val n = size

	if (n < count) {
		return false
	}

	val elem = quickSelect(count)
	var idx = indexOfLast { it == elem }
	idx = partition(idx)

	var tmp = 1
	for (i in 2..idx) {
		if (A[i] == A[1]) {
			tmp++
		}
	}
	if (tmp >= count) {
		return true
	}

	return A[count..n].nOverFour(count)
}

fun main(args: Array<String>) {
	val n = 100
	// there are at least 30 elements of 7 in A[1..100]
	val A = OneArray(n) { if (it <= 30) 7 else it }.shuffled()
//	println(A.nOverFourHash())
	println(A.nOverFour())
}