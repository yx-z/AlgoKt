package graph.abstract

import util.Tuple2

class Vertex<V>(var data: V) {
	override fun toString() = data.toString()

	override fun equals(other: Any?) = if (other is Vertex<*>) {
		data == other.data
	} else {
		false
	}

	override fun hashCode() = data?.hashCode() ?: 0
}

open class Edge<V>(var start: Vertex<V>, var end: Vertex<V>, var isDirected: Boolean = false) {
	override fun toString() = if (isDirected) {
		"$start ------> $end"
	} else {
		"$start <-------> $end"
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) {
			return true
		}

		if (other !is Edge<*>) {
			return false
		}

		if (isDirected != other.isDirected) {
			return false
		}

		return if (isDirected) {
			start == other.start && end == other.end
		} else {
			(start == other.start && end == other.end) ||
					(start == other.end && end == other.start)
		}
	}

	override fun hashCode(): Int {
		var result = start.hashCode()
		result = 31 * result + end.hashCode()
		result = 31 * result + isDirected.hashCode()
		return result
	}
}

class WeighedEdge<V, E>(start: Vertex<V>, end: Vertex<V>, var data: E? = null, isDirected: Boolean = false)
	: Edge<V>(start, end, isDirected) {
	override fun toString() = if (data == null) {
		super.toString()
	} else {
		if (isDirected) {
			"$start ---$data---> $end"
		} else {
			"$start <---$data---> $end"
		}
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) {
			return true
		}

		if (other !is WeighedEdge<*, *>) {
			return false
		}

		if (!super.equals(other)) {
			return false
		}

		return data == other.data
	}

	override fun hashCode(): Int {
		var result = super.hashCode()
		result = 31 * result + (data?.hashCode() ?: 0)
		return result
	}
}

open class Graph<V>(var vertices: Collection<Vertex<V>>, var edges: Collection<Edge<V>>) {
	fun getEdgesOf(startVertex: Vertex<V>) = edges.filter { it.start == startVertex }
}

open class WeighedGraph<V, E>(vertices: Collection<Vertex<V>>,
                              weighedEdges: Collection<WeighedEdge<V, E>>,
                              edges: Collection<Edge<V>> = weighedEdges)  // maintained only for inheritance
	: Graph<V>(vertices, edges)

class AdjListGraph<V>(var adjList: List<Tuple2<Vertex<V>, List<Vertex<V>>>>)
	: Graph<V>(
		adjList.map { it.first },
		adjList.map { (startVertex, endVertexList) ->
			endVertexList.map { endVertex ->
				Edge(startVertex, endVertex)
			}
		}.flatten()) {
	fun updateVertices() = adjList.map { it.first }

	fun updateEdges() =
			adjList.map { (startVertex, endVertexList) ->
				endVertexList.map { endVertex ->
					Edge(startVertex, endVertex)
				}
			}.flatten()
}

class AdjMatGraph<V>(var adjMat: Map<Vertex<V>, Map<Vertex<V>, Boolean>>)
	: Graph<V>(
		adjMat.keys,
		adjMat.map { (startVertex, endVertexMap) ->
			endVertexMap
					.filterValues { it }
					.map { (endVertex, _) ->
						Edge(startVertex, endVertex)
					}
		}.flatten()) {
	fun updateVertices() = adjMat.keys

	fun updateEdges() =
			adjMat.map { (startVertex, endVertexMap) ->
				endVertexMap
						.filterValues { it }
						.map { (endVertex, _) ->
							Edge(startVertex, endVertex)
						}
			}.flatten()
}

class WeighedAdjListGraph<V, E>(var adjList: List<Tuple2<Vertex<V>, List<Tuple2<E, Vertex<V>>>>>)
	: WeighedGraph<V, E>(
		adjList.map { it.first },
		adjList.map { (startVertex, endVertexList) ->
			endVertexList.map { (data, endVertex) ->
				WeighedEdge(startVertex, endVertex, data)
			}
		}.flatten()) {
	fun updateVertices() = adjList.map { it.first }

	fun updateWeighedEdges() =
			adjList.map { (startVertex, endVertexList) ->
				endVertexList.map { (data, endVertex) ->
					WeighedEdge(startVertex, endVertex, data)
				}
			}.flatten()
}


class WeighedAdjMatGraph<V, E>(var adjMat: Map<Vertex<V>, Map<Vertex<V>, E?>>)
	: WeighedGraph<V, E>(
		adjMat.keys,
		adjMat.map { (startVertex, endVertexMap) ->
			endVertexMap
					.filterValues { it != null }
					.map { (endVertex, data) ->
						WeighedEdge(startVertex, endVertex, data)
					}
		}.flatten()) {
	fun updateVertices() = adjMat.keys

	fun updateWeighedEdges() =
			adjMat.map { (startVertex, endVertexMap) ->
				endVertexMap
						.filterValues { it != null }
						.map { (endVertex, data) ->
							WeighedEdge(startVertex, endVertex, data)
						}
			}.flatten()
}
