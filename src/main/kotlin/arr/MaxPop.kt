package arr

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
	println(testArr.getMostPopulationYearOptimize())
}

fun Array<Pair<Int, Int>>.getMostPopulationYear(): Int {
	val minBirthYear = this.minBy { it.first }!!.first
	val maxDeathYear = this.maxBy { it.second }!!.second

	val year = IntArray(maxDeathYear - minBirthYear + 1)
	forEach { (birthYear, deathYear) ->
		(birthYear..deathYear).forEach {
			year[it - minBirthYear]++
		}
	}

	return year.withIndex().maxBy { it.value }!!.index + minBirthYear
}

fun Array<Pair<Int, Int>>.getMostPopulationYearOptimize(): Int {
	val minBirthYear = this.minBy { it.first }!!.first
	val maxDeathYear = this.maxBy { it.second }!!.second

	val year = IntArray(maxDeathYear - minBirthYear + 1)
	forEach { (birthYear, deathYear) ->
		year[birthYear - minBirthYear]++
		year[deathYear - minBirthYear]--
	}

	year.indices
			.filter { it > 0 }
			.forEach { year[it] += year[it - 1] }
	return year.withIndex().maxBy { it.value }!!.index + minBirthYear
}

