package str

fun main(args: Array<String>) {
	val testStr = "This is just a sample test sentence with a lot of words and spaces in it"
	println(testStr.reverseWordsByBuilder())
	println(testStr.reverseWords())
	println(testStr.reverseWordsRaw())
}

fun String.reverseWordsByBuilder() = split(" ")
		.joinToString(" ") { StringBuilder(it).reverse() }

fun String.reverseWords() = split(" ").joinToString(" ") { it.reverse() }

fun String.reverse(): String {
	val sb = StringBuilder()
	((length - 1) downTo 0).forEach {
		sb.append(this[it])
	}
	return sb.toString()
}

fun String.reverseWordsRaw(): String {
	val sb = StringBuilder()
	val word = StringBuilder()
	forEach {
		if (it == ' ') {
			sb.append(word.toString().reverse())
			sb.append(' ')
			word.setLength(0)
		} else {
			word.append(it)
		}
	}
	return sb.append(word.toString().reverse()).toString()
}
