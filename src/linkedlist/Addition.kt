package linkedlist

fun main(args: Array<String>) {
	var n1 = ListNode(9)
	n1.next = ListNode(8)
	n1.next!!.next = ListNode(3)

	var n2 = ListNode(3)
	n2.next = ListNode(9)
	// 983 + 39 = 1022, i.e. 1 -> 0 -> 2 -> 2
	println(n1 + n2)
	// 389 + 93 = 482, i.e. 2 -> 8 -> 4
	println(n1.reverseAdd(n2))

	n1 = ListNode(1)
	n1.next = ListNode(3)

	n2 = ListNode(4)
	n2.next = ListNode(8)
	// 13 + 48 = 61, i.e. 6 -> 1
	println(n1 + n2)
	// 31 + 84 = 115, i.e. 5 -> 1 -> 1
	println(n1.reverseAdd(n2))
}

operator fun ListNode.plus(other: ListNode): ListNode {
	var n1 = 0
	var t1: ListNode? = this
	while (t1 != null) {
		n1 *= 10
		n1 += t1.data
		t1 = t1.next
	}
	var n2 = 0
	var t2: ListNode? = other
	while (t2 != null) {
		n2 *= 10
		n2 += t2.data
		t2 = t2.next
	}

	var sum = n1 + n2
	val ans = ListNode(0)
	var ansTrav = ans
	while (sum > 0) {
		val data = sum % 10
		ansTrav.data = data
		ansTrav.next = ListNode(0)
		ansTrav = ansTrav.next!!

		sum /= 10
	}
	return ans.recurReverse()!!.next!!
}

fun ListNode.reverseAdd(other: ListNode): ListNode {
	var carry = 0
	var t1: ListNode? = this
	var t2: ListNode? = other
	val ans = ListNode(0)
	var ansTrav = ans
	while (t1 != null || t2 != null || carry != 0) {
		val sum = (t1?.data ?: 0) + (t2?.data ?: 0) + carry

		ansTrav.next = ListNode(sum % 10)
		ansTrav = ansTrav.next!!

		carry = sum / 10
		t1 = t1?.next
		t2 = t2?.next
	}
	return ans.next ?: ListNode(0)
}
