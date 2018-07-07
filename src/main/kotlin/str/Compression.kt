package str

fun main(args: Array<String>) {
	// test string
	val s = "aaabbccd"

	// should be a3b2c2d
	println(s.compress())
}

fun String.compress(): String {
	val size = this.length
	if (size == 0) {
		return ""
	}

	val sb = StringBuilder()
	var i = 0
	while (i < size) {
		var tmpLen = 1
		while (i + tmpLen < size && this[i + tmpLen] == this[i]) {
			tmpLen++
		}

		sb.append(this[i])
		if (tmpLen > 1) {
			sb.append(tmpLen)
		}

		i += tmpLen
	}

	return sb.toString()
}
