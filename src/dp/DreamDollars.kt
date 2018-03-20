package dp

// given a util.set of bills with the following values
val bills = intArrayOf(365, 91, 52, 28, 13, 7, 4, 1)

// a greedy algorithm to make up target money is always taking the largest bill that does not exceed
// the remaining amound. ex. $122 = $91 + $28 + $1 + $1 + $1 -> 5
fun billsGreedy(k: Int): Int {
	var rem = k
	var count = 0
	bills.forEach {
		while (rem >= it) {
			rem -= it
			count++
		}
	}
	return count
}

// 1. describe a recursive algorithm that computes the minimum number of bills needed to make up $k
fun billsRec(k: Int): Int {
	if (k == 0) {
		return 0
	}
	return bills.filter { k >= it }.map { billsRec(k - it) }.min()!! + 1
}

// 2. a DP algorithm of the above
fun billsDP(k: Int): Int {
	// dp[i] = minimum number of bills to make up i
	// dp[i] = 0, if i == 0
	// dp[i] = util.min(dp[i - bill]) + 1 for bill : i - bill >= 0
	val dp = IntArray(k + 1)
	for (i in 1..k) {
		dp[i] = bills.filter { i - it >= 0 }.map { dp[i - it] }.min()!! + 1
	}

	return dp[k]
}

fun main(args: Array<String>) {
//	prettyPrintln(billsGreedy(122))
//	prettyPrintln(billsRec(21))
//	prettyPrintln(billsDP(122))

// 3. give an example that such algorithm fails to be the option with least number of bills used
	var amount = 1
	while (billsGreedy(amount) == billsDP(amount)) {
		amount++
	}
	println(amount)
}
