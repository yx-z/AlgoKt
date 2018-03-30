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
	var container = arrayOfNulls<Any?>(size) as Array<T>
	/**
	 * a function that will be called when the array is being accessed
	 * in an invalid index
	 *
	 * this function should be given an invalid array index as a parameter
	 * of type Int and return an instance of <T> class
	 */
	// one-indexed
	var getterIndexOutOfBoundsHandler: ((Int) -> T)? = null
	/**
	 * a function that will be called when the array is being modified
	 * in an invalid index
	 *
	 * this function should be given an invalid array index as a paramter
	 * of type Int as well as the value that is trying to be set in the array
	 * and return Unit since setter method does not have a return value
	 */
	// one-indexed
	var setterIndexOutOfBoundsHandler: ((Int, T) -> Unit)? = null

	/**
	 * constructor that constructs from a given array
	 * @param array array to be copied from, i.e. will not be used directly
	 */
	constructor(array: Array<T>) : this(array.size) {
		container = Arrays.copyOf(array, size) as Array<T>
	}

	/**
	 * constructor that accepts an initialization method transforming index into <T>
	 */
	constructor(size: Int, init: (Int) -> T) : this(size) {
		set(init)
	}

	/**
	 * constructor that is able to initialize all public properties
	 */
	constructor(size: Int,
	            set: ((Int, T) -> Unit)? = null, // setHandler as the 2nd parameter avoids duplicate signature with this(size, init)
	            get: ((Int) -> T)? = null, // since we see that getHandler has the same method signature as init
	            init: ((Int) -> T)? = null) : this(size) {
		if (init != null) {
			set(init)
		}
		getterIndexOutOfBoundsHandler = get
		setterIndexOutOfBoundsHandler = set
	}

	// accessing with ArrayIndexOutofBoundException handling
	operator fun get(i: Int) = when {
		i in indices -> container[i - 1]
		getterIndexOutOfBoundsHandler != null -> getterIndexOutOfBoundsHandler!!(i)
		else -> throw ArrayIndexOutOfBoundsException()
	}

	operator fun set(i: Int, v: T) = when {
		i in indices -> container[i - 1] = v
		setterIndexOutOfBoundsHandler != null -> setterIndexOutOfBoundsHandler!!(i, v)
		else -> throw ArrayIndexOutOfBoundsException()
	}

	// functional setter
	fun set(func: (Int) -> T) {
		indices.forEach { this[it] = func(it) }
	}

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
		Arrays.sort(container, com)
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

	// note that the following two data strucutures are ZERO-indexed
	fun toArray() = Arrays.copyOf(container, size)

	fun toList() = toArray().toList()

	// sizing
	fun isEmpty() = size <= 0

	fun isNotEmpty() = isEmpty().not()

	// sequencing
	// note that this sequence is ZERO-indexed
	fun asSequence() = container.asSequence()

	inline fun forEach(action: (T) -> Unit) = container.forEach(action)

	// one-indexed
	inline fun forEachIndexed(action: (Int, T) -> Unit) = container.forEachIndexed { i, t -> action(i + 1, t) }

	inline fun <R> map(transform: (T) -> R) = container.map(transform)

	// one-indexed
	inline fun <R> mapIndexed(transform: (Int, T) -> R) = container.mapIndexed { i, t -> transform(i + 1, t) }

	inline fun filter(predicate: (T) -> Boolean) = container.filter(predicate)

	// one-indexed
	inline fun filterIndexed(predicate: (Int, T) -> Boolean) = container.filterIndexed { i, t -> predicate(i + 1, t) }

	operator fun contains(element: T): Boolean = indexOf(element) >= 0

	// one-indexed
	fun indexOf(element: T): Int =
			indices.filter { this[it] == element }.firstOrNull() ?: -1

	// searching
	fun last(predicate: (T) -> Boolean = { true }) = container.last(predicate)

	fun first(predicate: (T) -> Boolean = { true }) = container.first(predicate)
}

inline infix fun <reified T> OneArray<T>.append(that: OneArray<T>) =
		listOf(this.toList(), that.toList()).flatten().toOneArray()

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
// i.e. entry-wise operations
// ex. [10, 20, 30, 40] + [1, 2] = [10 + 1, 20 + 2, 30 + 1, 40 + 2]
operator fun OneArray<Int>.plus(that: OneArray<Int>) = indices.map { this[it] + that[it % that.size + 1] }.toOneArray()

operator fun OneArray<Int>.minus(that: OneArray<Int>) = indices.map { this[it] - that[it % that.size + 1] }.toOneArray()

operator fun OneArray<Int>.times(that: OneArray<Int>) = indices.map { this[it] * that[it % that.size + 1] }.toOneArray()

operator fun OneArray<Int>.div(that: OneArray<Int>) = indices.map { this[it] / that[it % that.size + 1] }.toOneArray()

