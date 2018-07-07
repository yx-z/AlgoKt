- The problem 12Color is defined as follows: Given an undirected graph $G$, determine whether we can color each vertex with one of twelve colors, so that every edge touches two different colors. Prove that 12Color is NP-hard. [Hint: Reduce from 3Color.]

  Given a graph $G=(V,E)$, we construct a new graph $G'=(V',E')$ where $V'=V\cup\{v_1,v_2,\dots,v_9\}$ and $E'=E\cup\{(v_i)u:\forall u\in V'\}$. Intuitively, we add a clique of size nine and connect all vertices in the clique to all vertices in $G$. I claim that $G$ can be 3-colored $iff.$ $G'$ can be 12-colored.

  $\Rightarrow$: Suppose $G$ can be 3-colored, then $v_i$ will have the $i+3$-th color.

  $\Leftarrow$: Suppose $G'$ can be 12-colored, we know that all newly added vertices in the clique (i.e. $v_1,v_2,\dots,v_9$) must take 9 colors. Therefore $G$ can be 3-colored.

- The problem 12SAT is defined as follows: Given a boolean formula  in CNF, with exactly twelve literals per clause, determine whether  has a satisfying assignment. Prove that 12Sat is NP-hard. [Hint: Reduce from 3Sat.]

  Given a 3CNF, add 9 more False variables $v_F$ for each clause by adding one more clause $\overline{v_F}\vee\overline{v_F}\vee\overline{v_F}$ at the end. Now this becomes a 12CNF. The proof should be straightforward.

- A subset $S$ of vertices in an undirected graph $G$ is called triangle-free if, for every
  triple of vertices $u,v,w\in S$, at least one of the three edges $uv,uw,vw$ is absent from $G$.
  Prove that finding the size of the largest triangle-free subset of vertices in a given undirected graph is NP-hard.

  From MaxIndependentSet: Given a graph $G=(V,E)$ with $n$ vertices $V=\{v_1,v_2,\dots,v_n\}$, we construct a new graph $G'=(V',E')$ where $V'=V\cup\{v_i':\forall i\in[1,n]\}$ and $E'=E\cup\{(v_iv_i'):\forall i\in[1,n]\}\cup\{(v_i'u):\forall(uv_i)\in E\}$. Intuitively, for each vertex $v_i$, we add a new vertex $v_i'$ and connect it to $v_i$ and all neighbor's of $v_i$. I claim that $G$ has an independent set of size $k$ $iff.$ $G'$ has a triangle-free set of size $n+k$.

  $\Rightarrow$: This direction is trivial. We just add all $v_i',\forall i\in[1,n]$ to the independent set.

  $\Leftarrow$: Suppose $G'$ has a triangle-free set of size $n+k$. I claim that there must be a subset that includes all $n$ newly added vertices $v_i',\forall i\in[1,n]$. Suppose for a given triangle-free set $T$, $v_j'\notin T$. Then suppose $v_j\notin T$, we can safely add $v_j'$ in $T$ and remove any $v_k\in T\cap V$. Suppose $v_j\in T$ and none of the neighbors of $v_j$ is in $T$, we can still add $v_j'$ to $T$ and remove a random vertex $v_k\in T\cap V$. Finally suppose one of $v_j$'s neighbor is in $T$. Since $T$ is triangle-free, we know that no other neighbors of $v_j$ is also in $T$. We can replace this neighbor by $v_j$. Then the set is still triangle-free and has the same size. Now I claim that after getting such triangle-free set $T$ of size $n+k$ for $G'$, removing all those newly added vertices $v_i',\forall i\in[1,n]$ gives us an independent set $T'$ for $G$ of size $k$. Indeed, $|T|=n+k-n=k$. All vertices in $T'$ are in $G$ (sinse we have removed all those newly added vertices). Moreover, suppose $\exists v_i,v_j\in T':(v_iv_j)\in E$. Then before removal of $v_i'$, $v_i,v_j,v_i'$ will form a triangle. This is a contradiction. Therefore, I claim that $T'$ must be an independent set for $G$ as desired.

- The RectangleTiling problem is defined as follows: Given one large rectangle and several smaller rectangles, determine whether the smaller rectangles can be placed inside the large rectangle with no gaps or overlaps.

	From Partition: Given a multiset with $n$ numbers, $S=\{s_1,s_2,\dots,s_n\}$. Let $s=\sum_{i=1}^ns_i$. We want to show that $S$ can be partitioned properly into two sets with each summing up to $\frac{s}{2}$.