package recur

// classic tower of hanoi problem:
// given three poles, with n disks placed through one pole, piled from big to small
// your goal is to move all poles to pole three
// but each move can be done by moving a subset of disks that is also piled from
// big to small
// find the # of steps required to finish the puzzle given n
fun hanoi(n: Int, from: Int, middle: Int, to: Int): Int {
	var count = 0
	if (n == 0) {
		return count
	}

	count += hanoi(n - 1, from, to, middle)

	move(n, from, to)
	count++

	count += hanoi(n - 1, middle, from, to)
	return count
}

fun move(n: Int, from: Int, to: Int) {
	println("move disk #$n from $from to $to")
}

fun main(args: Array<String>) {
	println("the puzzle takes ${hanoi(5, 1, 2, 3)} step(s)")
}