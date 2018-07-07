package stack

import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
	val a = arrayOf(1..3, 5..10, 2..4, 9..13)
	// after merged, i.e. simplify the overlapped region, we should have 1..4, 5..13
	println(a.merge())
}

fun Array<IntRange>.merge(): List<IntRange> {
	Arrays.sort(this, { r1, r2 ->
		if (r1.start == r2.start) {
			r1.endInclusive - r2.endInclusive
		} else {
			r1.start - r2.start
		}
	})

	val stack = Stack<IntRange>()
	stack.push(this[0])

	(1 until this.size).forEach {
		val top = stack.pop()
		val curr = this[it]
		if (top.endInclusive >= curr.start) {
			stack.push(top.start..maxOf(top.endInclusive, curr.endInclusive))
		} else {
			stack.push(top)
			stack.push(curr)
		}
	}

	return ArrayList(stack)
}

