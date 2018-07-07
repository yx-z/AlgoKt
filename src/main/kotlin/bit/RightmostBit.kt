package bit

fun main(args: Array<String>) {
	val testInt = 5 // 5DEC = 101BIN -> rightmost bit = 100BIN = 4DEC
	println(testInt.rightmostBit())
	println(Integer.lowestOneBit(testInt)) // answer
}

fun Int.rightmostBit() = this xor (this and (this - 1))
