package dp

// longest bitonic subsequence

// X[1..n] is bitonic if there exists i: 1 < i < n, X[1..i] is inscreasing and X[i..n] is decreasing

// find the length of lbs of A[1..n]
fun main(args: Array<String>) {
	val a = intArrayOf(1, 5, 2)
	println(a.lbs()) // 3
}

fun IntArray.lbs(): Int {
	// dp(i, j): [length of lbs for A[i..j], leftMin, max, rightMin]
	// dp(i, j) = [0, -inf, +inf, -inf] if i > j - 2
	//          = [3, A[j - 2], A[j], A[j - 1]] if i == j - 2 && A[j - 1] > A[j - 2] && A[j - 1] > A[j]
	//          = [0, -inf, +inf, -inf] if i == j - 2 && (A[j - 1] <= A[j - 2] || A[j - 1] <= A[j])
	//          = {
	//                (len, lMin, rMin) = dp(i + 1, j - 1)
	//                if (A[i] < lMin)
	//                    len++
	//                    lMin = A[i]
	//                if (A[j] < rMin)
	//                    len++
	//                    rMin = A[j]
	//                return (len, lMin, rMin)
	//            } o/w
	val dp = Array(size) { IntArray(size) }
	for (i in size - 1 downTo 0) {
		for (j in 0 until size) {
		}
	}
	return dp[0][size - 1]
}
