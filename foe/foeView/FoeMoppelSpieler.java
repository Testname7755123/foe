package foeView;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class FoeMoppelSpieler {
	int id0;
	JLabel label;
	JLabel labelIcon;
	boolean zuMoppeln, zuTav;
	
	public FoeMoppelSpieler(int id0, Container container) {
		this.label = new JLabel((id0 + 1) + ": xxx");
		this.labelIcon = new JLabel();
		this.id0 = id0;
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(labelIcon);
		panel.add(label);
		container.add(panel);
	}

	public void setMoppel(boolean zuMoppeln) {
		this.label.setText((this.id0 + 1) + ": " + (zuMoppeln ? "Y" : "N"));
		this.zuMoppeln = zuMoppeln;
		
	}

	public void setTaverne(boolean zuTaverne) {
		this.zuTav = zuTaverne;
		if (zuTaverne) {
			this.label.setText(this.label.getText() + "T");
		}
	}
	
	public boolean isZuMoppeln() {return this.zuMoppeln;}
	public boolean isZuTav() {return this.zuTav;}

}
