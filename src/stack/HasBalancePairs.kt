package stack

import java.util.*

// check if strings of "({[ ... )" is valid
fun main(args: Array<String>) {
	// true
	println("([]{[]})".hasBalancedPairs())
	// false
	println("([{]})".hasBalancedPairs())
}

fun String.hasBalancedPairs(): Boolean {
	val pairStack = Stack<Char>()
	forEach {
		when (it) {
			'(', '[', '{' -> pairStack.push(it)
			')', ']', '}' -> {
				if (pairStack.isEmpty()) {
					return false
				}

				pairStack.pop().apply {
					when (it) {
						')' -> if (this != '(') {
							return false
						}
						']' -> if (this != '[') {
							return false
						}
						'}' -> if (this != '{') {
							return false
						}
					}
				}

			}
		}
	}
	return pairStack.isEmpty()
}