package com.pathFinder;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import com.aalgorithm.AfinderEvent;
import com.aalgorithm.Anode;
import com.aalgorithm.ApathFinder;
import com.aalgorithm.InfoMap;
import com.views.GameFrame;

public class App implements AfinderEvent{

	private GameFrame gFrame;
	private ApathFinder aFinder = null;
	private InfoMap infoMap = null;

	public static void main(String[] args) {
		App fidnerApp = new App();
		fidnerApp.launch();
	}
	
	public void launch() {
		gFrame = new GameFrame(this);
		gFrame.pack();
		gFrame.setSize(1200, 800);

		gFrame.setLocationRelativeTo(null);
		gFrame.setVisible(true);

		infoMap = new InfoMap();
		infoMap.setStart(new Point(10,10));
		infoMap.setEnd(new Point(25,30));
		
		gFrame.addPointToGrid(infoMap.getStart(), Color.GREEN);
		gFrame.addPointToGrid(infoMap.getEnd(), Color.magenta);
	}
	
	public void runFinder() {
		if(aFinder == null || !aFinder.isFinderRunning()) {
			aFinder = new ApathFinder(infoMap);
			
			gFrame.addPointToGrid(infoMap.getStart(), Color.GREEN);
			gFrame.addPointToGrid(infoMap.getEnd(), Color.magenta);
			
			aFinder.addAfinderListener(this);
			aFinder.start();
		}
	}

	public synchronized void aNodeEvaluated(ArrayList<Anode> openSet, ArrayList<Anode> closeSet, ArrayList<Anode> finalPath) {
		this.handlerAfinderEvent(openSet, closeSet, finalPath);
	}

	public void pathFinded(ArrayList<Anode> openSet, ArrayList<Anode> closeSet, ArrayList<Anode> finalPath, double seconds) {
		this.handlerAfinderEvent(openSet, closeSet, finalPath);
		gFrame.setTimeElapsedInSearch(seconds);
	}
	
	private void handlerAfinderEvent(ArrayList<Anode> openSet, ArrayList<Anode> closeSet, ArrayList<Anode> finalPath) {
		ArrayList<Point> openSetPoints = new ArrayList<Point>();
		for (Anode node : openSet) {
			openSetPoints.add(node.getCoordenadas());
		}

		ArrayList<Point> closeSetPoints = new ArrayList<Point>();
		for (Anode node : closeSet) {
			closeSetPoints.add(node.getCoordenadas());
		}

		ArrayList<Point> finalPathPoints = new ArrayList<Point>();
		for (Anode node : finalPath) {
			finalPathPoints.add(node.getCoordenadas());
		}
		
		gFrame.clearAllPoint();
		gFrame.addPointsToGrid(closeSetPoints, Color.RED); 
		gFrame.addPointsToGrid(openSetPoints, Color.BLUE); 
		gFrame.addPointsToGrid(finalPathPoints, Color.CYAN); 
		gFrame.addPointToGrid(infoMap.getStart(), Color.GREEN);
		gFrame.addPointToGrid(infoMap.getEnd(), Color.magenta);
		gFrame.repaint();
	}
	

	public void setBlockedPoint(Point punto) {
		infoMap.addBlockedPoint(punto);
	}
	
	public void resetGrid() {
		infoMap.clearAllData();
		infoMap.setStart(new Point(10,10));
		infoMap.setEnd(new Point(25,30));
		gFrame.addPointToGrid(infoMap.getStart(), Color.GREEN);
		gFrame.addPointToGrid(infoMap.getEnd(), Color.magenta);
		gFrame.repaint();
	}


}
