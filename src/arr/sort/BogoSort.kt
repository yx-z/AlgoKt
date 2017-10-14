package arr.sort

import java.util.*

fun IntArray.bogoSort() {
	while (!this.isNonDecreasing()) {
		this.shuffle()
	}
}

fun IntArray.shuffle() {
	var randIdx: Int
	val rand = Random()
	for (i in (this.size - 1) downTo 1) {
		randIdx = rand.nextInt(i + 1)
		if (i != randIdx) {
			swap(i, randIdx)
		}
	}
}

fun main(args: Array<String>) {
	println(testCorrectness { bogoSort() })
}