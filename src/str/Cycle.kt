package str

fun main(args: Array<String>) {
	// test strings
	val s1 = "abcde"
	val s2 = "cdeab"

	println(isCycle(s1, s2))

	// test array
	val sArr = arrayOf("the", "brown", "fox", "oxf")
	// should return 3
	println(sArr.cyclicWords())
}

// Cycle Detection
// detect if 2 words are rotation (re-ordering) of each other
fun isCycle(s1: String, s2: String) = s1.length == s2.length && (s1 * 2).contains(s2)

operator fun String.times(i: Int) = this.repeat(i)

/**
 * @return number of different cyclic words in the array
 */
fun Array<String>.cyclicWords(): Int {
	val set = HashSet<String>()

	this.forEach { s ->
		if (!set.any { str.isCycle(s, it) }) {
			set.add(s)
		}
	}

	return set.size
}

