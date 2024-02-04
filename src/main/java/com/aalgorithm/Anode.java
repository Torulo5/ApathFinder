package com.aalgorithm;

import java.awt.Point;
import java.util.ArrayList;

public class Anode {
	
	private Point coordenadas = null;
	private int f = 0;
	private int g = 0;
	private int h = 0;
	private ArrayList<Point> relatedPoints = null;
	private Anode anterior = null;
	
	public Anode(Point punto) {
		this.coordenadas = new Point(punto.x,punto.y);
	}
	
	public Anode(int x, int y) {
		this.coordenadas = new Point(x,y);
	}
	
	private void generateRelatedNodes() {
		if(relatedPoints == null) {
			this.relatedPoints = new ArrayList<Point>();
		}
		//cruz
		relatedPoints.add(new Point(coordenadas.x + 1, coordenadas.y));
		relatedPoints.add(new Point(coordenadas.x - 1, coordenadas.y));
		relatedPoints.add(new Point(coordenadas.x, coordenadas.y + 1));
		relatedPoints.add(new Point(coordenadas.x, coordenadas.y - 1));
		//diagonales
		relatedPoints.add(new Point(coordenadas.x + 1, coordenadas.y + 1));
		relatedPoints.add(new Point(coordenadas.x - 1, coordenadas.y + 1));
		relatedPoints.add(new Point(coordenadas.x - 1, coordenadas.y - 1));
		relatedPoints.add(new Point(coordenadas.x + 1, coordenadas.y - 1));
	}
	
	public boolean isRelatedDiagonal(Point relatedNode) {
		boolean isRelatedDiagonal = false;
		if( (relatedNode.x ==  coordenadas.x + 1 && relatedNode.y == coordenadas.y + 1) ||
			(relatedNode.x ==  coordenadas.x - 1 && relatedNode.y == coordenadas.y + 1)	||
			(relatedNode.x ==  coordenadas.x - 1 && relatedNode.y == coordenadas.y - 1) ||
			(relatedNode.x ==  coordenadas.x + 1 && relatedNode.y == coordenadas.y - 1)
		) {
			isRelatedDiagonal = true;
		}
		return isRelatedDiagonal;
	}
	
	public ArrayList<Point> getRelatedNodes() {
		if(relatedPoints == null) {
			this.relatedPoints = new ArrayList<Point>();
			this.generateRelatedNodes();
		}
		return relatedPoints;
	}

	public int getF() {
		return f;
	}

	public int getG() {
		return g;
	}

	public int getH() {
		return h;
	}

	public void setG(int g) {
		this.g = g;
	}

	public void setH(int h) {
		this.h = h;
	}

	public void calculateF() {
		this.f = (g + h);
	}
	
	public Point getCoordenadas() {
		return coordenadas;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) { 
            return true; 
        } 
  
        if (!(obj instanceof Anode)) { 
            return false; 
        } 
          
        Anode o = (Anode) obj; 
		return this.coordenadas.equals(o.getCoordenadas());
	}

	public Anode getAnterior() {
		return anterior;
	}

	public void setAnterior(Anode anterior) {
		this.anterior = anterior;
	}
}
