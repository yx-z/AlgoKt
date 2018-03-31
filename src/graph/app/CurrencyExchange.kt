package graph.app

import util.OneArray
import util.get
import util.oneArrayOf
import java.util.*

// given R[1..n, 1..n] : R[i, j] represents the amount of currency j you can
// get if you have 1 unit of currency i
// report an array V[1..n] : V[i] = starting with one unit of currency 1, the max
// amount of currency i you may get by exchanging to different currencies
// 1. you may assume that no arbitrage cycle exists, that is, you cannot do
// $1 -> 100Yen -> 0.12Euro -> $1.2
fun OneArray<OneArray<Double>>.maxExchange(): OneArray<Double> {
	val R = this
	val n = size

	val V = OneArray(n) { Double.MIN_VALUE }
	V[1] = 1.0

	// modified dijkstra
	val maxHeap = PriorityQueue<Int> { i1, i2 -> i2 - i1 }
	maxHeap.add(1)
	while (maxHeap.isNotEmpty()) {
		val i = maxHeap.remove()
		for (j in 1..n) {
			if (V[i] * R[i, j] > V[j]) {
				V[j] = V[i] * R[i, j]
				maxHeap.add(j)
			}
		}
	}

	return V
}

fun main(args: Array<String>) {
	val R = oneArrayOf(
			oneArrayOf(1.0, 3.0, 5.0),
			oneArrayOf(0.3, 1.0, 100.0),
			oneArrayOf(0.002, 0.001, 1.0))
	println(R.maxExchange())
}