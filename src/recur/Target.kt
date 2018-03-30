package recur

import util.*
import java.lang.Math.floor

// given a list of distinct heights of people H[1..n]
// a shorter guy can aim his/her gun @ his/her neighbor!
// a taller guy can aim @ the first one who is even taller than him/her
// return l[1..n], r[1..n] where l represents the index who can target to the left
// and r represents targeting to the right

// 1. find a O(n log n) algorithm to get l, r
fun OneArray<Int>.target(): Tuple2<OneArray<Int>, OneArray<Int>> {
	val H = this
	val n = size

	if (n == 1) {
		return oneArrayOf(0) tu oneArrayOf(0)
	}

	if (n == 2) {
		val L = OneArray(2) { 0 }
		val R = OneArray(2) { 0 }
		if (H[1] > H[2]) {
			L[2] = 1
		} else { // H[1] < H[2]
			R[1] = 2
		}
		return L tu R
	}

	val m = floor(n / 2.0).toInt()
	val (ll, lr) = this[1..m].target()
	var (rl, rr) = this[m + 1..n].target()

	// ! tricky part !
	// if we recursively get the right part,
	// all non-zero indices should be shifted!
	rl = rl.map { if (it == 0) 0 else it + m }.toOneArray()
	rr = rr.map { if (it == 0) 0 else it + m }.toOneArray()

	val L = ll append rl
	val R = lr append rr

//	println("$n:\nL: $L\nR: $R\n")

	var i = m
	var j = m + 1
	while (i >= 1 && j <= n) {
		when {
			R[i] != 0 -> i--
			L[j] != 0 -> j++
			H[i] < H[j] -> {
				R[i] = j
				i--
			}
			else -> { // H[i] > H[j]
				L[j] = i
				j++
			}
		}
	}

	return L tu R
}

fun main(args: Array<String>) {
	val H = oneArrayOf(3, 8, 1, 6, 5, 7, 2, 4)
	println(H.target())
}