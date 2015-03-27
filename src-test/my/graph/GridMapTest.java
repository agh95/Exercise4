package my.graph;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.AssertJUnit;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import lejos.geom.Line;
import lejos.geom.Rectangle;

import org.testng.Assert;

import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;
import rp.robotics.mapping.Heading;
import viewersearch.BreathFirst;
import viewersearch.Coordinate;
import viewersearch.GraphForSearch;
import viewersearch.MyMap;
import viewersearch.NewGraph;
import viewersearch.Node;
import viewersearch.PopulateGraph;
import viewersearch.Predicate;

public class GridMapTest {

	/***
	 * Create an instance of an object that implements IGridMap from a LineMap.
	 * You should copy this test file to your own project and replace
	 * NicksGridMap with you own implementation.
	 * 
	 * @param _lineMap
	 *            The underlying line map
	 * @param _gridXSize
	 *            How many grid positions along the x axis
	 * @param _gridYSize
	 *            How many grid positions along the y axis
	 * @param _xStart
	 *            The x coordinate where grid position (0,0) starts
	 * @param _yStart
	 *            The y coordinate where grid position (0,0) starts
	 * @param _cellSize
	 *            The distance between grid positions
	 * @return
	 */
	public static IGridMap createGridMap(RPLineMap _lineMap, int _gridXSize,
			int _gridYSize, float _xStart, float _yStart, float _cellSize) {
		return new MyMap(_lineMap, _gridXSize, _gridYSize, _xStart, _yStart,
				_cellSize);
	}

	public static IGridMap createRectangularGridMap(int _xJunctions,
			int _yJunctions, float _pointSeparation) {

		int xInset = (int) (_pointSeparation / 2);
		int yInset = (int) (_pointSeparation / 2);

		float _width = _xJunctions * _pointSeparation;
		float _height = _yJunctions * _pointSeparation;

		ArrayList<Line> lines = new ArrayList<Line>();

		// these are the walls for the world outline
		lines.add(new Line(0f, 0f, _width, 0f));
		lines.add(new Line(_width, 0f, _width, _height));
		lines.add(new Line(_width, _height, 0f, _height));
		lines.add(new Line(0f, _height, 0f, 0f));

		Line[] lineArray = new Line[lines.size()];
		lines.toArray(lineArray);

		return createGridMap(new RPLineMap(lineArray, new Rectangle(0, 0,
				_width, _height)), _xJunctions, _yJunctions, xInset, yInset,
				_pointSeparation);
	}

	/**
	 * Creates a grid map to match the training map as of 6/3/2013.
	 * 
	 * @return
	 */
	public static IGridMap createTestMap() {
		float height = 238;
		float width = 366;

		// junction numbers
		int xJunctions = 12;
		int yJunctions = 7;

		// position of 0,0 junction wrt to top left of map
		int xInset = 24;
		int yInset = 24;

		// length of edges between junctions.
		int junctionSeparation = 30;

		ArrayList<Line> lines = new ArrayList<Line>();

		// these are the walls for the world outline
		lines.add(new Line(0f, 0f, width, 0f));
		lines.add(new Line(width, 0f, width, height));
		lines.add(new Line(width, height, 0f, height));
		lines.add(new Line(0f, height, 0f, 0f));

		lines.add(new Line(75.0f, 133f, 100f, 133f));
		lines.add(new Line(75.0f, 193.0f, 100f, 193.0f));
		lines.add(new Line(100f, 133f, 100f, 193.0f));
		lines.add(new Line(75.0f, 133f, 75.0f, 193.0f));

		lines.addAll(MapUtils.lineToBox(42f, 67f, 287f, 67f));
		lines.add(new Line(287f, 0, 287f, 67f));
		lines.add(new Line(257f, 0, 257f, 67f));

		lines.addAll(MapUtils.lineToBox(135f, 129f, 255f, 129f));
		lines.addAll(MapUtils.lineToBox(135f, 129f, 135f, height));

		lines.addAll(MapUtils.lineToBox(194f, 191f, 254f, 191f));
		lines.addAll(MapUtils.lineToBox(194f, 191f, 194f, height));

		lines.add(new Line(width - 42f, 99f, width, 99f));
		lines.add(new Line(width - 42f, 159f, width, 159f));
		lines.add(new Line(width - 42f, 99f, width - 42f, 159f));
		Line[] lineArray = new Line[lines.size()];
		lines.toArray(lineArray);

		return createGridMap(new RPLineMap(lineArray, new Rectangle(0, 0,
				width, height)), xJunctions, yJunctions, xInset, yInset,
				junctionSeparation);
	}

