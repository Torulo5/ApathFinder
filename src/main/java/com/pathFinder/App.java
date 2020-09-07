package com.pathFinder;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import com.aalgorithm.AfinderEvent;
import com.aalgorithm.Anode;
import com.aalgorithm.ApathFinder;
import com.views.GameFrame;

public class App implements AfinderEvent{

	private GameFrame gFrame;
	private ApathFinder aFinder = null;
	private Point start = null;
	private Point end = null;

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

		aFinder = new ApathFinder();
		start = new Point(10,10);
		end = new Point(20,24);
		aFinder.setStart(start);
		aFinder.setEnd(end);
		
		gFrame.addPointToGrid(start, Color.GREEN);
		gFrame.addPointToGrid(end, Color.magenta);
		
		aFinder.addAfinderListenersListener(this);

	}
	
	public void runFinder() {
		aFinder = new ApathFinder();
		start = new Point(10,10);
		end = new Point(20,24);
		aFinder.setStart(start);
		aFinder.setEnd(end);
		
		gFrame.addPointToGrid(start, Color.GREEN);
		gFrame.addPointToGrid(end, Color.magenta);
		
		aFinder.addAfinderListenersListener(this);
		aFinder.start();
	}

	public synchronized void aNodeEvalauted(ArrayList<Anode> openSet, ArrayList<Anode> closeSet, ArrayList<Anode> finalPath) {
		this.handlerAfinderEvent(openSet, closeSet, finalPath);
	}

	public void pathFinded(ArrayList<Anode> openSet, ArrayList<Anode> closeSet, ArrayList<Anode> finalPath) {
		this.handlerAfinderEvent(openSet, closeSet, finalPath);
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
		gFrame.addPointsToGrid(openSetPoints, Color.BLUE); 
		gFrame.addPointsToGrid(closeSetPoints, Color.RED); 
		gFrame.addPointsToGrid(finalPathPoints, Color.CYAN); 
		gFrame.addPointToGrid(start, Color.GREEN);
		gFrame.addPointToGrid(end, Color.magenta);
		gFrame.repaint();
	}
	



}
