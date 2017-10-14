package arr.sort

fun IntArray.bubbleSort() {
	for (i in 0 until this.size) {
		for (j in 1 until this.size - i) {
			if (this[j - 1] > this[j]) {
				swap(j - 1, j)
			}
		}
	}
}

fun main(args: Array<String>) {
	println(testCorrectness { bubbleSort() })
}