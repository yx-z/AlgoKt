import org.junit.jupiter.api.Test
import java.util.Arrays

// util functions
fun min(vararg ints: Int) = ints.min() ?: 0

fun max(vararg ints: Int) = ints.max() ?: 0

operator fun String.get(range: IntRange) = substring(range)

inline operator fun <reified T> Array<T>.get(range: IntRange): Array<T?> =
		Arrays.copyOfRange(this, range.first, range.last + 1) ?: arrayOfNulls(0)

operator fun IntArray.get(range: IntRange) = Arrays.copyOfRange(this, range.first, range.last + 1)
		?: intArrayOf()

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
	fun testStringGet() {
		assert("abc"[1..2] == "bc")
	}

	@Test
	fun testArrayGet() {
		assert(arrayOf(1, 3, 4)[1..2].contentEquals(arrayOf(3, 4)))
		assert(intArrayOf(1, 3, 2, 2, 9)[2..4].contentEquals(intArrayOf(2, 2, 9)))
	}
}

