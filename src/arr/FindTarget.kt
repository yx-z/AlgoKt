import java.lang.Math;

fun main(args: Array<String>) {
	// sorted arrays
	val a1 = arrayOf(1, 2, 3, 5, 9, 12, 50)
	val a2 = arrayOf(4, 5, 9, 30, 42)
	
	// (1, 5) since 1 + 5 = 6
	println(a1.pairClosestTo(6))
	// (9, 30) since 9 + 30 = 39 which is closest to 40
	println(a2.pairClosestTo(40))
	
	// (5, 30) one from a1 and the other from a2 and 5 + 30 = 35
	println(pairFromArraysClosestTo(a1, a2, 36))
}

fun Array<Int>.pairClosestTo(target: Int): Pair<Int, Int> {
	val s = this.size
	var l = 0
	var h = s - 1
	var p = Pair(this[l], this[h])
	if (s < 2) {
		return p		
	}
	var min = Int.MAX_VALUE
			
	while (l < h) {
		val sum = this[l] + this[h]
		val diff = Math.abs(sum - target)
		if (diff < min) {
			min = diff
			p = Pair(this[l], this[h])
		}
		if (sum > target) {
			h--
		} else if (sum == target) {
			break
		} else {
			l++
		}
	}
	
	return p	
}

fun pairFromArraysClosestTo(a1: Array<Int>, a2: Array<Int>, target: Int): Pair<Int, Int> {
	var l = 0
	var h = a2.size - 1
	var p = Pair(a1[l], a2[h])
	var min = Int.MAX_VALUE
	
	while (l < a1.size && h >= 0) {
		val sum = a1[l] + a2[h]
		val diff = Math.abs(sum - target)
		
		if (diff < min) {
			min = diff
			p = Pair(a1[l], a2[h])
		}
		
		if (sum > target) {
			h--
		} else if (sum == target) {
			break
		} else {
			l++
		}
	}
	
	return p
}