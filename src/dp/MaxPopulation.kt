package dp

fun main(args: Array<String>) {
	// given a list of [birth year, death year]
	// find the year that the most people are alive
	val testArr = arrayOf(
			1961 to 2000,
			1978 to 2015,
			1858 to 1912,
			1900 to 1950,
			1992 to 2025,
			1990 to 1991
	)
	println(testArr.getMostPopulationYear())
}

fun Array<Pair<Int, Int>>.getMostPopulationYear(): Int {
	val minBirtYear = this.minBy { it.first }!!.first
	val maxDeathYear = this.maxBy { it.second }!!.second
	val range = maxDeathYear - minBirtYear + 1

	val year = IntArray(range)
	forEach { (birthYear, deathYear) ->
		(birthYear..deathYear).forEach {
			year[it - minBirtYear]++
		}
	}

	return year.withIndex().maxBy { it.value }!!.index + minBirtYear
}