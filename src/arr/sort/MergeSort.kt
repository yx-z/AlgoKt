package arr.sort

fun IntArray.mergeSort(start: Int = 0, end: Int = size - 1) {
	if (end == start) {
		return
	}

	val mid = start + (end - start) / 2
	mergeSort(start, mid)
	mergeSort(mid + 1, end)

	val merge = IntArray(end - start + 1)
	var l = start
	var r = mid + 1
	var i = 0
	while (l <= mid && r <= end) {
		if (this[l] < this[r]) {
			merge[i] = this[l]
			l++
		} else {
			merge[i] = this[r]
			r++
		}
		i++
	}
	while (l <= mid) {
		merge[i] = this[l]
		i++
		l++
	}
	while (r <= end) {
		merge[i] = this[r]
		i++
		r++
	}

	for (idx in start..end) {
		this[idx] = merge[idx - start]
	}
}

fun main(args: Array<String>) {
	println(testCorrectness { mergeSort() })
}