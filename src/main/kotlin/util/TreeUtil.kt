package util

import tree.bintree.BinTreeNode
import java.util.*

/**
 * Binary Tree Printer
 * @author MightyPork
 *
 * Reference:
 * https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
 */

interface PrintableBinTreeNode {
	val left: PrintableBinTreeNode?
	val right: PrintableBinTreeNode?
	val text: String
}

fun prettyPrintBinTree(root: PrintableBinTreeNode) {
	val lines = ArrayList<List<String?>>()

	var level: MutableList<PrintableBinTreeNode?> = ArrayList()
	var next: MutableList<PrintableBinTreeNode?> = ArrayList()

	level.add(root)
	var nn = 1

	var widest = 0

	while (nn != 0) {
		val line = ArrayList<String?>()
		nn = 0
		for (n in level) {
			if (n == null) {
				line.add(null)
				next.add(null)
				next.add(null)
			} else {
				val aa = n.text
				line.add(aa)
				widest = max(widest, aa.length)

				next.add(n.left)
				next.add(n.right)

				if (n.left != null) {
					nn++
				}
				if (n.right != null) {
					nn++
				}
			}
		}

		if (widest % 2 == 1) {
			widest++
		}

		lines.add(line)

		val tmp = level
		level = next
		next = tmp
		next.clear()
	}

	var perpiece = lines[lines.size - 1].size * (widest + 4)
	for (i in lines.indices) {
		val line = lines[i]
		val hpw = Math.floor((perpiece / 2f).toDouble()).toInt() - 1

		if (i > 0) {
			for (j in line.indices) {
				var c = ' '
				if (j % 2 == 1) {
					if (line[j - 1] != null) {
						c = if (line[j] != null) '┴' else '┘'
					} else {
						if (j < line.size && line[j] != null) c = '└'
					}
				}
				print(c)

				if (line[j] == null) {
					for (k in 0 until perpiece - 1) {
						print(" ")
					}
				} else {
					for (k in 0 until hpw) {
						print(if (j % 2 == 0) " " else "─")
					}
					print(if (j % 2 == 0) "┌" else "┐")
					for (k in 0 until hpw) {
						print(if (j % 2 == 0) "─" else " ")
					}
				}
			}
			println()
		}

		for (j in line.indices) {
			var f: String? = line[j]
			if (f == null) f = ""
			val gap1 = Math.ceil((perpiece / 2f - f.length / 2f).toDouble()).toInt()
			val gap2 = Math.floor((perpiece / 2f - f.length / 2f).toDouble()).toInt()

			for (k in 0 until gap1) {
				print(" ")
			}
			print(f)
			for (k in 0 until gap2) {
				print(" ")
			}
		}
		println()

		perpiece /= 2
	}
}

fun <T> BinTreeNode<T>.prettyPrintTree() = util.prettyPrintBinTree(this)
