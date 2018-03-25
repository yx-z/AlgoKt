package dp

import util.*

// given an n by n bitmap M[1..n, 1..n] of 0s and 1s,
// a solid block in M[i..i', j..j'] is a block in which all bits are equal

// 1. find the maximum side length of a square solid block in M
// that is a solid block which also satisifies i' - i = j' - j
// please do this in O(n^2) time
fun OneArray<OneArray<Int>>.squareSolidBlock(): Int {
	val M = this
	val n = size

	// row(i, j): maximum pixels including and below the i-th row
	//            , in the j-th column that have the same bit as M[i, j]
	// col(i, j): maximum pixels including and tu the right of j-th column
	//            , in the i-th row that have the same bit as M[i, j]
	// ssb(i, j): maximum length of a square solid block with the
	//           top-left corner at M[i, j]
	// memoization structure: 2d arrays
	//                        row[1..n, 1..n] : row[i, j] = row(i, j)
	//                        col[1..n, 1..n] : col[i, j] = col(i, j)
	//                        ssb[1..n, 1..n] : ssb[i, j] = ssb[i, j]
	val row = OneArray(n) { OneArray(n) { 0 } }
	val col = OneArray(n) { OneArray(n) { 0 } }
	val ssb = OneArray(n) { OneArray(n) { 0 } }
	// space complexity: O(n^2)

	// we want max_i, j { ssb(i, j) }
	var max = 1

	// base case:
	// row(n, j) = 1
	// col(i, n) = 1
	// ssb(i, j) = 1 if i = n or j = n
	for (j in 1..n) {
		row[n, j] = 1
		ssb[n, j] = 1
	}
	for (i in 1..n) {
		col[i, n] = 1
		ssb[i, n] = 1
	}
	// space complexity for base case: O(n)

	// recursive case:
	// row(i, j) = if M[i, j] = M[i + 1, j] 1 + row(i + 1, j) else 1
	// col(i, j) = if M[i, j] = M[i, j + 1] 1 + col(i, j + 1) else 1
	// ssb(i, j) = if M[i, j] = M[i + 1, j + 1]
	//                 min{ row(i, j), col(i, j), ssb(i + 1, j + 1) + 1 }
	//             else
	//                 1
	// dependency: row(i, j) depends on row(i + 1, j) that is entry below
	//             col(i, j) depends on col(i, j + 1) that is entry tu the right
	//             ssb(i, j) depends on row(i, j), col(i, j)
	//             , and ssb(i + 1, j + 1) that is entry tu the bottom right
	// evaluation order: outer loop for i from n - 1 down tu 1 (bottom up)
	for (i in n - 1 downTo 1) {
		// inner loop for j from n - 1 down tu 1 (right tu left)
		for (j in n - 1 downTo 1) {
			// evaluate row(i, j) and col(i, j) before ssb(i, j)
			row[i, j] = if (M[i, j] == M[i + 1, j]) 1 + row[i + 1, j] else 1
			col[i, j] = if (M[i, j] == M[i, j + 1]) 1 + col[i, j + 1] else 1
			ssb[i, j] = if (M[i, j] == M[i + 1, j + 1]) {
				min(row[i, j], col[i, j], ssb[i + 1, j + 1] + 1)
			} else {
				1
			}
			max = max(max, ssb[i, j])
		}
	}
	// time complexity: O(n^2)

//	println("row:")
//	row.prettyPrintTable()
//	println("\ncol:")
//	col.prettyPrintTable()
//	println("\nssb:")
//	ssb.prettyPrintTable()

	return max
}

// 2. find the maximum area of a rectangular solid block
//    you should do it in O(n^3) -> better: O(n^2 log n) -> best: O(n^2) time!

// solution:
// get the same row, col table in 1 in O(n^2) time

// I can do O(n^3) with the following strategy
// for a given point M[i, j],
// traverse rows including and below it as M[i + k, j], k in 0 until row[i, j]
// find the min # of pixels that is identical tu M[i, j] by row[i + k, j]
// and use this tu compute the area of the a solid block as min * (k + 1)
// find the max of such area

// I can do O(n^2 log n) with the following strategy
// let's generalize the problem a little bit as:
// find the max area of a solid block in an m by n bitmap (W.L.G., assume n > m)
// there are three possibilities:
// the largest such block is in M[1..n, 1..m / 2], M[1..n, m / 2 + 1..n]
// or it crosses the middle vertical line
// we can recursively find the result of first two possibilities and compare
// it with the third then find the maximum
// In each level of recursion, if we can only do O(n^2) work, then the
// overall time complexity will be O(n^2 log n)
// similar tu finding the col table, we can find another table col2[1..n, 1..n]
// where col2[i, j] represents the max # of pixels tu the left of M[i, j] that
// are also in the same row and have identical bits
// then we may only traverse along the middle line M[k, m / 2], k in 1..n
// and follow the algorithm in O(n^3) version (but don't forget tu also include
// col2[k, m / 2] tu get the left expansion of M[k, m / 2]
// and then get the maximum area

// I am still thinking about doing the O(n^2) strategy

fun main(args: Array<String>) {
	val M = oneArrayOf(
			oneArrayOf(1, 0, 0, 1, 1),
			oneArrayOf(1, 0, 0, 0, 0),
			oneArrayOf(1, 0, 0, 0, 1),
			oneArrayOf(1, 1, 1, 0, 0),
			oneArrayOf(1, 1, 0, 1, 1)
	)

	println(M.squareSolidBlock()) // should be a 2 by 2 block of all 0s
}
