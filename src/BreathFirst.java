import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class BreathFirst<A> {

	public Maybe<Node<A>> Search(Node<A> startNode, Predicate<A> predicate) {
		Queue<Node<A>> queue = new LinkedList<Node<A>>();
		
		Stack<Node<A>> visited = new Stack<Node<A>>();

		queue.add(startNode);

		while (!queue.isEmpty()) {
			Node<A> x = queue.remove();
			A content = x.contents();
			if (!alreadyVisited(x, visited)) {
				if (predicate.holds(content)) {
					return new Just<Node<A>>(x);
				}

				visited.push(x);
				for (Node<A> suc : x.successors()) {
					if (!(alreadyVisited(suc, visited))) {
						queue.add(suc);
					}
				}

			}
		}
		return new Nothing<Node<A>>();
	}
	
	public Stack<Node<A>> findPath(Node<A> startNode, Predicate<A> predicate) {
		Queue<Node<A>> queue = new LinkedList<Node<A>>();
		LinkedHashMap<Node<A>, Node<A>> map = new LinkedHashMap<Node<A>,Node<A>>();
		Stack<Node<A>> visited = new Stack<Node<A>>();
		Stack<Node<A>> path = new Stack<Node<A>>();

		queue.add(startNode);

		while (!queue.isEmpty()) {
			Node<A> x = queue.remove();
			A content = x.contents();
			if (!alreadyVisited(x, visited)) {
				if (predicate.holds(content)) {
					Node<A> a = x;
					while(a != startNode)
					{
						path.push(a);
						a = map.get(a);
					}
					path.push(startNode);
					return path;
				}

				visited.push(x);
				for (Node<A> suc : x.successors()) {
					if (!(alreadyVisited(suc, visited))) {
						map.put(suc,  x);
						queue.add(suc);
					}
				}

			}
		}
		return new Stack<Node<A>>();
	}
	private boolean alreadyVisited(Node<A> something, Stack<Node<A>> visited) {

		if (visited.contains(something)) {
			return true;
		}
		return false;
	}
}
