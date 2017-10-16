package arr

fun main(args: Array<String>) {
	// miss 2
	val testArr = intArrayOf(4, 1, 0, 3, 5, 6)

	println(testArr.findMissingBySum())
	println(testArr.findMissingByXor())
}

fun IntArray.findMissingBySum() = size * (size + 1) / 2 - sum()

fun IntArray.findMissingByXor() = (0 until size).fold(0) { acc, i -> acc xor this[i] } xor (0..size).reduce { acc, i -> acc xor i }
