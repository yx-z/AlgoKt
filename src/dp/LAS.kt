package dp

import util.max

// longest alternating subsequence
fun main(args: Array<String>) {
	val A = intArrayOf(7, 5, 6, 10, 2)
	println(las(A))
	println(lasNoDP(A))
}

// time complexity: O(n)
// space complexity: O(1)
// count the number of local extrema
fun lasNoDP(A: IntArray) = A.indices.filter {
	it in 1 until A.size - 1 && (A[it] > A[it - 1] && A[it] > A[it + 1] || A[it] < A[it - 1] && A[it] < A[it + 1])
}.count() + 2

fun las(A: IntArray): Int {
	val n = A.size

	// inc(i): len of las that increases first (X[2] > X[1]) and starts at A[i]
	// dec(i): len of las that decreases first (X[2] < X[1]) and starts at A[i]
	// inc(i), dec(i) = 0 if i !in 1..n
	// inc(i), dec(i) = 1 if i = n
	// assume max{ } = 0
	// inc(i) = 1 + max{ dec(k) : k in i + 1..n && A[k] > A[i] } o/w
	// dec(i) = 1 + max{ inc(k) : k in i + 1..n && A[k] < A[i] } o/w

	// we want max_i { inc(i), dec(i) }
	var max = Int.MIN_VALUE

	// two 2d array:
	// inc[1..n]: inc[i] = inc(i)
	// dec[1..n]: dec[i] = inc(i)
	val inc = IntArray(n)
	val dec = IntArray(n)
	// space complexity: O(n)

	// base case
	inc[n - 1] = 1
	dec[n - 1] = 1

	// we see that inc(i) depends on dec(k), k > i
	// , and dec(i) depends on inc(k), k > i
	// so the evaluation order should be from right to left, i.e. i from n down to 1
	for (i in n - 2 downTo 0) {
		inc[i] = 1 + (dec
				.filterIndexed { k, _ -> k in i + 1 until n && A[k] > A[i] }
				.max() ?: 0)
		dec[i] = 1 + (inc
				.filterIndexed { k, _ -> k in i + 1 until n && A[k] < A[i] }
				.max() ?: 0)
		max = max(max, inc[i], dec[i])
	}
	// time complexity: O(n^2)

	return max
}

