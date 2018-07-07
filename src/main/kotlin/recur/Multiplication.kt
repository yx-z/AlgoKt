package recur

import math.time
import java.lang.Math.floor
import java.lang.Math.pow
import kotlin.math.ceil

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

// x * y, each of which are n digit numbers
fun karastuba(x: Double, y: Double, n: Int): Double {
	if (n == 1) {
		return (x * y)
	}

	val m = ceil(n / 2.0).toInt()

	val a = floor(x / pow(10.0, m.toDouble()))
	val b = x % pow(10.0, m.toDouble())
	val c = floor(y / pow(10.0, m.toDouble()))
	val d = y % pow(10.0, m.toDouble())

	val e = karastuba(a, c, m)
	val f = karastuba(b, d, m)
	val g = karastuba(a - b, c - d, m)

	return (pow(10.0, 2.0 * m) * e + pow(10.0, m.toDouble()) * (e + f - g) + f)
}


fun main(args: Array<String>) {
	val M1 = 1234
	val M2 = 4321

	val direct = time({ M1 * M2 } * 1000)
	val indirect = time({ mult(M1, M2) } * 1000)
	val karastuba = time({ karastuba(M1.toDouble(), M2.toDouble(), 5) * 1000 })

	println("direct method takes $direct ms")
	println("indirect method takes $indirect ms")
	println("karastuba's method takes $karastuba ms")
}

// invoke the method `max` times
operator fun <R> (() -> R).times(max: Int) = {
	for (i in 1..max) {
		invoke()
	}
}
