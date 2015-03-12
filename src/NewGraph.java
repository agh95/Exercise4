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
	    // Choose any implementation of sets you please, but you need to
	    // choose one.
	    nodes = new LinkedHashSet<Node<A>>(); 
	  }

	  // Get method:
	  public Set<Node<A>> nodes() {
	    return nodes;
	  }

	  // Finds or else creates a node with a given contents c:
	  public Node<A> nodeWith(A c) { 
	    for (Node<A> node : nodes) {  // Inefficient for large graph.
	      if (node.contentsEquals(c))
	        return node; // Found.
	    }
	    // Not found, hence create it:
	    Node<A> node = new Node<A>(c);
	    nodes.add(node);
	    return node;
	  }
	  public static void main(String args []) {
		  NewGraph<Coordinate> myGraph = new NewGraph<Coordinate>();
			RPLineMap lineMap = MapUtils.create2015Map1();

			// grid map dimensions for this line map
			int xJunctions = 12;
			int yJunctions = 8;
			float junctionSeparation = 30;

			// position of grid map 0,0
			int xInset = 15;
			int yInset = 15;
			MyMap map = new MyMap(lineMap, xJunctions,yJunctions,xInset, yInset, junctionSeparation);
			ArrayList<Node<Coordinate>> graph = new ArrayList<Node<Coordinate>>();
			//Node<Coordinate> [] [] graph = new Node<Coordinate>[xJunctions][yJunctions];
			Node<Coordinate> [] nodes;
			for(int q=0; q< xJunctions;q++)
			  {
				  for(int p=0; p< yJunctions; p++)
				  {
					   Coordinate coord = new Coordinate(q,p);
					   graph.add(myGraph.nodeWith(coord));
				  }
			  }
			
		for (Node<Coordinate> node : graph)
		{
			// Find node neighbors
			//node.getNeighbors();
			int xcoord = node.contents().x;
			int ycoord = node.contents().y;
			// Add the valid ones as successors
			//for (Node<Coordinate> neighbor)
			//{
				
			//}
			if(map.isValidTransition(xcoord, ycoord, xcoord+1, ycoord))
			  {
			
				Coordinate coord1 = new Coordinate(xcoord+1,ycoord);
				node.addSuccessor(myGraph.nodeWith(coord1));
			  }
			if(map.isValidTransition(xcoord, ycoord, xcoord, ycoord+1))
			  {
			
				Coordinate coord2 = new Coordinate(xcoord,ycoord+1);
				node.addSuccessor(myGraph.nodeWith(coord2));
			  }
			if(map.isValidTransition(xcoord, ycoord, xcoord-1, ycoord))
			  {
			
				Coordinate coord3 = new Coordinate(xcoord-1,ycoord);
				node.addSuccessor(myGraph.nodeWith(coord3));
			  }
			if(map.isValidTransition(xcoord, ycoord, xcoord, ycoord-1))
			  {
			
				Coordinate coord4 = new Coordinate(xcoord,ycoord-1);
				node.addSuccessor(myGraph.nodeWith(coord4));
			  }
		}
		for (Node<Coordinate> node : myGraph.nodes()) {
	    	System.out.print("(" + node.contents().x + "," + node.contents().y + "): ");
	    	for(Node<Coordinate> s : node.successors()) {
	    		System.out.print("(" + s.contents().x + "," + s.contents().y + ")");
	    	}
	    	System.out.println();
	    }	
		Predicate<Coordinate> p = new Predicate<Coordinate>()
				{
					public boolean holds(Coordinate x) {
						return (x.equals(new Coordinate(8,3)));
						
					}
				};
		
		Coordinate c = new Coordinate(0,0);
		//Coordinate goal = new Coordinate(5,4);
		Node<Coordinate> startnode = myGraph.nodeWith(c);
		//Node<Coordinate> goalnode = myGraph.nodeWith(goal);
		BreathFirst<Coordinate> searchB = new BreathFirst<Coordinate>();
		Maybe<Node<Coordinate>> node2 = searchB.Search(startnode, p);
		System.out.println("Finding the node using BFS:");	
		try{
		System.out.println("("+node2.fromMaybe().contents().x+", "+node2.fromMaybe().contents().y+")");
		}
		catch(RuntimeException e)
		{
			System.out.println("Not found");
		}
		System.out.println("Finding a path using BFS:");	
		Stack<Node<Coordinate>> path = searchB.findPath(startnode, p);
			for(int i=0; i<path.size(); i++)
			{
				System.out.print("(" + path.get(i).contents().x +", "+ path.get(i).contents().y + ") ");		
			}
		System.out.println("");
	  }
}