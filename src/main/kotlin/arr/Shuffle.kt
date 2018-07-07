package arr

import util.swap
import java.util.*

fun main(args: Array<String>) {
	val testArr = intArrayOf(1, 2, 3, 4, 5)
	testArr.shuffle()
	println(Arrays.toString(testArr))
}

fun IntArray.shuffle() {
	val rand = Random()
	(size - 1 downTo 1).forEach {
		swap(rand.nextInt(it), it)
	}
}
