package linkedlist

fun main(args: Array<String>) {
	val head = ListNode(0)
	head.next = ListNode(1)
	head.next!!.next = ListNode(2)
	println(head.getMiddle().data)

	head.next!!.next!!.next = ListNode(3)
	println(head.getMiddle().data)
}

fun ListNode.getMiddle(): ListNode {
	var slow: ListNode? = this
	var fast: ListNode? = this
	while (fast != null) {
		fast = fast.next
		if (fast == null) {
			break
		}
		slow = slow!!.next
		fast = fast.next
	}
	return slow ?: ListNode(-1)
}