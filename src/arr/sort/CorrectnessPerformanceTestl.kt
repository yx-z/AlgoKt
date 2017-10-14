package arr.sort

import java.io.File
import java.lang.reflect.Method
import java.util.*

/**
 * a collection of sorting algorithms
 * how fun!
 */

fun main(args: Array<String>) {
	// test a longer array
	val arr = randomArray(length = 70000)

	File("./src/arr/sort").listFiles()
			.filter { it.name != Thread.currentThread().stackTrace[1].fileName }
			.forEach { it.name.removeExtension().testPerformance(arr) }
}

fun String.testPerformance(arr: IntArray) {
	arr.copyOf().run {
		val sortName = this@testPerformance.padSpaces()

		val cls: Class<*>
		try {
			cls = Class.forName("arr.sort.${this@testPerformance.toKtClass()}")
		} catch (e: ClassNotFoundException) {
			return
		}

		sortName.printAsTitle()

		val methodName = sortName.toLowerCamelCase()
		val method: Method
		try {
			method = cls.getMethod(methodName, IntArray::class.java)
		} catch (e: NoSuchMethodException) {
			return
		}

		{ method.invoke(null, this) }.printTime()

		println()
	}
}

fun String.padSpaces(): String {
	val sb = StringBuilder()
	sb.append(this[0])
	asSequence().withIndex()
			.filterIndexed { i, _ -> i > 0 }
			.forEach {
				it.value.let {
					when {
						it.isUpperCase() -> sb.append(" ").append(it)
						else -> sb.append(it)
					}
				}
			}
	return sb.toString()
}

fun String.removeExtension() = this.substring(0, this.lastIndexOf('.'))

fun String.toKtClass() = this + "Kt"

fun String.toLowerCamelCase() = this
		.splitToSequence(" ")
		.map { it.toLowerCase().capitalize() }
		.joinToString("")
		.decapitalize()

fun String.printAsTitle(length: Int = 5) = println("${"=" * length} $this ${"=" * length}")

operator fun String.times(n: Int) = repeat(n)

fun IntArray.printArray() = println(Arrays.toString(this))

fun IntRange.random() = (Math.random() * (endInclusive - start) + start).toInt()

fun <T> (() -> T).printTime(digits: Int = 3) = println("Time taken: ${time().toString(digits)} s")

/**
 * time a method
 *
 * the method may return anything you like, i just don't care
 * also, I don't know parameters for your function at all
 * so throw me a method without any input needed from me
 */
fun <T> (() -> T).time(): Double {
	val start = System.currentTimeMillis()
	this()
	val duration = System.currentTimeMillis() - start
	return duration / 1000.0
}

fun Double.toString(digits: Int) = String.format("%.${digits}f", this)

fun IntArray.swap(i1: Int, i2: Int) {
	val tmp = this[i1]
	this[i1] = this[i2]
	this[i2] = tmp
}

fun randomArray(min: Int = 0, max: Int = 100, length: Int = 10) = IntArray(length) { (min..max).random() }

/**
 * sort an array with smaller size and print out results
 */
fun testCorrectness(min: Int = 0, max: Int = 100, length: Int = 10, sort: IntArray.() -> Unit): Boolean {
	val arr = randomArray(min, max, length)
	arr.printArray()

	val spaces = " " * (arr.size * 2 - 1)
	println("$spaces|")
	println("${spaces}v")

	arr.copyOf().run {
		sort()
		printArray()
		return isNonDecreasing() && isAnagram(arr)
	}
}

fun IntArray.isNonDecreasing() = withIndex()
		.filterIndexed { idx, _ -> idx > 0 }
		.all { (idx, i) -> i >= this[idx - 1] }

fun IntArray.isAnagram(ori: IntArray) = this.all { ori.contains(it) } &&
		ori.all { this.contains(it) } &&
		this.size == ori.size