package recur

import util.*
import kotlin.math.floor

// given an unsorted array S[1..n] of (value, weight)
// find weighted median S[i] : total weight of elements that has value < S[i]_1
// is less than half of the total weight of S
// similarly, total weight of elements that has value > S[i]_1 is also less than
// half of the total weight of S
fun OneArray<Tuple2<Int, Int>>.weightedMedian(targetWeight: Int = map { it.second }.sum() / 2): Tuple2<Int, Int> {
	val S = this
	val n = size

	if (n == 1) {
		return S[1]
	}

	val m = floor(n / 2.0).toInt()
	partitionTuple(1)
	val leftW = (1 until m).map { S[it].second }.sum()

	return if (leftW > targetWeight) {
		S[1..m].weightedMedian(targetWeight)
	} else {
		S[m + 1..n].weightedMedian(targetWeight - leftW)
	}
}

// same partition as in QuickSelect
fun OneArray<Tuple2<Int, Int>>.partitionTuple(idx: Int) {
	val A = this
	val n = size

	swap(idx, n)
	var i = 0
	var j = n
	while (i < j) {
		do {
			i++
		} while (i < j && A[i].first < A[n].first)

		do {
			j--
		} while (i < j && A[j].first > A[n].first)

		if (i < j) {
			swap(i, j)
		}
	}

	// now idx >= j
	swap(i, n)
}

fun main(args: Array<String>) {
	val S = oneArrayOf(
			1 tu 20,
			2 tu 15,
			1 tu 16,
			3 tu 23,
			2 tu 40,
			4 tu 33,
			5 tu 30)
	println(S.weightedMedian())
}