	@Test
	public void testMapTest() {

		IGridMap map = createTestMap();
		int width = map.getXSize();
		int height = map.getYSize();

		HashSet<Point> blocked = new HashSet<Point>();
		blocked.add(new Point(8, 0));
		blocked.add(new Point(8, 1));
		blocked.add(new Point(10, 3));
		blocked.add(new Point(11, 3));
		blocked.add(new Point(10, 4));
		blocked.add(new Point(11, 4));
		blocked.add(new Point(2, 4));
		blocked.add(new Point(2, 5));

		HashMap<Point, Point> invalid = new HashMap<Point, Point>();
		invalid.put(new Point(1, 1), new Point(1, 2));
		invalid.put(new Point(2, 1), new Point(2, 2));
		invalid.put(new Point(3, 1), new Point(3, 2));
		invalid.put(new Point(4, 1), new Point(4, 2));
		invalid.put(new Point(5, 1), new Point(5, 2));
		invalid.put(new Point(6, 1), new Point(6, 2));
		invalid.put(new Point(7, 1), new Point(7, 2));
		invalid.put(new Point(4, 3), new Point(4, 4));
		invalid.put(new Point(5, 3), new Point(5, 4));
		invalid.put(new Point(6, 3), new Point(6, 4));
		invalid.put(new Point(7, 3), new Point(7, 4));

		invalid.put(new Point(3, 4), new Point(4, 4));
		invalid.put(new Point(3, 5), new Point(4, 5));
		invalid.put(new Point(3, 6), new Point(4, 6));

		invalid.put(new Point(5, 6), new Point(6, 6));
		invalid.put(new Point(6, 5), new Point(6, 6));
		invalid.put(new Point(7, 5), new Point(7, 6));

		for (int y = 0; y < height; y++) {

			for (int x = 0; x < width; x++) {

				Point from = new Point(x, y);

				if (blocked.contains(from)) {

					// an in place transition should be fine
					AssertJUnit.assertFalse(map.isValidTransition(from.x,
							from.y, from.x, from.y));
				} else {
					if (x > 0) {

						Point to = new Point(x - 1, y);

						if (blocked.contains(to)
								|| !isManuallyApproved(from, to, invalid)) {
							AssertJUnit.assertFalse(map.isValidTransition(
									from.x, from.y, to.x, to.y));
						} else {
							AssertJUnit.assertTrue(map.isValidTransition(
									from.x, from.y, to.x, to.y));
						}
					}

					if (x < width - 1) {

						Point to = new Point(x + 1, y);

						if (blocked.contains(to)
								|| !isManuallyApproved(from, to, invalid)) {
							AssertJUnit.assertFalse(map.isValidTransition(
									from.x, from.y, to.x, to.y));
						} else {
							AssertJUnit.assertTrue(map.isValidTransition(
									from.x, from.y, to.x, to.y));
						}
					}

					if (y > 0) {

						Point to = new Point(x, y - 1);

						if (blocked.contains(to)
								|| !isManuallyApproved(from, to, invalid)) {
							AssertJUnit.assertFalse(map.isValidTransition(
									from.x, from.y, to.x, to.y));
						} else {
							AssertJUnit.assertTrue(map.isValidTransition(
									from.x, from.y, to.x, to.y));
						}
					}

					if (y < height - 1) {

						Point to = new Point(x, y + 1);

						if (blocked.contains(to)
								|| !isManuallyApproved(from, to, invalid)) {
							AssertJUnit.assertFalse(map.isValidTransition(
									from.x, from.y, to.x, to.y));
						} else {
							AssertJUnit.assertTrue(
									"from " + from + " to " + to, map
											.isValidTransition(from.x, from.y,
													to.x, to.y));
						}
					}
				}
			}
		}

	}

