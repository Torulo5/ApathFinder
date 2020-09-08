package com.aalgorithm;

import java.awt.Point;
import java.util.ArrayList;

import com.pathFinder.InfoMap;

@SuppressWarnings("unused")
public class ApathFinder extends Thread{

	
	private Anode start = null;
	private Anode end = null;
	private boolean outFinder = false;
	private ArrayList<Anode> openSet = null;
	private ArrayList<Anode> closeSet = null;
	private ArrayList<Anode> finalPath = null;
	
	private ArrayList<AfinderEvent> aFinderListeners = null;
	
	private int sleepTime = 0;
	
	private InfoMap infoMap = null;
	
	public ApathFinder(InfoMap map) {
		openSet = new ArrayList<Anode>();
		closeSet = new ArrayList<Anode>();
		finalPath = new ArrayList<Anode>();
		aFinderListeners = new ArrayList<AfinderEvent>();
		this.infoMap = map;
	}
	
	public void run() {
		if( start ==  null && end == null && openSet == null && closeSet == null) {
			System.out.println("ERROR datos");
			return;
		}

		// con cada llamada reiniciamos los arrays de trabajo
		openSet.clear();
		closeSet.clear();
		finalPath.clear();
		outFinder = false;
		
		double startTime = System.currentTimeMillis();
		
		openSet.add(start);
		Anode current = null;
		while (!openSet.isEmpty() && !outFinder) {
			current = openSet.get(0);
			for (Anode point : openSet) {
				if (point.getF() < current.getF()) {
					current = point;
				}
			}
			//System.out.println(current.getCoordenadas() + " F:" + current.getF());
			if (current.equals(end)) {
				this.generateFinalPath(current);
				outFinder = true;
				break;
			}

			closeSet.add(current);
			openSet.remove(current);

			ArrayList<Point> relatedNodesOfCurrent = current.getRelatedNodes();
			for (Point relatedNode : relatedNodesOfCurrent) {
				if(infoMap != null && infoMap.isBlocked(relatedNode))
					continue;
				
				Anode aux = new Anode(relatedNode);
				
				if (closeSet.contains(aux))
					continue;

				int tempG = current.getG() + 1; // +  1 lo que cuesta ir del current al vecino es este caso 1 para todos

				if (openSet.contains(aux)) {
					int index = 0;
					for (Anode auxSet : openSet) {
						if (auxSet.equals(aux))
							break;
						index++;
					}
					Anode nodeToUpdate = openSet.get(index);
					if (tempG < nodeToUpdate.getG()) {
						nodeToUpdate.setG(tempG);
						nodeToUpdate.setH(heuristicDiagonalDistance(aux, end));
						nodeToUpdate.setF(aux.getG() + aux.getH());
						nodeToUpdate.setAnterior(current);
					}
				} else {
					aux.setG(tempG);
					aux.setH(heuristicDiagonalDistance(aux, end));
					aux.setF(aux.getG() + aux.getH());
					aux.setAnterior(current);
					openSet.add(aux);
				}


			}
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.generateFinalPath(current);
			for (AfinderEvent listener : this.aFinderListeners) {
				listener.aNodeEvalauted(this.openSet,this.closeSet,this.finalPath);
			}
		}
		
		if(!outFinder) {
			System.out.println("NO SE ENCOTRO CAMINO");
		}
		
		double estimatedTime = System.currentTimeMillis() - startTime;
		double seconds = estimatedTime/1000;
		for (AfinderEvent listener : this.aFinderListeners) {
			listener.pathFinded(this.openSet,this.closeSet,this.finalPath,seconds);
		}
	}
	
	private void generateFinalPath(Anode current) {
		if(finalPath == null)
			return;
		finalPath.clear();
		
		Anode temp = current;
		while (temp.getAnterior() != null) {
			finalPath.add(temp);
			temp = temp.getAnterior();
		}
	}
	
	public void addAfinderListenersListener(AfinderEvent afEnvent) {
		this.aFinderListeners.add(afEnvent);
	}

	public void setStart(Point start) {
		this.start = new Anode(start);
	}

	public void setEnd(Point end) {
		this.end = new Anode(end);
	}
	

	private int heuristicDiagonalDistance(Anode a, Anode b) {
		return (int) Math.max(Math.abs(a.getCoordenadas().x-b.getCoordenadas().x),Math.abs(a.getCoordenadas().y-b.getCoordenadas().y));
	}
	
	private int heuristicManhattanDistance(Anode a, Anode b) {
		return (int) Math.abs(a.getCoordenadas().x-b.getCoordenadas().x) + Math.abs(a.getCoordenadas().y-b.getCoordenadas().y);
	}
	
	private int heuristicEuclideanDistance(Anode a, Anode b) {
		return (int) a.getCoordenadas().distance(b.getCoordenadas());
	}
}
