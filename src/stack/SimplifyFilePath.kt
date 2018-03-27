package stack

import java.util.*

fun main(args: Array<String>) {
	// given path strings such as /abc/../bcd/../cde
	// compress it to the simplest form

	println("/abc/def/.././".compress()) // -> /abc
	println("/../".compress()) // -> /
	println("/home//foo/".compress()) // /home/foo
	println("/a/./b/../../c/".compress()) // -> /c
	println("/...".compress()) // -> /...
}

fun String.compress(): String {
	val dirs = this.split("/")
	val stack = Stack<String>()
	dirs.forEach {
		if (it.isNotBlank() && it != ".") {
			if (it.trim() == "..") {
				if (stack.isNotEmpty()) {
					stack.pop()
				}
			} else {
				stack.push(it)
			}
		}
	}

	val stackReverse = Stack<String>()
	while (stack.isNotEmpty()) {
		stackReverse.push(stack.pop())
	}

	val sb = StringBuilder()
	sb.append("/")
	while (stackReverse.isNotEmpty()) {
		sb.append(stackReverse.pop()).append("/")
	}

	val str = sb.toString()
	if (str == "/") {
		return str
	}
	return str.substring(0, str.length - 1)
}
