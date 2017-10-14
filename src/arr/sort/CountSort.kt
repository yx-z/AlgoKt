package arr.sort

fun IntArray.countSort(max: Int = 100) {
	val ans = IntArray(size)
	val count = IntArray(max + 1)
	forEach { count[it]++ }

	count.indices
			.filter { it > 0 }
			.forEach { count[it] += count[it - 1] }

	indices.reversed().forEach {
		count[this[it]]--
		ans[count[this[it]]] = this[it]
	}

	System.arraycopy(ans, 0, this, 0, size)
}

fun main(args: Array<String>) {
	val m = 100
	println(testCorrectness(min = 0, max = m) { countSort(m) })
}