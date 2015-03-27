package viewersearch;

// Minimal class for a particular implementation of directed graphs.
// All we include is what is necessary to build a graph, in the class
// graph.

import java.util.*;

/**
 * The Class Node.
 *
 * @param <A>
 *            the generic type
 */
public class Node<A> {

	/** The contents. */
	private A contents;

	/** The successors. */
	private Set<Node<A>> successors;

	/** The node pointers. */
	private ArrayList<Node<A>> nodePointers;

	/**
	 * Instantiates a new node.
	 *
	 * @param contents
	 *            the contents
	 */
	public Node(A contents) {
		this.contents = contents;

		this.successors = new LinkedHashSet<Node<A>>();
		nodePointers = new ArrayList<Node<A>>();
	}

	/**
	 * Adds the pointers.
	 *
	 * @param point
	 *            The pointer
	 */
	public void addPointers(Node<A> point) {
		nodePointers.add(point);
	}

	/**
	 * Gets the pointers.
	 *
	 * @return The pointers
	 */
	public ArrayList<Node<A>> getPointers() {
		return nodePointers;
	}

	/**
	 * Adds the successor.
	 *
	 * @param s
	 *            The successors
	 */
	public void addSuccessor(Node<A> s) {
		successors.add(s);
	}

	/**
	 * Contents equals.
	 *
	 * @param c
	 *            the content
	 * @return true, if the content equals
	 */
	public boolean contentsEquals(A c) {
		return contents.equals(c);
	}

	// Get methods:
	/**
	 * Contents.
	 *
	 * @return the content
	 */
	public A contents() {
		return contents;
	}

	/**
	 * Successors.
	 *
	 * @return the set of successors
	 */
	public Set<Node<A>> successors() {
		return successors;
	}

	/**
	 * F.
	 *
	 * @param h
	 *            the h heuristics
	 * @param d
	 *            the d distance
	 * @return the integer sum of them
	 */
	public Integer f(int h, int d) {
		int f = h + d;
		return f;

	}

	public String toString() {
		return "" + contents;
	}

}
