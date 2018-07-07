package dp

import java.util.*

// shortest oscillating supersequence

// find the definition of an oscillating sequence in 'LOS'
// find the definition of a supersequence in 'SCS'

// find sos of A[1..n] here

fun main(args: Array<String>) {
	val A = intArrayOf(3, 10, 15, 9, 29)
	println(sos(A))
}

fun sos(A: IntArray): Int {
	val n = A.size
	// sos(i): len of sos for A[i..n]
	// so's(i): len of so's for A[i..n]
	// where so's is the shortest oscillating' supersequence
	// also find the definition of oscillating' sequence in 'LOS'
	// sos(i), so's(i) = 0 if i > n
	// sos(i), so's(i) = 1 if i = n
	// sos(i) = 2 + sos(i + 1) if A[i] < A[i + 1]
	// so's(i) = 1 + sos(i + 1) if A[i] < A[i + 1]
	// sos(i) = 1 + so's(i + 1) o/w
	// so's(i) = 2 + so's(i + 1) o/w
	val sos = IntArray(n)
	val soos = IntArray(n)
	sos[n - 1] = 1
	soos[n - 1] = 1

	for (i in n - 2 downTo 0) {
		sos[i] = if (A[i] < A[i + 1]) {
			2 + sos[i + 1]
		} else {
			1 + soos[i + 1]
		}

		soos[i] = if (A[i] < A[i + 1]) {
			1 + sos[i + 1]
		} else {
			2 + soos[i + 1]
		}
	}

	println(Arrays.toString(sos))
	println(Arrays.toString(soos))

	return sos[0]
}
