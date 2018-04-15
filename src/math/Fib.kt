package math

import util.*
import java.lang.Math.pow
import kotlin.math.sqrt

// finding the i-th fibonacci number

fun main(args: Array<String>) {
//	println(6.thFibRec())
//	println(6.thFibTailRec())
//	println(6.thFibIter())
//	println(6.thFibDP())
	println(6.thFibMat())
	println(6.thFibDoubling())
	println(6.thFibFormula())
}

// recursion uses exponential runtime
fun Int.thFibRec(): Int {
	if (this <= 2) {
		return 1
	}

	return (this - 1).thFibRec() + (this - 2).thFibRec()
}

tailrec fun Int.thFibTailRec(a: Int = 1, b: Int = 0): Int = if (this == 0) {
	b
} else {
	(this - 1).thFibTailRec(a + b, a)
}

// DP uses O(n)
fun Int.thFibIter(): Int {
	if (this <= 2) {
		return 1
	}

	var a = 0
	var b = 1
	var c: Int
	(2..this).forEach {
		c = a + b
		a = b
		b = c
	}
	return b
}

fun Int.thFibDP(): Int {
	val dp = IntArray(this + 1)
	dp[1] = 1
	dp[2] = 1
	(3..this).forEach {
		dp[it] = dp[it - 1] + dp[it - 2]
	}
	return dp[this]
}


// w/ matrix power (pun intended), we can do O(log n)
// let f(n) = n-th fibonacci power, then
// [1 1] ^n = [f(n+1)   f(n)]
// [1 0]      [f(n)   f(n-1)]
// google: fibonacci-q matrix
val Q = oneArrayOf(
		oneArrayOf(1, 1),
		oneArrayOf(1, 0))

fun Int.thFibMat() = Q.pow(this)[1, 2]

// only works as Q^n, n >= 1, not a generalized matrix multiplication
private fun OneArray<OneArray<Int>>.pow(n: Int): OneArray<OneArray<Int>> = when (n) {
	1 -> this
	2 -> timesQ()
	else -> (timesQ()).pow(n - 1)
}

private fun OneArray<OneArray<Int>>.timesQ(): OneArray<OneArray<Int>> {
	val M = this
	val a = M[1, 1]
	val b = M[1, 2]
	val c = M[2, 1]
	val d = M[2, 2]
	return oneArrayOf(
			oneArrayOf(a + b, a),
			oneArrayOf(c + d, c))
}

// O(log n) w/ math tricks
// this can be proved by the fibonacci-q matrix above
fun Int.thFibDoubling(): Int {
	// f(2k) = f(k) * [2 * f(k+1) - f(k)]
	// f(2k+1) = f(k+1)^2 + f(k)^2
	return thFibDoublingRecur().first
}

// return the tuple (f(n), f(n+1))
private fun Int.thFibDoublingRecur(): Tuple2<Int, Int> = if (this == 0) {
	0 tu 1
} else {
	val (a, b) = (this / 2).thFibDoublingRecur()
	val c = a * (2 * b - a)
	val d = a * a + b * b

	if (this % 2 == 0) {// even number
		c tu d
	} else { // odd
		d tu c + d
	}
}

// hold on a sec., if more complex math is coming into this problem, i clearly
// remember that there is actually a formula!! we can do this in O(1) lol
// google: Binet's formula
val SQRT_5 = sqrt(5.0)
// two golden ratios
val PSI = (1 + SQRT_5) / 2
val PHI = (1 - SQRT_5) / 2
fun Int.thFibFormula() = ((pow(PSI, this.toDouble()) - pow(PHI, this.toDouble())) / SQRT_5).toInt()
