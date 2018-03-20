package util

// multi-parameter version of util.min/util.max
// ex. util.min(i1, i2, i3, i4), util.max(i1, i2, i3, i4, i5)
fun min(vararg ints: Int) = ints.min()
		?: throw NullPointerException("no minimum value")

fun max(vararg ints: Int) = ints.max()
		?: throw NullPointerException("no maximum value")

