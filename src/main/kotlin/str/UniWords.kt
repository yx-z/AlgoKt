package str

fun main(args: Array<String>) {
	val testSentence1 = "hello world this is a sample test of string"
	val testSentence2 = "hello there this is not a test of string"

	println(testSentence1 unique testSentence2)
}

infix fun String.unique(str: String): Collection<String> {
	val set = HashSet<String>()
	this.split(" ").forEach { set.add(it) }

	str.split(" ").forEach {
		if (set.contains(it)) {
			set.remove(it)
		} else {
			set.add(it)
		}
	}

	return set
}