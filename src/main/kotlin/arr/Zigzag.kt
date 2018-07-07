package arr

import java.util.*

fun main(args: Array<String>) {
	// test arrays
	val sa1 = intArrayOf(1, 4, 5, 6, 7)
	val sa2 = intArrayOf(2, 3, 5, 8)

	// 8, 1, 7, 2, 6, 3, 5, 4, 5
	println(Arrays.toString(zigzagArr(sa1, sa2)))
}

fun zigzagArr(sorted1: IntArray, sorted2: IntArray): IntArray {
	val s1 = sorted1.size
	val s2 = sorted2.size

	var l1 = 0
	var l2 = 0
	var h1 = s1 - 1
	var h2 = s2 - 1

	val ans = IntArray(s1 + s2)
	var idx = 0

	var findLargest = true
	while (l1 <= h1 && l2 <= h2) {
		if (findLargest) {
			if (sorted1[h1] > sorted2[h2]) {
				ans[idx] = sorted1[h1]
				h1--
			} else {
				ans[idx] = sorted2[h2]
				h2--
			}
		} else {
			if (sorted1[l1] < sorted2[l2]) {
				ans[idx] = sorted1[l1]
				l1++
			} else {
				ans[idx] = sorted2[l2]
				l2++
			}
		}

		idx++
		findLargest = !findLargest
	}

	while (l1 <= h1) {
		if (findLargest) {
			ans[idx] = sorted1[h1]
			h1--
		} else {
			ans[idx] = sorted1[l1]
			l1++
		}

		idx++
		findLargest = !findLargest
	}

	while (l2 <= h2) {
		if (findLargest) {
			ans[idx] = sorted2[h2]
			h2--
		} else {
			ans[idx] = sorted1[l2]
			l2++
		}

		idx++
		findLargest = !findLargest
	}

	return ans
}
