package map

/**
 * MyHashMap is a simple hash map implementation for linking a String key to a generic class
 *
 * @constructor creates an empty fixed-size hash map with given size
 * @param size define the size of the fixed-size map
 */
open class MyHashMap<T>(val size: Int) {
	/**
	 * the number of elements in the current hash map
	 */
	private var numberOfElements = 0

	fun getNumberOfElements() = numberOfElements

	/**
	 * the container for holding values
	 */
	private val container: Array<Entry<String, T>?> = arrayOfNulls<Entry<String, T>?>(size)

	/**
	 * put the key value pair into the map
	 * @param key the String key for the pair
	 * @param value the corresponding value
	 * @return true if the operation completes successfully, false if operation failed due to size overflow etc.
	 */
	fun set(key: String, value: T): Boolean {
		if (indexCollision(key)) {
			return false
		}

		container[getIndex(key)] = Entry(key, value)
		numberOfElements++

		return true
	}

	/**
	 * @param key the String key for searching the value
	 * @return the corresponding value if found, null if not found
	 */
	fun get(key: String) = if (contains(key)) {
		container[getIndex(key)]?.value
	} else {
		null
	}

	/**
	 * delete a key value pair in the given map
	 * @param key the String key for the key value pair to be deleted
	 * @return the deleted value if the key value pair exists in the current map, null if not found
	 */
	fun delete(key: String): T? {
		return if (contains(key)) {
			val index = getIndex(key)
			val deletedValue = container[index]!!.value

			container[index] = null
			numberOfElements--

			deletedValue
		} else {
			null
		}
	}

	/**
	 * @return the load factor, i.e. #items / total size in the current map
	 */
	fun load() = numberOfElements.toFloat() / size

	/**
	 * @return the index for the container to place the key value pair
	 */
	private fun getIndex(key: String) = key.hashCode() % size

	/**
	 * @return true if the key value pair exists in the current container, false if not
	 */
	private fun contains(key: String) = key == container[getIndex(key)]?.key

	private fun indexCollision(key: String) = !contains(key) && container[getIndex(key)] != null

	/**
	 * @return String representation of map
	 */
	override fun toString() = container.joinToString { it -> it.toString() + " " }

	/**
	 * data model representing the key value pair
	 */
	private data class Entry<String, T>(val key: String, val value: T)
}

fun main(args: Array<String>) {
	// create a map linking String to Int of size 10
	val map = MyHashMap<Int>(10)
	// put values 0 - 9
	map.set("zero", 0)
	map.set("one", 1)
	map.set("two", 2)
	map.set("three", 3)
	map.set("four", 4)
	map.set("five", 5)
	map.set("six", 6)
	map.set("seven", 7)
	map.set("eight", 8)
	map.set("nine", 9)
	// print map
	println(map)
	// print count of elements
	println(map.getNumberOfElements())
	// util.get load factor
	println(map.load())
	// some operations
	println(map.get("one"))
	println(map.delete("four"))
	println(map.delete("asdf"))
	// new load factor
	println(map.load())
}
