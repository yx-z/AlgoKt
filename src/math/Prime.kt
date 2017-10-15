package math

fun main(args: Array<String>) {
	val LIMIT = 100000
	
	println(time { sievePrime(LIMIT) })
	println(time { listPrime(LIMIT) })
	println(time { listByIsPrime(LIMIT) })
}

/**
 * return a list of sievePrime numbers (>= 2) <= i (i >= 2)
 */
fun sievePrime(i: Int): List<Int> {
	val p = ArrayList<Int>()
	if (i <= 1) {
		return p
	}

	// b[i] = whether i is prime (true) or not (false)
	// ex. b[2] = true since 2 is a prime number
	// ex. b[4] = false since 4 = 2 * 2
	val b = Array(i) { false }
	
	for (n in 2 until i) {
		if (!b[n]) {
			p.add(n)
			var mult = 2
			while (n * mult < i) {
				b[n * mult] = true
				mult++
			} 
		}
	}
	
	return p
}

// alternative method
// slower
fun listPrime(i: Int): List<Int> {
	val p = ArrayList<Int>()
	if (i <= 1) {
		return p
	}

	p.add(2)
	for (n in 2 until i) {
		if (p.all { n % it != 0 }) {
			p.add(n)
		}
	}

	return p
}

// alternative method
// call isPrime() for each number
fun listByIsPrime(i: Int): List<Int> {
	val p = ArrayList<Int>()
	if (i <= 1) {
		return p
	}
	
	(2..i)
		.filter { it.isPrime() }
		.forEach { p.add(it) }
	
	return p
}


fun Int.isPrime() = (2..Math.sqrt(this.toDouble()).toInt()).all { this % it != 0 }

fun time(f: () -> Unit): Long {
	val start = System.currentTimeMillis()
	f()
	return System.currentTimeMillis() - start
}