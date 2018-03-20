package util

import java.util.*
import kotlin.Comparator



// One-indexed Array
class OneArray<T>(val size: Int) {
	val indices = 1..size
	var getOverflowHandler: ((Int) -> T)? = null
	var setOverflowHandler: ((Int, T) -> Unit)? = null
	var container = arrayOfNulls<Any?>(size)

	constructor(array: Array<T>) : this(array.size) {
		container = Arrays.copyOf(array, size) as Array<Any?>
	}

	constructor(size: Int, init: (Int) -> T) : this(size) {
		(1..size).forEach {
			container[it - 1] = init(it)
		}
	}

	operator fun get(i: Int): T {
		if (i in indices) {
			return container[i - 1] as T
		}

		return getOverflowHandler?.invoke(i)
				?: throw ArrayIndexOutOfBoundsException()
	}

	operator fun set(i: Int, v: T) {
		if (i in indices) {
			container[i - 1] = v
		} else {
			setOverflowHandler?.invoke(i, v)
					?: throw ArrayIndexOutOfBoundsException()
		}
	}

	override fun toString() = Arrays.deepToString(container)

	fun toArray() = Arrays.copyOf(container, size)

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

	fun forEach(action: (T) -> Unit) {
		for (element in container) {
			action(element as T)
		}
	}

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

	fun isEmpty() = size <= 0

	fun isNotEmpty() = isEmpty().not()
}

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

operator fun String.times(n: Int) = repeat(n)

fun IntArray.toOneArray() = toTypedArray().toOneArray()

fun CharArray.toOneArray() = toTypedArray().toOneArray()

fun <T> Array<T>.toOneArray() = OneArray(this)

fun String.toCharOneArray() = toCharArray().toOneArray()

inline fun <reified T> Collection<T>.toOneArray() = toTypedArray().toOneArray()

inline fun <reified T> oneArrayOf(vararg ts: T) = ts.toList().toOneArray()
