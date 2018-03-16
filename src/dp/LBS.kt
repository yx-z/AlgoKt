package dp

// longest bitonic subsequence

// X[1..n] is bitonic if there exists i: 1 < i < n, X[1..i] is inscreasing and X[i..n] is decreasing

// find the length of lbs of A[1..n]
fun main(args: Array<String>) {
	val a = intArrayOf(1, 11, 2, 10, 4, 5, 2, 1)
	println(a.lbs())
}

fun IntArray.lbs(): Int {
	val A = this
	val n = A.size

	if (n < 3) {
		return 0
	}
	
	// inc(i): length of LIS that ends @ A[i]
	// 1d array inc[1..n] : inc[i] = inc(i)
	val inc = IntArray(n)
	// dec(i): length of LDS that starts @ A[i]
	// 1d array dec[1..n] : dec[i] = dec(i)
	val dec = IntArray(n)
	// space complexity: O(n)

	// base case:
	// inc(1) = 1
	// dec(n) = 1
	inc[0] = 1
	dec[n - 1] = 1

	// recursive case:
	// inc(i) = max{ inc(k) + 1 } for k in 1 until i and A[k] < A[i]
	// dec(i) = max{ dec(k) + 1 } for k in i + 1..n and A[k] < A[i]
	// dependency: inc(i) depends on inc(k) where k < i, i.e. entries to the left
	//             dec(i) depends on dec(k) where k > i, i.e. entries to the right
	// evaluation order: for inc(i), iterate i,k from 1 to n (left to right)
	for (i in 1 until n) {
		inc[i] = (inc.filterIndexed { k, _ -> k < i && A[k] < A[i]}.max() ?: 0) + 1
	}

	// for dec(i), iterate i, k from n down to 1 (right to left)
	for (i in n - 2 downTo 0) {
		dec[i] = (dec.filterIndexed { k, _ -> k > i && A[k] < A[i]}.max() ?: 0) + 1
	}

	// we want max_i{ inc(i) + dec(i) }
	return (0 until n).map { inc[it] + dec[it] }.max() ?: 0
}