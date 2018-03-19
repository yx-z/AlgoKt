import java.lang.NullPointerException
import java.util.*
import kotlin.Comparator

// multi-parameter version of min/max
// ex. min(i1, i2, i3, i4), max(i1, i2, i3, i4, i5)
fun min(vararg ints: Int) = ints.min()
		?: throw NullPointerException("no minimum value")

fun max(vararg ints: Int) = ints.max()
		?: throw NullPointerException("no maximum value")

// substring and subarray slicing
// ex. "abcde"[1..2] -> "bc", intArrayOf(1, 5, 6, 10)[0..0] -> intArrayOf(1)
operator fun String.get(range: IntRange) = substring(range)

inline operator fun <reified T> Array<T>.get(range: IntRange): Array<T?> =
		Arrays.copyOfRange(this, range.first, range.last + 1) ?: arrayOfNulls(0)

operator fun IntArray.get(range: IntRange) =
		Arrays.copyOfRange(this, range.first, range.last + 1) ?: intArrayOf()

operator fun <T> OneArray<T>.get(range: IntRange): OneArray<T> {
	val start = range.start
	val end = range.endInclusive
	val len = end - start + 1
	val arr = OneArray<T>(len)
	var arrIdx = 1
	for (i in start..end) {
		arr[arrIdx] = this[i]
		arrIdx++
	}
	return arr
}

// python like multidimensional array indexing
// ex. arr[1, 2, 3, 4], arr[2, 5, 3] = 2
operator fun Array<IntArray>.get(i1: Int, i2: Int) = this[i1][i2]

operator fun <T> Array<Array<T>>.get(i1: Int, i2: Int) = this[i1][i2]

operator fun Array<Array<IntArray>>.get(i1: Int, i2: Int, i3: Int) = this[i1][i2][i3]

operator fun <T> Array<Array<Array<T>>>.get(i1: Int, i2: Int, i3: Int) = this[i1][i2][i3]

operator fun Array<Array<Array<IntArray>>>.get(i1: Int, i2: Int, i3: Int, i4: Int) = this[i1][i2][i3][i4]

operator fun <T> Array<Array<Array<Array<T>>>>.get(i1: Int, i2: Int, i3: Int, i4: Int) = this[i1][i2][i3][i4]

operator fun Array<IntArray>.set(i1: Int, i2: Int, v: Int) {
	this[i1][i2] = v
}

operator fun <T> Array<Array<T>>.set(i1: Int, i2: Int, v: T) {
	this[i1][i2] = v
}

operator fun Array<Array<IntArray>>.set(i1: Int, i2: Int, i3: Int, v: Int) {
	this[i1][i2][i3] = v
}

operator fun <T> Array<Array<Array<T>>>.set(i1: Int, i2: Int, i3: Int, v: T) {
	this[i1][i2][i3] = v
}

operator fun Array<Array<Array<IntArray>>>.set(i1: Int, i2: Int, i3: Int, i4: Int, v: Int) {
	this[i1][i2][i3][i4] = v
}

operator fun <T> Array<Array<Array<Array<T>>>>.set(i1: Int, i2: Int, i3: Int, i4: Int, v: T) {
	this[i1][i2][i3][i4] = v
}

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

// One-indexed Array
class OneArray<T>(val size: Int) {
	val indices = 1..size
	var getOverflowHandler: ((Int) -> T)? = null
	var setOverflowHandler: ((Int, T) -> Unit)? = null
	private var container = arrayOfNulls<Any?>(size)

	constructor(array: Array<T>) : this(array.size) {
		container = array as Array<Any?>
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

	fun <R : Comparable<R>> sortBy(selector: (T) -> R) {
		val com = Comparator { t1: T, t2: T -> selector(t1).compareTo(selector(t2)) }
		Arrays.sort(container as Array<T>, com)
	}

	fun sorted(): OneArray<T> {
		val copy = copy()
		copy.sort()
		return copy as OneArray<T>
	}

	fun <R : Comparable<R>> sortedBy(selector: (T) -> R): OneArray<T> {
		val copy = copy()
		val com = Comparator { t1: T, t2: T -> selector(t1).compareTo(selector(t2)) }
		Arrays.sort(copy.container as Array<T>, com)
		return copy as OneArray<T>
	}

	fun copy() = toArray().toOneArray()
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

