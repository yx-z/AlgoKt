package arr.sort

fun IntArray.sleepSort() {
	val threads = arrayOfNulls<Thread>(size)
	var aId = 0
	withIndex().forEach { (idx, i) ->
		threads[idx] = Thread {
			Thread.sleep(i.toLong() * 10)
			this[aId] = i
			aId++
		}
		threads[idx]!!.start()
	}
	threads.forEach { it!!.join() }
}

fun main(args: Array<String>) {
	println(testCorrectness(max = 10, length = 20) { sleepSort() })
}