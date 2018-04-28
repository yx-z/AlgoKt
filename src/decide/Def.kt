// some global definitions

// naming convention
typealias Input = String

typealias TM /* Turing Machine */ = (Input) -> Boolean

// useful definition in some problems, ex. halting problem
// it is still a valid turing machine since we can transform the first parameter
// TM, into a form of its proper encoding, which is still a string input
typealias TM2 /* Turing Machine w/ 2 params */ = (TM, Input) -> Boolean

// loop forever
fun loop(): Boolean {
    while(true)
    // NOT gonna be here, i.e. will NOT halt at all
    return halt()
}

// halt (don't care about true or false)
fun halt() = Math.random() >= 0.5 // have fun with some randomness