- Prove the following is NP-hard: Given an undirected graph $G$, does $G$ contain a simple path that visits all but 17 vertices?

  From Hamiltonian Path: Given a graph $G=(V,E)$, we construct a new graph $G'=(V',E'):V'=V\cup\{v_1,v_2,\dots,v_{17}\},E'=E$. I claim that $G$ has a Hamiltonian Cycle $iff.$ $G'$ has such path.

  $\Rightarrow:$ Suppose $G$ has a Hamiltonian Path. Then this path is exactly a path in $G'$ visiting all but 17 vertices.

  $\Leftarrow$: Suppose $G'$ has such path. I claim that 17 unvisited vertices must be the newly added vertices $v_1,v_2,\dots,v_{17}$. Otherwise, we will have at least 18 unvisited vertices. This is a contradiction. Therefore, all vertices covered in this path are actually in $G$. Then this path is indeed a Hamiltonian Path of $G$.

- Prove the following is NP-hard: Given an undirected graph $G$, does $G$ have a spanning tree in which every node has degree at most 23?

	From Hamiltonian path: Given an undirected graph $G=(V,E)$ with $n$ vertices, we construct a new graph $G'$ by for each vertex, we connect 21 new vertices to it. I claim that $G$ has a Hamiltonian Path $iff.$ $G'$ has a spanning tree in which every node has degree at most 23.

	$\Rightarrow$: Suppose $G$ has a Hamiltonian Path. Then following this path and visiting all other 21 newly added vertices give us a spanning tree in $G'$ with each node having degree at most 23. Indeed, for an intermediate vertex, it connects two vertices in this path and all its 21 connected new vertices.

	$\Leftarrow$: Suppose $G'$ has a such spanning tree. For a given vertex, since 21 of neighbors only connects to it, so there are at most two other vertices in $G$ that is connected to the tree. And since the spanning tree cover all vertices in $G$, this must be a Hamiltonian Path. If a vertex connects to more than two other vertices in $G$, then it must have degree greater than 23. This is a contradiction.

- Prove the following is NP-hard: Given an undirected graph $G$, does $G$ have a spanning tree with at most 42 leaves?

	From Hamiltonian Cycle. Given an undirected graph $G=(V,E)$, we construct a new graph $G'=(V',E'):V'=V\cup\{v_1,v_2,\dots,v_{41}\},E'=E\cup\{uv_i:i\in[1,41]\}$ for some $u\in V$. Intuitively, we add 41 new vertices and connect them with a single random vertex  $u$ in the original graph $G$. I claim that $G$ has a Hamiltonian Cycle $iff.$ $G'$ has a spanning tree with at most 42 leaves.

	$\Rightarrow$: Suppose $G$ has a Hamiltonian Cycle. Then it must be like $a\to b\to\dots\to u\to a$. Then, following along this cylce until $u$ and visiting $v_1,v_2,\dots,v_{41}$ give a spanning tree with 42 leaves as $a,v_1,v_2,\dots,v_{41}$.

	$\Leftarrow$: Suppose $G'$ has a spanning tree with at most 42 leaves. Since $v_1,v_2,\dots,v_{41}$ only have degree one, they must be 41 leaves. Therefore, the rest of the tree that must be in $G$ only contains one leaf. This forces $G$ to have a Hamiltonian Cycle. Otherwise, there will be at least two leaves in $G$. This is a contradiction.

- Prove the following is NP-hard: Given an undirected graph $G=(V,E)$, what is the size of the largest subset of vertices $S\subseteq  V:$ most 473 edges in $E$ have both endpoints in S?

	From Max Independent Set: Given an undirected graph $G=(V,E)$, we construct a new graph $G'=(V',E')$ where $V'=V\cup\{v_1,v_2,\dots,v_{473}\}\cup\{u_1,u_2,\dots,u_{473}\}$ and $E'=E\cup\{(u_iv_i):\forall i\in[1,473]\}$. I claim that $G$ has a max independent set of size $k$ $iff.$ $G'$ has such subset of vertices of size $k+473\cdot 2$.

	$\Rightarrow$: Suppose $G$ has a max independent set of size $k$. Now unioning this set with all newly added vertices (i.e. $u_1,u_2,\dots,u_{473},v_1,v_2,\dots,v_{473}$) gives us a subset of vertices $V'$ of size $k+473\cdot2$ where there are 473 edges in $E'$ having both endpoints in $S$.

	$\Leftarrow$: Suppose $G'$ has such set of size $k+473\cdot2$. I claim that there is at least one such set that contains all those newly added vertices. Indeed, suppose there exists a newly added vertex that is not contained, either add it to the subset or replace another vertex in $V$ by it gives us a valid subset that is at least of size $k+473\cdot 2$. Now since all $473\cdot 2$ newly added  vertices are included and use all 473 opportunities of including all edges with both endpoints in this set, removing those vertices gives us an independent set of size $k$ in $G$.

- Prove that the following is NP-hard: Given an undirected graph $G$, is it possible to color the vertices of $G$ with three different colors, so that at most 31337 edges have both endpoints the same color?

	From 3Color: Given an undirected graph $G=(V,E)$,  we construct a new graph $G'=(V',E')$. Intuitively, we add 31337 cliques of size four (vertices). I cliam that $G$ can be 3-colored $iff.$ $G'$ can be 31337-such colored.

	$\Rightarrow$: Suppose $G$ can be 3-colored. Since we know that a clique of size four cannot be 3-colored. Thus there will be at least one edge per clique that has endpoints colored the same. In total, there will be 31337 such edges as desired.

	$\Leftarrow$: Suppose $G'$ can be 31337-such colored. We know that 31337 edges are in those newly added cliques. Therefore, $G$ must be able to be 3-colored.