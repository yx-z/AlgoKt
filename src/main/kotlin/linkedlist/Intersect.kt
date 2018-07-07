package linkedlist

fun main(args: Array<String>) {
	// test list:
	// l1:       1 -\
	//               -> 2 -> 3 -> 4
	// l2: 10 -> 9 -/
	val l1 = ListNode(1)
	l1.next = ListNode(2)
	l1.next!!.next = ListNode(3)
	l1.next!!.next!!.next = ListNode(4)

	val l2 = ListNode(10)
	l2.next = ListNode(9)
	l2.next!!.next = l1.next


	// should be 2
	println(getIntersectionNode(l1, l2).data)
	println(getIntersectionNodeOpt(l1, l2).data)
}

/**
 * given 2 LinkedLists w/o cycles that intersect with each other
 * @return the ListNode where 2 lists intersects
 */
fun getIntersectionNode(l1: ListNode, l2: ListNode): ListNode {
	var n1: ListNode? = l1
	var n2: ListNode? = l2

	while (n1 !== null && n2 !== null) {
		n1 = n1.next
		n2 = n2.next
	}

	if (n1 === null) {
		n1 = l2
		while (n2 !== null) {
			n2 = n2.next
			n1 = n1!!.next
		}
		n2 = l1
	} else {
		n2 = l1
		while (n1 !== null) {
			n1 = n1.next
			n2 = n2!!.next
		}
		n1 = l2
	}

	while (n1 !== n2) {
		n1 = n1!!.next
		n2 = n2!!.next
	}

	return n1!!
}

fun getIntersectionNodeOpt(l1: ListNode, l2: ListNode): ListNode {
	var n1: ListNode? = l1
	var n2: ListNode? = l2

	while (n1 !== n2) {
		n1 = n1!!.next ?: l1
		n2 = n2!!.next ?: l2
	}

	return n1!!
}

