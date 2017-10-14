package arr.sort

fun IntArray.selectionSort() {
	indices.forEach {
		withIndex()
				.filterIndexed { idx, _ -> idx > it }
				.forEach { (idx, i) ->
					if (this[it] > i) {
						swap(it, idx)
					}
				}
	}
}

fun main(args: Array<String>) {
	println(testCorrectness { selectionSort() })
}