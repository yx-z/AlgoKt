package str

fun main(args: Array<String>) {
	println("This is just a sample test sentence with a lot of words and spaces in it".reverseWords())
}

fun String.reverseWords() = split(" ").joinToString(" ") { StringBuilder(it).reverse() }