operator fun OneArray<Int>.rem(that: OneArray<Int>) = indices.map { this[it] % that[it % that.size + 1] }.toOneArray()

operator fun OneArray<Int>.plus(num: Int) = map { it + num }.toList().toOneArray()

operator fun OneArray<Double>.plus(num: Double) = map { it + num }.toList().toOneArray()

operator fun OneArray<Int>.minus(num: Int) = map { it - num }.toList().toOneArray()

operator fun OneArray<Double>.minus(num: Double) = map { it - num }.toList().toOneArray()

operator fun OneArray<Int>.times(mult: Int) = map { it * mult }.toList().toOneArray()

operator fun OneArray<Double>.times(num: Double) = map { it * num }.toList().toOneArray()

operator fun OneArray<Int>.div(num: Int) = map { it / num }.toList().toOneArray()

operator fun OneArray<Double>.div(num: Double) = map { it / num }.toList().toOneArray()

operator fun OneArray<Int>.rem(num: Int) = map { it % num }.toList().toOneArray()

operator fun OneArray<Double>.rem(num: Double) = map { it % num }.toList().toOneArray()

operator fun OneArray<Int>.inc() = map { it + 1 }.toList().toOneArray()

operator fun OneArray<Int>.dec() = map { it - 1 }.toList().toOneArray()

// some default handlers
val INT_DEFAULT_GETTER_INDEX_OUT_OF_BOUNDS_HANDLER = { 0 }

val BOOLEAN_DEFAULT_GETTER_INDEX_OUT_OF_BOUNDS_HANDLER = { false }

val CHAR_DEFAULT_GETTER_INDEX_OUT_OF_BOUNDS_HANDLER = { 0 }

val STRING_DEFAULT_GETTER_INDEX_OUT_OF_BOUNDS_HANDLER = { "" }

val DEFAULT_STTER_INDEX_OUT_OF_BOUNDS_HANDLER = { }

// python-like indexing for multi-dimensional arrays
operator fun <T> OneArray<OneArray<T>>.get(i1: Int,
                                           i2: Int,
                                           handler: ((Int, Int) -> T)? = null) =
		if (handler != null && (i1 !in indices ||
						i2 !in this[i1].indices)) {
			handler(i1, i2)
		} else {
			this[i1][i2] // may throw ArrayIndexOutOfBoundsException
		}

operator fun <T> OneArray<OneArray<OneArray<T>>>.get(i1: Int,
                                                     i2: Int,
                                                     i3: Int,
                                                     handler: ((Int, Int, Int) -> T)? = null) =
		if (handler != null && (i1 !in indices ||
						i2 !in this[i1].indices ||
						i3 !in this[i1][i2].indices)) {
			handler(i1, i2, i3)
		} else {
			this[i1][i2][i3]
		}

operator fun <T> OneArray<OneArray<OneArray<OneArray<T>>>>.get(i1: Int,
                                                               i2: Int,
                                                               i3: Int,
                                                               i4: Int,
                                                               handler: ((Int, Int, Int, Int) -> T)? = null) =
		if (handler != null && (i1 !in indices ||
						i2 !in this[i1].indices ||
						i3 !in this[i1][i2].indices ||
						i4 !in this[i1][i2][i3].indices)) {
			handler(i1, i2, i3, i4)
		} else {
			this[i1][i2][i3][i4]
		}

operator fun <T> OneArray<OneArray<OneArray<OneArray<OneArray<T>>>>>.get(i1: Int,
                                                                         i2: Int,
                                                                         i3: Int,
                                                                         i4: Int,
                                                                         i5: Int,
                                                                         handler: ((Int, Int, Int, Int, Int) -> T)? = null) =
		if (handler != null && (i1 !in indices ||
						i2 !in this[i1].indices ||
						i3 !in this[i1][i2].indices ||
						i4 !in this[i1][i2][i3].indices ||
						i5 !in this[i1][i2][i3][i4].indices)) {
			handler(i1, i2, i3, i4, i5)
		} else {
			this[i1][i2][i3][i4][i5]
		}

operator fun <T> OneArray<OneArray<T>>.set(i1: Int, i2: Int, v: T) {
	this[i1][i2] = v
}

operator fun <T> OneArray<OneArray<OneArray<T>>>.set(i1: Int, i2: Int, i3: Int, v: T) {
	this[i1][i2][i3] = v
}

operator fun <T> OneArray<OneArray<OneArray<OneArray<T>>>>.set(i1: Int, i2: Int, i3: Int, i4: Int, v: T) {
	this[i1][i2][i3][i4] = v
}

operator fun <T> OneArray<OneArray<OneArray<OneArray<OneArray<T>>>>>.set(i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, v: T) {
	this[i1][i2][i3][i4][i5] = v
}
