package graph.core

import util.Tuple2

open class Vertex<V>(var data: V) {
	override fun toString() = "(${data.toString()})"

	override fun equals(other: Any?) =
			if (other is Vertex<*>) {
				data == other.data
			} else {
				false
			}

	override fun hashCode() = data?.hashCode() ?: 0
}

// distinguishable vertex in the sense that we will assign a unique id (of type
// Int) to distinguish vertices that possibly have the same data member stored
class DVertex<V>(data: V) : Vertex<V>(data) {
	private val id = cId

	init {
		cId++ // update identifier
	}

	companion object {
		var cId = 0 // Class-level Identifier
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is DVertex<*>) return false
		if (!super.equals(other)) return false

		if (id != other.id) return false

		return true
	}

	override fun hashCode(): Int {
		var result = super.hashCode()
		result = 31 * result + id
		return result
	}

	override fun toString() = "(<$id> : $data)"
}

open class Edge<V>(var vertex1: Vertex<V>,
                   var vertex2: Vertex<V>,
                   var isDirected: Boolean = false) {
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

class WeightedEdge<V, E>(vertex1: Vertex<V>,
                         vertex2: Vertex<V>,
                         isDirected: Boolean = false,
                         var weight: E? = null)
	: Edge<V>(vertex1, vertex2, isDirected) {
	operator fun component4() = weight

	override fun toString() = if (weight == null) {
		super.toString()
	} else {
		if (isDirected) {
			"$vertex1 ---[$weight]---> $vertex2"
		} else {
			"$vertex1 <---[$weight]---> $vertex2"
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

		return weight == other.weight
	}

	override fun hashCode(): Int {
		var result = super.hashCode()
		result = 31 * result + (weight?.hashCode() ?: 0)
		return result
	}
}

open class Graph<V>(var vertices: Collection<Vertex<V>>,
                    var edges: Collection<Edge<V>>) {
	open fun getEdgesOf(v: Vertex<V>) = getEdgesOf(v, true)

	open fun getEdgesOf(v: Vertex<V>, checkIdentity: Boolean) =
			edges.filter {
				if (checkIdentity) {
					it.vertex1 === v || it.vertex2 === v
				} else {
					it.vertex1 == v || it.vertex2 == v
				}
			}

	open fun getEdgesFrom(v: Vertex<V>) = getEdgesFrom(v, true)

	open fun getEdgesFrom(v: Vertex<V>, checkIdentity: Boolean) =
			edges.filter { (s, e, d) ->
				if (d) {
					if (checkIdentity) {
						s === v
					} else {
						s == v
					}
				} else {
					if (checkIdentity) {
						s === v || e === v
					} else {
						s == v || e == v
					}
				}
			}

	open fun getEdgesTo(v: Vertex<V>) = getEdgesTo(v, true)

	open fun getEdgesTo(v: Vertex<V>, checkIdentity: Boolean) =
			edges.filter { (s, e, d) ->
				if (d) {
					if (checkIdentity) {
						v === e
					} else {
						v == e
					}
				} else {
					if (checkIdentity) {
						v === s || v === e
					} else {
						v == s || v == e
					}
				}
			}

	override fun toString() = "V:\n${vertices.joinToString("\n")}\n\nE:\n${edges.joinToString("\n")}"
}

open class WeightedGraph<V, E>(vertices: Collection<Vertex<V>>,
                               var weightedEdges: Collection<WeightedEdge<V, E>>,
                               edges: Collection<Edge<V>> = weightedEdges)  // maintained only for inheritance
	: Graph<V>(vertices, edges) {

	override fun getEdgesOf(v: Vertex<V>) = getWeightedEdgesOf(v)

	override fun getEdgesOf(v: Vertex<V>, checkIdentity: Boolean) = getWeightedEdgesOf(v, checkIdentity)

	private fun getWeightedEdgesOf(v: Vertex<V>, checkIdentity: Boolean = true) =
			weightedEdges.filter {
				if (checkIdentity) {
					it.vertex1 === v || it.vertex2 === v
				} else {
					it.vertex1 == v || it.vertex2 == v
				}
			}

	private fun getWeightedEdgesFrom(v: Vertex<V>, checkIdentity: Boolean = true) =
			weightedEdges.filter { (s, e, d) ->
				if (d) {
					if (checkIdentity) {
						s === v
					} else {
						s == v
					}
				} else {
					if (checkIdentity) {
						s === v || e === v
					} else {
						s == v || e == v
					}
				}
			}

	override fun getEdgesFrom(v: Vertex<V>, checkIdentity: Boolean) = getWeightedEdgesFrom(v, checkIdentity)

	override fun getEdgesFrom(v: Vertex<V>) = getEdgesFrom(v, true)

	override fun getEdgesTo(v: Vertex<V>, checkIdentity: Boolean) = getWeightedEdgesTo(v, checkIdentity)

	override fun getEdgesTo(v: Vertex<V>) = getEdgesTo(v, true)

	private fun getWeightedEdgesTo(v: Vertex<V>, checkIdentity: Boolean = true) =
			weightedEdges.filter { (s, e, d) ->
				if (d) {
					if (checkIdentity) {
						v === e
					} else {
						v == e
					}
				} else {
					if (checkIdentity) {
						v === e || s === e
					} else {
						v == e || s == e
					}
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

	override fun getEdgesOf(v: Vertex<V>) =
			adjList[v]!!.map {
				Edge(v, it)
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

	override fun getEdgesOf(v: Vertex<V>) =
			adjMat[v]!!
					.filterValues { it }
					.map { (endVertex, _) ->
						Edge(v, endVertex)
					}
}

class WeightedAdjListGraph<V, E>(var adjList: Map<Vertex<V>, List<Tuple2<E, Vertex<V>>>>)
	: WeightedGraph<V, E>(
		adjList.keys,
		adjList.map { (startVertex, endVertexList) ->
			endVertexList.map { (data, endVertex) ->
				WeightedEdge(startVertex, endVertex, weight = data)
			}
		}.flatten()) {
	fun updateVertices() {
		vertices = adjList.keys
	}

	fun updateWeighedEdges() {
		weightedEdges = adjList.map { (startVertex, endVertexList) ->
			endVertexList.map { (data, endVertex) ->
				WeightedEdge(startVertex, endVertex, weight = data)
			}
		}.flatten()
	}

	override fun getEdgesOf(v: Vertex<V>) =
			adjList[v]!!.map { (data, endVertex) ->
				WeightedEdge(v, endVertex, weight = data)
			}
}


class WeightedAdjMatGraph<V, E>(var adjMat: Map<Vertex<V>, Map<Vertex<V>, E?>>)
	: WeightedGraph<V, E>(
		adjMat.keys,
		adjMat.map { (startVertex, endVertexMap) ->
			endVertexMap
					.filterValues { it != null }
					.map { (endVertex, data) ->
						WeightedEdge(startVertex, endVertex, weight = data)
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
						WeightedEdge(startVertex, endVertex, weight = data)
					}
		}.flatten()
	}

	override fun getEdgesOf(v: Vertex<V>) =
			adjMat[v]!!
					.filterValues { it != null }
					.map { (endVertex, data) ->
						WeightedEdge(v, endVertex, weight = data)
					}
}
