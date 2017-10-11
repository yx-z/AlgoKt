package arr

fun main(args: Array<String>) {
	val arr = arrayOf(1, 2, 1, 2, 1, 3)

	// 1 + 2 + 1 = 4
	// 2 + 1 + 3 = 6
	// so we should have 2 in order to let the sum of the first half equal to that of the last half
	println(balanceArr(arr))
}

// arr.size is even
// return the min non-negative number that can be added to either half of the array that balances it
fun balanceArr(arr: Array<Int>): Int {
	val sumLeft = arr.slice(0..arr.size / 2).sum()
	val sumRight = arr.slice(arr.size / 2 + 1 until arr.size).sum()
	return Math.abs(sumRight - sumLeft)
}