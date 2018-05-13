- Consider the following problem, called BoxDepth: Given a set of n axis-aligned rectangles in the plane, how big is the largest subset of these rectangles that contain a common point? Describe a polynomial-time reduction from BoxDepth to MaxClique.

  Given a configuration, create an unweighted, undirected graph $G=(V,E)$ where $v\in V$ represents a given box and $(uv)\in E$ represents two boxes share a common point. I claim that the maximum size of a subset of these rectangles containing a common point is exactly the maximum size of a clique in $G$.

  $\Rightarrow$: Suppose the maximum size of a subset of these rectangles containing a common point is $k$. By construction, since they all share a common point, their corresponding vertices in $G$ all have edges between each other. Therefore, they form a clique. And since there are $k$ such rectangles, there will be $k$ vertices in the clique, i.e. a clique of size $k$.

  $\Rightarrow$: Suppose the maximum clique in $G$ has size $k$. Then in the clique, all vertices have edges to each other. By construction, an edge between two vertices implies the common point between them. Therefore, all $k$ corresponding rectangles share a common point.

- Describe a polynomial-time reduction from UndirectedHamiltonianCycle to
  DirectedHamiltonianCycle.

  Given an undirected graph $G=(V,E)$, we create a new directed graph $G'=(V',E'):V'=V$, and  $\forall(uv)\in E,(u\to v),(v\to u)\in E'$. Intuitively, we replace every undirected edge to two directed edges connecting the same vertices. I claim that $G$ has a Hamiltonian Cycle $iff.$ $G'$ has a Hamiltonian Cycle.

  $\Rightarrow$: Suppose $G$ has a Hamiltonian Cycle: $a\to b\to c\to\dots\to z\to a$. This implies $ab,bc,\dots,za\in E$ and it covers all vertices in $V$. By construction, $a\to b,b\to c,\dots,z\to a\in E'$. Therefore, The original cycle also exists in $G'$. Moreover, $V'=V$ implies this is a Hamiltonian Cycle in $G'$ as well.

  $\Leftarrow$: Suppose $G'$ has a Hamiltonian Cycle: $a\to b\to c\to\dots\to z\to a$. This implies $a\to b,b\to c,\dots,z\to a\in E'$. By construction, $(ab),(bc),\dots,(za)\in E$. Therefore, the cycle also exists in $G$ and cover all vertices. As a result, this is a Hamiltonian Cycle in $G$.

- Describe a polynomial-time reduction from DirectedHamiltonianCycle to UndirectedHamiltonianCycle.

  Given a directed graph $G=(V,E)$, we construct a new undirected graph $G'=(V',E')$ where $\forall v\in V,(v,in),(v,out)\in V'$ and $\forall u\to v\in E,(u,out)(v,in)\in E'$. Moreover, there are edges $(v,in)(v,out),\forall v\in V$. Intuitively, we split every vertex into two connected vertices with an extra attribution, i.e. in/out. And we transform every directed edge in $E$ to corresponding version of vertices in $V'$. I claim that $G$ has a Hamiltonian Cycle $iff.$ $G'$ has a Hamiltonian Cycle.

  $\Rightarrow$: Suppose $G$ has a Hamiltonian Cycle: $a\to b\to c\to\dots\to z\to a$.This implies $a\to b,b\to c,\dots,z\to a\in E$. By construction, $(a,out)(b,in),(b,out)(c,in),\dots\in E'$. Therefore, there is a corresponding cycle in $G'$. And I claim that such cycle covers every vertex at least once and at most once (therefore, exactly once, and thus is a Hamiltonian cycle).

  Suppose there is a vertex $(v,X)\in V'$ is not covered. If $X=in$, then there will be no edge coming into $v$. Then $v$ is not covered in the Hamiltonian Cycle in $G$. If $X=out$, then there will be no edge coming out from $v$. But every vertex in $G$ has an outgoing edge in a Hamiltonian Cycle. This is a contradiction so every vertex in $V'$ is covered at least once.

  Now suppose there is a vertex $(v,X)\in V'$ that is covered at least twice. If $X=in$, then there will be two edges coming into $v$ by construction. If $X=out$, then there will be two edges coming out from $v$. But for all vertices in a Hamiltonian Cycle in $G$, each vertex has in-degree one as well as out-degree one. So these cases cannot happen.                                                                                                                                                                                                                                                  $\Leftarrow:$ Suppose $G$ does NOT have a Hamiltonian Cycle. That means, every cycle in $G$ has at least one vertex not covered. Fix an arbitrary cycle in $G$ and let $v$ be the uncovered vertex. By construction this corresponding cycle in $G'$ won't cover $(v,in)$ and $(v,out)$. Otherwise, there must be an edge coming into or out from $v$. Thus, $G'$ won't have a Hamiltonian Cycle in that $(v,X)$ is NOT covered.

- Describe a polynomial-time reduction from HamiltonianPath to HamiltonianCycle.

  Given a graph $G=(V,E)$, we construct a new graph $G'=(V',E')$ as follows $V'=V\cup\{s\},E'=E\cup\{sv:\forall v\in V\}$. I claim that $G$ has a Hamiltonian Path $iff.$ $G'$ has a Hamiltonian Cycle.

  $\Rightarrow$: Suppose $G$ has a Hamiltonian Path $a\to b\to\dots\to z$. Since $s$ has edges to very other vertex, $(sa),(za)\in E'$. And by construction, $s\to a\to b\to\dots\to z\to s$ is a Hamiltonian Cycle in $G'$ in that it is indeed a cycle that covers all vertices in $V$ as well as $s$.

  $\Rightarrow$: Suppose $G'$ has a Hamiltonian Cycle $a\to b\to\dots s\to\dots\to a$. Since $s$ must be in the cycle, there are vertices $u,v:u\to s\to v$ is a subsequence in the cycle. And we know that $u,v\in V$. Now I claim that $v\to\dots\to v$ is a Hamiltonian Path in $G$. Intuitively, we only remove $s$ from the Hamiltonian Cycle and thus it must cover all vertices in $V$. Moreover, it also visits every vertex at most once in that it is derived from a Hamiltonian Cycle.

- Describe a polynomial-time reduction from HamiltonianCycle to HamiltonianPath.

  Given a graph $G=(V,E)$, we create $|E|$ new graphs $G'=(V',E')$ as follows: pick an edge $(uv)\in E$, add two more vertices $s,t:V'=V\cup\{s,t\},E'=E\cup\{(su),(tv)\}$. And there will be $|E|$ such choices of edges. I claim that $G$ has a Hamiltonian Cycle $iff.$any of such $G'$ has a Hamiltonian Cycle.

  $\Rightarrow$: Suppose $G$ has a Hamiltonian Cycle: $a\to b\to\dots\to z\to a$. Then let us add two more vertices at $(ab)\in E$. That is, $E'=E\cup\{(sa),(tb)\}$. Then $t\to b\to\dots\to a\to s$ is a Hamiltonian Path in $G'$. Indeed, it traverses vertices exactly once and covers all vertices in $V'=V\cup\{s,t\}$.

  $\Leftarrow:$ Suppose $G'$ has a Hamiltonian Path. Since there is only one edge connecting $s$ and connecting $t$, they have to be endpoints of the path. That is the path goes like $s\to a\to\dots\to z\to t$. Now I claim that $a\to\dots\to z\to a$ is a Hamiltonian Cycle. Since by construction, $(za)\in E$ and we already have a path from $a$ to $z$ without using that edge. Otherwise, $z$ will be visited twice and breaks the Hamiltonian Cycle.

  ​