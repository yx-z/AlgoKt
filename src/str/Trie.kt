package str

class Trie {
	private val root: Node = Node()

	private class Node(private val c: Char) {
		val children = HashMap<Char, Node>()
		var end = false

		constructor() : this(0.toChar())

		override fun toString() = "$c($end) -> $children"
	}

	override fun toString() = root.children.toString()

	fun insert(word: String) {
		var currMap = root.children
		word.withIndex().forEach { (i, c) ->
			val currNode = if (currMap.containsKey(c)) {
				currMap[c]!!
			} else {
				Node(c)
			}
			currMap.put(c, currNode)
			currMap = currNode.children

			if (i == word.length - 1) {
				currNode.end = true
			}
		}
	}

	fun contains(word: String) = search(word)?.end == true

	fun startWith(prefix: String) = search(prefix) != null

	private fun search(word: String): Node? {
		var currNode = root
		var currMap = root.children
		word.forEach {
			if (!currMap.containsKey(it)) {
				return null
			}

			currNode = currMap[it]!!
			currMap = currNode.children
		}
		return currNode
	}
}

fun main(args: Array<String>) {
	val testTrie = Trie()
	testTrie.insert("abcd")
	testTrie.insert("abc")
	testTrie.insert("bcd")

	println(testTrie)

	println(testTrie.contains("bcd"))
	println(testTrie.contains("ab"))
	println(testTrie.startWith("ab"))
	println(testTrie.contains("asfd"))
}
