package dp

import util.OneArray
import util.get
import util.set
import util.toCharOneArray

// given two strings X and Y, a shuffle of them is interspersing characters of X and Y
// while keeping them in the same order
// ex. "abc" shuffle "123" -> "abc123", "a1b2c3", "1ab23c", ...

// 1. given A[1..m], B[1..n], and C[1..m + n] determine if C is a shuffle of A and B
fun String.isShuffleOf(A: String, B: String): Boolean {
	val C = this // follow the naming convention in the problem
	val m = A.length
	val n = B.length

	// dp(a, b): whether C[1..a + b] is a shuffle of A[1..a] and B[1..b]
	// memoization structure: 2d array dp[0..m, 0..n] : dp[a, b] = dp(a, b)
	val dp = Array(m + 1) { Array(n + 1) { false } }
	// space complexity: O(mn)

	// base case:
	// dp(a, b) = true if a = 0 and b = 0 (empty string is the shuffle of two empty strings)
	//          = false if a !in 0..m || b !in 0..n
	dp[0, 0] = true
	for (a in 1..m) {
		dp[a, 0] = dp[a - 1, 0] && C[a - 1] == A[a - 1]
	}
	for (b in 1..n) {
		dp[0, b] = dp[0, b - 1] && C[b - 1] == B[b - 1]
	}
	// time complexity here: O(m + n)


	// recursive case
	// dp(a, b) = dp(a - 1, b) or dp(a, b - 1) if C[a + b] = A[a] = B[b]
	//          = dp(a - 1, b) if C[a + b] = A[a]
	//          = dp(a, b - 1) if C[a + b] = B[b]
	//          = false o/w
	// dependency: dp(a, b) depends on dp(a - 1, b) and dp(a, b - 1)
	//             that is the entry above and entry to the left
	// evaluation order: outer loop for a from 1 to m (top down)
	for (a in 1..m) {
		// inner loop for b from 1 to n (left to right)
		for (b in 1..n) {
			dp[a, b] = when {
				C[a + b - 1] == A[a - 1] && A[a - 1] == B[b - 1] -> dp[a - 1, b] || dp[a, b - 1]
				C[a + b - 1] == A[a - 1] -> dp[a - 1, b]
				C[a + b - 1] == B[b - 1] -> dp[a, b - 1]
				else -> false
			}
		}
	}
	// time complexity: O(mn)

	// we want dp(m, n)
	return dp[m, n]
}

// a smooth shuffle of X and Y is a shuffle of X and Y
// that never uses more than two consecutive strings of either string
// ex. "abcd" smoothShuffle "1234" -> "ab12cd34", "a12b3cd4", ...
//     but not "abc12d34" since "abc", has length more than two
//     neither is "ab1234cd" (due to "1234")
// 2. given X[1..m], Y[1..n], and Z[1..m + n], determine if Z is a smooth shuffle of X and Y
fun String.isSmoothShuffleOf(X: String, Y: String): Boolean {
	// follow naming conventions in the problem statement
	val Z = this
	val m = X.length
	val n = Y.length

	// ss(i, j) = null if Z[1..i + j] is NOT a smooth shuffle of X[1..i] and Y[1..j]
	//          = set of pairs of possible combination of sources of last two characters
	//            that make Z[1..i + j] to be a smooth shuffle of X[1..i] and Y[1..j] o/w
	// ex. ss(i, j) = [(X, Y), (Y, Y)] means Z[1..i + j] is a smooth shuffle of X[1..i] and Y[1..j]
	//     and the last two characters of Z can be from EITHER (X then Y) OR (Y then Y)
	// memoization structure: 2d array dp[0..m, 0..n]: dp[i, j] = ss(i, j)
	val dp = Array(m + 1) { Array(n + 1) { HashSet<Pair<Char, Char>>(0) } }
	// the longest list is just [(X, X), (X, Y), (Y, X), (Y, Y)] taking O(1) space
	// space complexity: O(m * n * 2 * 4) = O(m * n)

	// base case:
	// ss(i, j) = [] if i !in 0..m or j !in 0..n
	// ss(0, j) = [] if j > 2
	// ss(i, 0) = [] if i > 2
	// ss(0, 0) = [(X, X), (X, Y), (Y, X), (Y, Y)]
	dp[0, 0] = hashSetOf('X' to 'Y', 'X' to 'X', 'Y' to 'X', 'Y' to 'Y')
	// ss(1, 0) = [] if Z[1] != X[1]
	//          = [(X, X), (Y, X)] o/w
	if (Z[0] == X[0]) {
		dp[1, 0] = hashSetOf('X' to 'X', 'Y' to 'X')
	}
	// ss(2, 0) = [] if Z[1..2] != X[1..2]
	//          = [(X, X)] o/w
	if (Z[0..1] == X[0..1]) {
		dp[2, 0] = hashSetOf('X' to 'X')
	}
	// ss(0, 1) = [] if Z[1] != Y[1]
	//          = [(X, Y), (Y, Y)] o/w
	if (Z[0] == Y[0]) {
		dp[0, 1] = hashSetOf('X' to 'Y', 'Y' to 'Y')
	}
	// ss(0, 2) = [] if Z[1..2] != Y[1..2]
	//          = [(Y, Y)] o/w
	if (Z[0..1] == Y[0..1]) {
		dp[0, 2] = hashSetOf('Y' to 'Y')
	}

	// recursive case:
	// ss(i, j) = [do both of the following] if Z[i + j] = X[i] = Y[j]
	//          = [copy ss(i - 1, j), remove (X, X) if it exists, then transform (A, B) to (B, X)]
	//            if Z[i + j] = X[i] != Y[j]
	//          = [copy ss(i, j - 1), remove (Y, Y) if it exists, then transform (A, B) to (B, Y)]
	//            if Z[i + j] = Y[j] != X[i]
	//          = [] o/w i.e. Z[i + j] != X[i] and Z[i + j] != Y[j]
	// dependency: ss(i, j) depends on ss(i - 1, j) and ss(j, i - 1)
	//             that is, entries to the left and entries above
	// evaluation order: outer loop for i from 1 to m (top down)
	for (i in 1..m) {
		// inner loop for j from 1 to n (left to right)
		for (j in 1..n) {
			when {
				Z[i + j - 1] == X[i - 1] && X[i - 1] == Y[j - 1] -> {
					dp[i - 1, j]
							.filterNot { it.first == 'X' && it.second == 'X' }
							.forEach { dp[i, j].add(it.second to 'X') }

					dp[i, j - 1]
							.filterNot { it.first == 'Y' && it.second == 'Y' }
							.forEach { dp[i, j].add(it.second to 'Y') }
				}
				Z[i + j - 1] == X[i - 1] -> {
					dp[i - 1, j]
							.filterNot { it.first == 'X' && it.second == 'X' }
							.forEach { dp[i, j].add(it.second to 'X') }
				}
				Z[i + j - 1] == Y[j - 1] -> {
					dp[i, j - 1]
							.filterNot { it.first == 'Y' && it.second == 'Y' }
							.forEach { dp[i, j].add(it.second to 'Y') }
				}
				// else -> do nothing, keep the current set empty
			}
		}
	}
	// time complexity: O(m * n * (8 + 8)) = O(m * n)

	// we want ss(m, n) != []
	return dp[m][n].isNotEmpty()
}

