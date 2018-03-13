package dp

import get
import max

// similar to `LargestSum`,
// given an array of numbers,
// find the largest product in any contiguous subarray.
// note that two negative numbers produce a positive result, ex. (-2) * (-4) = 8
fun main(args: Array<String>) {
	println(floatArrayOf(1F, 2F, 3F).largestProd())
	val arr = floatArrayOf(-6F, 12F, -7F, 0F, 14F, -7F, 5F)
	println(arr.largestProd())
}

fun FloatArray.largestProd(): Float {
	if (size == 0) {
		return 0F
	}
	if (size == 1) {
		return max(0F, this[0])
	}
	for (i in 0 until size) {
		if (this[i] == 0F) {
			return max(this[0 until i].largestProd(), this[i + 1 until size].largestProd())
		}
	}

	// no 0s in the array
	var max = this.reduce { acc, fl -> acc * fl }
	return max
}

