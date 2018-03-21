package util

import java.util.*
import kotlin.Comparator

/**
 * one-indexed fixed-length array implementation
 *
 * created since many algorithm questions use a one-indexed array system
 * as their naming convention
 */
class OneArray<T>(val size: Int) {
	/**
	 * range of valid indices
	 */
	val indices = 1..size
	/**
	 * underlying array for holding values
	 */
	var container = arrayOfNulls<Any?>(size)
	/**
	 * a function that will be called when the array is being accessed
	 * in an invalid index
	 *
	 * this function should be given an invalid array index as a parameter
	 * of type Int and return an instance of <T> class
	 */
	var getterIndexOutOfBoundHandler: ((Int) -> T)? = null
	/**
	 * a function that will be called when the array is being modified
	 * in an invalid index
	 *
	 * this function should be given an invalid array index as a paramter
	 * of type Int as well as the value that is trying to be set in the array
	 * and return Unit since setter method does not have a return value
	 */
	var setterIndexOutOfBoundHandler: ((Int, T) -> Unit)? = null

	// constructing
	constructor(array: Array<T>) : this(array.size) {
		container = Arrays.copyOf(array, size) as Array<Any?>
	}

	constructor(size: Int, init: (Int) -> T) : this(size) {
		(1..size).forEach {
			container[it - 1] = init(it)
		}
	}

	// accessing with ArrayIndexOutofBoundException handling
	operator fun get(i: Int): T {
		if (i in indices) {
			return container[i - 1] as T
		}

		return getterIndexOutOfBoundHandler?.invoke(i)
				?: throw ArrayIndexOutOfBoundsException()
	}

	operator fun set(i: Int, v: T) {
		if (i in indices) {
			container[i - 1] = v
		} else {
			setterIndexOutOfBoundHandler?.invoke(i, v)
					?: throw ArrayIndexOutOfBoundsException()
		}
	}

	fun toArray() = Arrays.copyOf(container, size)

	// printing, i.e. toString()
	override fun toString() = Arrays.deepToString(container)

	fun prettyPrintln(printIndex: Boolean = true) {
		if (!printIndex) {
			println(this)
			return
		}

		print(" ")
		container.indices
				.map { it + 1 }
				.forEach {
					print(it)
					print(" ")
					val lenIdx = it.toString().length
					val lenEle = container[it - 1].toString().length
					if (lenEle > lenIdx) {
						print(" " * (lenEle - lenIdx))
					}
				}
		println()

		print("[")
		container.indices
				.map { it + 1 }
				.forEach {
					print(container[it - 1])
					val lenIdx = it.toString().length
					val lenEle = container[it - 1].toString().length
					if (lenIdx > lenEle) {
						print(" " * (lenIdx - lenEle))
					}
					if (it == size) {
						println("]")
					} else {
						print(" ")
					}
				}
	}

	// sorting
	fun sort() {
		container.sort()
	}

	fun sortDescending() {
		sort()
		reverse()
	}

	fun <R : Comparable<R>> sortBy(selector: (T) -> R) {
		val com = Comparator { t1: T, t2: T -> selector(t1).compareTo(selector(t2)) }
		Arrays.sort(container as Array<T>, com)
	}

	fun <R : Comparable<R>> sortByDescending(selector: (T) -> R) {
		sortBy(selector)
		reverse()
	}

	fun sorted(): OneArray<T> {
		val ret = copy()
		ret.sort()
		return ret
	}

	fun sortedDescending(): OneArray<T> {
		val ret = sorted()
		ret.reverse()
		return ret
	}

	fun <R : Comparable<R>> sortedBy(selector: (T) -> R): OneArray<T> {
		val ret = copy()
		ret.sortBy(selector)
		return ret
	}

	fun <R : Comparable<R>> sortedByDescending(selector: (T) -> R): OneArray<T> {
		val ret = sortedBy(selector)
		ret.reverse()
		return ret
	}

	// modifying
	fun copy(): OneArray<T> {
		val ret = OneArray<T>(size)
		ret.container = toArray()
		return ret
	}

	fun reverse() {
		container.reverse()
	}

	fun reversed(): OneArray<T> {
		val ret = copy()
		ret.reverse()
		return ret
	}

	fun asSequence() = (container as Array<T>).asSequence()

	// sizing
	fun isEmpty() = size <= 0

	fun isNotEmpty() = isEmpty().not()
}

// comparing elements in OneArray
fun <T : Comparable<T>> OneArray<T>.max(): T? {
	if (isEmpty()) {
		return null
	}
	var max = this[1]
	for (i in 2..size) {
		val e = this[i]
		if (max < e) {
			max = e
		}
	}
	return max
}

fun <T : Comparable<T>> OneArray<T>.min(): T? {
	if (isEmpty()) {
		return null
	}
	var min = this[1]
	for (i in 2..size) {
		val e = this[i]
		if (min > e) {
			min = e
		}
	}
	return min
}

