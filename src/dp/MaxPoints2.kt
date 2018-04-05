package dp

import util.*

// similar to MaxPoints, suppose you are playing a card game
// you and your opponent can see all C[1..n] cards, each having an int value
// if this is your turn, you should always take the leftmost card
// but you can either keep this card and get its value, OR give it to your
// opponent (so it will count to his/her points) and continue taking the
// leftmost card and have another turn

// find the max points you can get if you are playing with another perfect
// player just as you, assuming s/he plays first

// since we are both perfect players, and the total number of points is fixed,
// we can first know the max points i can get if i play first against a perfect
// player, and then subtract it from the total number of points
fun OneArray<Int>.maxPoints2(): Int {
	val C = this
	val n = size

	// dp(i): max points i can get assuming i play first given C[i..n]
	val dp = OneArray(n) { 0 }
	// space: O(n)

	// i get 0 (i.e. giving the card to my opponent
	// when the card has a negative value)
	// o/w, i will take the card
	dp[n] = max(0, C[n])

	// eval order: decreasing i
	for (i in n - 1 downTo 1) {
		dp[i] = max(dp[i + 1], C[i] + C[i + 1..n].sum() - dp[i + 1])
	}
	// time: O(n^2)

	return sum() - dp[1]
}

fun main(args: Array<String>) {
	val C = oneArrayOf(3, -1, 4, 1, 5, 9)
	println(C.maxPoints2()) // 10
}