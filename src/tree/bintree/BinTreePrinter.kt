package tree.bintree

import util.max
import util.times
import java.util.ArrayList

/**
 * Reference:
 * https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
 */


fun <T> parse(root: BinTreeNode<T>): StringBuilder {
	val maxLevel = maxLevel(root)
	return parse(listOf(root), 1, maxLevel)
}

private fun <T> parse(nodes: List<BinTreeNode<T>?>,
                      level: Int,
                      maxLevel: Int): StringBuilder {
	if (nodes.isEmpty() || isAllElementsNull(nodes)) {
		return StringBuilder()
	}

	val sb = StringBuilder()

	val floor = maxLevel - level
	val endgeLines = Math.pow(2.0, Math.max(floor - 1, 0).toDouble()).toInt()
	val firstSpaces = Math.pow(2.0, floor.toDouble()).toInt() - 1
	val betweenSpaces = Math.pow(2.0, (floor + 1).toDouble()).toInt() - 1

	sb.append(whiteSpace(firstSpaces))

	val newNodes = ArrayList<BinTreeNode<T>?>()
	for (binTreeNode in nodes) {
		if (binTreeNode != null) {
			sb.append(binTreeNode.data)
			newNodes.add(binTreeNode.left)
			newNodes.add(binTreeNode.right)
		} else {
			newNodes.add(null)
			newNodes.add(null)
			sb.append(' ')
		}

		sb.append(whiteSpace(betweenSpaces))
	}
	sb.append('\n')

	for (i in 1..endgeLines) {
		for (j in nodes.indices) {
			sb.append(whiteSpace(firstSpaces - i))
			if (nodes[j] == null) {
				sb.append(whiteSpace(endgeLines + endgeLines + i + 1))
				continue
			}

			if (nodes[j]?.left != null) {
				sb.append('/')
			} else {
				sb.append(whiteSpace(1))
			}

			sb.append(whiteSpace(i + i - 1))

			if (nodes[j]?.right != null) {
				sb.append(("\\"))
			} else {
				sb.append(whiteSpace(1))
			}

			sb.append(whiteSpace(endgeLines + endgeLines - i))
		}
		sb.append('\n')
	}
	sb.append(parse(newNodes, level + 1, maxLevel))

	return sb
}

private fun whiteSpace(count: Int) = " " * max(count, 0)

private fun <T> maxLevel(binTreeNode: BinTreeNode<T>?): Int = if (binTreeNode == null) {
	0
} else {
	max(maxLevel(binTreeNode.left), maxLevel(binTreeNode.right)) + 1
}

private fun <T> isAllElementsNull(list: List<T>) = list.all { it == null }
