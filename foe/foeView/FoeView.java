package foeView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;

import foeMoppel.FoeMoppel;

public class FoeView {
	JFrame frame;
	FoeViewLine[] lines;
	FoeCheckboxZiel[] checkboxZiel;
	FoeViewActionAndFeedback actionAndFeedback;
	FoeMoppel moppel;
	FoeFileSave fileSave;
	
	public FoeView(final String fileName) {
		this.frame = new JFrame("FoeMoppel");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLayout(new BorderLayout());
		this.lines = FoeViewLine.createLines(this);
		JPanel panelSouth = new JPanel(new BorderLayout());
		this.frame.add(panelSouth, BorderLayout.SOUTH);
		this.checkboxZiel = FoeCheckboxZiel.createLines(this, panelSouth);
		this.actionAndFeedback = new FoeViewActionAndFeedback(this, panelSouth);
		this.fileSave = new FoeFileSave(this, panelSouth, fileName);
		
		this.frame.pack();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.frame.setLocation(dimension.width - this.frame.getWidth() - 10, dimension.height - this.frame.getHeight() - 40);
		this.frame.setVisible(true);
		this.frame.setAlwaysOnTop(true);
		try {Thread.sleep(1);} catch (InterruptedException err) {}
		this.lines[0].grabFocus();
		
		KeyListener keyListener = new KeyAdapter() {public void keyReleased(KeyEvent evt) {keyClicked(evt);}};
		Stack<Component[]> allComponents = new Stack<Component[]>();
		allComponents.push(new Component[] {this.frame});
		while(!allComponents.isEmpty()) {
			for (Component component : allComponents.pop()) {
				component.addKeyListener(keyListener);
				if (component instanceof Container) {
					allComponents.push(((Container)component).getComponents());
				}
			}
		}
		this.moppel = new FoeMoppel(this, this.actionAndFeedback.spieler);
	}
	
	private void keyClicked(KeyEvent evt) {
		switch (evt.getKeyCode()) {
		case KeyEvent.VK_F1: ((FoeViewKoordsLine)(this.lines[0])).button_catch(); return;
		case KeyEvent.VK_F2: ((FoeViewKoordsLine)(this.lines[1])).button_catch(); return;
		case KeyEvent.VK_F3: ((FoeViewKoordsLine)(this.lines[2])).button_catch(); return;
		case KeyEvent.VK_F5: this.moppel.starten(); return;
		case KeyEvent.VK_F8: this.fileSave.laden_click(); return;
		}
	}
	
	public void feedback(String text, Color color) {
		this.actionAndFeedback.feedback(text, color);
	}
	
	public FoeCheckboxZiel[] getCheckboxZiel() {
		return this.checkboxZiel;
	}
	
	public int getMillisekunden() {
		return this.lines[3].getZahl(0);
	}

	public boolean serverMinus1() {
		int anzahl = 0;
		boolean result;
		try {
			anzahl = this.lines[FoeViewLine.ZEILE_SERVER].getZahl(0);
		} catch(NumberFormatException err) {
			anzahl = 0;
		}
		if (anzahl > 1) {
			anzahl--;
			result = true;
		} else {
			result = false;
		}
		this.lines[FoeViewLine.ZEILE_SERVER].tf[0].setText(Integer.toString(anzahl));
		return result;
	}
}