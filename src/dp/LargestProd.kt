package dp

import get
import max

// similar to `LargestSum`,
// given an array of numbers,
// find the largest product in any contiguous subarray.
// note that two negative numbers produce a positive result, ex. (-2) * (-4) = 8
fun main(args: Array<String>) {
	val arr = intArrayOf(-6, 12, -7, 0, 14, -7, 5)
	println(arr.largestProd())
}

fun IntArray.largestProd(): Int {
	if (size == 0) {
		return 0
	}
	if (size == 1) {
		return max(0, this[0])
	}
	for (i in 0 until size) {
		if (this[i] == 0) {
			return max(this[0 until i].largestProd(), this[i + 1 until size].largestProd())
		}
	}

	// no 0s in the array
	val total = this.reduce { acc, it -> acc * it }
	if (total < 0) {
		var leftMax = total
		var leftIdx = 0
		while (leftIdx < size && this[leftIdx] > 0) {
			leftMax /= this[leftIdx]
			leftIdx++
		}
		// first negative number from left to right
		leftMax /= this[leftIdx]

		var rightMax = total
		var rightIdx = size - 1
		while (rightIdx >= 0 && this[rightIdx] > 0) {
			rightMax /= this[rightIdx]
			rightIdx--
		}
		// first negative number from right to left
		rightMax /= this[rightIdx]
		return max(leftMax, rightMax)
	}
	return total
}
