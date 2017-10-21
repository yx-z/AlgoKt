package math

import java.util.*

fun main(args: Array<String>) {
	val LIMIT = 100000

	println(time { sievePrime(LIMIT) })
	println(time { listPrime(LIMIT) })
	println(time { listByIsPrime(LIMIT) })

	println(4.thPrime()) // 4th prime = 7
	println(Arrays.toString(4.thPrimes())) // first 4 primes = [2, 3, 5, 7]
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
fun listByIsPrime(i: Int) = if (i <= 1) {
	ArrayList()
} else {
	(2..i).filter { it.isPrime() }.toList()
}

fun Int.isPrime() = (2..Math.sqrt(this.toDouble()).toInt()).all { this % it != 0 }

fun time(f: () -> Unit): Long {
	val start = System.currentTimeMillis()
	f()
	return System.currentTimeMillis() - start
}

fun Int.thPrime(): Int {
	if (this < 1) {
		return -1
	}

	var count = 0
	var num = 1
	while (count < this) {
		num++
		while (!num.isPrime()) {
			num++
		}
		count++
	}
	return num
}

fun Int.thPrimes(): IntArray {
	if (this < 1) {
		return IntArray(0)
	}
	val primes = IntArray(this) { 1 }
	primes[0] = 2
	var num = 3
	(1 until this).forEach {
		while (primes.filterIndexed { idx, _ -> idx < it }.any { num % it == 0 }) {
			num++
		}
		primes[it] = num
		num++
	}

	return primes
}