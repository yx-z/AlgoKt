package str

fun main(args: Array<String>) {
	val testStr = "This is just a sample test sentence with a lot of words and spaces in it"
	println(testStr.reverseWordsByBuilder())
	println(testStr.reverseWords())
	println(testStr.reverseWordsRaw())

	val charArr = testStr.toCharArray()
	charArr.reverseWordsInPlace()
	println(String(charArr))
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

fun CharArray.reverseWordsInPlace() {
	var idx = 0
	while (idx < size) {
		var start = idx
		var end = idx
		while (end < size && this[end] != ' ') {
			end++
		}
		idx = end + 1
		end--
		while (start < end) {
			val tmpChar = this[start]
			this[start] = this[end]
			this[end] = tmpChar

			start++
			end--
		}
	}
}
