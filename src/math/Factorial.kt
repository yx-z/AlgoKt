package math

fun main(args: Array<String>) {
	println(5.factorialRec())
	println(5.factorialTailRec())
	println(5.factorialDP())
}

fun Int.factorialRec(): Int {
	if (this <= 1) {
		return 1
	}

	return this * (this - 1).factorialRec()
}

tailrec fun Int.factorialTailRec(ans: Int = 1): Int = if (this <= 1) {
	ans
} else {
	(this - 1).factorialTailRec(ans * this)
}

fun Int.factorialDP(): Int {
	val dp = IntArray(this + 1)
	dp[0] = 1
	(1..this).forEach {
		dp[it] = dp[it - 1] * it
	}
	return dp[this]
}