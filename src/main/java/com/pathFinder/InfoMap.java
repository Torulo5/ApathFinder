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
	
	
	public ArrayList<Point> calculateNeighbors(Point point) {
		return null;
	}
	

	public int getCostOfPoint(Point point) {
		return 0;
	}
	
	public boolean isBlocked(Point point) {
		return false;
	}
}
