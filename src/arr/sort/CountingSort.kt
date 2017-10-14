package arr.sort

fun IntArray.countingSort() {
	val sorted = IntArray(size)
	val count = IntArray(max()!! + 1)
	forEach { count[it]++ }

	count.indices
			.filter { it > 0 }
			.forEach { count[it] += count[it - 1] }

	indices.reversed().forEach {
		count[this[it]]--
		sorted[count[this[it]]] = this[it]
	}

	System.arraycopy(sorted, 0, this, 0, size)
}

fun main(args: Array<String>) {
	println(testCorrectness { countingSort() })
}