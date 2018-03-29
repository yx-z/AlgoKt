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
fun karastuba(x: Int, y: Int, n: Int): Long {
	if (n == 1) {
		return (x * y).toLong()
	}

	val m = ceil(n / 2.0)
	val p = pow(10.0, m)
	val a = floor(x / p)
	val b = x % p
	val c = floor(y / p)
	val d = y % p
	val e = karastuba(a.toInt(), c.toInt(), m.toInt())
	val f = karastuba(b.toInt(), d.toInt(), m.toInt())
	val g = karastuba(b.toInt(), c.toInt(), m.toInt())
	val h = karastuba(a.toInt(), d.toInt(), m.toInt())
	return (p * p * e + p * (g + h) + f).toLong()
}

fun main(args: Array<String>) {
	val M1 = 12345
	val M2 = 54321

	val direct = time({ M1 * M2 } * 100)
	val indirect = time({ mult(M1, M2) } * 100)
	val karastuba = time({ karastuba(M1, M2, 5) * 100 })

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
