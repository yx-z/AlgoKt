package dp

import get

// given a = 1, b = 2, c = 3, ..., z = 26
// and a number (as a string), determine the number of ways to interpret this number into alphabets

// ex. 123 == [12, 3] -> lc
//         == [1, 2, 3] -> abc
//         == [1, 23] -> aw
// so we should return 3

// note that no leading 0s are allowed
// ex. 102 == [10, 2] -> jb
//        (!= [1, 02])
// so we should return 1

// ex. 0123 == []
// so we should return 0 since there is no valid interpretation
fun main(args: Array<String>) {
	println(alphaCombin("123"))
	println(alphaCombin("102"))
	println(alphaCombin("0123"))
}

fun alphaCombin(n: String): Int {
	val len = n.length
	// dp[i] = # of interpretations for n[i until len]
	// dp[i] = 1 if (i >= len - 1)
	//         note that for empty strings, there is still one interpretation: empty string
	//       = dp[i + 1] + dp[i + 2] if (isValid(n[i..i+1]) && isValid(n[i])) ex. [1, 2, ...]
	//       = dp[i + 1] if (isValid(n[i])) ex. [9, 0, ...] 9 is valid but not 90
	//       = 0 o/w
	val dp = IntArray(len + 1) { 1 }
	for (i in len - 2 downTo 0) {
		dp[i] = if (isValid(n[i..i]) && isValid(n[i..i + 1])) {
			dp[i + 1] + dp[i + 2]
		} else if (isValid(n[i..i])) {
			dp[i + 1]
		} else {
			0
		}
	}
	return dp[0]
}

// assume O(1) operation
fun isValid(n: String) = !n.startsWith('0') && Integer.parseInt(n) in 1..26