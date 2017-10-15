package tree

class TreeNode(var data: Int) {
	val children = ArrayList<TreeNode>()

	override fun toString() = if (children.isEmpty()) {
		"$data"
	} else {
		"$data -> $children"
	}
}
