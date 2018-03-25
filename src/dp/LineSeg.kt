package dp

import util.OneArray
import util.Tuple2
import util.oneArrayOf
import util.tu

// given L[1..n] lines, with one endpoint on y = 0 and the other on y = 1,
// assuming all 2n endpoints are distinct

typealias Line = Tuple2<Int, Int> // (endpoint on y = 0, endpoint on y = 1)

// 1. find the largest subset of L : NO pair of segments intersects
fun OneArray<Line>.noSegIntersect(): Set<Line> {
	val L = this
	val n = size

	// dp(i): (largest subset of L[1..i] with no pair of segments intersecting,
	//        leftmost point on y = 0, rightmost point on y = 0,
	//        leftmost point on y = 1, rightmost point on y = 1)
	// memoization structure: 1d array dp[1..n] : dp[i] = dp(i)
	val dp = OneArray(n) {
		HashSet<Line>() tu 0 tu 0 tu 0 tu 0
	}
	// space complexity: O(n^2)

	// base case:
	// dp(1) = { L[1] }, L[1]_1, L[1]_1, L[1]_2, L[1]_2
	dp[1].first.add(L[1])
	val (e1, e2) = L[1]
	dp[1].second = e1
	dp[1].third = e1
	dp[1].fourth = e2
	dp[1].fifth = e2

	// we see that two lines intersect iff.:
	// the end point of a line is smaller than that of the other on y = 0
	// the other end point of the line is larger than that of the other on y = 1

	// recursive case:
	// dp(i) = max_k{ dp(k) + if (L[i] intersect with L[k]) 0 else 1 }, k < i
	// dependency: dp(i) depends on dp(k) where k < i, that is entries to the right
	// evaluation order: loop for i from 2 to n (left to right)
	for (i in 2..n) {
		val (i0, i1) = L[i]
		// inner loop for k from 1 until i (left to right)
		var maxSize = 0
		var maxSizeSetIdx = 1
		var lm0 = 0
		var rm0 = 0
		var lm1 = 0
		var rm1 = 0
		var addCurr = false
		for (k in 1 until i) {
			val (s, l0, r0, l1, r1) = dp[k]
			when {
				i0 < l0 && i1 < l1 -> {
					if (s.size + 1 > maxSize) {
						maxSize = s.size + 1
						maxSizeSetIdx = k
						addCurr = true
						lm0 = i0
						lm1 = i1
						rm0 = r0
						rm1 = r1
					}
				}
				i0 > r0 && i1 > r1 -> {
					if (s.size + 1 > maxSize) {
						maxSize = s.size + 1
						maxSizeSetIdx = k
						addCurr = true
						rm0 = i0
						rm1 = i1
						lm0 = l0
						lm1 = l1
					}
				}
				else -> {
					if (s.size > maxSize) {
						maxSize = s.size
						maxSizeSetIdx = k
						addCurr = false
						rm0 = r0
						rm1 = r1
						lm0 = l0
						lm1 = l1
					}
				}
			}
		}
		dp[i].first.addAll(dp[maxSizeSetIdx].first)
		if (addCurr) {
			dp[i].first.add(L[i])
		}
		dp[i].second = lm0
		dp[i].third = rm0
		dp[i].fourth = lm1
		dp[i].fifth = rm1
	}
	// time complexity: O(n^2)

//	dp.prettyPrintln()

	// we want dp(n)_1
	return dp[n].first
}

// 2. now find the largest subset : EVERY pair of segments intersects
fun OneArray<Line>.everySegIntersect(): Set<Line> {
	val L = this
	val n = size

	// dp(i): such subset for L[1..n]
	// memoization structure: 1d array dp[1..n] : dp[i] = dp(i)
	val dp = OneArray(n) { HashSet<Line>() }
	// space complexity: O(n^2)

	// base case:
	// dp(1) = { L[1] }
	dp[1].add(L[1])

	// recursive case:
	// dp(i) = max{ dp(k) + if (L[1] intersects with every Line in dp(k)) L[i] }
	//         for all 1 <= k < i
	// dependency: dp[i] depends on dp[k] where k < i
	// evaluation order: outer loop i from 2 to n (right to left)
	for (i in 2..n) {
		// inner loop for k from 1 until i
		var addCurr = false
		var maxIdx = 0
		var maxSize = 0
		for (k in 1 until i) {
			if (dp[k].all { it.interset(L[i]) }) {
				if (dp[k].size + 1 > maxSize) {
					maxSize = dp[k].size + 1
					maxIdx = k
					addCurr = true
				}
			} else {
				if (dp[k].size > maxSize) {
					maxSize = dp[k].size
					maxIdx = k
					addCurr = false
				}

			}
		}
		dp[i].addAll(dp[maxIdx])
		if (addCurr) {
			dp[i].add(L[i])
		}
	}
	// time complexity: O(n^3)

	// we want dp(n)
	return dp[n]
}

fun OneArray<Line>.everySegIntersect2(): Set<Line> {
	val L = this
	L.sortBy { it.first }
	// time complexity: O(n log n)
	val n = size

	// dp(i): a sublist of L[1..i] that increases in L[k]_1
	//        and intersects with all other pairs -> decreases in L[k]_2
	// memoization structure: 1d array dp[1..n] : dp[i] = dp(i)
	val dp = OneArray(n) { ArrayList<Line>() }
	// space complexity: O(n^2)

	// base case:
	// dp(1) = { L[1] }
	dp[1].add(L[1])

	// recursive case:
	// dp(i) = max_k { dp(k) + if (L[i]_1 > dp(i)[-1]_1 && L[i]_2 < dp(i)[-1]_2 }
	// dependency: dp[i] depends on dp[k] where k < i
	// evaluation order: outer loop for i from 2..n
	for (i in 2..n) {
		// inner loop for k from 1 until i
		var addCurr = false
		var maxIdx = 0
		var maxSize = 0
		for (k in 1 until i) {
			if (L[i].first > dp[k].last().first && L[i].second < dp[k].last().second) {
				if (dp[k].size + 1 > maxSize) {
					maxSize = dp[k].size + 1
					maxIdx = k
					addCurr = true
				}
			} else {
				if (dp[k].size > maxSize) {
					maxSize = dp[k].size
					maxIdx = k
					addCurr = false
				}

			}
		}
		dp[i].addAll(dp[maxIdx])
		if (addCurr) {
			dp[i].add(L[i])
		}
	}
	// time complexity: O(n^2)

	// we want dp(n)
	return dp[n].toSet()
}

fun Line.interset(other: Line) = (first - other.first) * (second - other.second) < 0

fun main(args: Array<String>) {
	val L = oneArrayOf(
			1 tu 10,
			2 tu 4,
			3 tu 3,
			4 tu 2
	)

	println(L.noSegIntersect())
	println(L.everySegIntersect())
	println(L.everySegIntersect2())
}