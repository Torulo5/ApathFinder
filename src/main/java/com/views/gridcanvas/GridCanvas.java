package com.views.gridcanvas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

public class GridCanvas extends JPanel{

	private static final long serialVersionUID = 1L;
	private final boolean DEBUG = false;

	//tama�o ventana y numero de columas
	private int width = 0, height = 0;
	private double rows = 0;
	private double cols = 0;

	// tama�o de cada casila
	private int rowHt = 0;
	private int rowWid = 0;
	private int stroke = 0;

	// flags para controlar el comportamiento del grid
	private boolean paintLines = true;
	
	// variables para guardar valor de la esquina equivalente a (0,0)
	private int xActual = 0;
	private int yActual = 0;

	private HashMap<Color, ArrayList<Point>> paintData = new HashMap<Color, ArrayList<Point>>();
	
	private HashMap<Color, ArrayList<Point>> staticsPoints = new HashMap<Color, ArrayList<Point>>();
	
	private MouseAdapterGrid maG = null;
	
	private ArrayList<GridCanvasEvent> gridCanvasListeners = null;
	
	public GridCanvas(int ladoCuadrado) {
		this.gridCanvasListeners = new ArrayList<GridCanvasEvent>();	
		this.rowHt = this.rowWid = ladoCuadrado;
		this.stroke = 1;
	        	
		maG = new MouseAdapterGrid(this);
		addMouseListener(maG);
		addMouseMotionListener(maG);
		addMouseWheelListener(maG);
	}
	
	public synchronized void  clearAllPoints() {
		this.paintData.clear();
	}
	
	public synchronized void  resetGrid() {
		this.paintData.clear();
		this.staticsPoints.clear();
	}
	
	public void changeToDragg() {
		maG.setSetNewPoints(false);
	}
	
	public void changeToSelect() {
		maG.setSetNewPoints(true);
	}
	
	public void removePoint(Point point) {
		ArrayList<Point> aux = null;
		for (Map.Entry<Color, ArrayList<Point>> entry : paintData.entrySet()) {
			if(entry.getValue().contains(point)) {
				aux = entry.getValue();
				break;
			}
		}
		if(aux != null)
			aux.remove(point);
	}
	
	public void removePointStatic(Point point) {
		ArrayList<Point> aux = null;
		for (Map.Entry<Color, ArrayList<Point>> entry : staticsPoints.entrySet()) {
			if(entry.getValue().contains(point)) {
				aux = entry.getValue();
				break;
			}
		}
		if(aux != null)
			aux.remove(point);
	}
	
	public synchronized void  addStaticPoint(Point point, Color color) {
		
		this.removePointStatic(point);
		
		ArrayList<Point> puntos = staticsPoints.get(color);
		if( puntos == null) {
			ArrayList<Point> auxPuntos = new ArrayList<Point>();
			auxPuntos.add(point);
			staticsPoints.put(color, auxPuntos);
		} else {
			puntos.add(point);
		}
		
	}
	
	public synchronized void  addPoint(Point point, Color color) {
		
		this.removePoint(point);
		
		ArrayList<Point> puntos = paintData.get(color);
		if( puntos == null) {
			ArrayList<Point> auxPuntos = new ArrayList<Point>();
			auxPuntos.add(point);
			paintData.put(color, auxPuntos);
		} else {
			puntos.add(point);
		}
		
	}
	
	public synchronized void  addPoints(ArrayList<Point> points, Color color) {
		for (Point point : points) {
			addPoint(point,color);
		}
	}
	
	
	@Override
	protected synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);

		this.width = this.getSize().width;
		this.height = this.getSize().height;
		this.rows = (double) this.height / this.rowHt;
		this.cols = (double) this.width / this.rowWid;

		if(paintLines)
			paintLines(g);
		if(paintData != null) {
			for (Map.Entry<Color, ArrayList<Point>> entry : paintData.entrySet()) {
		        paintRectangles(g,entry.getValue(),entry.getKey());
			}
		}
		
		if(staticsPoints != null) {
			for (Map.Entry<Color, ArrayList<Point>> entry : staticsPoints.entrySet()) {
				paintOvals(g,entry.getValue(),entry.getKey());
			}
		}
	}

	private synchronized void paintLines(Graphics g) {
		int i;
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setStroke(new BasicStroke(this.stroke));

		for (i = 0; i < this.rows; i++)
			g2d.drawLine(0, i * this.rowHt, this.width, i * this.rowHt);

		for (i = 0; i < this.cols; i++)
			g2d.drawLine(i * this.rowWid, 0, i * this.rowWid, this.height);
	}

	private synchronized void paintRectangles(Graphics g, ArrayList<Point> pointsAlive, Color color) {

		int strokeSpacer = 1;
		if (this.stroke != 1)
			strokeSpacer = Math.round((float) this.stroke / 2);

		for (Point point : pointsAlive) {
			if (DEBUG) {
				System.out.println("----------------------PAINT RECTANGLE------------------------");
				System.out.println("InitialX: " + point.x + " InitialY: " + point.y);
				System.out.println("FinalX  : " + point.x * this.rowHt + " FinalY  : " + point.y * this.rowWid);
				System.out.println("STROKE: " + this.stroke + " strokeSpacer: " + strokeSpacer);
				System.out.println("-------------------------------------------------------------");
			}
			g.setColor(color);
			g.fillRect((xActual + point.x) * this.rowHt + strokeSpacer, (yActual + point.y) * this.rowWid + strokeSpacer,
					this.rowHt - this.stroke, this.rowWid - this.stroke);
		}
	}

	private synchronized void paintOvals(Graphics g, ArrayList<Point> pointsAlive, Color color) {

		for (Point point : pointsAlive) {
			if (DEBUG) {
				System.out.println("----------------------PAINT RECTANGLE------------------------");
				System.out.println("InitialX: " + point.x + " InitialY: " + point.y);
				System.out.println("FinalX  : " + point.x * this.rowHt + " FinalY  : " + point.y * this.rowWid);
				System.out.println("-------------------------------------------------------------");
			}
			g.setColor(color);
			g.fillOval((xActual + point.x) * this.rowHt + Math.round((float) this.rowHt / 3),
					   (yActual + point.y) * this.rowWid + Math.round((float) this.rowWid / 3), 
					   Math.round((float) this.rowHt / 4), Math.round((float) this.rowHt / 4));

		}
	}

	public void addGridCanvasListener(GridCanvasEvent gcEvent) {
		this.gridCanvasListeners.add(gcEvent);
	}
	
	public ArrayList<GridCanvasEvent> getGridCanvasListeners() {
		return gridCanvasListeners;
	}

	public int getxActual() {
		return xActual;
	}

	public int getyActual() {
		return yActual;
	}

	public void setxActual(int xActual) {
		this.xActual = xActual;
	}

	public void setyActual(int yActual) {
		this.yActual = yActual;
	}

	public int getRowHt() {
		return rowHt;
	}

	public int getRowWid() {
		return rowWid;
	}

	public void setRowHt(int rowHt) {
		this.rowHt = rowHt;
	}

	public void setRowWid(int rowWid) {
		this.rowWid = rowWid;
	}

}