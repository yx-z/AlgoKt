import util.times
import java.io.File

/**
 * test cases
 */
fun main(args: Array<String>) {
	println("===== JSON -> XML =====")
	println("example.json".json2xml())

	println("===== XML -> JSON =====")
	println("example.xml".xml2Json())
}

/**
 * internal data structure to preserve the hierarchical ordering for both xml and json file
 * i.e. abstraction of the markup file
 */
data class Node(var key: String,
                val value: String = "",
                val parent: Node? = null) {
	val children = if (value.isBlank()) {
		ArrayList<Node>()
	} else {
		null
	}

	fun toXmlStr(indent: Int = 0): String {
		val sb = StringBuilder()
		sb
				.append("\t" * indent)
				.append("<$key>")
		if (value.isBlank()) {
			sb.append("\n")
			children!!.forEach {
				sb.append(it.toXmlStr(indent + 1))
			}
			sb.append("\t" * indent)
		} else {
			sb.append(value)
		}

		return sb
				.append("</$key>\n")
				.toString()
	}

	fun toJsonStr(indent: Int = 0, isLastChild: Boolean = true, inArray: Boolean = false): String {
		val sb = StringBuilder()
		val isArray = indent != 0 &&
				key[key.length - 1] == 's' &&
				children != null &&
				children.all { it.key == key.substring(0, key.length - 1) }

		sb.append("\t" * indent)
		if (indent != 0 && !inArray) {
			sb.append(if (isArray) {
				"\"${children!![0].key}\":"
			} else {
				"\"$key\":"
			})
		}
		if (value.isBlank()) {
			if (indent != 0 && !inArray) {
				sb.append(" ")
			}
			sb.append(if (isArray) {
				"["
			} else {
				"{"
			})
			sb.append("\n")
			children!!
					.withIndex()
					.forEach { (idx, node) ->
						sb.append(node.toJsonStr(indent + 1, idx == children.size - 1, inArray = isArray))
					}
			sb.append("\t" * indent)
			sb.append(if (isArray) {
				"]"
			} else {
				"}"
			})
		} else {
			sb.append(" \"$value\"")
		}

		if (!isLastChild) {
			sb.append(",")
		}

		return sb
				.append("\n")
				.toString()
	}
}

fun String.read() = File(this).bufferedReader().readLines()

fun String.isKeyValPairJson() = matches(".*\".*\": \"?.*\"?,?.*".toRegex())

fun String.isBraceStart() = matches(".*\\{.*".toRegex())

fun String.isBraceEnd() = matches(".*},?.*".toRegex())

fun String.isBracketStart() = matches(".*\\[.*".toRegex())

fun String.isBracketEnd() = matches(".*],?.*".toRegex())

fun String.getKeyJson() = with(split("\"")) {
	if (size >= 2) {
		this[1]
	} else {
		""
	}
}

fun String.getValJson() = if (contains(":")) {
	with(substring(lastIndexOf(": ") + 2)) {
		return substring(if (this[0] == '\"') {
			1
		} else {
			0
		}, length - if (this[length - 1] == ',' || this[length - 1] == '\"') {
			1
		} else {
			0
		} - if (this[length - 2] == '\"') {
			1
		} else {
			0
		})
	}
} else {
	""
}

fun String.isKeyValPairXml() = matches(".*<.+>.*</.+>.*".toRegex())

fun String.isStartTag() = matches(".*<.*>.*".toRegex())

fun String.isEndTag() = matches(".*</.*>.*".toRegex())

fun String.getKeyXml() = substring(indexOf("<") + 1, indexOf(">"))

fun String.getValXml() = substring(indexOf(">") + 1, lastIndexOf("<"))

fun String.json2xml() = read().json2Node().toXmlStr()

fun List<String>.json2Node(rootName: String = "root"): Node {
	val root = Node(rootName)
	var trav = root
	var inArray = false
	asSequence()
			.filterIndexed { i, _ -> i != 0 && i != size - 1 }
			.forEach {
				with(it) {
					when {
						isBraceStart() -> {
							with(getKeyJson()) {
								val newChild = Node(if (isEmpty()) {
									if (inArray) {
										with(trav.key) {
											substring(0, length - 1)
										}
									} else {
										trav.key
									}
								} else {
									this
								}, parent = trav)
								trav.children!!.add(newChild)
								trav = newChild
							}
						}
						isBraceEnd() -> {
							trav = trav.parent!!
						}

						isBracketStart() -> {
							with(getKeyJson()) {
								inArray = true
								val newChild = Node(this + if (this[length - 1] == 's') {
									""
								} else {
									"s"
								}, parent = trav)
								trav.children!!.add(newChild)
								trav = newChild
							}
						}
						isBracketEnd() -> {
							inArray = false
							trav = trav.parent!!
						}

						isKeyValPairJson() -> {
							trav.children!!.add(Node(getKeyJson(), getValJson()))
						}

						else -> {
							error("invalid json")
						}
					}
				}
			}
	return root
}

fun String.xml2Json() = read().xml2Node().toJsonStr()

fun List<String>.xml2Node(): Node {
	val root = Node("")
	var trav = root
	asSequence()
			.filterIndexed { i, _ -> i != 0 && i != size - 1 }
			.forEach {
				with(it) {
					when {
						isKeyValPairXml() -> {
							trav.children!!.add(Node(getKeyXml(), getValXml()))
						}

						isEndTag() -> {
							trav = trav.parent!!
						}
						isStartTag() -> {
							with(getKeyXml()) {
								val newChild = Node(this, parent = trav)
								trav.children!!.add(newChild)
								trav = newChild
							}
						}

						else -> {
							error("invalid xml")
						}
					}
				}
			}
	return root
}