package misc

const val EMPTY_CHAR = 0.toChar()
const val START_CHAR = 'a'
const val END_CHAR = 'z'

const val NUM_CHARS = END_CHAR - START_CHAR + 1

const val EMPTY_STRING = ""

class Trie {
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
			val idx = c.idx()
			val currNode = if (currChildren[idx] != null) {
				currChildren[idx]
			} else {
				Node(c)
			}
			currChildren[idx] = currNode
			currChildren = currNode!!.children

			if (i == word.length - 1) {
				currNode.address = addr
			}
		}
	}

	fun contains(word: String) = search(word)?.isEnd() == true

	fun startWith(prefix: String) = search(prefix) != null

	private fun search(word: String): Node? {
		var currMap = root.children
		word.forEachIndexed { i, c ->
			val currNode = currMap[c.idx()] ?: return null

			if (i == word.length - 1) {
				return currNode
			}

			currMap = currNode.children
		}
		return null
	}

	operator fun get(word: String) = search(word)?.address ?: ""

	operator fun get(i: Int) = root.children[i]
}

fun main(args: Array<String>) {
	val testTrie = Trie()
	testTrie.insert("abcd", "501 s 6th st")
	testTrie.insert("abc", "601 s white st")
	testTrie.insert("bcd", "309 green st")

	println(testTrie["abcd"]) // 501 s 6th st
	println(testTrie[0]) // node 'a'

	println(testTrie.contains("bcd")) // true
	println(testTrie.contains("ab")) // false
	println(testTrie.startWith("ab")) // true
	println(testTrie.contains("asfd")) // false
}
