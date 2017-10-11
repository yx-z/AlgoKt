package linkedlist

fun main(args: Array<String>) {
	val l1 = ListNode(1)
	l1.next = ListNode(2)
	l1.next!!.next = ListNode(3)
	println(l1)

	val l1Reverse = l1.iterReverse()
	println(l1Reverse)

	val l1ReverseReverse = l1Reverse.recurReverse()
	println(l1ReverseReverse)
}

fun ListNode.iterReverse(): ListNode {
	var prev: ListNode? = null
	var curr: ListNode? = this
	while (curr != null) {
		val next = curr.next
		curr.next = prev
		prev = curr
		curr = next
	}

	return prev!!
}

fun ListNode?.recurReverse(): ListNode? {
	if (this == null || this.next == null) {
		return this
	}

	val next = this.next
	val reversedAfter = this.next.recurReverse()
	next?.next = this
	this.next = null
	return reversedAfter
}
