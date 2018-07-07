import kotlin.math.max

fun main(args: Array<String>) {
	val arr = intArrayOf(3, 1, 4, 1, 5, 9) // PI!
	val las = max(arr.las(Int.MIN_VALUE, 0, '+'), arr.las(Int.MAX_VALUE, 0, '-'))
	println(las)
}

// Longest Alternating Subsequence
// see dp.LAS for a faster DP solution
fun IntArray.las(last: Int, curr: Int, sign: Char): Int = when {
	curr >= size -> 0
	sign == '+' && this[curr] > last -> {
		max(1 + las(this[curr], curr + 1, '-'), las(last, curr + 1, '+'))
	}
	sign == '-' && this[curr] < last -> {
		max(1 + las(this[curr], curr + 1, '+'), las(last, curr + 1, '-'))
	}
	else -> las(last, curr + 1, sign)
}
