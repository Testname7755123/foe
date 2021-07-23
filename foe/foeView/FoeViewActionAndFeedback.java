package foeView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FoeViewActionAndFeedback {
	public final static Color MAUSBEWEGUNG = new Color(255, 220, 220); 
	
	final FoeMoppelSpieler[] spieler = new FoeMoppelSpieler[5];
	final JLabel labelFeedback = new JLabel("warte auf Start");
	final JButton buttonStart = new JButton("start");
	final JButton buttonWeiter = new JButton("weiter");
	final JButton buttonScannen = new JButton("scan-test");
	
	public FoeViewActionAndFeedback(FoeView view, Container container) {
		JPanel panelInto = new JPanel(new BorderLayout());
		buttonStart.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent evt) {
			view.moppel.starten();
		}});
		buttonStart.setToolTipText("Startet den Moppelbot neu. Alle ausgewählten Gruppen werden gemoppelt. ");
		panelInto.add(buttonStart, BorderLayout.CENTER);
		
		buttonWeiter.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent evt) {
			view.moppel.weiter();
		}});
		buttonWeiter.setToolTipText("Setzt den Moppelbot fort, nachdem er unterbrochen wurde. Bereits erledigte Gruppen werden nicht resettet");
		panelInto.add(buttonWeiter, BorderLayout.WEST);
		
		buttonScannen.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent evt) {
			view.moppel.scanTest();
		}});
		buttonScannen.setToolTipText("Liest den aktuellen Bildschirm aus und zeigt das Ergebnis in der Zeile darüber an. ");
		panelInto.add(buttonScannen, BorderLayout.EAST);
		
		JPanel panelSpieler = new JPanel(new GridLayout());
		for (int i = 0; i < spieler.length; i++) {
			spieler[i] = new FoeMoppelSpieler(i, panelSpieler);
		}
		
		panelInto.add(panelSpieler, BorderLayout.NORTH);
		panelInto.add(labelFeedback, BorderLayout.SOUTH);
		labelFeedback.setOpaque(true);
		container.add(panelInto, BorderLayout.SOUTH);
	}

	public void feedback(String text, Color color) {
		if (color == null) {
			color = new Color(200, 200, 200); 
		}
		labelFeedback.setText(text);
		labelFeedback.setBackground(color);
		
	}
	
	public void grabFocus() {
		this.buttonStart.grabFocus();
	}
}
