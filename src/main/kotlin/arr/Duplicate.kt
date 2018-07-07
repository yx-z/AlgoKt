package arr

import java.util.*

fun main(args: Array<String>) {
	// test array
	val arr = intArrayOf(1, 1, 1, 3, 3, 5, 6)

	println(arr.containsDuplicateOneLiner())
	println(arr.containsDuplicate())
	println(arr.removeDuplicate())
	println(Arrays.toString(arr))
}

// Duplicate Detection
fun IntArray.containsDuplicateOneLiner() = setOf(this).size != this.size

fun IntArray.containsDuplicate(): Boolean {
	for (i in 1 until this.size) {
		if (this[i] == this[i - 1]) {
			return true
		}
	}
	return false
}

// Duplicate Removal
fun IntArray.removeDuplicate(): Int {
	var idx = 1
	for (i in 1 until this.size) {
		if (this[i] != this[i - 1]) {
			this[idx] = this[i]
			idx++
		}
	}
	return idx
}