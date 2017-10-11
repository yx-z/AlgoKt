package linkedlist

fun main(args: Array<String>) {
	// sample list
	// 12 -> 23 -> 1 -> 3
	//        ^         |
	//        |         |
	//        -----------
	val head = ListNode(12)
	head.next = ListNode(23)
	head.next!!.next = ListNode(1)
	head.next!!.next!!.next = ListNode(3)
	head.next!!.next!!.next!!.next = head.next

	println(head.hasCycle())
}

fun ListNode.hasCycle(): Boolean {
	var slow: ListNode? = this
	var fast: ListNode? = this

	while (slow !== null && fast !== null && slow !== fast) {
		slow = slow.next
		if (fast.next === null) {
			return false
		}
		fast = fast.next!!.next
	}

	return true
}
