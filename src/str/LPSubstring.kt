package str

import util.max

fun main(args: Array<String>) {
	val s = "abbaaa"
	// should be abba -> 4
	println(lenLPS(s))
}

// Longest Palindrome Substring (consecutive chars), NOT LPS
fun lenLPS(s: String): Int {
	var sb = StringBuilder(s)
	for (i in s.length - 1 downTo 1) {
		sb = sb.insert(i, '_')
	}
	// max of the length of palindrome substring centered at s[i]
	var max = Int.MIN_VALUE
	sb.indices.forEach {
		var len = 1
		while (it - len >= 0 && it + len < sb.length && sb[it - len] == sb[it + len]) {
			len++
		}
		max = max(max, len)
	}

	return max
}
