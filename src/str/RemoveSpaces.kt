package str

fun main(args: Array<String>) {
	val testStr = " hel lo  World!   "
	println(testStr.removeSpaces())
	println(testStr.trim())
	println("(${"   ".trim()})")
	println("a  ".trim())
	println(" b".trim())
	println(" c ".trim())
}

// O(String.length)
fun String.removeSpaces() = this.filter { it != ' ' }.map { it }.joinToString("")

// O(#spaces)
fun String.trim(): String {
	var startIdx = 0
	while (startIdx < length && this[startIdx] == ' ') {
		startIdx++
	}
	// now startIdx is pointing at the first character that is not a space OR
	// startIdx = length if the string only contains spaces

	var endIdx = this.length - 1
	while (endIdx >= 0 && this[endIdx] == ' ') {
		endIdx--
	}
	// now endIdx is pointing at the last character that is not a space OR
	// endIdx = -1

	if (startIdx <= endIdx) {
		return substring(startIdx, endIdx + 1)
	}

	return ""
}
