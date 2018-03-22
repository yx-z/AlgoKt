package dp

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
// letters from 'a' to 'z' and Value['a'..'z'] represents the value for them

// assume you can find all English words in a set of size <= 7 in O(1) time
// and the Value lookup also costs O(1) time
fun main(args: Array<String>) {
	val Letter = "adogbookalgorithm".toCharOneArray()
	val Value = Array(26) { it + 1 }
	TODO()
}
