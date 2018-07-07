package tree

class TreeNode(var data: Int) {
	val children = ArrayList<TreeNode>()

	override fun toString() = if (children.isEmpty()) {
		data.toString()
	} else {
		"$data -> $children"
	}
}

class GenericTreeNode<T>(var data: T) {
	val children = ArrayList<GenericTreeNode<T>>()

	override fun toString() = if (children.isEmpty()) {
		data.toString()
	} else {
		"$data -> $children"
	}
}