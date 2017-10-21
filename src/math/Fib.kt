package math

fun main(args: Array<String>) {
	println(6.thFibRec())
	println(6.thFibTailRec())
	println(6.thFibIter())
	println(6.thFibDP())
}

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