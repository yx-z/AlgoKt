// global definitions for decision problems

// naming convention
typealias Input = String
typealias Encoding = Input

// Turing Machine
typealias TM = (Input) -> Boolean

// a Turing Machine that Decides if a TM (as input) has some property
// it is still a valid Turing Machine since we can transform one Turing Machine
// to its proper encoding
typealias TMD = (TM) -> Boolean

// another useful definition in some problems, ex. halting problem
// it is still a valid Turing Machine since we can transform the first parameter
// TM, into a form of its proper encoding, which is still a string input
typealias TM2 /* Turing Machine w/ 2 params */ = (TM, Input) -> Boolean

// halt (don't care about true or false)
fun halt() = Math.random() >= 0.5 // have fun with some randomness :-)

// loop forever
fun loop(): Boolean {
	while (true) {
	}
	// NOT gonna be here, i.e. will NOT halt at all
	return halt()
}

// simple Turing Machines w/ special behaviors
val M_ACC: TM = { w: Input -> true } // accept Sigma*

val M_REJ: TM = { w: Input -> false } // reject Sigma*

val M_HALT: TM = { w: Input -> halt() } // halt on Sigma*

val M_HANG: TM = { w: Input -> loop() } // hang on Sigma*
