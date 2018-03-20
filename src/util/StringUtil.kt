package util

operator fun String.get(range: IntRange) = substring(range)

operator fun String.times(n: Int) = repeat(n)