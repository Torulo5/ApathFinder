package com.views.gridcanvas;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.SwingUtilities;

public class MouseAdapterGrid extends MouseAdapter {

	private Point puntoInicial = null;
	private int initialDragX = 0;
	private int initialDragY = 0;
	private boolean mouseInCanvas = false;
	private int mouseSensitivity = 20;

	private GridCanvas gcanvas = null;

	private boolean setNewPoints = true;
	
	public MouseAdapterGrid(GridCanvas gcanvas) {
		super();
		this.gcanvas = gcanvas;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!mouseInCanvas)
			return;

		int x = e.getX();
		int y = e.getY();
		int xActual = gcanvas.getxActual();
		int yActual = gcanvas.getyActual();
		int rowHt = gcanvas.getRowHt();
		int rowWid = gcanvas.getRowWid();
		if (setNewPoints) {
			Point newPoint = new Point((x / rowHt) - xActual, (y / rowWid) - yActual);
			for (GridCanvasEvent listener : this.gcanvas.getGridCanvasListeners()) {
				listener.newPointEvent(newPoint, SwingUtilities.isLeftMouseButton(e));
			}
		} else {
			puntoInicial = new Point(x, y);
			initialDragX = xActual;
			initialDragY = yActual;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!mouseInCanvas)
			return;

		int x = e.getX();
		int y = e.getY();
		int xActual = gcanvas.getxActual();
		int yActual = gcanvas.getyActual();
		int rowHt = gcanvas.getRowHt();
		int rowWid = gcanvas.getRowWid();
		if (setNewPoints) {
			Point newPoint = new Point((x / rowHt) - xActual, (y / rowWid) - yActual);
			for (GridCanvasEvent listener : this.gcanvas.getGridCanvasListeners()) {
				listener.newPointEvent(newPoint, SwingUtilities.isLeftMouseButton(e));
			}
		} else {
			Point puntoFinal = new Point(x, y);
			gcanvas.setxActual(initialDragX + (puntoFinal.x - puntoInicial.x) / mouseSensitivity);
			gcanvas.setyActual(initialDragY + (puntoFinal.y - puntoInicial.y) / mouseSensitivity);
			gcanvas.repaint();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseInCanvas = false;
		gcanvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseInCanvas = true;
		if (setNewPoints) {
			gcanvas.setCursor(new Cursor(Cursor.HAND_CURSOR));
		} else {
			gcanvas.setCursor(new Cursor(Cursor.MOVE_CURSOR));
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int rowHt = gcanvas.getRowHt();
		int rowWid = gcanvas.getRowWid();
		if (e.getWheelRotation() < 0) {
			gcanvas.setRowHt(rowHt+1);
			gcanvas.setRowWid(rowWid+1);
			mouseSensitivity++;
			gcanvas.repaint();
		} else {
			if (!(rowHt == 5 || rowWid == 5)) {
				gcanvas.setRowHt(rowHt-1);
				gcanvas.setRowWid(rowWid-1);
				mouseSensitivity--;
				gcanvas.repaint();
			}
		}
	}

	public void setSetNewPoints(boolean setNewPoints) {
		this.setNewPoints = setNewPoints;
	}

}
