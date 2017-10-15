package str

fun main(args: Array<String>) {
	val testStr = "This is just a sample test sentence with a lot of words and spaces in it"
	println(testStr.reverseWordByBuilder())
	println(testStr.reverseWords())
}

fun String.reverseWordByBuilder() = split(" ")
		.joinToString(" ") { StringBuilder(it).reverse() }

fun String.reverseWords() = split(" ").joinToString(" ") { it.reverse() }

fun String.reverse(): String {
	val sb = StringBuilder()
	((length - 1) downTo 0).forEach {
		sb.append(this[it])
	}
	return sb.toString()
}
