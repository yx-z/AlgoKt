package bit

fun main(args: Array<String>) {
	// 5DEC =  101BIN -> 2 bits with value 1
	println(5.countBit())
	println(Integer.bitCount(5))
}

fun Int.countBit(): Int {
	var int = this
	var sum = 0
	while (int != 0) {
		sum++
		// update int by setting its least significant bit (LSB) (with value 1) to 0
		// n         = .....(no care)...100000...0
		//                              ^
		//                              |
		//                              LSB here, all 0s behind
		// n - 1     = .....(no care)...011111...1
		// n & (n-1) = ...(no change)...000000...0
		int = int and (int - 1)
	}
	return sum
}