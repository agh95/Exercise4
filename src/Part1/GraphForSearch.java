package Part1;

import java.util.Set;
import java.util.Stack;

// TODO: Auto-generated Javadoc
/**
 * The Class GraphForSearch.
 */
public class GraphForSearch {

	public static void main(String args[]) {
		NewGraph<Coordinate> myGraph = new NewGraph<Coordinate>();
		PopulateGraph<Coordinate> populate = new PopulateGraph<Coordinate>();
		populate.populateGraph(myGraph);

		for (Node<Coordinate> node : myGraph.nodes()) {
			System.out.print("(" + node.contents().x + "," + node.contents().y
					+ "): ");
			for (Node<Coordinate> s : node.successors()) {
				System.out.print("(" + s.contents().x + "," + s.contents().y
						+ ")");
			}
			System.out.println();
		}

		Stack<Node<Coordinate>> result = getPath(myGraph, new Coordinate(5, 4),
				new Coordinate(8, 1));
		int j = result.size();
		for (int i = 0; i < j; i++) {
			Node<Coordinate> x1 = result.pop();
			System.out.print("(" + x1.contents().x + ", " + x1.contents().y
					+ ") ");
		}
		System.out.println("");
	}

	/**
	 * Gets the path.
	 *
	 * @param myGraph
	 *            The nodes
	 * @param startCoord
	 *            The start coord
	 * @param goalCoord
	 *            the goal coord
	 * @return the path
	 */
	public static Stack<Node<Coordinate>> getPath(NewGraph<Coordinate> myGraph,
			Coordinate startCoord, final Coordinate goalCoord) {
		Predicate<Coordinate> p = new Predicate<Coordinate>() {
			public boolean holds(Coordinate x) {
				return (x.equals(goalCoord));

			}
		};

		Node<Coordinate> startnode = myGraph.nodeWith(startCoord);
		BreathFirst<Coordinate> searchB = new BreathFirst<Coordinate>();

		System.out.println("Finding a path using BFS:");
		Stack<Node<Coordinate>> path = searchB.findPath(startnode, p);
		return path;
	}
}
