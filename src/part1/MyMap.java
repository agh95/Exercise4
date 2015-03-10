package part1;

import lejos.geom.Line;
import lejos.geom.Point;
import lejos.geom.Rectangle;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.RPLineMap;

public class MyMap implements IGridMap {

	private int gridXSize;
	private int gridYSize;
	private int xStart;
	private int yStart;
	private int cellSize;
	private RPLineMap lineMap;
	
	public MyMap(int _gridXSize, int _gridYSize, int _xStart, int _yStart,
		int	_cellSize, RPLineMap _lineMap) {
		this.gridXSize = _gridXSize;
		this.gridYSize = _gridYSize;
		this.xStart = _xStart;
		this.yStart = _yStart;
		this.cellSize = _cellSize;
		this.lineMap = _lineMap;
	}

	@Override
	public int getXSize() {
		
		return gridXSize;
	}

	@Override
	public int getYSize() {
		// TODO Auto-generated method stub
		return gridYSize;
	}

	@Override
	public boolean isValidGridPosition(int _x, int _y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isObstructed(int _x, int _y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Point getCoordinatesOfGridPosition(int _x, int _y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValidTransition(int _x1, int _y1, int _x2, int _y2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float rangeToObstacleFromGridPosition(int _x, int _y, float _heading) {
		// TODO Auto-generated method stub
		return 0;
	}

}
