package com.aalgorithm;

import java.awt.Point;
import java.util.ArrayList;

public class InfoMap {

	/**
	 *  Coste normal de un punto es 1
	 *  
	 */

	private ArrayList<Point> pointsBlocked = null;
	private ArrayList<Point> pointsDiferentCost = null;
	private Point start = null;
	private Point end = null;
	private boolean diagonalCost = false;
	
	public InfoMap() {
		this.pointsBlocked = new ArrayList<Point>();
		this.pointsDiferentCost = new ArrayList<Point>();
	}
	
	public void addBlockedPoint(Point punto) {
		if(pointsDiferentCost.contains(punto))
			pointsDiferentCost.remove(punto);
		
		if(!pointsBlocked.contains(punto))
			pointsBlocked.add(punto);
	}
	
	public void addDiferentCostPoint(Point punto) {
		if(pointsBlocked.contains(punto))
			pointsBlocked.remove(punto);
		
		if(!pointsDiferentCost.contains(punto))
			pointsDiferentCost.add(punto);
	}
	
	public int getCostOfPoint(Point point) {
		int coste = 1;
		if(pointsDiferentCost.contains(point)) {
			coste = 100;
		}
		return coste;
	}
	
	public boolean isBlocked(Point point) {
		boolean isBlocked = false;
		if(pointsBlocked.contains(point)) {
			isBlocked = true;
		}
		return isBlocked;
	}
	
	public Point getStart() {
		return start;
	}

	public Point getEnd() {
		return end;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public void setEnd(Point end) {
		this.end = end;
	}
	
	public boolean isDiagonalCost() {
		return diagonalCost;
	}

	public void setDiagonalCost(boolean diagonalCost) {
		this.diagonalCost = diagonalCost;
	}

	public void clearAllData() {
		pointsBlocked.clear();
		pointsDiferentCost.clear();
		start = null;
		end = null;
	}
}
