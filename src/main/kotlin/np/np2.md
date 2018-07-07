- Prove that PlanarCircuitSat is NP-hard.

  We will prove that PlanarCircuitSat is NP-hard by showing a polynomial-time reduction from CircuitSat.

  Given a circuit board, for each crossing of wires $x$ and $y$, we will construct a sequence of gates : we can resolve the crossing while maintaining the same output.

  First note that $x\oplus y\oplus y=x,x\oplus y\oplus x=y$. And we can implement an XOR gate with only AND, OR, and NOT gates by using the fact that $a\oplus b=(a+b)(\overline{a}+\overline{b})$. Therefore, for each crossing, we can replace it with three XOR gates and make the circuit becomes planar. The reduction takes clearly polynomial time. Thus solving PlanarCircuitSat in polynomial time directly implies CircuitSat can be solved in polynomial time. Thereby, PlanarCircuitSat is NP-hard.

- Prove that NotAllEqual3Sat is NP-hard.

	We will prove that NotAllEqual3Sat is NP-hard by showing a polynomial-time reduction from 3SAT.

	Given a 3CNF, for each clause $(a\vee b\vee c)$, we split it into two clauses $(a\vee b\vee d)\wedge(c\vee\overline{d}\vee F)$, where $F$ is a constant variable for false. We will then replace $F$ by $v_F$ and assuring that $v_F$ is false by adding one more clause at the end $(v_T\vee v_F\vee v_F)$. Note that $v_F$ might be true but in order to satisfy the 3CNF, $v_F$ still has to be false. Now I claim that the original 3CNF is satisfiable $iff.$ the transformed 3CNF is NotAllEqual3 satisfiable

	$\Leftarrow$: Suppose the transformed 3CNF is satisfiable. Then either $a$ or $b$ is true or $c$ is true for each clause. Therefore, the original 3CNF must be satisfiable.

	$\Rightarrow$: Suppose the original 3CNF is satisfiable, then for each clause, all seven possible assignments of $a,b$ and $c$ can lead to a possible assignment for $d$ such that the transformed two clauses are true.

- Prove that 1-in-3Sat is NP-hard.

	We will prove that 1-in-3Sat is NP-hard by showing a polynomial-time reduction from 3SAT.

	Given a 3CNF, for each clause $(a\vee b\vee c)$, we split it into three clauses $(\overline{a}\vee w\vee x)\wedge(b\vee x\vee y)\wedge(\overline{c}\vee y\vee z)$. I claim that $(a\vee b\vee c)$ is satisfiable $iff.$ the transformed three clauses are 1-in-3 satisfiable.

	$\Leftarrow$: Supopse the transformed 3CNF is 1-in-3 satisfiable. Suppose $w$ is true… $y$ is true… $z$ is true… then...

	$\Rightarrow$: Suppose $a\vee b\vee c$ is satisfiable. There are seven cases...

- Prove that the following variant of 3Sat is NP-hard: Given a boolean formula in conjunctive normal form where each clause contains at most 3 literals and each variable appears in at most 3 clauses, does it have a satisfying assignment?

	Given a 3CNF, we will use a map to keep track of number of times each varaible is used in different clauses. Intuitively, when we saw a variable $x$ in the fourth clause, we will replace it with $x'$. Similarly, when we then saw $x'$ in the fourth clause, we will replace it with $x''$. Now all we need to do is to make sure that $x$ and $x'$ should have the same value assignment. We can add new clauses: $(x\vee\overline{x'}\vee F)\wedge(\overline{x}\vee x'\vee F)$. This can be true $iff.$ $x=x'$. Now we have used $F$ multiple times, we have to make sure that we create enough $F$ variables by $(\overline{v_F}\vee\overline{v_F}\vee\overline{v_F})$. But still, this is finite amount of $v_F$s so it is still doable. We can see that the original 3CNF is satisifiable $iff.$ the transformed 3CNF is satisifaible with extra condition. Therefore, this problem is NP-hard.