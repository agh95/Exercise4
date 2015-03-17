package Part1;

import java.util.Set;
import java.util.Stack;

public class GraphForSearch {
	
	public static void main (String args[])
	{
		NewGraph<Coordinate> myGraph = new NewGraph<Coordinate>();
		PopulateGraph<Coordinate> populate = new PopulateGraph<Coordinate>();
		populate.populateGraph(myGraph);
		
//		for (Node<Coordinate> node : myGraph.nodes()) {
//	    	System.out.print("(" + node.contents().x + "," + node.contents().y + "): ");
//	    	for(Node<Coordinate> s : node.successors()) {
//	    		System.out.print("(" + s.contents().x + "," + s.contents().y + ")");
//	    	}
//	    	System.out.println();
//	    }	
		
		Stack<Node<Coordinate>> result = getPath(myGraph, new Coordinate(0,0), new Coordinate(2,3));
		for(int i=0; i<result.size(); i++)
			{
				System.out.print("(" + result.get(i).contents().x +", "+ result.get(i).contents().y + ") ");		
			}
		System.out.println("");
	  }
	

	
	public static Stack<Node<Coordinate>> getPath(NewGraph<Coordinate> myGraph, Coordinate startCoord, final Coordinate goalCoord)
	{
		Predicate<Coordinate> p = new Predicate<Coordinate>()
				{
					public boolean holds(Coordinate x) {
						return (x.equals(goalCoord));
						
					}
				};
		
		
		//Coordinate goal = new Coordinate(5,4);
		Node<Coordinate> startnode = myGraph.nodeWith(startCoord);
		BreathFirst<Coordinate> searchB = new BreathFirst<Coordinate>();
		
		System.out.println("Finding a path using BFS:");	
		Stack<Node<Coordinate>> path = searchB.findPath(startnode, p);
		return path;
	}
	}


