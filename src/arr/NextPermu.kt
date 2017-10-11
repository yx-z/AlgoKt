package arr;

import java.util.*

fun main(args: Array<String>) {
	val arr = arrayOf(9, 3, 2, 4, 1)
	arr.nextPermute()
	println(Arrays.toString(arr))
}

fun Array<Int>.nextPermute() {
	if (this.size <= 1) {
		return
	}

	var i = this.size - 2
	while (i >= 0 && this[i] >= this[i + 1]) {
		i--
	}

	if (i < 0) {
		return
	}

	var j = this.size - 1
	while (this[j] <= this[i]) {
		j--
	}
	swap(i, j)

	reverse(i + 1, this.size - 1)
}

fun <T> Array<T>.reverse(i1: Int, i2: Int) {
	var t1 = i1
	var t2 = i2
	while (t1 < t2) {
		swap(t1, t2)
		t1++
		t2--
	}
}

fun <T> Array<T>.swap(i1: Int, i2: Int) {
	val tmp = this[i1]
	this[i1] = this[i2]
	this[i2] = tmp
}