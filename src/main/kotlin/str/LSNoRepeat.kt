package str

// find the longest substring without any duplicated characters
fun main(args: Array<String>) {
	val testStr = "dcbacd"
	println(testStr.lSNoRepeatBrute())
	println(testStr.lSNoRepeat())
}

fun String.lSNoRepeatBrute(): String {
	var maxStart = 0
	var maxEnd = 0
	var maxLenth = 1
	for (start in 0 until maxLenth) {
		for (end in start + maxLenth until length) {
			val sub = substring(start, end)
			val len = maxEnd - maxStart + 1
			if (sub.noRepeat() && sub.length > len) {
				maxStart = start
				maxEnd = end
				maxLenth = len
			}
		}
	}
	return substring(maxStart, maxEnd)
}

fun String.noRepeat() = toSet().size == length

fun String.lSNoRepeat(): String {
	val set = HashSet<Char>()
	var currStart = 0
	var currEnd = 0
	var maxStart = 0
	var maxEnd = 0
	while (currEnd < length) {
		if (!set.contains(this[currEnd])) {
			set.add(this[currEnd])
			currEnd++
		} else {
			// find a repeated character
			// remove start
			// update info
			if (currEnd - currStart > maxEnd - maxStart) {
				maxEnd = currEnd
				maxStart = currStart
			}

			set.remove(this[currStart])
			currStart++
		}
	}

	return substring(maxStart, maxEnd)
}