package tree.bintree

class BinTreeNode(var data: Int) {
	var left: BinTreeNode? = null
	var right: BinTreeNode? = null

	override fun toString() = if (left == null && right == null) {
		data.toString()
	} else {
		"$data -> [${left ?: ""}, ${right ?: ""}]"
	}
}

