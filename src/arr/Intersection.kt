package arr

import java.util.*

fun main(args: Array<String>) {
	val a1 = arrayOf(1, 2, 2, 3)
	val a2 = arrayOf(1, 2, 2, 2, 3, 3)

	println(Arrays.toString(a1.intersectNoDuplicate(a2)))
	println(Arrays.toString(a1.intersectWithDuplicate(a2)))
}

fun Array<Int>.intersectNoDuplicate(arr: Array<Int>) = this.toSet().intersect(arr.toSet()).toIntArray()

fun Array<Int>.intersectWithDuplicate(arr: Array<Int>): Array<Int> {
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
