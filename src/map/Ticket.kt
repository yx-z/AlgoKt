package map

import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

data class Ticket(var departure: String, var arrival: String)

fun List<Ticket>.reorder(start: String): List<Ticket> {
	var arrival = start
	val map = this.map { it.departure to it }.toMap().toMutableMap()
	val list = ArrayList<Ticket>()
	while (map.containsKey(arrival)) {
		list.add(map[arrival]!!)
		arrival = map.remove(arrival)!!.arrival
	}
	return list
}

fun main(args: Array<String>) {
	val testList = listOf(Ticket("NYC", "ORD"), Ticket("ORD", "NYC"))
	println(testList.reorder("NYC"))
}
