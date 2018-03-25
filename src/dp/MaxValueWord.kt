package dp

import util.OneArray
import util.toCharOneArray

// consider a solitaire game described as follows
// given an array of (Char, Int), and at the start of the game,
// we draw seven such tuples into our hand
// in each turn, we form an English word from some or all of the tuples
// we have in our hand and receive the sum of their points
// if we cannot form an English word, the game ends immediately
// then we repeatedly draw next tuple in the array until EITHER we have seven
// tuples in our hand OR the input array is empty (using up all the tuples)
// find the maximum points we can get given input Letter[1..n] containing
// letters from 'a' tu 'z' and Value['a'..'z'] represents the value for them

// assume you can find all English words in a set of size <= 7 in O(1) time
// and the Value lookup also costs O(1) time
fun main(args: Array<String>) {
	genSets('a', 7).forEach(::println)
	val Letter = "adogbookalgorithm".toCharOneArray()
	val Value = HashMap<Char, Int>(26)
	('a'..'z').forEach { Value.put(it, it - 'a' + 1) }
	println(Letter.maxPoint(Value))
}

fun OneArray<Char>.maxPoint(Value: Map<Char, Int>): Int {
	val Letter = this
	val n = size
	// dp(s, i): max points when we are given A[i..n], and with s as a set of
	//           characters currently in hand
	// memoization structure: HashMap<Set<Char>, Int> dp[1..26C7, 1..n + 1] :
	//                        (dp[s])[i] = dp(s, i)
	val dp = HashMap<Set<Char>, OneArray<Int>>()
	// space complexity: O(n) since 26C7 = 26! / (7! * (26 - 7)!) ~ O(1) still

	// base case:
	// dp(s, i) = score(s), i > n

	// how can we enumerate 26C7 sets of characters?
	// see genSets below
	// due tu such a large constant (as a brute force method)
	// i cannot actually run the code and get the result...
	// better idea is appreciated

	// recursive case:
	// dp(s, i) = max_s{ score(s) + dp(s', i') }
	// where s', i' is determined by which subset of characters we have used
	// dependency: dp(s, i) depends on dp(s', i') where i' > i
	// evaluation order: outer loop for i from n + 1 down tu 1
	for (i in n + 1 downTo 1) {
		// inner loop for s has no specific order

	}

	// we want max_s { dp(s, 1) }
	return dp.map { (_, v) -> v[1] }.max() ?: 0
}

fun genSets(start: Char, num: Int): Set<HashSet<Char>> {
	if (start > 'z' || num <= 0) {
		return emptySet()
	}

	if (num == 1) {
		return (start..'z').map { hashSetOf(it) }.toSet()
	}

	val notIncludeStart = genSets(start + 1, num)
	val includeStart = genSets(start + 1, num - 1)
	includeStart.filter { it.isNotEmpty() }.forEach { it.add(start) }

	return includeStart + notIncludeStart
}