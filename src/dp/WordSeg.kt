package dp

// given a string and `isWord` method, determine if the string can be segmented into different words
fun main(args: Array<String>) {
	// variations of "this is just an example of a very long string"
	val strs = arrayOf("thisisjustanexampleofaverylongstring", // true
			"thisisjstanexamplofavrylngstr", // false
			"thisisjustanexampleofaverylongstringana", // true
			"tthisisjustanexampleofaverylongstring") // false
	strs.forEach { println(it.canBeSegmented()) }
}

// simulation of `isWord`
val dict = setOf("this", "is", "just", "an", "example", "of", "a", "very", "long", "string")

fun String.isWord(start: Int, end: Int) = dict.contains(this.substring(start, end + 1))

fun String.canBeSegmented(): Boolean {
	val canBeSegmented = BooleanArray(length + 1)
	canBeSegmented[length] = true

	outer@ for (i in length - 1 downTo 0) {
		for (j in i until length) {
			if (isWord(i, j) && canBeSegmented[j + 1]) {
				canBeSegmented[i] = true
				continue@outer
			}
		}
	}
	return canBeSegmented[0]
}