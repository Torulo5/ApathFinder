package com.aalgorithm;

import java.awt.Point;
import java.util.ArrayList;

public class Anode {
	
	private Point coordenadas = null;
	private int f = 0;
	private int g = 0;
	private int h = 0;
	private ArrayList<Point> relatedPoints = new ArrayList<Point>();
	private Anode anterior = null;
	
	public Anode(Point punto) {
		this.coordenadas = new Point(punto.x,punto.y);
		this.generateRelatedNodes();
	}
	
	public Anode(int x, int y) {
		this.coordenadas = new Point(x,y);
		this.generateRelatedNodes();
	}
	
	private void generateRelatedNodes() {
		//cruz
		relatedPoints.add(new Point(coordenadas.x + 1, coordenadas.y));
		relatedPoints.add(new Point(coordenadas.x - 1, coordenadas.y));
		relatedPoints.add(new Point(coordenadas.x, coordenadas.y + 1));
		relatedPoints.add(new Point(coordenadas.x, coordenadas.y - 1));
		//diagonales
//		relatedPoints.add(new Point(coordenadas.x + 1, coordenadas.y + 1));
//		relatedPoints.add(new Point(coordenadas.x - 1, coordenadas.y + 1));
//		relatedPoints.add(new Point(coordenadas.x - 1, coordenadas.y + 1));
//		relatedPoints.add(new Point(coordenadas.x + 1, coordenadas.y - 1));
	}
	
	public ArrayList<Point> getRelatedNodes() {
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

	public void setF(int f) {
		this.f = f;
	}
	public Point getCoordenadas() {
		return coordenadas;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(obj instanceof Anode)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        Anode o = (Anode) obj; 
		// TODO Auto-generated method stub
		return this.coordenadas.equals(o.getCoordenadas());
	}

	public Anode getAnterior() {
		return anterior;
	}

	public void setAnterior(Anode anterior) {
		this.anterior = anterior;
	}
}
