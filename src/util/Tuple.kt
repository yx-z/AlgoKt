package util

// modifiable pairs and tuples
class Tuple2<A, B>(var first: A, var second: B) {
	operator fun component1(): A = first
	operator fun component2(): B = second

	override fun toString() = "($first, $second)"

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Tuple2<*, *>) return false

		if (first != other.first) return false
		if (second != other.second) return false

		return true
	}

	override fun hashCode(): Int {
		var result = first?.hashCode() ?: 0
		result = 31 * result + (second?.hashCode() ?: 0)
		return result
	}
}

class Tuple3<A, B, C>(var first: A, var second: B, var third: C) {
	operator fun component1(): A = first
	operator fun component2(): B = second
	operator fun component3(): C = third

	override fun toString() = "($first, $second, $third)"

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Tuple3<*, *, *>) return false

		if (first != other.first) return false
		if (second != other.second) return false
		if (third != other.third) return false

		return true
	}

	override fun hashCode(): Int {
		var result = first?.hashCode() ?: 0
		result = 31 * result + (second?.hashCode() ?: 0)
		result = 31 * result + (third?.hashCode() ?: 0)
		return result
	}
}

class Tuple4<A, B, C, D>(var first: A,
                         var second: B,
                         var third: C,
                         var fourth: D) {
	operator fun component1(): A = first
	operator fun component2(): B = second
	operator fun component3(): C = third
	operator fun component4(): D = fourth

	override fun toString() = "($first, $second, $third, $fourth)"

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Tuple4<*, *, *, *>) return false

		if (first != other.first) return false
		if (second != other.second) return false
		if (third != other.third) return false
		if (fourth != other.fourth) return false

		return true
	}

	override fun hashCode(): Int {
		var result = first?.hashCode() ?: 0
		result = 31 * result + (second?.hashCode() ?: 0)
		result = 31 * result + (third?.hashCode() ?: 0)
		result = 31 * result + (fourth?.hashCode() ?: 0)
		return result
	}
}

class Tuple5<A, B, C, D, E>(var first: A,
                            var second: B,
                            var third: C,
                            var fourth: D,
                            var fifth: E) {
	operator fun component1(): A = first
	operator fun component2(): B = second
	operator fun component3(): C = third
	operator fun component4(): D = fourth
	operator fun component5(): E = fifth

	override fun toString() = "($first, $second, $third, $fourth, $fifth)"

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Tuple5<*, *, *, *, *>) return false

		if (first != other.first) return false
		if (second != other.second) return false
		if (third != other.third) return false
		if (fourth != other.fourth) return false
		if (fifth != other.fifth) return false

		return true
	}

	override fun hashCode(): Int {
		var result = first?.hashCode() ?: 0
		result = 31 * result + (second?.hashCode() ?: 0)
		result = 31 * result + (third?.hashCode() ?: 0)
		result = 31 * result + (fourth?.hashCode() ?: 0)
		result = 31 * result + (fifth?.hashCode() ?: 0)
		return result
	}
}

// `tu` is a custom name for `to`, so that we can distinguish between Tuple and Pair
infix fun <A, B> A.tu(second: B) = Tuple2(this, second)

infix fun <A, B, C> Tuple2<A, B>.tu(third: C) = Tuple3(first, second, third)

infix fun <A, B, C, D> Tuple3<A, B, C>.tu(fourth: D) = Tuple4(first, second, third, fourth)

infix fun <A, B, C, D, E> Tuple4<A, B, C, D>.tu(fifth: E) = Tuple5(first, second, third, fourth, fifth)
