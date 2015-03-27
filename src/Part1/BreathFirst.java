package Part1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class BreathFirst<A> {

	/**
	 * Finding a path from node 1 to node 2.
	 *
	 * @param startNode
	 *            The start node
	 * @param predicate
	 *            The goal node
	 * @return A Stack consisting the path
	 */
	public Stack<Node<A>> findPath(Node<A> startNode, Predicate<A> predicate) {
		Queue<Node<A>> queue = new LinkedList<Node<A>>();
		Stack<Node<A>> pointers = new Stack<Node<A>>();
		Stack<Node<A>> visited = new Stack<Node<A>>();
		Stack<Node<A>> path = new Stack<Node<A>>();

		queue.add(startNode);

		while (!queue.isEmpty()) {
			Node<A> x = queue.remove();
			A content = x.contents();
			if (!alreadyVisited(x, visited)) {
				if (predicate.holds(content)) {
					Node<A> a = x;
					while (a != startNode) {
						Node<A> aNode = a;
						path.push(a);
						while (aNode.contents().equals(a.contents())) {
							if (!pointers.empty()) {
								Node<A> z = pointers.pop();
								ArrayList<Node<A>> point = z.getPointers();
								for (int i = 0; i < point.size(); i++) {
									if (point.get(i).contents()
											.equals(a.contents())) {
										a = z;
									}
								}
							}
						}

					}
					path.push(startNode);
					return path;
				}

				visited.push(x);
				pointers.push(x);

				for (Node<A> suc : x.successors()) {
					if (!(alreadyVisited(suc, visited))) {
						queue.add(suc);
						x.addPointers(suc);
					}
				}

			}
		}
		return new Stack<Node<A>>();
	}

	/**
	 * Check if a node is already visited
	 * 
	 * @param something
	 *            The node
	 * @param visited
	 *            Stack of visited nodes
	 * @return true if the node is in the visited stack
	 */
	private boolean alreadyVisited(Node<A> something, Stack<Node<A>> visited) {

		if (visited.contains(something)) {
			return true;
		}
		return false;
	}
}
