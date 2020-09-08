package com.pathFinder;

import java.awt.Point;
import java.util.ArrayList;

public class InfoMap {

	/**
	 *  Coste normal de un punto es 1
	 *  
	 */

	private ArrayList<Point> pointsBlocked = null;
	private ArrayList<Point> pointsDiferentCost = null;
	
	public InfoMap() {
		this.pointsBlocked = new ArrayList<Point>();
		this.pointsDiferentCost = new ArrayList<Point>();
	}
	
	public void addBlockedPoint(Point punto) {
		pointsBlocked.add(punto);
	}
	
	public int getCostOfPoint(Point point) {
		return 0;
	}
	
	public boolean isBlocked(Point point) {
		boolean isBlocked = false;
		if(pointsBlocked.contains(point)) {
			isBlocked = true;
		}
		return isBlocked;
	}
	
	public void clearAllData() {
		pointsBlocked.clear();
		pointsDiferentCost.clear();
	}
}
