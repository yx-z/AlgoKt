import org.junit.jupiter.api.Test
import java.lang.NullPointerException
import java.util.Arrays

// multi-parameter version of min/max
fun min(vararg ints: Int) = ints.min() ?: throw NullPointerException("no minimum value")

fun max(vararg ints: Int) = ints.max() ?: throw NullPointerException("no maximum value")

// substring and subarray slicing
operator fun String.get(range: IntRange) = substring(range)

inline operator fun <reified T> Array<T>.get(range: IntRange): Array<T?> =
		Arrays.copyOfRange(this, range.first, range.last + 1) ?: arrayOfNulls(0)

operator fun IntArray.get(range: IntRange) =
		Arrays.copyOfRange(this, range.first, range.last + 1) ?: intArrayOf()

// python like multidimensional array indexing
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

class UtilTest {

	@Test
	fun testMin() {
		assert(min(1, 0, 3, 4) == 0)
	}

	@Test
	fun testMax() {
		assert(max(1, 5, 0, 8, 2, 3) == 8)
	}

	@Test
	fun testStringSlicing() {
		assert("abc"[1..2] == "bc")
	}

	@Test
	fun testArraySlicing() {
		assert(arrayOf(1, 3, 4)[1..2].contentEquals(arrayOf(3, 4)))
		assert(intArrayOf(1, 3, 2, 2, 9)[2..4].contentEquals(intArrayOf(2, 2, 9)))
	}

	@Test
	fun testArrayIndexing() {
		val arr = Array(3) { Array(5) { IntArray(4) { 2 } } }
		arr[0, 0, 0] = 6
		assert(arr[0, 0, 0] == 6)
	}
}

