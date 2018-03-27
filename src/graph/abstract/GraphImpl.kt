package graph.abstract

import util.Tuple2

class Vertex<V>(var data: V) {
	override fun toString() = "(${data.toString()})"

	override fun equals(other: Any?) = if (other is Vertex<*>) {
		data == other.data
	} else {
		false
	}

	override fun hashCode() = data?.hashCode() ?: 0
}

open class Edge<V>(var vertex1: Vertex<V>, var vertex2: Vertex<V>, var isDirected: Boolean = false) {
	operator fun component1() = vertex1
	operator fun component2() = vertex2
	operator fun component3() = isDirected

	override fun toString() = if (isDirected) {
		"$vertex1 ------> $vertex2"
	} else {
		"$vertex1 <-------> $vertex2"
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
			vertex1 == other.vertex1 && vertex2 == other.vertex2
		} else {
			(vertex1 == other.vertex1 && vertex2 == other.vertex2) ||
					(vertex1 == other.vertex2 && vertex2 == other.vertex1)
		}
	}

	override fun hashCode(): Int {
		var result = vertex1.hashCode()
		result = 31 * result + vertex2.hashCode()
		result = 31 * result + isDirected.hashCode()
		return result
	}
}

class WeightedEdge<V, E>(start: Vertex<V>, end: Vertex<V>, isDirected: Boolean = false, var data: E? = null)
	: Edge<V>(start, end, isDirected) {
	operator fun component4() = data

	override fun toString() = if (data == null) {
		super.toString()
	} else {
		if (isDirected) {
			"$vertex1 ---($data)---> $vertex2"
		} else {
			"$vertex1 <---($data)---> $vertex2"
		}
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) {
			return true
		}

		if (other !is WeightedEdge<*, *>) {
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
	open fun getEdgesOf(vertex: Vertex<V>) =
			edges.filter {
				if (it.isDirected) {
					it.vertex1 == vertex
				} else {
					it.vertex1 == vertex || it.vertex2 == vertex
				}
			}
}

open class WeightedGraph<V, E>(vertices: Collection<Vertex<V>>,
                               var weightedEdges: Collection<WeightedEdge<V, E>>,
                               edges: Collection<Edge<V>> = weightedEdges)  // maintained only for inheritance
	: Graph<V>(vertices, edges) {
	fun getWeigedEdgesOf(vertex: Vertex<V>) =
			weightedEdges.filter {
				if (it.isDirected) {
					it.vertex1 == vertex
				} else {
					it.vertex1 == vertex || it.vertex2 == vertex
				}
			}
}

class AdjListGraph<V>(var adjList: Map<Vertex<V>, List<Vertex<V>>>)
	: Graph<V>(
		adjList.keys,
		adjList.map { (startVertex, endVertexList) ->
			endVertexList.map { endVertex ->
				Edge(startVertex, endVertex)
			}
		}.flatten()) {
	fun updateVertices() {
		vertices = adjList.keys
	}

	fun updateEdges() {
		edges = adjList.map { (startVertex, endVertexList) ->
			endVertexList.map { endVertex ->
				Edge(startVertex, endVertex)
			}
		}.flatten()
	}

	override fun getEdgesOf(vertex: Vertex<V>) =
			adjList[vertex]!!.map {
				Edge(vertex, it)
			}.toList()
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
	fun updateVertices() {
		vertices = adjMat.keys
	}

	fun updateEdges() {
		edges = adjMat.map { (startVertex, endVertexMap) ->
			endVertexMap
					.filterValues { it }
					.map { (endVertex, _) ->
						Edge(startVertex, endVertex)
					}
		}.flatten()
	}

	override fun getEdgesOf(vertex: Vertex<V>) =
			adjMat[vertex]!!
					.filterValues { it }
					.map { (endVertex, _) ->
						Edge(vertex, endVertex)
					}
}

class WeightedAdjListGraph<V, E>(var adjList: Map<Vertex<V>, List<Tuple2<E, Vertex<V>>>>)
	: WeightedGraph<V, E>(
		adjList.keys,
		adjList.map { (startVertex, endVertexList) ->
			endVertexList.map { (data, endVertex) ->
				WeightedEdge(startVertex, endVertex, data = data)
			}
		}.flatten()) {
	fun updateVertices() {
		vertices = adjList.keys
	}

	fun updateWeighedEdges() {
		weightedEdges = adjList.map { (startVertex, endVertexList) ->
			endVertexList.map { (data, endVertex) ->
				WeightedEdge(startVertex, endVertex, data = data)
			}
		}.flatten()
	}

	override fun getEdgesOf(vertex: Vertex<V>) =
			adjList[vertex]!!.map { (data, endVertex) ->
				WeightedEdge(vertex, endVertex, data = data)
			}
}


class WeightedAdjMatGraph<V, E>(var adjMat: Map<Vertex<V>, Map<Vertex<V>, E?>>)
	: WeightedGraph<V, E>(
		adjMat.keys,
		adjMat.map { (startVertex, endVertexMap) ->
			endVertexMap
					.filterValues { it != null }
					.map { (endVertex, data) ->
						WeightedEdge(startVertex, endVertex, data = data)
					}
		}.flatten()) {
	fun updateVertices() {
		vertices = adjMat.keys
	}

	fun updateWeighedEdges() {
		weightedEdges = adjMat.map { (startVertex, endVertexMap) ->
			endVertexMap
					.filterValues { it != null }
					.map { (endVertex, data) ->
						WeightedEdge(startVertex, endVertex, data = data)
					}
		}.flatten()
	}

	override fun getEdgesOf(vertex: Vertex<V>) =
			adjMat[vertex]!!
					.filterValues { it != null }
					.map { (endVertex, data) ->
						WeightedEdge(vertex, endVertex, data = data)
					}
}
