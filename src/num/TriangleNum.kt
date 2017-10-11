package num

fun main(args: Array<String>) {
	println(levelOfTriangleNumber(1))
	println(levelOfTriangleNumber(5))
}

fun levelOfTriangleNumber(n: Int): Int {
	if (n <= 0) {
		return -1
	}

	val triangle = ArrayList<ArrayList<Int>>()
	triangle.add(arrayListOf(1))

	while (triangle[triangle.size - 1].all { it < n }) {
		val curr = ArrayList<Int>()

		val prev = triangle[triangle.size - 1]

		val prevInsert = ArrayList<Int>()
		prevInsert.add(0)
		prevInsert.addAll(prev)

		val prevAppend = ArrayList<Int>()
		prevAppend.addAll(prev)
		prevAppend.add(0)

		(0 until prevInsert.size).mapTo(curr) { prevAppend[it] + prevInsert[it] }

		triangle.add(curr)
	}

	return if (triangle[triangle.size - 1].contains(n)) {
		triangle.size
	} else {
		-1
	}
}