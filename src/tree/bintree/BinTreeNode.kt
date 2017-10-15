package tree.bintree

class BinTreeNode(var data: Int) {
	var left: BinTreeNode? = null
	var right: BinTreeNode? = null

	override fun toString() = if (left == null && right == null) {
		data.toString()
	} else {
		if (left != null && right != null) {
			"$data -> [$left, $right]"
		} else if (right == null) {
			"$data -> [$left]"
		} else {
			"$data -> [, $right]"
		}
	}
}

