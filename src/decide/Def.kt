// some global definitions

// naming convention
typealias Input = String
typealias Encoding = String
typealias TM /* Turing Machine */ = (Input) -> Boolean 

// loop forever
fun loop(): Boolean {
    while(true)
    // not gonna be here
    return true
}

// halt (don't care true or false)
// have fun with some randomness
fun halt() = Math.random() > 0.5