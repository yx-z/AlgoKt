import java.lang.NullPointerException
import java.util.*

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

// pretty print for arrays
// ex. arr.prettyPrintln()
fun IntArray.prettyPrintln(printIdx: Boolean = false) {
	if (printIdx) {
		print(" ")
		indices.forEach { print(" $it") }
	}
	System.out.println()
	println(Arrays.toString(this))
}

fun <T> Array<T>.prettyPrintln(printIdx: Boolean = false) {
	if (printIdx) {
		print(" ")
		indices.forEach { print(" $it") }
		System.out.println()
	}
	println(Arrays.toString(this))
}

fun <T> Array<Array<T>>.prettyPrintln(printIdx: Boolean = false) {
	if (printIdx) {
		print("   ")
		val maxCol = map { it.size }.max() ?: 0
		(0 until maxCol).forEach {
			print(" $it ")
		}
		System.out.println()
		indices.forEach {
			print("$it ")
			if (it < 10) {
				print(" ")
			}
			print(Arrays.toString(this[it]))
			System.out.println()
		}
	} else {
		forEach { it.prettyPrintln(false) }
	}
}

fun Array<IntArray>.prettyPrintln(printIdx: Boolean = false) {
	if (printIdx) {
		print("   ")
		val maxCol = map { it.size }.max() ?: 0
		(0 until maxCol).forEach {
			print(" $it ")
		}
		System.out.println()
		indices.forEach {
			print("$it ")
			if (it < 10) {
				print(" ")
			}
			print(Arrays.toString(this[it]))
			System.out.println()
		}
	} else {
		forEach { it.prettyPrintln(false) }
	}
}

// one-indexed Array
class OneArray<T>(val size: Int) {
	private var container = arrayOfNulls<Any?>(size)

	operator fun get(i: Int) = container[i - 1] as T

	operator fun set(i: Int, v: T) {
		container[i - 1] = v
	}

	override fun toString() = Arrays.toString(container)

	fun prettyPrintln(printIdx: Boolean = false) {
		if (printIdx) {
			container.indices.forEach { print(" ${it + 1} ") }
			System.out.println()
		}
		println(this)
	}

	constructor(intArray: IntArray) : this(intArray.size) {
		container = intArray.toTypedArray() as Array<Any?>
	}

	constructor(array: Array<T>) : this(array.size) {
		container = array as Array<Any?>
	}

	fun toArray() = container
}

fun IntArray.toOneArray() = OneArray<Int>(this)

fun <T> Array<T>.toOneArray() = OneArray(this)
