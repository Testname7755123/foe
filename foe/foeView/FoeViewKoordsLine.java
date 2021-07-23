package foeView;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class FoeViewKoordsLine extends FoeViewLine {
	private JButton buttonCatch;
	
	public FoeViewKoordsLine(FoeView view, String text, JPanel panelInto, GridBagConstraints ct) {
		super(view, text, panelInto, ct);
	}
	
	@Override
	protected void addComponents(JPanel panelInto, GridBagConstraints ct) {
		this.tf = new JTextField[2];
		ct.weightx = 0.2;
		FocusListener focusListener = new FocusListener() {
			public void focusLost(FocusEvent evt) {
				FoeViewKoordsLine.this.getKoords();				
			}
			
			public void focusGained(FocusEvent evt) {
				JTextField tf = (JTextField)(evt.getSource());
				tf.selectAll();				
			}
		};
		for (int i = 0; i < this.tf.length; i++) {
			this.tf[i] = new JTextField("0000");
			this.tf[i].addFocusListener(focusListener);
			this.tf[i].setBackground(new Color(255, 100, 200));
			ct.gridx++;
			panelInto.add(this.tf[i], ct);
		}
		
		ct.weightx = 0.05;
		this.buttonCatch = new JButton("X");
		this.buttonCatch.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent evt) {button_catch();}});
		ct.gridx++;
		panelInto.add(this.buttonCatch, ct);

		JButton buttonMoveTo = new JButton(">");
		buttonMoveTo.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent evt) {button_moveTo();}});
		ct.gridx++;
		panelInto.add(buttonMoveTo, ct);
	}
	
	int[] getKoords() {
		int[] result = new int[this.tf.length];
		for (int i = 0; i < result.length; i++) 
		try {
			result[i] = Integer.parseInt(this.tf[i].getText());
			if (result[i] <= 0) {
				throw new NumberFormatException("0 is reserved for invalid");
			}
			this.tf[i].setBackground(new Color(222, 244, 234));
		} catch (Exception err) {
			result[i] = 0;
			this.tf[i].setBackground(new Color(255, 204, 214));
		}
		return result;
	}
	
	void button_catch() {
		Point m = MouseInfo.getPointerInfo().getLocation();
		this.tf[0].setText(Integer.toString(m.x));
		this.tf[1].setText(Integer.toString(m.y));
		this.getKoords();
		
		if (this == view.lines[0]) {
			view.lines[1].grabFocus();
		} else if (this == view.lines[1]) {
			view.lines[2].grabFocus();
		} else if (this == view.lines[2]) {
			view.actionAndFeedback.grabFocus();
		}
	}
	
	void button_moveTo() {
		try {
			Robot robot = new Robot();
			int[] koords = this.getKoords();
			robot.mouseMove(koords[0], koords[1]);
		} catch (AWTException err) {
			err.printStackTrace();
		}
		
	}
	
	void grabFocus() {
		this.buttonCatch.grabFocus();
	}
	
	public void fromSaveString(String line) {
		super.fromSaveString(line);
		this.getKoords();
	}
}
