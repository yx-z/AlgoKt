package dp

import util.OneArray
import util.get
import util.set
import util.toOneArray

// given an array of positive integers and an integer, determine if any subset of the array can sum
// up to the integer
fun main(args: Array<String>) {
	val arr = intArrayOf(1, 6, 4)
	val ints = intArrayOf(7, // true
			5, // true
			8, // false
			3, // false
			11, // true
			9, // false
			10) // true
//	ints.forEach { println(arr.subsetSum(it)) }
//	println()
//	ints.forEach { println(arr.subsetSum2(it)) }
	ints.forEach {
		println(arr.toOneArray().subsetSumRedo(it))
	}
}

// O(size * n)
fun IntArray.subsetSum(n: Int): Boolean {
	// dp[i][j] = whether any subset of this[i..size - 1] can sum up to j
	// dp[i][j] = false, if j <= 0 or i >= size
	//          = true, if arr[i] == j
	//          = dp[i + 1][j - this[i]] || dp[i + 1][j], o/w
	val dp = Array(size + 1) { BooleanArray(n + 1) }
	for (i in size - 1 downTo 0) {
		for (j in 1..n) {
			dp[i][j] = when {
				this[i] == j -> true
				j - this[i] < 0 -> dp[i + 1][j]
				else -> dp[i + 1][j - this[i]] || dp[i + 1][j]
			}
		}
	}
	return dp[0][n]
}


// O(2^n)
fun IntArray.subsetSum2(n: Int, idx: Int = 0): Boolean {
	if (idx == size) {
		return n == 0
	}

	return subsetSum2(n, idx + 1) || subsetSum2(n - this[idx], idx + 1)
}

fun OneArray<Int>.subsetSumRedo(T: Int): Set<Int>? {
	val A = this
	val n = size

	// dp[i, j]: a subset of A[1..i] that sums up to j
	// note that empty set is a subset of any set that sums up to 0
	// so we use null to represent the case of no such set exists
	// memoization structure: 2d arr dp[0..n, 0..T]
	val dp = Array(n + 1) { Array<HashSet<Int>?>(T + 1) { null } }
	// note that each cell contains a subset of A, taking space O(n)
	// space: O(Tn * n) = O(Tn^2)

	// base case:
	// dp[i, 0] = { } for all i
	for (i in 0..n) {
		dp[i, 0] = HashSet()
	}
	// dp[0, j] = null for all j > 0
	// dp[i, j] = null for all j < 0

	// recursive case:
	// dp[i, j] = NOT include A[i]: dp[i - 1, j] if any
	//            include A[i]: find dp[i - 1, j - A[i]]
	// dependency: dp[i, j] depends on dp[i - 1, j], dp[i - 1, j - A[i]]
	//             that is entries above
	// eval order: outer loop for i increasing from 1 to n
	for (i in 1..n) {
		// inner loop for j with no specific order, say 1..T
		for (j in 1..T) {
			if (dp[i - 1, j] != null) {
				dp[i, j] = HashSet(dp[i - 1, j])
			} else {
				if (j - A[i] >= 0 && dp[i - 1, j - A[i]] != null) {
					dp[i, j] = HashSet(dp[i - 1, j - A[i]])
					dp[i, j]!!.add(A[i])
				}
				// o/w dp[i, j] = null as initialized
			}
		}
	}
	// we are filling a table of O(Tn), with each step (at worst case)
	// taking O(n) work for copying all elements of A
	// time: O(Tn^2)

	// we want to find the subset of A[1..n] that sums up to T
	return dp[n, T]
}
