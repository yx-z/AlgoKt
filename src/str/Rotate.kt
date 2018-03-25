package str

import java.util.*

fun main(args: Array<String>) {
	// test string
	val s1 = arrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g')

	// should be: abc + cdefg -> defg + abc
	s1.rotate(2) // rotate up tu index 2 (0..2)
	println(Arrays.toString(s1))
}

private fun <T> Array<T>.rotate(i: Int) {
	this.reverse(0, i + 1)
	this.reverse(i + 1, this.size)
	this.reverse(0, this.size)
}

// helper reverse [l, h)
private fun <T> Array<T>.reverse(l: Int, h: Int) {
	var lo = l
	var hi = h - 1

	while (lo < hi) {
		val tmp = this[lo]
		this[lo] = this[hi]
		this[hi] = tmp

		lo++
		hi--
	}
}


