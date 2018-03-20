package oop

class ParkingLot(val cap: Int = 10) {
	val area = ArrayList<ParkingSpot>(10)

	fun park(v: Vehicle) {
		if (area.size + v.size > cap) {
			throw ParkingLotFullException(cap, area.size)
		}

		for (i in 0 until v.size) {
			val spot = ParkingSpot(v)
			area.add(spot)
		}
	}

	fun leave(v: Vehicle) {
		area.filter { it.vehicle == v }.forEach {
			it.leave(v)
			area.remove(it) }
	}

	override fun toString() = "cap: $cap, now taken: ${area.size}\ncurr: $area"
}

class ParkingSpot(var vehicle: Vehicle? = null) {
	var isTaken = vehicle != null

	fun leave(v: Vehicle) {
		if (isTaken && v == vehicle) {
			vehicle = null
			isTaken = false
		} else {
			throw NoVehicleFoundException()
		}
	}

	fun park(v: Vehicle) {
		if (isTaken && v != vehicle) {
			throw ParkingSpotTakenException()
		}

		vehicle = v
		isTaken = true
	}

	override fun toString() = if (isTaken) {
		"taken By $vehicle"
	} else {
		"FREE parking spot"
	}
}

class ParkingSpotTakenException : RuntimeException("parking spot taken here")

class NoVehicleFoundException : RuntimeException("no vehicle found in this parking spot")

class ParkingLotFullException(cap: Int, currSize: Int) : RuntimeException("parking lot full:\nutil.max cap: $cap\ncurr size: $currSize")

abstract class Vehicle(val license: String, val size: Int) {
	override fun toString() = "$license ($size)"
}

class Motorcycle(license: String, size: Int = 1) : Vehicle(license, size)

class Car(license: String, size: Int = 1) : Vehicle(license, size)

class Truck(license: String, size: Int = 2) : Vehicle(license, size)

class Bus(license: String, size: Int = 3) : Vehicle(license, size)

// test cases
fun main(args: Array<String>) {
	val parkingLot = ParkingLot(20)
	val vehicles = arrayOf(
			Motorcycle("ABC123"),
			Car("DEF456"),
			Truck("GHI789"),
			Bus("QWERTY")
	)
	vehicles.forEach { parkingLot.park(it) }
	println(parkingLot)

	parkingLot.leave(vehicles[0])
	println(parkingLot)

	parkingLot.leave(vehicles[3])
	println(parkingLot)
}
