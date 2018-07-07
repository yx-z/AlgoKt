package util

import java.util.*

// substring and subarray slicing
// ex. "abcde"[1..2] -> "bc", intArrayOf(1, 5, 6, 10)[0..0] -> intArrayOf(1)

inline operator fun <reified T> Array<T>.get(range: IntRange): Array<T> = Arrays.copyOfRange(this, range.first, range.last + 1)

operator fun IntArray.get(range: IntRange) = Arrays.copyOfRange(this, range.first, range.last + 1)

operator fun CharArray.get(range: IntRange) = Arrays.copyOfRange(this, range.first, range.last + 1)

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

operator fun <T> List<List<T>>.get(i1: Int, i2: Int) = this[i1][i2]

operator fun <T, R> Map<T, List<R>>.get(i1: T, i2: Int) = this[i1]?.get(i2)

operator fun <T, R, S> Map<T, Map<R, S>>.get(i1: T, i2: R) = this[i1]?.get(i2)

operator fun Array<Array<IntArray>>.get(i1: Int, i2: Int, i3: Int) = this[i1][i2][i3]

operator fun <T> Array<Array<Array<T>>>.get(i1: Int, i2: Int, i3: Int) = this[i1][i2][i3]

operator fun <T> List<MutableList<T>>.set(i1: Int, i2: Int, t: T) {
	this[i1][i2] = t
}

operator fun <T, R> Map<T, MutableList<R>>.set(i1: T, i2: Int, r: R) {
	this[i1]?.set(i2, r)
}

operator fun <T, R, S> Map<T, HashMap<R, S>>.set(i1: T, i2: R, s: S) {
	this[i1]?.set(i2, s)
}

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

// pretty-print
fun <T> Array<Array<T>>.prettyPrintLines() = forEach { println(Arrays.toString(it)) }

fun <T> Array<T>.prettyPrintln() = println(Arrays.toString(this))

fun IntArray.prettyPrintln() = println(Arrays.toString(this))

// swap
fun <T> OneArray<T>.swap(i1: Int, i2: Int) {
	if (i1 !in indices || i2 !in indices) {
		return
	}

	val tmp = this[i1]
	this[i1] = this[i2]
	this[i2] = tmp
}

fun <T> Array<T>.swap(i1: Int, i2: Int) {
	val tmp = this[i1]
	this[i1] = this[i2]
	this[i2] = tmp
}

fun IntArray.swap(i1: Int, i2: Int) {
	val tmp = this[i1]
	this[i1] = this[i2]
	this[i2] = tmp
}

fun <T> MutableList<T>.swap(i1: Int, i2: Int) {
	val tmp = this[i1]
	this[i1] = this[i2]
	this[i2] = tmp
}
