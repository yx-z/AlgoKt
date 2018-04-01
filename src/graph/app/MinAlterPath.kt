package graph.app

import graph.abstract.ComparableVertex
import graph.abstract.WeightedGraph

// given a weighted graph, with each vertex associated with an int
// you start from s and want to reach t in a path : # of alternations in the
// path is as small as possible
// an alternation happens in u -> v -> w if the value of v is greater/less
// than both that of u and that of v
// note that the path might not be shortest in total weight

// report this path
fun WeightedGraph<Int, Int>.minAlterPath(s: ComparableVertex<Int>,
                                         t: ComparableVertex<Int>)
		: Map<ComparableVertex<Int>, ComparableVertex<Int>?> {
	val parent = HashMap<ComparableVertex<Int>, ComparableVertex<Int>?>()
	vertices.forEach { parent[it as ComparableVertex<Int>] = null }

	TODO()

	return parent
}

fun main(args: Array<String>) {

}