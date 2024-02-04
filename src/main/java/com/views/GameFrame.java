package com.views;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import com.aalgorithm.InfoMap;
import com.pathFinder.App;
import com.views.gridcanvas.GridCanvas;
import com.views.gridcanvas.GridCanvasEvent;
import javax.swing.JCheckBox;
import java.awt.Point;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;


public class GameFrame extends JFrame implements GridCanvasEvent{

	private static final long serialVersionUID = 1L;

	private GridCanvas grindCanvas = null;
	private JCheckBox chckbxNewCheckBox = null;
	private JButton btnFind = null;
	private App finderApp = null;
	private JLabel lblAsd  = null;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private JRadioButton rdbtnNewRadioButton = null;
	
	public GameFrame(App finderApp_) {
		
		this.finderApp = finderApp_;
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		rdbtnNewRadioButton = new JRadioButton("Bloque");
		rdbtnNewRadioButton.setSelected(true);
		buttonGroup.add(rdbtnNewRadioButton);
		panel_1.add(rdbtnNewRadioButton);
		
		chckbxNewCheckBox = new JCheckBox("Mover");
		chckbxNewCheckBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(grindCanvas == null)
					return;
				if(chckbxNewCheckBox.isSelected()) {
					grindCanvas.changeToDragg();
				} else {
					grindCanvas.changeToSelect();
				}
			}
		});
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Tierra");
		buttonGroup.add(rdbtnNewRadioButton_1);
		panel_1.add(rdbtnNewRadioButton_1);
		chckbxNewCheckBox.setSelected(true);
		panel_1.add(chckbxNewCheckBox);
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		lblAsd = new JLabel(" ");
		panel.add(lblAsd);

		
		
		grindCanvas = new GridCanvas(20);
		grindCanvas.addGridCanvasListener(this);
		grindCanvas.setLayout(new BoxLayout(grindCanvas, BoxLayout.X_AXIS));
		grindCanvas.changeToDragg();
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(panel_1, BorderLayout.WEST);

		getContentPane().add(grindCanvas, BorderLayout.CENTER);
		getContentPane().add(panel, BorderLayout.SOUTH);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		chckbxNewCheckBox.setFocusable(false);
		rdbtnNewRadioButton.setFocusable(false);
		
		JButton btnResetGrid = new JButton("RESET GRID");
		btnResetGrid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grindCanvas.resetGrid();
				grindCanvas.repaint();
				finderApp.resetGrid();
			}
		});
		panel_1.add(btnResetGrid);
		
		JCheckBox chckbxCosteDiagonal = new JCheckBox("Coste adicional diagonales");
		chckbxCosteDiagonal.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(chckbxCosteDiagonal.isSelected()) {
					finderApp.setDiagonalCost(true);
				} else {
					finderApp.setDiagonalCost(false);
				}
			}
		});
		panel_1.add(chckbxCosteDiagonal);
		
		
		btnFind = new JButton("FIND");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finderApp.runFinder();
			}
		});
		panel_1.add(btnFind);

	}
	
	public void setTimeElapsedInSearch(double seconds) {
		this.lblAsd.setText(seconds + " Seconds");
	}
	
	public void clearAllPoint() {
		this.grindCanvas.clearAllPoints();
	}
	
	public void addPointToGrid(Point punto, Color color) {
		this.grindCanvas.addPoint(punto,color);
	}
	
	public void addStaticPointToGrid(Point punto, Color color) {
		this.grindCanvas.addStaticPoint(punto,color);
	}
	
	public void addPointsToGrid(ArrayList<Point> points, Color color) {
		this.grindCanvas.addPoints(points, color);
	}
	
	public void repaint() {
		this.grindCanvas.repaint();
	}
	
	public void newPointEvent(Point point, boolean isLeftMouse) {
		if(rdbtnNewRadioButton.isSelected()) {
			finderApp.setBlockedPoint(point);
			this.addStaticPointToGrid(point, Color.gray);	
		} else {
			this.addStaticPointToGrid(point, Color.ORANGE);	
			finderApp.setIncresedGPoint(point);
		}
		this.repaint();
	}
	
}