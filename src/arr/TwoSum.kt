package arr

fun main(args: Array<String>) {
	val testArr = intArrayOf(1, 2, 5, 4, 10, 8)
	println(testArr.findPair(9))
}

fun IntArray.findPair(target: Int): Pair<Int, Int> {
	val set = HashSet<Int>()
	forEach {
		if (set.contains(target - it)) {
			return it to target - it
		} else {
			set.add(it)
		}
	}
	return -1 to -1
}
