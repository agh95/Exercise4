package Part1;

/**
 *Predicate to create the goal node
 * @param <A>
 */
public interface Predicate<A> {
    boolean holds(A a);
}
