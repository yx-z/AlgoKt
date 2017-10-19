package str

const val EMPTY_CHAR = 0.toChar()
const val START_CHAR = 'a'
const val END_CHAR = 'z'

const val NUM_CHARS = END_CHAR - START_CHAR + 1

const val EMPTY_STRING = ""

class AddrTrie {
	private val root = Node()

	class Node(private val c: Char = EMPTY_CHAR, var address: String = EMPTY_STRING) {
		val children = arrayOfNulls<Node>(NUM_CHARS)

		fun isEnd() = address.isNotEmpty()

		override fun toString() = "$c${if (isEnd()) {
			" ($address)"
		} else {
			" -> ${children.filter { it != null }.toList()}"
		}}"
	}

	override fun toString(): String = root.children.filter { it != null }.joinToString("\n")

	private fun Char.idx() = this - START_CHAR

	fun insert(word: String, addr: String) {
		var currChildren = root.children
		word.withIndex().forEach { (i, c) ->
			val currNode = if (currChildren[c.idx()] != null) {
				currChildren[c.idx()]
			} else {
				Node(c)
			}
			currChildren[c.idx()] = currNode
			currChildren = currNode!!.children

			if (i == word.length - 1) {
				currNode.address = addr
			}
		}
	}

	fun contains(word: String) = search(word)?.isEnd() ?: false

	fun startWith(prefix: String) = search(prefix) != null

	private fun search(word: String): Node? {
		var currNode = root
		var currMap = root.children
		word.forEach {
			if (currMap[it.idx()] == null) {
				return null
			}

			currNode = currMap[it.idx()]!!
			currMap = currNode.children
		}
		return currNode
	}

	operator fun get(word: String) = search(word)?.address ?: ""

	operator fun get(i: Int) = root.children[i]
}

fun main(args: Array<String>) {
	val testTrie = AddrTrie()
	testTrie.insert("abcd", "501 s 6th st")
	testTrie.insert("abc", "601 s white st")
	testTrie.insert("bcd", "309 green st")

	println(testTrie)

	println(testTrie["abcd"])

	println(testTrie.contains("bcd")) // true
	println(testTrie[0])

	println(testTrie.contains("ab")) // false

	println(testTrie.startWith("ab")) // true

	println(testTrie.contains("asfd")) // false
}
