package arr

import java.util.*

fun main(args: Array<String>) {
	// given two sorted arrays (may be different length)
	// util.get median of their merged sorted array
	val test1 = intArrayOf(1, 3, 8)
	val test2 = intArrayOf(2, 7, 9, 10)
	println(Arrays.toString(test1))
	println(Arrays.toString(test2))

	// merge all and report middle element
	val merged = test1 merge test2
	println(Arrays.toString(merged))
	println("===== =====")

	println(merged.getMedian())

	// merge up to middle
	println(getMedianByMerging(test1, test2))

	// find kth smallest by removing half of elements in each time
	println(getMedianByComparing(test1, test2))
}

// O((size1 + size2) / 2)
fun getMedianByMerging(arr1: IntArray, arr2: IntArray): Double {
	val size1 = arr1.size
	val size2 = arr2.size
	val lenMerged = size1 + size2
	val midIdx = lenMerged / 2
	var i1 = 0
	var i2 = 0
	var curr = -1

	if (lenMerged % 2 == 0) {
		// even length
		var prev = -1
		(0..midIdx).forEach {
			prev = curr
			curr = when {
				i1 >= size1 -> arr2[i2++]
				i2 >= size2 -> arr1[i1++]
				arr1[i1] < arr2[i2] -> arr1[i1++]
				else -> arr2[i2++]
			}
		}
		return (curr + prev) / 2.0
	}

	// odd length
	(0..midIdx).forEach {
		curr = when {
			i1 >= arr1.size -> arr2[i2++]
			i2 >= arr1.size -> arr1[i1++]
			arr1[i1] < arr2[i2] -> arr1[i1++]
			else -> arr2[i2++]
		}
	}
	return curr.toDouble()
}

fun getMedianByComparing(arr1: IntArray, arr2: IntArray): Double {
	val totalSize = arr1.size + arr2.size
	val midIdx = totalSize / 2
	return if (totalSize % 2 == 0) {
		(kth(arr1, 0, arr2, 0, midIdx) + kth(arr1, 0, arr2, 0, midIdx + 1)) / 2.0
	} else {
		kth(arr1, 0, arr2, 0, midIdx + 1).toDouble()
	}
}

// O(log(size1 + size2))
fun kth(arr1: IntArray, start1: Int, arr2: IntArray, start2: Int, k: Int): Int {
	if (start1 >= arr1.size) {
		return arr2[start2 + k - 1]
	}
	if (start2 >= arr2.size) {
		return arr1[start1 + k - 1]
	}

	if (k == 1) {
		return minOf(arr1[start1], arr2[start2])
	}

	val mid1 = if (start1 + k / 2 - 1 < arr1.size) {
		arr1[start1 + k / 2 - 1]
	} else {
		Int.MAX_VALUE
	}
	val mid2 = if (start2 + k / 2 - 1 < arr2.size) {
		arr2[start2 + k / 2 - 1]
	} else {
		Int.MAX_VALUE
	}

	val kUp = k - k / 2
	return if (mid1 < mid2) {
		kth(arr1, start1 + k / 2, arr2, start2, kUp)
	} else {
		kth(arr1, start1, arr2, start2 + k / 2, kUp)
	}
}

// O(size1 + size2)
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
	(this[size / 2] + this[size / 2 - 1]) / 2.0
} else {
	this[size / 2].toDouble()
}