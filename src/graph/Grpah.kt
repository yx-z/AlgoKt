package graph

import util.Tuple2

interface Graph<V> {
	fun getVertices(): Collection<Vertex<V>>
	fun getEdges(): Collection<Edge<V>>
}

interface WeighedGraph<V, E> : Graph<V> {
	override fun getEdges(): Collection<WeighedEdge<V, E>>
}

class Vertex<V>(var data: V? = null)

open class Edge<V>(var start: Vertex<V>, var end: Vertex<V>)

class WeighedEdge<V, E>(start: Vertex<V>, end: Vertex<V>, var data: E? = null) : Edge<V>(start, end)

class AdjListGraph<V>(var adjList: List<Tuple2<Vertex<V>, List<Vertex<V>>>>) : Graph<V> {
	override fun getVertices() = adjList.map { it.first }

	override fun getEdges() =
			adjList.map { (startVertex, endVertexList) ->
				endVertexList.map { endVertex -> Edge(startVertex, endVertex) }
			}.flatten()
}

class AdjMatGraph<V>(var adjMat: Map<Vertex<V>, Map<Vertex<V>, Boolean>>) : Graph<V> {
	override fun getVertices() = adjMat.keys

	override fun getEdges() =
			adjMat.map { (startVertex, endVertexMap) ->
				endVertexMap
						.filterValues { it }
						.map { (endVertex, _) -> Edge(startVertex, endVertex) }
			}.flatten()
}

class WeighedAdjMatGraph<V, E>(var adjMat: Map<Vertex<V>, Map<Vertex<V>, E?>>) : WeighedGraph<V, E> {
	override fun getVertices() = adjMat.keys

	override fun getEdges() =
			adjMat.map { (startVertex, endVertexMap) ->
				endVertexMap
						.filterValues { it != null }
						.map { (endVertex, data) -> WeighedEdge(startVertex, endVertex, data) }
			}.flatten()
}

fun main(args: Array<String>) {

}
