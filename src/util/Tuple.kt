package util

// modifiable tuples
class Tuple2<A, B>(var first: A, var second: B) {
	operator fun component1(): A = first
	operator fun component2(): B = second

	override fun toString() = "($first, $second)"
}

class Tuple3<A, B, C>(var first: A, var second: B, var third: C) {
	operator fun component1(): A = first
	operator fun component2(): B = second
	operator fun component3(): C = third

	override fun toString() = "($first, $second, $third)"
}

class Tuple4<A, B, C, D>(var first: A, var second: B, var third: C, var fourth: D) {
	operator fun component1(): A = first
	operator fun component2(): B = second
	operator fun component3(): C = third
	operator fun component4(): D = fourth

	override fun toString() = "($first, $second, $third, $fourth)"
}

infix fun <A, B> A.to(second: B) = Tuple2(this, second)

infix fun <A, B, C> Tuple2<A, B>.to(third: C) = Tuple3(first, second, third)

infix fun <A, B, C, D> Tuple3<A, B, C>.to(fourth: D) = Tuple4(first, second, third, fourth)
