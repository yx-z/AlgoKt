package sort

fun IntArray.insertionSort() {
	withIndex()
			.filterIndexed { idx, _ -> idx > 0 }
			.forEach { (idx, key) ->
				var update: Int = idx - 1
				while (update >= 0 && key < this[update]) {
					this[update + 1] = this[update]
					update--
				}
				this[update + 1] = key
			}
}

fun main(args: Array<String>) {
	println(testCorrectness { insertionSort() })
}