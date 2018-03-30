package graph.abstract

import java.util.*
import kotlin.collections.HashMap

fun <V> Graph<V>.bfs(start: Vertex<V>, checkIdentity: Boolean = true): Map<Vertex<V>, Vertex<V>?> {
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
			getEdgesOf(vertex, checkIdentity).forEach {
				queue.add(it.vertex2)
				if (parent[it.vertex2] == null) {
					parent[it.vertex2] = vertex
				}
			}
		}
	}

	return parent
}
