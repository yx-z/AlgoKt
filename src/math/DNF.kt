package math

import util.OneArray
import util.oneArrayOf

// given a Disjunctive Normal Form (DNF), i.e. ORing several conjunctions, ex.
// (!x & y & !z) | (y & z) | (x & !y & !z), determine if it is SATisfiable, i.e.
// find there exists x, y, z,... : DNF can output true in polynomial time

fun main(args: Array<String>) {
	// from the example above
	val DNF = oneArrayOf(
			oneArrayOf("!x", "y", "!z"),
			oneArrayOf("y", "z"),
			oneArrayOf("x", "!y", "!z"))

	println(DNF.sat())
}

fun OneArray<OneArray<String>>.sat() = any { !it.containsConflict() }

fun OneArray<String>.containsConflict(): Boolean {
	val (t, f) = asSequence().partition { it[0] == '!' }
	return f.any { t.contains(it.substring(1)) }
}

// note that this is not Conjunctive Normal Form (CNF) which is NP-hard
// google related topics for more info