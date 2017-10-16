package linkedlist

data class ListNode(var data: Int) {
	var next: ListNode? = null

	override fun toString(): String {
		val sb = StringBuilder()
		sb
				.append(data)
				.append(" -> ")
				.append(next?.toString() ?: ".")
		return sb.toString()
	}
}