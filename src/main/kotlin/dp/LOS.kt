package dp

import util.OneArray
import util.max
import util.oneArrayOf

// longest oscillating subsequence

// X[1..n] is oscillating if X[i] < X[i + 1] for even i, and X[i] > X[i + 1] for odd i

// find the length of los of A[1..n]
fun main(args: Array<String>) {
	val A = intArrayOf(-1 /* ignored value to be one-indexed */, 1, 25, 9, 20, 5)
//	println(los(A))

	val B = oneArrayOf(1, 25, 9, 20, 5)
	println(B.los())
}

fun los(A: IntArray): Int {
	val n = A.size - 1
	// los(i): len of los for A[i..n] starting at A[i]
	// we want max{ los(i) : i in 1..n }
	// define lo's(i): len of lo's for A[i..n] starting at A[i], lo's is the longest oscillating'
	// subsequence where X[i] < X[i +1] for odd i, and X[i] > X[i + 1] for even i
	// los(i), los'(i) = 0 i > n
	// assume max {} = 0
	// los(i) = max { lo's(k) : i < k <= n && A[k] > A[i] } + 1
	// lo's(i) = max { los(k) : i < k <= n && A[k] < A[i] } + 1

	val los = IntArray(n + 1)
	val loos = IntArray(n + 1)
	for (i in n downTo 1) {
		los[i] = (loos.filterIndexed { idx, _ -> idx in i + 1 until n && A[idx] > A[i] }.max()
				?: 0) + 1
		loos[i] = (los.filterIndexed { idx, _ -> idx in i + 1 until n && A[idx] < A[i] }.max()
				?: 0) + 1
	}
//	println(Arrays.toString(los))
//	println(Arrays.toString(loos))
	return los[1]
}

fun OneArray<Int>.los(): Int {
	val A = this
	val n = size

	// los(i): len of los of A[i..n] that MUST include A[i]
	// loos(i): len of loos of A[i..n] that MUST include A[i]
	//          where loos of X[1..n] satisfies the reversed condition of los of X
	// memoization structure: two 1d arrs, los[1..n], loos[1..n]
	val los = OneArray(n) { 1 }
	val loos = OneArray(n) { 1 }

	// assuming max { } = 0
	// los(i) = 1 + max_k { loos(k) } where k > i and A[k] < A[i]
	// loos(i) = 1 + max_k { los(k) } where k > i and A[k] > A[i]
	// dependency: los(i), loos(i) depends on loos(k), los(k), where k > i
	//             that is, entries to the right
	// eval order: outer loop for i decreasing from n - 1 down to 1
	for (i in n - 1 downTo 1) {
		// inner loop k with no specific order, say increasing from i + 1 to n
		los[i] = 1 + ((i + 1..n)
				.filter { k -> A[k] < A[i] }
				.map { k -> loos[k] }
				.max() ?: 0)
		loos[i] = 1 + ((i + 1..n)
				.filter { k -> A[k] > A[i] }
				.map { k -> los[k] }
				.max() ?: 0)
	}
	// time: O(n^2)

//	los.prettyPrintln()
//	loos.prettyPrintln()

	// we want max_i { los(i) }
	return los.max() ?: 0
}