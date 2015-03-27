package viewersearch;

import java.util.ArrayList;

import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;

public class PopulateGraph<A> {

	public int xJunctions = 12;
	public int yJunctions = 8;

	public PopulateGraph() {

	}

	/**
	 * Populating the graph with the nodes returned from NewGraph
	 * 
	 * @param myGraph
	 *            The nodes
	 */
	public void populateGraph(NewGraph<Coordinate> myGraph) {
		MyMap map = getMap();
		ArrayList<Node<Coordinate>> graph = new ArrayList<Node<Coordinate>>();

		for (int q = 0; q < xJunctions; q++) {
			for (int p = 0; p < yJunctions; p++) {
				Coordinate coord = new Coordinate(q, p);
				graph.add(myGraph.nodeWith(coord));
			}
		}

		for (Node<Coordinate> node : graph) {
			int xcoord = node.contents().x;
			int ycoord = node.contents().y;

			if (map.isValidTransition(xcoord, ycoord, xcoord + 1, ycoord)) {

				Coordinate coord1 = new Coordinate(xcoord + 1, ycoord);
				node.addSuccessor(myGraph.nodeWith(coord1));
			}
			if (map.isValidTransition(xcoord, ycoord, xcoord, ycoord + 1)) {

				Coordinate coord2 = new Coordinate(xcoord, ycoord + 1);
				node.addSuccessor(myGraph.nodeWith(coord2));
			}
			if (map.isValidTransition(xcoord, ycoord, xcoord - 1, ycoord)) {

				Coordinate coord3 = new Coordinate(xcoord - 1, ycoord);
				node.addSuccessor(myGraph.nodeWith(coord3));
			}
			if (map.isValidTransition(xcoord, ycoord, xcoord, ycoord - 1)) {

				Coordinate coord4 = new Coordinate(xcoord, ycoord - 1);
				node.addSuccessor(myGraph.nodeWith(coord4));
			}
		}
	}

	/**
	 * Getting the map with all lines and junctions
	 * 
	 * @return
	 */
	public MyMap getMap() {
		RPLineMap lineMap = MapUtils.create2015Map1();

		// grid map dimensions for this line map
		float junctionSeparation = 30;

		// position of grid map 0,0
		int xInset = 15;
		int yInset = 15;
		MyMap map = new MyMap(lineMap, xJunctions, yJunctions, xInset, yInset,
				junctionSeparation);
		return map;
	}
}
