package graph.app

import graph.abstract.Edge
import graph.abstract.Graph
import graph.abstract.Vertex
import graph.abstract.topoSort
import util.*

// given m games being played between n players,
// each game is played by three players and have a single ranking (1st, 2nd, 3rd)
// note that not all players played same number of games
// and also assume that if one player wins another (has a higher ranking) in one
// game, then s/he will win the other player in all games and thus has an overall
// higher ranking

// find the overall ranking among n players

fun main(args: Array<String>) {
	// player id from 1 to 5
	val players = OneArray(5) { it }
	// [(1st place, 2nd place, 3rd place), ...]
	val games = oneArrayOf(
			1 tu 3 tu 2,
			1 tu 3 tu 4,
			1 tu 2 tu 5,
			1 tu 4 tu 5)
	println(gameRanking(players, games))
}

fun gameRanking(players: OneArray<Int>,
                games: OneArray<Tuple3<Int, Int, Int>>): OneArray<Int> {
	val vertices = players.map { Vertex(it) }
	val edges = HashSet<Edge<Int>>()
	games.forEach { (first, second, third) ->
		edges.add(Edge(Vertex(first), Vertex(second), true))
		edges.add(Edge(Vertex(first), Vertex(third), true))
		edges.add(Edge(Vertex(second), Vertex(third), true))
	}
	val graph = Graph(vertices, edges)
	return graph.topoSort().map { it.data }.toOneArray()
}
