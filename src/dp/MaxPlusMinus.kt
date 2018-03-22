package dp

import util.get
import util.set
import util.max
import util.min

// given a sequence of integers with +/- signs in between
// find the maximum possible value obtained from the sequence (no multiplication) by adding (/)
fun main(args: Array<String>) {
	val arr = arrayOf('1', '+', '3', '-', '2', '-', '5', '+', '1', '-', '6', '+', '7')
	println(arr.maxPlusMinus())
}

fun Array<Char>.maxPlusMinus(): Int {
	// assume that input always starts with a number and ends with a number
	// then this[2i] is a digit, for i in 0..(size - 1) / 2
	// and this[2i + 1] is a sign, for i in 0 until (size - 1) / 2
	val cap = (size - 1) / 2

	// let 0 <= i <= j <= cap
	// max[i, j] = maximum value obtained from this[2i..2j]
	val max = Array(cap + 1) { IntArray(cap + 1) { Int.MIN_VALUE } }
	// min[i, j] = minimum value obtained from this[2i..2j]
	val min = Array(cap + 1) { IntArray(cap + 1) { Int.MAX_VALUE } }

	// the base case is when i == j, max[i, j] == min[i, j] == this[2i]
	for (i in 0..cap) {
		max[i, i] = this[2 * i].toDigit()
		min[i, i] = this[2 * i].toDigit()
	}

	// first we see that for this[2i..2j], signs appear in 2k + 1, where k in i until j
	// note that k != j since o/w 2k + 1 = 2j - 1 > 2j

	// so the recursive case is
	// max[i, j] = max(max[i, k] + max[k + 1, j], max[i, l] - min[l + 1, j])
	// , where k, l in i until j, and this[2k + 1] == '+', this[2l + 1] == '-'
	// min[i, j] = min(min[i, k] + min[k + 1, j], min[i, l] - max[l + 1, j])
	// , where k, l in i until j, and this[2k + 1] == '+', this[2l + 1] == '-'

	// now suppose we want to figure out max[i, j], we need
	// max[i, k], i.e. in the max table, in the same row, left of col j
	// max[k + 1, j], i.e. in the max table, in the same col, below row i
	// min[l + 1, j], i.e. in the min table, in the same col, below row i

	// therefore the evaluation order can be
	// outer loop: bottom to top
	for (i in cap downTo 0) {
		// inner loop: left to right
		for (j in i + 1..cap) {
			// linear searching: no specific order
			for (k in i until j) {
				if (this[2 * k + 1] == '+') {
					// min/max: no specific order
					max[i, j] = max(max[i, j], max[i, k] + max[k + 1, j])
					min[i, j] = min(min[i, j], min[i, k] + min[k + 1, j])
				} else { // this[2 * k + 1] == '-'
					max[i, j] = max(max[i, j], max[i, k] - min[k + 1, j])
					min[i, j] = min(min[i, j], min[i, k] - max[k + 1, j])
				}
			}
		}
	}

	return max[0, cap]
}

fun Char.toDigit() = this - '0'
