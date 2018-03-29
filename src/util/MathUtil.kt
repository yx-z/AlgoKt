package util

// multi-parameter version of util.min/max
// ex. util.min(i1, i2, i3, i4), max(i1, i2, i3, i4, i5)
fun <T : Comparable<T>> max(vararg ts: T) = ts.max()
		?: throw NullPointerException("no maximum value")

fun <T : Comparable<T>> min(vararg ts: T) = ts.min()
		?: throw NullPointerException("no minimum value")

fun <T, R : Comparable<R>> maxBy(vararg ts: T, selector: (T) -> R) = ts.maxBy(selector)
		?: throw NullPointerException("no maximum value")

fun <T, R : Comparable<R>> minBy(vararg ts: T, selector: (T) -> R) = ts.minBy(selector)
		?: throw NullPointerException("no minimum value")

/**
 * an Int that is large enough to represent infinity
 * that can also prevents overflow comparing to some small perturbations
 */
const val INF = Int.MAX_VALUE / 2
