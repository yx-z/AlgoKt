- Prove the following is NP-hard: Given a graph $G$, compute the maximum-diameter spanning tree of $G$. (diameter of a spanning tree $T$ is the length of the longest path in $T$)

  From Hamiltonian Path: Given a graph $G=(V,E)$, I claim that $G$ has a Hamiltonian path $iff.$ the max diameter spanning tree of $G$ is $|V| - 1$.

  $\Rightarrow$: Suppose $G$ has a Hamiltonian Path. It must be the longest path in $T$. Moreover, it has length $|V|-1$.

  $\Leftarrow$: Suppose $G$ has a spanning tree with longest path of length $|V|-1$. We know that the only possible case is that this path traverses all $|V|$ vertices. Therefore, it must be a Hamiltonian Path in $G$.

- Prove the following is NP-hard: Given a graph $G$ with weighted edges, compute the min weight depth-first spanning tree of $G$.

  From Hamiltonian Path: Given a graph $G=(V,E)$, we construct a new graph $G'=(V',E')$, where $V'=V, E'=\{(uv):\forall u,v\in V'\}$. Then we associate edge weight $+\infty$ for newly added edges and edge weigth 1 for original ones. I claim that $G$ has a Hamiltonian Path $iff.$ $G'$ has a min weight depth first spanning tree $\neq+\infty$.

  $\Rightarrow$: Suppose $G$ has a Hamiltonian Path. Then it will be indeed a depth first spanning tree (rooted at the starting point). And since we only use edges with edge weight 1, the total weight will not be $+\infty$ as claimed.

  $\Leftarrow$: Suppose $G'$ has a depth first spanning tree with edge weight $\neq+\infty$. We know that $G'$ is a complete graph, meaning all spanning trees of $G'$ will be a path. Since it is not $+\infty$, we know this path only uses original edges. Therefore, $G$ must have a Hamiltonian Path.

- Prove the following is NP-hard: Given a graph $G$ with weighted edges and a subset $S$ of vertices of $G$, compute the min weight spanning tree all of whose leaves are in S.

  From Hamiltonian cycle: Given a graph $G=(V,E)$, I claim that $G$ has a Hamiltonian Path $iff.$ $G$ has a spanning tree with leaves only in $S=\{t,s\}$, for some $(ts)\in E$.

  $\Rightarrow$: Suppose $G$ has a Hamiltonian Cycle. Then this breaking any edge of this cycle is indeed a spanning tree with only two leaves and covers all vertices in $G$ (by definition).

  $\Leftarrow$: Suppose there is a spanning tree with two leaves only as $s$ and $t$. Since $(ts)\in E$, connecting the edge forms a cycle together with this spanning tree.

- Prove the following is NP-hard: Given a graph $G$ with weighted edges and an integer $i$, compute the min weighted spanning tree with at most  $i$ leaves.

  From Hamiltonian Path: I claim that $G$ has a Hamiltonian Path $iff.$ $G$ has a spanning tree with at most 2 leaves.

  $\Rightarrow$: Indeed, a Hamiltonian Path contains 2 leaves.

  $\Leftarrow$: Suppose $G$ has a spanning tree with at most 2 leaves. Since a spanning tree doesn't have any cycles, 2 leaves must be two endpoints and thus the spanning tree is indeed a path that covers all vertices in $G$, i.e. a Hamiltonian Path.

- Prove the following is NP-hard: Given a graph $G$ with weighted edges and an integer $i$, compute the min weight spanning tree where every node has degree at most $i$.

  From Hamiltonian Path: Given a graph $G=(V,E)$, we construct a new graph $G'=(V',E')$, where $V'=V\cup\{s\}$ and $E'=E\cup\{(sv)\}$ for some $v\in V$. Intuitively, we add a new vertex and connect it to some vertex in $G$. Then, we set $(sv)$ having weight -100 and all other edges having weight 0. I claim that $G$ has a Hamiltonian Path $iff.$ $G'$ has a spanning tree with weight -100 in which each node has degree at most 2.

  $\Rightarrow$: Suppose $G$ has a Hamiltonian Path. We add $s$ at one of its endpoints of the path and then it will be a spanning tree of weight -100 in which each node has degree at most 2.

  $\Leftarrow$: Suppose $G' $has such spanning tree described above. First notice that $s$ must be a leaf of the tree. W.L.G., set $s$ to be the root of the tree.Now since every node has degree at most 2, this tree must be a path. Otherwise, one node (as an intermediate node) having more than one child must have degree more than 2. This is a contradiction.


