package arr.sort

import java.io.File
import java.lang.reflect.Method
import java.util.*

/**
 * a collection of sorting algorithms
 * how fun!
 */

fun main(args: Array<String>) {
	// test array initialization
	val min = 0
	val max = 100
	val length = 10 // for correctness tes // 70000 // for performance test
	val arr = IntArray(length) { (min..max).random() }

	val testCorrectNess = true
	if (testCorrectNess) {
		"Shuffled".printAsTitle()
		arr.printArray()
		println()
	}


	File("./src/arr/sort")
			.listFiles()
			.filterNot { it.name == "Main.kt" }
			.forEach {
				it.name.removeExtension().runSort(arr, testCorrectNess)
			}
}

fun String.runSort(arr: IntArray, printArray: Boolean = true) {
	arr.copyOf().run {
		val sortName = this@runSort.padSpaces()

		val cls: Class<*>
		try {
			cls = Class.forName("arr.sort.${this@runSort.toKtClass()}")
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

		val sort = { method.invoke(null, this) }
		sort.printTime()

		if (printArray) {
			printArray()
		}
		println()
	}
}

fun String.padSpaces(): String {
	val sb = StringBuilder()
	sb.append(this[0])
	asSequence()
			.withIndex()
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