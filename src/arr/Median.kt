package arr

import java.util.*

fun main(args: Array<String>) {
	val test1 = intArrayOf(1, 4, 6)
	println(test1.getMedian())
	val test2 = intArrayOf(2, 9, 10)
	println(test2.getMedian())

	val merged = test1 merge test2
	println(Arrays.toString(merged))
	println(merged.getMedian())


}

infix fun IntArray.merge(intArr: IntArray): IntArray {
	val retArr = IntArray(size + intArr.size)
	var i1 = size - 1
	var i2 = intArr.size - 1
	(retArr.size - 1 downTo 0).forEach {
		retArr[it] = when {
			i1 < 0 -> intArr[i2--]
			i2 < 0 -> this[i1--]
			this[i1] < intArr[i2] -> intArr[i2--]
			else -> this[i1--]
		}
	}
	return retArr
}

fun IntArray.getMedian() = if (size % 2 == 0) {
	(this[size / 2] + this[size / 2 - 1]) / 2
} else {
	this[size / 2]
}