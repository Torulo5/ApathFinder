package com.aalgorithm;

import java.awt.Point;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class ApathFinder extends Thread{

	private InfoMap infoMap = null;
	
	private Anode start = null;
	private Anode end = null;
	private boolean outFinder = false;
	private ArrayList<Anode> openSet = null;
	private ArrayList<Anode> closeSet = null;
	private ArrayList<Anode> finalPath = null;
	private ArrayList<AfinderEvent> aFinderListeners = null;
	private int sleepTime = 0;

	private boolean isFinderRunning = false;
	
	public ApathFinder(InfoMap map) {
		openSet = new ArrayList<Anode>();
		closeSet = new ArrayList<Anode>();
		finalPath = new ArrayList<Anode>();
		aFinderListeners = new ArrayList<AfinderEvent>();
		this.infoMap = map;
		start = new Anode(infoMap.getStart());
		end = new Anode(infoMap.getEnd());
		isFinderRunning = false;
	}
	
	@Override
	public void run() {
		if( start ==  null && end == null && openSet == null && closeSet == null) {
			System.out.println("ERROR datos");
			return;
		}
		
		this.isFinderRunning = true;
		openSet.clear();
		closeSet.clear();
		finalPath.clear();
		outFinder = false;
		
		double startTime = System.currentTimeMillis();
		
		openSet.add(start);
		Anode current = null;
		while (!openSet.isEmpty() && !outFinder) {
			current = this.getLowestFfromOpenset();

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
				
				Anode nodeToUpdate = getAnodeIfExistFromOpenSet(aux);
				if (nodeToUpdate != null) {
					if (tempG < nodeToUpdate.getG()) {
						this.updateAnode(nodeToUpdate, tempG, current);;
					}
				} else {
					this.updateAnode(aux, tempG, current);
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
				listener.aNodeEvaluated(this.openSet,this.closeSet,this.finalPath);
			}
		}
		
		double estimatedTime = System.currentTimeMillis() - startTime;
		double seconds = estimatedTime/1000;
		if(!outFinder) {
			System.out.println("NO SE ENCOTRO CAMINO");
		} else {
			for (AfinderEvent listener : this.aFinderListeners) {
				listener.pathFinded(this.openSet,this.closeSet,this.finalPath,seconds);
			}
		}	
		
		isFinderRunning = false;
	}
	
	private Anode getAnodeIfExistFromOpenSet(Anode searchedAnode) {
		Anode aux = null;
		for (Anode auxSet : openSet) {
			if (auxSet.equals(searchedAnode)) {
				aux = auxSet;
				break;
			}
		}
		return aux;
	}
	
	private Anode getLowestFfromOpenset(){
		Anode current = openSet.get(0);
		for (Anode point : openSet) {
			if (point.getF() < current.getF()) {
				current = point;
			}
		}
		return current;
	}
	
	private void updateAnode(Anode nodeToUpdate, int newG, Anode current) {
		nodeToUpdate.setG(newG);
		nodeToUpdate.setH(heuristicDiagonalDistance(nodeToUpdate, end));
		nodeToUpdate.calculateF();
		nodeToUpdate.setAnterior(current);
		
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
	
	public void addAfinderListener(AfinderEvent afEnvent) {
		this.aFinderListeners.add(afEnvent);
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

	public boolean isFinderRunning() {
		return isFinderRunning;
	}
}
