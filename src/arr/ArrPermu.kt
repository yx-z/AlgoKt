package arr

fun main(args: Array<String>) {
	val arrList = arrayListOf(1, 3, 5)
	// order doesn't matter
	// [
	//      [1, 3, 5],
	//      [1, 5, 3],
	//      [3, 1, 5],
	//      [3, 5, 1],
	//      [5, 1, 3],
	//      [5, 3, 1]
	// ]
	println("PERM:${arrList.permu()}")
	println("ORIG:$arrList") // not changing the original list
}

fun ArrayList<Int>.permu(): ArrayList<ArrayList<Int>> {
	if (size == 1) {
		return arrayListOf(this)
	}
	val curr = removeAt(0)
	val all = ArrayList<ArrayList<Int>>()
	val sub = permu()
	sub.forEach { list ->
		(0..(list.size)).forEach {
			val new = ArrayList<Int>()
			new.addAll(list)
			new.add(it, curr)
			all.add(new)
		}
	}
	add(0, curr)
	return all
}