	private boolean isManuallyApproved(Point _from, Point _to,
			HashMap<Point, Point> _invalid) {

		Point to = _invalid.get(_from);
		if (to != null && to.equals(_to)) {
			return false;
		}

		Point from = _invalid.get(_to);
		if (from != null && from.equals(_from)) {
			return false;
		}

		return true;
	}

	@Test
	public void rectangularMapTest() {
		int width = 10;
		int height = 10;

		IGridMap map = createRectangularGridMap(width, height, 30);

		for (int y = 0; y < height; y++) {

			for (int x = 0; x < width; x++) {

				int toX = x;
				int toY = y;

				// an in place transition should be fine
				AssertJUnit.assertTrue(map.isValidTransition(x, y, toX, toY));

				if (x > 0) {
					toX = x - 1;
					toY = y;
					AssertJUnit.assertTrue(map
							.isValidTransition(x, y, toX, toY));
					System.out.println(x + " " + y + " " + toX + " " + toY);
				}

				if (x < width - 1) {
					toX = x + 1;
					toY = y;
					AssertJUnit.assertTrue(map
							.isValidTransition(x, y, toX, toY));
					System.out.println(x + " " + y + " " + toX + " " + toY);

				}

				if (y > 0) {
					toX = x;
					toY = y - 1;
					AssertJUnit.assertTrue(map
							.isValidTransition(x, y, toX, toY));
					System.out.println(x + " " + y + " " + toX + " " + toY);
				}

				if (y < height - 1) {
					toX = x;
					toY = y + 1;
					AssertJUnit.assertTrue(map
							.isValidTransition(x, y, toX, toY));
					System.out.println(x + " " + y + " " + toX + " " + toY);
				}

			}
		}
	}

	@Test
	private void rangeToObstacleTest() {

		float sep = 30f;
		float target = sep / 2f;
		IGridMap map = createRectangularGridMap(1, 1, sep);
		AssertJUnit.assertEquals(
				map.rangeToObstacleFromGridPosition(0, 0,
						Heading.toDegrees(Heading.PLUS_X)), target, 0f);
		AssertJUnit.assertEquals(
				map.rangeToObstacleFromGridPosition(0, 0,
						Heading.toDegrees(Heading.PLUS_Y)), target, 0f);
		AssertJUnit.assertEquals(
				map.rangeToObstacleFromGridPosition(0, 0,
						Heading.toDegrees(Heading.MINUS_X)), target, 0f);
		AssertJUnit.assertEquals(
				map.rangeToObstacleFromGridPosition(0, 0,
						Heading.toDegrees(Heading.MINUS_Y)), target, 0f);

		map = createTestMap();

		// copied from above method
		float height = 238;
		float width = 366;

		// position of 0,0 junction wrt to top left of map
		int xInset = 24;
		int yInset = 24;

		AssertJUnit.assertEquals(
				map.rangeToObstacleFromGridPosition(0, 0,
						Heading.toDegrees(Heading.PLUS_X)), 233f, 0f);

		AssertJUnit
				.assertEquals(
						map.rangeToObstacleFromGridPosition(0, 0,
								Heading.toDegrees(Heading.PLUS_Y)), height
								- yInset, 0f);

		AssertJUnit.assertEquals(
				map.rangeToObstacleFromGridPosition(0, 0,
						Heading.toDegrees(Heading.MINUS_X)), xInset, 0f);

		AssertJUnit.assertEquals(
				map.rangeToObstacleFromGridPosition(0, 0,
						Heading.toDegrees(Heading.MINUS_Y)), yInset, 0f);

	}

