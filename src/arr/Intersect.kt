package arr

import java.util.*

fun main(args: Array<String>) {
	val a1 = intArrayOf(1, 2, 2, 3)
	val a2 = intArrayOf(1, 2, 2, 2, 3, 3)

	println(Arrays.toString(a1.intersectNoDuplicate(a2)))
	println(Arrays.toString(a1.intersectWithDuplicate(a2)))
}

fun IntArray.intersectNoDuplicate(arr: IntArray) = this.toSet().intersect(arr.toSet()).toIntArray()

fun IntArray.intersectWithDuplicate(arr: IntArray): Array<Int> {
	Arrays.sort(this)
	Arrays.sort(arr)

	var idx1 = 0
	var idx2 = 0

	val ans = ArrayList<Int>()

	while (idx1 < this.size && idx2 < arr.size) {
		when {
			this[idx1] == arr[idx2] -> {
				ans.add(this[idx1])

				idx1++
				idx2++
			}
			this[idx1] > arr[idx2] -> idx2++
			else -> idx1++
		}
	}

	return ans.toTypedArray()
}
