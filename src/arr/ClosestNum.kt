package arr

import java.util.*

fun main(args: Array<String>) {
	// test array
	val arr = arrayOf(1, 4, 8, 6, 3)
	
	// should return [(4, 3)]
	println(arr.closestPairs())
}

fun Array<Int>.closestPairs(): List<Pair<Int, Int>> {
	Arrays.sort(this)
	
	val ans = ArrayList<Pair<Int, Int>>()
	var min = Int.MAX_VALUE
	
	for (i in 1 until this.size) {
		val diff = this[i] - this[i - 1]
		if (diff < min) {
			if (diff < min) {
				min = diff
				ans.clear()
			}
			ans.add(Pair(this[i], this[i - 1]))
		}
	}
	
	return ans
}
