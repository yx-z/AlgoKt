package recur

// a variation of the famous Tower of Hanoi:
// you are given 1..k pegs and n disks @ peg 1 (piled from big to small)
// your goal is to move all of them to peg k : you can move one disk at a time
// but say it's currently at peg i, you can only move it to peg i - 1 or i + 1
// and as Tower of Hanoi, large disks cannot be placed on smaller disks

// you don't need to find the most optimal strategy

// 1. solve a special case for k = 3
fun libertyTowers3(n: Int, start: Int = 1, end: Int = 3): Int {
	if (n == 0 || start == end) {
		return 0
	}

	var count = 0
	when (start) {
		1 -> {
			if (end == 2) {
				count += libertyTowers3(n - 1, 1, 3)

				move(n, 1, 2)
				count++

				count += libertyTowers3(n - 1, 3, 2)
			} else { // end == 3
				count += libertyTowers3(n - 1, 1, 3)

				move(n, 1, 2)
				count++

				count += libertyTowers3(n - 1, 3, 2)
				count += libertyTowers3(n, 2, 3)
			}
		}
		2 -> {
			if (end == 1) {
				count += libertyTowers3(n - 1, 2, 3)

				move(n, 2, 1)
				count++

				count += libertyTowers3(n - 1, 3, 2)
				count += libertyTowers3(n - 1, 2, 1)
			} else { // end == 3
				count += libertyTowers3(n - 1, 2, 1)

				move(n, 2, 3)
				count++

				count += libertyTowers3(n - 1, 1, 2)
				count += libertyTowers3(n - 1, 2, 3)
			}
		}
		3 -> {
			if (end == 2) {
				count += libertyTowers3(n - 1, 3, 2)
				count += libertyTowers3(n - 1, 2, 1)

				move(n, 3, 2)
				count++

				count += libertyTowers3(n - 1, 1, 2)
			} else { // end == 1
				count += libertyTowers3(n - 1, 3, 2)
				count += libertyTowers3(n - 1, 2, 1)

				move(n, 3, 2)
				count++

				count += libertyTowers3(n - 1, 1, 2)
				count += libertyTowers3(n, 2, 1)
			}
		}
	}
	return count
}

fun main(args: Array<String>) {
	println(libertyTowers3(3))
}