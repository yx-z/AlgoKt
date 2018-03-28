package recur

import math.time
import java.lang.Math.floor

// divide and conquer multiplication
fun mult(x: Int, y: Int): Long {
	if (x == 0) {
		return 0L
	}

	val halfX = floor(x / 2.0).toInt()
	val doubleY = y + y
	var prod = mult(halfX, doubleY)
	if (x % 2 != 0) {
		prod += y
	}
	return prod
}

fun main(args: Array<String>) {
	val M1 = 12345
	val M2 = 54321

	val direct = time({ M1 * M2 } * 100)
	val indirect = time({ mult(M1, M2) } * 100)

	println("direct method takes $direct ms")
	println("indirect method takes $indirect ms")
}

// invoke the method `max` times
operator fun <R> (() -> R).times(max: Int) = {
	for (i in 1..max) {
		invoke()
	}
}
