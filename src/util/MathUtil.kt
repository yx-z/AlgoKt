package util

// multi-parameter version of util.min/util.max
// ex. util.min(i1, i2, i3, i4), util.max(i1, i2, i3, i4, i5)
fun <T : Comparable<T>> max(vararg ts: T) = ts.max()
		?: throw NullPointerException("no maximum value")

fun <T : Comparable<T>> min(vararg ts: T) = ts.min()
		?: throw NullPointerException("no minimum value")
