package dp

// shortest oscillating supersequence

// find the definition of an oscillating sequence in 'LOS'
// find the definition of a supersequence in 'SCS'

// find sos of A[1..n] here

fun main(args: Array<String>) {
}

fun sos(A: IntArray): Int {
	// sos(i) : len of sos for A[i..n]
	// so's(i) : len os so's for A[i..n]
	// where so's is the shortest oscillating' supersequence
	// also find the definition of oscillating' sequence in 'LOS'
	// sos(i), so's(i) = 0 if i > n
	// sos(i), so's(i) = 1 if i == n
	// sos(i) = min{1 + so's(i), } if A[i] < A[i + 1]
	// so's(i) = min{1 + so's(i), 2 + sos(i)} if A[i] < A[i + 1]
	TODO()
}
