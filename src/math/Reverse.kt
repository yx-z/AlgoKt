package math

fun main(args: Array<String>) {
	// reverse a non-negative integer
	val testInt = 23450
	println(testInt.reverseIter())
	println(testInt.reverseRecur())
}

fun Int.reverseIter(): Int {
	if (this < 0) {
		throw IllegalArgumentException("Int >= 0")
	}

	var rev = 0
	var rem = this

	while (rem > 0) {
		rev *= 10
		rev += rem % 10

		rem /= 10
	}

	return rev
}

fun Int.reverseRecur(): Int {
	return this.reverseRecurHelper().toString().toInt()
}

fun Int.reverseRecurHelper(): StringBuilder {
	if (this < 0) {
		throw IllegalArgumentException("Int >= 0")
	}

	return when(this) {
		in 0..9 -> StringBuilder().append(this)
		else ->  StringBuilder().append(this % 10).append((this / 10).reverseRecurHelper())
	}
}