// a solution w/o set operations
fun OneArray<Char>.isSmoothShuffleOf(X: OneArray<Char>, Y: OneArray<Char>): Boolean {
	// assuming Z has the length m + n
	// assuming m, n >= 2, o/w solve by brute force
	val Z = this
	val m = X.size
	val n = Y.size

	X.getterIndexOutOfBoundsHandler = { 0.toChar() }
	Y.getterIndexOutOfBoundsHandler = { 0.toChar() }

	// dp[i, j, w, c]: whether Z[1..i + j] is a smooth shuffle of X[1..i] and
	// Y[1..j] : the last c characters are from w (0 for X, 1 for Y), c = 1 or 2
	val dp = Array(m + 1) { Array(n + 1) { Array(2) { Array(3) { false } } } }
	// space: O(mn)

	// dp[i, j, w, c] = true if i, j, c = 0 for all w
	dp[0, 0, 1, 0] = true
	dp[0, 0, 0, 0] = true

	// dp[0, j, w, c] = true if Z[1..j] = Y[1..j], j <= 2, w = 1, and c = j
	//                = false o/w
	for (j in 1..2) {
		if (Z[1..j] == Y[1..j]) {
			dp[0, j, 1, j] = true
		}
	}
	// dp[i, 0, w, c] = true if Z[1..i] = X[1..i], i <= 2, w = 0, and c = i
	//                = false o/w
	for (i in 1..2) {
		if (Z[1..i] == X[1..i]) {
			dp[i, 0, 0, i] = true
		}
	}

	// dp[i, j, w, c] = false if Z[i + j] != either of X[i] or Y[j]
	//                = dp[i - 1, j, 1, c] || dp[i - 1, j, 0, c - 1] if Z[i + j] = X[i] != Y[j]
	//                = dp[i, j - 1, 0, c] || dp[i, j - 1, 1, c - 1] if Z[i + j] = Y[j] != X[i]
	//                = || { previous two cases } if Z[i + j] = X[i] = Y[j]
	// dependency: dp[i, j, w, c] depends on dp[i - 1, j, w, c], dp[i, j - 1, w, c]
	// for some w and c, that is entries in the previous tables
	// eval order: increasing i/j from 1 to m/n
	for (i in 1..m) {
		for (j in 1..n) {
			for (w in 0..1) {
				for (c in 1..2) {
					if (Z[i + j] != X[i] && Z[i + j] != Y[j]) {
						continue
					}

					// last character can be from X
					if (Z[i + j] == X[i]) {
						if (w == 1) {
							continue
						} else { // w = 0
							dp[i, j, w, c] = dp[i - 1, j, 0, c - 1] || dp[i - 1, j, 1, 1] || dp[i - 1, j, 1, 2]
						}
					}

					// last character can be from Y
					if (Z[i + j] == Y[j]) {
						if (w == 0) {
							continue
						} else { // w = 1
							dp[i, j, w, c] = dp[i, j - 1, 1, c - 1] || dp[i, j - 1, 0, 1] || dp[i, j - 1, 0, 2]
						}
					}
				}
			}
		}
	}


	// we want to find if for some w and c, dp[m, n, w, c] = true
	return dp[m, n].any { it.any { it } }
}

fun main(args: Array<String>) {
	val X = "12".toCharOneArray()
	val Y = "ab".toCharOneArray()
	val Z = "12ab".toCharOneArray()
	println(Z.isSmoothShuffleOf(X, Y))
}

