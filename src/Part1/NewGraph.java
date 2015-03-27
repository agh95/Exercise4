package Part1;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;

public class NewGraph<A> {

	private Set<Node<A>> nodes;

	// Constructs the empty graph:
	public NewGraph() {

		nodes = new LinkedHashSet<Node<A>>();

	}

	/**
	 * Get the set of nodes
	 * 
	 * @return The set of nodes
	 */
	public Set<Node<A>> nodes() {
		return nodes;
	}

	/**
	 * Finds or else creates a node with a given contents c:
	 * 
	 * @param c
	 *            The content of the node
	 * @return The node
	 */
	public Node<A> nodeWith(A c) {
		for (Node<A> node : nodes) { // Inefficient for large graph.
			if (node.contentsEquals(c))
				return node; // Found.
		}
		// Not found, hence create it:
		Node<A> node = new Node<A>(c);
		nodes.add(node);
		return node;
	}
}
