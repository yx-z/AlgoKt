package graph

import util.Tuple2

interface Graph<V> {
	fun getVertices(): Collection<Vertex<V>>
	fun getEdges(): Collection<Edge<V>>
}

class AdjListGraph<V>(var adjList: List<Tuple2<Vertex<V>, List<Vertex<V>>>>) : Graph<V> {
	override fun getVertices() = adjList.map { it.first }

	override fun getEdges() =
			adjList.map { (startVertex, endVertexList) ->
				endVertexList.map { endVertex ->
					Edge(startVertex, endVertex)
				}
			}.flatten()
}

class AdjMatGraph<V>(var adjMat) : Graph<V> {
	override fun getVertices(): Collection<Vertex<V>> {
		TODO()
	}

	override fun getEdges(): Collection<Edge<V>> {
		TODO()
	}
}

class Vertex<V>(var data: V? = null)

open class Edge<V>(var start: Vertex<V>, var end: Vertex<V>)

class WeighedEdge<V, E>(start: Vertex<V>, end: Vertex<V>, var data: E? = null) : Edge<V>(start, end)

fun main(args: Array<String>) {

}
