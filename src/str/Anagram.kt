package str

import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap

// Determine If 2 Strings are Anagram (re-ordering of each other)
fun main(args: Array<String>) {
	// test strings
	val s1 = "abcd"
	val s2 = "dcab"
	// true
	println(isAnagram(s1, s2))

	// test array
	val sArr = arrayOf("abc", "123", "cba", "132", "213")
	// should be ("abc", "cba", "123", "132", "213") or something like that
	println(Arrays.toString(groupAnagram(sArr)))
}

fun isAnagram(s1: String, s2: String): Boolean {
	// support lowercase standard english letters only
	val count = Array(26) { 0 }

	s1.chars().forEach { count[it - 'a'.toInt()]++ }
	s2.chars().forEach { count[it - 'a'.toInt()]-- }

	return count.asSequence().all { it == 0 }
}

fun groupAnagram(arr: Array<String>): Array<String> {
	val ans = Array(arr.size) { "" }
	val map = HashMap<String, ArrayList<String>>()
	arr.forEach {
		val key = it.sorted()
		if (map.containsKey(key)) {
			map[key]?.add(it)
		} else {
			map.put(key, ArrayList(Arrays.asList(it)))
		}
	}

	var idx = 0
	map.values.forEach {
		it.forEach {
			ans[idx++] = it
		}
	}
	return ans
}

fun String.sorted(): String = this
		.chars()
		.sorted()
		.boxed()
		.map { it -> it.toChar() }
		.collect(Collectors.toList())
		.toString()

