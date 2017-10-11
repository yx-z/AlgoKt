// KMP Algorithm for Pattern Matching
fun main(args: Array<String>) {
	val kmp = KMP("aabacaabaabaaa", "aabaabaaa")
	println(kmp.match())
	kmp.pattern = "1"
	println(kmp.match())
}

class KMP(var matching: String, pattern: String) {
	var pattern: String = pattern
		set(value) {
			field = value
			this.patternArray = buildPatternArray()
		}
	private var patternArray: Array<Int> = buildPatternArray()

	private fun buildPatternArray(): Array<Int> {
		val len = pattern.length
		val arr = Array(len) { 0 }

		var j = 0
		var i = 1

		while (i < len) {
			when {
				pattern[i] == pattern[j] -> {
					arr[i] = j + 1

					j++
					i++
				}
				j != 0 -> j = arr[j - 1]
				else -> {
					arr[i] = 0
					i++
				}
			}
		}

		return arr
	}

	fun match(): Boolean {
		var i = 0
		var j = 0

		while (i < matching.length && j < pattern.length) {
			when {
				matching[i] == pattern[j] -> {
					i++
					j++
				}
				j != 0 -> j = patternArray[j - 1]
				else -> i++
			}
		}

		return j == pattern.length
	}
}

