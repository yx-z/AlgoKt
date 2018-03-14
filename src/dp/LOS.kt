package dp

import java.util.*

// longest oscillating subsequence

// X[1..n] is oscillating if X[i] < X[i + 1] for even i, and X[i] > X[i + 1] for odd i

// find the length of los of A[1..n]
fun main(args: Array<String>) {
	val A = intArrayOf(-1 /* ignored value to be one-indexed */, 1, 25, 9, 20, 5)
	println(los(A))
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
	println(Arrays.toString(los))
	println(Arrays.toString(loos))
	return los[1]
}