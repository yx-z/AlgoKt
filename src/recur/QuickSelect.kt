package recur

import util.OneArray
import util.get
import util.swap

// given an unsorted array, A[1..n], find the k-th smallest element
fun main(args: Array<String>) {
	val arr = OneArray(100) { it * 10 }
	println(arr.quickSelect(30))
	println(arr.momSelect(30))
}

// here we partition based on pivot index 1
// find a better privoting strategy in `momSelect` below
fun OneArray<Int>.quickSelect(k: Int): Int {
	val A = this
	val n = size

	if (n == 1) {
		return A[1]
	}

	val r = partition(1)
//	prettyPrintln(false)
	return when {
		k < r -> A[1 until r].quickSelect(k)
		k > r -> A[r + 1..n].quickSelect(k - r)
		else -> A[r]
	}
}

// mom = median of median
fun OneArray<Int>.momSelect(k: Int): Int {
	val A = this
	val n = size

	// we don't need to do fancy things if n is small
	if (n <= 25) {
		return quickSelect(k)
	}

	// here is the interesting part
	// how can we find a good pivot?
	// well an estimate of a median is a good pivot, so we can recurse and find
	// a rough median!
	val m = n / 5
	val M = OneArray(m) { 0 }
	for (i in 1..m) {
		// find the median of five numbers
		// either do brute force or quickselect <- overkill!
		M[i] = A[5 * i - 4..5 * i].quickSelect(3)
	}
	val mom = M.momSelect(m / 2)
	val r = partition(mom)
	return when {
		k < r -> A[1 until r].quickSelect(k)
		k > r -> A[r + 1..n].quickSelect(k - r)
		else -> A[r]
	}
}

// given an element A[idx], partition A :
// A[1 until idx'] are elements less than or equal to A[idx] and
// A[idx' + 1..n] are elements greater than A[idx]
// return the correct index of A[idx], i.e. idx' after the partition
fun OneArray<Int>.partition(idx: Int): Int {
	val A = this
	val n = size

	swap(idx, n)
	var i = 0
	var j = n
	while (i < j) {
		do {
			i++
		} while (i < j && A[i] <= A[n])

		do {
			j--
		} while (i < j && A[j] > A[n])

		if (i < j) {
			swap(i, j)
		}
	}

	// now idx >= j
	swap(i, n)
	return i
}

