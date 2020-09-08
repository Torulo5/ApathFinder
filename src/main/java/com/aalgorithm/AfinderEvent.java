package com.aalgorithm;

import java.util.ArrayList;

public interface AfinderEvent {
	public void aNodeEvalauted(ArrayList<Anode> openSet, ArrayList<Anode> closeSet, ArrayList<Anode> finalPath);
	public void pathFinded(ArrayList<Anode> openSet, ArrayList<Anode> closeSet, ArrayList<Anode> finalPath, double seconds);
}
