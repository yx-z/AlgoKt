package stack

import java.util.*

fun main(args: Array<String>) {
	// parse command line calculations such as 1+2*3
	// tu answer -> 1+6 -> 7
	// only +, -, *, /, no `.`, `^`, `(`, `)`, etc.
	// assume input string is always valid and formatted (no spaces, tabs, etc.)

	val test1 = "13*2+8" // = 34
	val test2 = "3-12+5*9" // = 36
	val test3 = "1+3*2/5-3" // = -0.8

	println(test1.calc())
	println(test2.calc())
	println(test3.calc())
}

fun String.calc(): Double {
	val numStack = Stack<Double>()
	val opStack = Stack<Char>()

	val numBuilder = StringBuilder()
	this.forEach {
		with(it) {
			when {
				isDigit() -> {
					numBuilder.append(it)
				}

			// is oper
				else -> {
					// push math and reset
					numStack.push(numBuilder.toString().toDouble())
					numBuilder.setLength(0)

					// calc
					while (opStack.isNotEmpty() && it.getPriority() <= opStack.peek().getPriority()) {
						numStack.push(opStack.pop().calc(numStack.pop(), numStack.pop()))
					}
					opStack.push(it)
				}
			}
		}
	}
	numStack.push(numBuilder.toString().toDouble())

	while (opStack.isNotEmpty()) {
		numStack.push(opStack.pop().calc(numStack.pop(), numStack.pop()))
	}

	return numStack.pop()
}

fun Char.getPriority() = when (this) {
	'+' -> 0
	'-' -> 0
	'*' -> 1
	'/' -> 1
	else -> -1
}

fun Char.calc(a: Double, b: Double) = when (this) {
	'+' -> b + a
	'-' -> b - a
	'*' -> b * a
	'/' -> b / a
	else -> 0.0
}


