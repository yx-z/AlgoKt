package oop

import java.net.HttpURLConnection
import java.net.URL
import java.util.*

fun main(args: Array<String>) {
	println("https://yx-z.github.io".extractURL())
}

fun String.extractURL(capacity: Int = 100, linkSet: HashSet<String> = HashSet(capacity)): Set<String> {
	val LINK_HREF = "href='"
	val LINK_ATTR = "href=\"http"

	val USER_AGENT = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)"

	val RESPONSE_OK = 200

	val USER_AGENT_PROPERTY = "User-Agent"


	val urlQueue: Queue<String> = LinkedList()
	urlQueue.add(this)

	while (urlQueue.isNotEmpty() && linkSet.size <= capacity) {
		val conn = URL(urlQueue.remove()).openConnection() as HttpURLConnection
		conn.addRequestProperty(USER_AGENT_PROPERTY, USER_AGENT)

		if (conn.responseCode == RESPONSE_OK) {
			val reader = conn.inputStream.bufferedReader()

			var line: String? = reader.readLine()
			while (line !== null) {
				val hrefStartIdx = line.indexOf(LINK_ATTR)
				if (hrefStartIdx != -1) {
					val hrefEndIdx = line.indexOf("\"", hrefStartIdx + LINK_HREF.length)
					val link = line.substring(hrefStartIdx + LINK_HREF.length, hrefEndIdx)

					linkSet.add(link)
					urlQueue.add(link)
				}

				line = reader.readLine()
			}
		}
	}

	return linkSet
}
