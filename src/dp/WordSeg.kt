package dp

// given a string and `isWord` method, determine if the string can be segmented into different words

// simulation of `isWord`
val dict = setOf("this", "is", "just", "an", "example", "of", "a", "very", "long", "string")

fun String.isWord(start: Int, end: Int) = dict.contains(this.substring(start, end + 1))

fun String.canBeSegmented(): Boolean {
	// dp[i] = this.subString(i) can be segmented
	val dp = BooleanArray(length + 1)
	// sentinel value
	dp[length] = true

	for (i in length - 1 downTo 0) {
		// iterate over the rest and util.set "breakpoint" there
		// if we can divide the rest into a word && string after the breakpoint can be segmented
		// then current suffix can also be segmented
		dp[i] = (i until length).any { isWord(i, it) && dp[it + 1] }
	}

	// want the whole string
	return dp[0]
}

// test cases
fun main(args: Array<String>) {
	// variations of "this is just an example of a very long string"
	val strs = arrayOf("thisisjustanexampleofaverylongstring", // true
			"thisisjstanexamplofavrylngstr", // false
			"thisisjustanexampleofaverylongstringana", // true
			"tthisisjustanexampleofaverylongstring") // false
	strs.forEach { println(it.canBeSegmented()) }
}
