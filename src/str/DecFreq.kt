package str

// rearrange string to a decreasing frequency of characters
// use alphabetical order in case of a tie
fun main(args: Array<String>) {
	println("abbccddd".decFreq()) // dddbbcca
}

fun String.decFreq(): String {
	val map = HashMap<Char, Int>()
	forEach { map.put(it, (map[it] ?: 0) + 1) }

	val list = ArrayList<Map.Entry<Char, Int>>()
	map.forEach { list.add(it) }

	val sb = StringBuilder()
	list.sortedWith(compareBy({ -it.value }, { it.key }))
			.forEach {
				sb.append(it.key * it.value)
			}
	return sb.toString()
}

operator fun Char.times(i: Int) = toString().repeat(i)