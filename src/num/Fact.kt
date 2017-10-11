package num

fun main(args: Array<String>) {
	println(17.fact())
	println(34.fact())
	println(120.fact())
}

fun Int.fact(): List<Int> {
	val list = ArrayList<Int>()
	var curr = this
	var div = 2
	while (curr > 1) {
		while (curr % div == 0) {
			// found divisor
			curr /= div
			list.add(div)
		}
		div++
	}
	return list
}