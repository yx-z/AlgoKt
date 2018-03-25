package graph.abstract

import java.util.*
import kotlin.collections.HashMap

fun <V> Graph<V>.bfs(start: Vertex<V>): Map<Vertex<V>, Vertex<V>?> {
	val marked = HashMap<Vertex<V>, Boolean>()
	val parent = HashMap<Vertex<V>, Vertex<V>?>()
	vertices.forEach {
		marked[it] = false
		parent[it] = null
	}

	val queue: Queue<Vertex<V>> = LinkedList<Vertex<V>>()
	queue.add(start)

	while (queue.isNotEmpty()) {
		val vertex = queue.remove()

		if (marked[vertex] == false) {
			marked[vertex] = true
			getEdgesOf(vertex).forEach {
				queue.add(it.end)
				if (parent[it.end] == null) {
					parent[it.end] = vertex
				}
			}
		}
	}

	return parent
}
