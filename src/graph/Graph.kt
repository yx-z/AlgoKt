package graph

import util.Tuple2

class Vertex<V>(var data: V? = null)

open class Edge<V>(var start: Vertex<V>, var end: Vertex<V>)

class WeighedEdge<V, E>(start: Vertex<V>, end: Vertex<V>, var data: E? = null) : Edge<V>(start, end)

abstract class Graph<V> {
	abstract var vertices: Collection<Vertex<V>>
	abstract var edges: Collection<Edge<V>>
}

abstract class WeighedGraph<V, E> {
	abstract var vertices: Collection<Vertex<V>>
	abstract var weighedEdges: Collection<WeighedEdge<V, E>>
}

class AdjListGraph<V>(var adjList: List<Tuple2<Vertex<V>, List<Vertex<V>>>>)
	: Graph<V>() {
	override var vertices: Collection<Vertex<V>> = adjList.map { it.first }

	override var edges: Collection<Edge<V>> =
			adjList.map { (startVertex, endVertexList) ->
				endVertexList.map { endVertex ->
					Edge(startVertex, endVertex)
				}
			}.flatten()
}

class WeighedAdjListGraph<V, E>(var adjList: List<Tuple2<Vertex<V>, List<Tuple2<E, Vertex<V>>>>>)
	: WeighedGraph<V, E>() {
	override var vertices: Collection<Vertex<V>> = adjList.map { it.first }
	override var weighedEdges: Collection<WeighedEdge<V, E>> =
			adjList.map { (startVertex, endVertexList) ->
				endVertexList.map { (data, endVertex) ->
					WeighedEdge(startVertex, endVertex, data)
				}
			}.flatten()
}

class AdjMatGraph<V>(var adjMat: Map<Vertex<V>, Map<Vertex<V>, Boolean>>)
	: Graph<V>() {
	override var vertices: Collection<Vertex<V>> = adjMat.keys

	override var edges: Collection<Edge<V>> =
			adjMat.map { (startVertex, endVertexMap) ->
				endVertexMap
						.filterValues { it }
						.map { (endVertex, _) ->
							Edge(startVertex, endVertex)
						}
			}.flatten()
}

class WeighedAdjMatGraph<V, E>(var adjMat: Map<Vertex<V>, Map<Vertex<V>, E?>>)
	: WeighedGraph<V, E>() {
	override var vertices: Collection<Vertex<V>> = adjMat.keys

	override var weighedEdges: Collection<WeighedEdge<V, E>> =
			adjMat.map { (startVertex, endVertexMap) ->
				endVertexMap
						.filterValues { it != null }
						.map { (endVertex, data) ->
							WeighedEdge(startVertex, endVertex, data)
						}
			}.flatten()
}