	@Test
	private void testFindPath() {

		NewGraph<Coordinate> ng = new NewGraph<Coordinate>();
		PopulateGraph<Coordinate> pg = new PopulateGraph<Coordinate>();
		pg.populateGraph(ng);

		BreathFirst<Coordinate> bf = new BreathFirst<Coordinate>();
		/*
		 * Test 1
		 */
		Stack<Node<Coordinate>> path = bf.findPath(
				ng.nodeWith(new Coordinate(0, 0)), new Predicate<Coordinate>() {
					public boolean holds(Coordinate c) {
						return c.equals(new Coordinate(2, 3));
					}
				});
		Node<Coordinate> n1 = ng.nodeWith(new Coordinate(2, 3));
		Node<Coordinate> n2 = ng.nodeWith(new Coordinate(1, 3));
		Node<Coordinate> n3 = ng.nodeWith(new Coordinate(1, 2));
		Node<Coordinate> n4 = ng.nodeWith(new Coordinate(1, 1));
		Node<Coordinate> n5 = ng.nodeWith(new Coordinate(1, 0));
		Node<Coordinate> n6 = ng.nodeWith(new Coordinate(0, 0));

		Stack<Node<Coordinate>> expected = new Stack<Node<Coordinate>>();
		expected.push(n1);
		expected.push(n2);
		expected.push(n3);
		expected.push(n4);
		expected.push(n5);
		expected.push(n6);

		Assert.assertTrue(path.equals(expected));

		/*
		 * Test 2
		 */
		Stack<Node<Coordinate>> path2 = bf.findPath(
				ng.nodeWith(new Coordinate(2, 1)), new Predicate<Coordinate>() {
					public boolean holds(Coordinate c) {
						return c.equals(new Coordinate(4, 1));
					}
				});
		Node<Coordinate> x1 = ng.nodeWith(new Coordinate(4, 1));
		Node<Coordinate> x2 = ng.nodeWith(new Coordinate(3, 1));
		Node<Coordinate> x3 = ng.nodeWith(new Coordinate(2, 1));

		Stack<Node<Coordinate>> expected2 = new Stack<Node<Coordinate>>();
		expected2.push(x1);
		expected2.push(x2);
		expected2.push(x3);

		Assert.assertTrue(path2.equals(expected2));
		/*
		 * Test 3
		 */
		Stack<Node<Coordinate>> path3 = bf.findPath(
				ng.nodeWith(new Coordinate(5, 1)), new Predicate<Coordinate>() {
					public boolean holds(Coordinate c) {
						return c.equals(new Coordinate(8, 1));
					}
				});
		Node<Coordinate> d1 = ng.nodeWith(new Coordinate(8, 1));
		Node<Coordinate> d2 = ng.nodeWith(new Coordinate(7, 1));
		Node<Coordinate> d3 = ng.nodeWith(new Coordinate(6, 1));
		Node<Coordinate> d4 = ng.nodeWith(new Coordinate(5, 1));

		Stack<Node<Coordinate>> expected3 = new Stack<Node<Coordinate>>();
		expected3.push(d1);
		expected3.push(d2);
		expected3.push(d3);
		expected3.push(d4);

		Assert.assertTrue(path3.equals(expected3));
		/*
		 * Test 4
		 */
		Stack<Node<Coordinate>> path4 = bf.findPath(
				ng.nodeWith(new Coordinate(5, 4)), new Predicate<Coordinate>() {
					public boolean holds(Coordinate c) {
						return c.equals(new Coordinate(8, 1));
					}
				});
		Node<Coordinate> k1 = ng.nodeWith(new Coordinate(8, 1));
		Node<Coordinate> k2 = ng.nodeWith(new Coordinate(8, 2));
		Node<Coordinate> k3 = ng.nodeWith(new Coordinate(8, 3));
		Node<Coordinate> k4 = ng.nodeWith(new Coordinate(8, 4));
		Node<Coordinate> k5 = ng.nodeWith(new Coordinate(8, 5));
		Node<Coordinate> k6 = ng.nodeWith(new Coordinate(7, 5));
		Node<Coordinate> k7 = ng.nodeWith(new Coordinate(6, 5));
		Node<Coordinate> k8 = ng.nodeWith(new Coordinate(5, 5));
		Node<Coordinate> k9 = ng.nodeWith(new Coordinate(5, 4));

		Stack<Node<Coordinate>> expected4 = new Stack<Node<Coordinate>>();
		expected4.push(k1);
		expected4.push(k2);
		expected4.push(k3);
		expected4.push(k4);
		expected4.push(k5);
		expected4.push(k6);
		expected4.push(k7);
		expected4.push(k8);
		expected4.push(k9);

		Assert.assertTrue(path4.equals(expected4));

		/*
		 * Test 5
		 */
		Stack<Node<Coordinate>> path5 = bf.findPath(
				ng.nodeWith(new Coordinate(9, 3)), new Predicate<Coordinate>() {
					public boolean holds(Coordinate c) {
						return c.equals(new Coordinate(4, 1));
					}
				});
		Node<Coordinate> p1 = ng.nodeWith(new Coordinate(4, 1));
		Node<Coordinate> p2 = ng.nodeWith(new Coordinate(5, 1));
		Node<Coordinate> p3 = ng.nodeWith(new Coordinate(6, 1));
		Node<Coordinate> p4 = ng.nodeWith(new Coordinate(7, 1));
		Node<Coordinate> p5 = ng.nodeWith(new Coordinate(8, 1));
		Node<Coordinate> p6 = ng.nodeWith(new Coordinate(9, 1));
		Node<Coordinate> p7 = ng.nodeWith(new Coordinate(9, 2));
		Node<Coordinate> p8 = ng.nodeWith(new Coordinate(9, 3));

		Stack<Node<Coordinate>> expected5 = new Stack<Node<Coordinate>>();
		expected5.push(p1);
		expected5.push(p2);
		expected5.push(p3);
		expected5.push(p4);
		expected5.push(p5);
		expected5.push(p6);
		expected5.push(p7);
		expected5.push(p8);

		Assert.assertTrue(path5.equals(expected5));

		/*
		 * Test 6
		 */
		// There is no path between these two nodes
		Stack<Node<Coordinate>> path6 = bf.findPath(
				ng.nodeWith(new Coordinate(2, 0)), new Predicate<Coordinate>() {
					public boolean holds(Coordinate c) {
						return c.equals(new Coordinate(11, 1));
					}
				});
		// It should return empty stack
		Stack<Node<Coordinate>> expected6 = new Stack<Node<Coordinate>>();

		Assert.assertTrue(path6.equals(expected6));

		/*
		 * Test 7
		 */
		Stack<Node<Coordinate>> path7 = bf.findPath(
				ng.nodeWith(new Coordinate(8, 1)), new Predicate<Coordinate>() {
					public boolean holds(Coordinate c) {
						return c.equals(new Coordinate(11, 1));
					}
				});
		Stack<Node<Coordinate>> not_expected7 = new Stack<Node<Coordinate>>();
		Node<Coordinate> no5 = ng.nodeWith(new Coordinate(8, 1));
		Node<Coordinate> no6 = ng.nodeWith(new Coordinate(9, 1));
		Node<Coordinate> no7 = ng.nodeWith(new Coordinate(9, 2));
		Node<Coordinate> no8 = ng.nodeWith(new Coordinate(9, 3));

		not_expected7.add(no5);
		not_expected7.add(no6);
		not_expected7.add(no7);
		not_expected7.add(no8);

		Assert.assertFalse(path7.equals(not_expected7));
		/*
		 * Test 8
		 */
		Stack<Node<Coordinate>> path8 = bf.findPath(
				ng.nodeWith(new Coordinate(1, 0)), new Predicate<Coordinate>() {
					public boolean holds(Coordinate c) {
						return c.equals(new Coordinate(0, 6));
					}
				});
		Stack<Node<Coordinate>> not_expected8 = new Stack<Node<Coordinate>>();
		Node<Coordinate> not1 = ng.nodeWith(new Coordinate(0, 1));
		Node<Coordinate> not2 = ng.nodeWith(new Coordinate(1, 0));
		Node<Coordinate> not3 = ng.nodeWith(new Coordinate(1, 1));
		Node<Coordinate> not4 = ng.nodeWith(new Coordinate(1, 2));
		Node<Coordinate> not5 = ng.nodeWith(new Coordinate(1, 3));
		Node<Coordinate> not6 = ng.nodeWith(new Coordinate(1, 4));
		Node<Coordinate> not7 = ng.nodeWith(new Coordinate(1, 5));
		Node<Coordinate> not8 = ng.nodeWith(new Coordinate(1, 6));
		Node<Coordinate> not9 = ng.nodeWith(new Coordinate(0, 6));

		not_expected8.add(not1);
		not_expected8.add(not2);
		not_expected8.add(not3);
		not_expected8.add(not4);
		not_expected8.add(not5);
		not_expected8.add(not6);
		not_expected8.add(not7);
		not_expected8.add(not8);
		not_expected8.add(not9);

		Assert.assertFalse(path8.equals(not_expected8));
		/*
		 * Test 9
		 */
		Stack<Node<Coordinate>> path9 = bf.findPath(
				ng.nodeWith(new Coordinate(1, 0)), new Predicate<Coordinate>() {
					public boolean holds(Coordinate c) {
						return c.equals(new Coordinate(0, 6));
					}
				});
		Stack<Node<Coordinate>> not_expected9 = new Stack<Node<Coordinate>>();
		Node<Coordinate> ot1 = ng.nodeWith(new Coordinate(1, 6));
		Node<Coordinate> ot2 = ng.nodeWith(new Coordinate(2, 5));
		Node<Coordinate> ot3 = ng.nodeWith(new Coordinate(2, 6));
		Node<Coordinate> ot4 = ng.nodeWith(new Coordinate(1, 5));
		Node<Coordinate> ot5 = ng.nodeWith(new Coordinate(1, 4));
		Node<Coordinate> ot6 = ng.nodeWith(new Coordinate(1, 3));
		Node<Coordinate> ot7 = ng.nodeWith(new Coordinate(1, 2));
		Node<Coordinate> ot8 = ng.nodeWith(new Coordinate(1, 1));
		Node<Coordinate> ot9 = ng.nodeWith(new Coordinate(1, 0));

		not_expected9.add(ot1);
		not_expected9.add(ot2);
		not_expected9.add(ot3);
		not_expected9.add(ot4);
		not_expected9.add(ot5);
		not_expected9.add(ot6);
		not_expected9.add(ot7);
		not_expected9.add(ot8);
		not_expected9.add(ot9);

		Assert.assertFalse(path9.equals(not_expected9));
		/*
		 * Test 10
		 */

		Stack<Node<Coordinate>> path10 = bf.findPath(
				ng.nodeWith(new Coordinate(1, 0)), new Predicate<Coordinate>() {
					public boolean holds(Coordinate c) {
						return c.equals(new Coordinate(5, 6));
					}
				});
		Stack<Node<Coordinate>> not_expected10 = new Stack<Node<Coordinate>>();
		Node<Coordinate> f1 = ng.nodeWith(new Coordinate(5, 6));
		Node<Coordinate> f2 = ng.nodeWith(new Coordinate(5, 5));
		Node<Coordinate> f3 = ng.nodeWith(new Coordinate(4, 5));
		Node<Coordinate> f4 = ng.nodeWith(new Coordinate(3, 5));
		Node<Coordinate> f5 = ng.nodeWith(new Coordinate(2, 5));
		Node<Coordinate> f6 = ng.nodeWith(new Coordinate(2, 6));
		Node<Coordinate> f7 = ng.nodeWith(new Coordinate(1, 6));
		Node<Coordinate> f8 = ng.nodeWith(new Coordinate(1, 5));
		Node<Coordinate> f9 = ng.nodeWith(new Coordinate(1, 4));
		Node<Coordinate> f10 = ng.nodeWith(new Coordinate(1, 3));
		Node<Coordinate> f11 = ng.nodeWith(new Coordinate(1, 2));
		Node<Coordinate> f12 = ng.nodeWith(new Coordinate(1, 1));
		Node<Coordinate> f13 = ng.nodeWith(new Coordinate(1, 0));

		not_expected10.add(f1);
		not_expected10.add(f2);
		not_expected10.add(f3);
		not_expected10.add(f4);
		not_expected10.add(f5);
		not_expected10.add(f6);
		not_expected10.add(f7);
		not_expected10.add(f8);
		not_expected10.add(f9);
		not_expected10.add(f10);
		not_expected10.add(f11);
		not_expected10.add(f12);
		not_expected10.add(f13);

		Assert.assertFalse(path10.equals(not_expected10));

	}

}