// pretty-printing as table(s)
fun <T> OneArray<OneArray<T>>.prettyPrintTable(printIndex: Boolean = true) {
	var maxLenEle = 0
	var maxLenCol = 0
	for (row in 1..size) {
		maxLenCol = max(maxLenCol, this[row].size)
		for (col in 1..this[row].size) {
			maxLenEle = max(maxLenEle, this[row, col].toString().length)
		}
	}
	maxLenEle = max(maxLenEle, maxLenCol.toString().length)

	if (printIndex) {
		print(" " * (size.toString().length + 2))
		for (col in 1..maxLenCol) {
			print(col)
			print(" " * (maxLenEle - col.toString().length + 1))
		}
		println()
	}
	for (row in 1..size) {
		if (printIndex) {
			print(" " * (size.toString().length - row.toString().length))
			print(row)
			print(" ")
		}
		print("[")
		for (col in 1..this[row].size) {
			print(this[row, col])
			print(" " * (maxLenEle - this[row, col].toString().length))
			if (col == this[row].size) {
				println("]")
			} else {
				print(" ")
			}
		}
	}
}

fun <T> OneArray<OneArray<OneArray<T>>>.prettyPrintTables(printIndex: Boolean = true) {
	for (i in 1..this.size) {
		if (printIndex) {
			println("#$i")
		}
		this[i].prettyPrintTable(printIndex)
		println()
	}
}

// converting other general arrays to OneArray
fun IntArray.toOneArray() = toTypedArray().toOneArray()

fun CharArray.toOneArray() = toTypedArray().toOneArray()

fun <T> Array<T>.toOneArray() = OneArray(this)

fun String.toCharOneArray() = toCharArray().toOneArray()

inline fun <reified T> Collection<T>.toOneArray() = toTypedArray().toOneArray()

inline fun <reified T> oneArrayOf(vararg ts: T) = ts.toList().toOneArray()

// numerical operating with broadcasting
operator fun OneArray<Int>.plus(other: OneArray<Int>): OneArray<Int> {
	if (size != other.size) {
		throw TwoArrayNotAlignedException()
	}

	return indices.map { this[it] + other[it] }.toOneArray()
}

operator fun OneArray<Int>.minus(other: OneArray<Int>): OneArray<Int> {
	if (size != other.size) {
		throw TwoArrayNotAlignedException()
	}

	return indices.map { this[it] - other[it] }.toOneArray()
}

operator fun OneArray<Int>.times(other: OneArray<Int>): OneArray<Int> {
	if (size != other.size) {
		throw TwoArrayNotAlignedException()
	}

	return indices.map { this[it] * other[it] }.toOneArray()
}

operator fun OneArray<Int>.div(other: OneArray<Int>): OneArray<Int> {
	if (size != other.size) {
		throw TwoArrayNotAlignedException()
	}

	return indices.map { this[it] / other[it] }.toOneArray()
}

operator fun OneArray<Int>.rem(other: OneArray<Int>): OneArray<Int> {
	if (size != other.size) {
		throw TwoArrayNotAlignedException()
	}

	return indices.map { this[it] % other[it] }.toOneArray()
}

operator fun OneArray<Int>.plus(num: Int) = asSequence().map { it + num }.toList().toOneArray()

operator fun OneArray<Double>.plus(num: Double) = asSequence().map { it + num }.toList().toOneArray()

operator fun OneArray<Int>.minus(num: Int) = asSequence().map { it - num }.toList().toOneArray()

operator fun OneArray<Double>.minus(num: Double) = asSequence().map { it - num }.toList().toOneArray()

operator fun OneArray<Int>.times(mult: Int) = asSequence().map { it * mult }.toList().toOneArray()

operator fun OneArray<Double>.times(num: Double) = asSequence().map { it * num }.toList().toOneArray()

operator fun OneArray<Int>.div(num: Int) = asSequence().map { it / num }.toList().toOneArray()

operator fun OneArray<Double>.div(num: Double) = asSequence().map { it / num }.toList().toOneArray()

operator fun OneArray<Int>.rem(num: Int) = asSequence().map { it % num }.toList().toOneArray()

operator fun OneArray<Double>.rem(num: Double) = asSequence().map { it % num }.toList().toOneArray()

operator fun OneArray<Int>.inc() = asSequence().map { it + 1 }.toList().toOneArray()

operator fun OneArray<Int>.dec() = asSequence().map { it - 1 }.toList().toOneArray()

// python-like vararg indexing for multi-dimensional arrays
operator fun <T> OneArray<OneArray<T>>.get(i1: Int, i2: Int) = this[i1][i2]

operator fun <T> OneArray<OneArray<OneArray<T>>>.get(i1: Int, i2: Int, i3: Int) = this[i1][i2][i3]

operator fun <T> OneArray<OneArray<OneArray<OneArray<T>>>>.get(i1: Int, i2: Int, i3: Int, i4: Int) = this[i1][i2][i3][i4]

operator fun <T> OneArray<OneArray<T>>.set(i1: Int, i2: Int, v: T) {
	this[i1][i2] = v
}

operator fun <T> OneArray<OneArray<OneArray<T>>>.set(i1: Int, i2: Int, i3: Int, v: T) {
	this[i1][i2][i3] = v
}

operator fun <T> OneArray<OneArray<OneArray<OneArray<T>>>>.set(i1: Int, i2: Int, i3: Int, i4: Int, v: T) {
	this[i1][i2][i3][i4] = v
}

/**
 * Exception class that handles size mismatch while array broadcasting
 */
class TwoArrayNotAlignedException : Exception("size mismatches between two arrays")