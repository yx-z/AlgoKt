package graph.app

import graph.abstract.Vertex
import graph.abstract.WeightedGraph
import graph.abstract.dijkstra
import util.min

// suppose you have computed the all pairs shortest path (apsp) table
// but later you find out that your edge weight for u -> v is wrong during
// the computation... that is, w(u -> v) is used but w'(u -> v) is true weight

// 1. suppose w'(u -> v) < w(u -> v), restore the table in O(V^2) time
fun <V> HashMap<Vertex<V>, HashMap<Vertex<V>, Int>>.restore(
		u: Vertex<V>,
		v: Vertex<V>,
		newWeight: Int) {
	// dist[x, y] = min{ dist[x, y], dist[x, u] + w'(u -> v) + dist[v, y] }
	val dist = this
	forEach { (x, map) ->
		map.forEach { (y, _) ->
			map[y] = min(map[y]!!, map[u]!! + newWeight + dist[v]!![y]!!)
		}
	}
}

// 2. use O(1) time to check whether your computed table is correct or not
//    (yes it can still be correct), still assuming w'(u -> v) < w(u -> v)
fun <V> HashMap<Vertex<V>, HashMap<Vertex<V>, Int>>.isCorrect(
		u: Vertex<V>,
		v: Vertex<V>,
		newWeight: Int) = this[u]!![v]!! <= newWeight
// we actually just need to check if there exists a shortest path from u to v
// that doesn't use the edge u -> v!

// 3. ues O(VE) time to check whether your computed table is correct or not
//    , but without restriction that w'(u -> v) < w(u -> v)
fun <V> HashMap<Vertex<V>, HashMap<Vertex<V>, Int>>.isCorrect(
		u: Vertex<V>,
		v: Vertex<V>,
		g: WeightedGraph<V, Int>,
		newWeight: Int) = g.dijkstra(u).second[v] !== u && this[u]!![v]!! <= newWeight
// the idea is similar: check if the shortest path from u to v
// is actually using the edge u -> v, if it is then it must be wrong
// if it is NOT, it still can be wrong if the newWeight is smaller! we should use the new weight
