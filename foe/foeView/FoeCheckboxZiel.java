package foeView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class FoeCheckboxZiel {
	// [OFFEN] [WAITING] [WORKING] [FERTIG] [AKTIV] [INAKTIV]
	public static final int STATUS_INAKTIV = 1;
	public static final int STATUS_FERTIG = 2+4;
	public static final int STATUS_WORKING = 2+8+32;
	public static final int STATUS_OFFEN = 2+16+32;
	int status;
	
	private final boolean isZiel;
	private JCheckBox checkbox;
	String text;
	
	public FoeCheckboxZiel(FoeView view, String text, JPanel panelInto, boolean isZiel) {
		this.isZiel = isZiel;
		this.text = text;
		this.status = STATUS_OFFEN;
		this.checkbox = new JCheckBox(text, true);
		this.checkbox.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent evt) {clicked();}});
		panelInto.add(this.checkbox);
		this.clicked();
	}
	
	public void reset() {
		this.checkbox.setSelected(true);
		status = STATUS_OFFEN;
		
		this.clicked();
	}
	
	public void restart() {
		if (this.checkbox.isSelected()) {
			this.status = STATUS_INAKTIV;
		} else if (this.isZiel) {
			this.status = STATUS_OFFEN;
		} else {
			this.status = 0;
		}
		this.clicked();
	}

	
	public boolean grabWorkingIfPossible() {
		if ((this.status & STATUS_WORKING) == STATUS_WORKING) {
			this.status = STATUS_FERTIG;
			this.clicked();
			return false;
		}
		
		if ((this.status & STATUS_OFFEN) == STATUS_OFFEN && this.isZiel) {
			this.status = STATUS_WORKING;
			this.clicked();
			return true;
		}
		return false;
	}
	
	public void setFertig() {
		this.status = STATUS_FERTIG;
		this.clicked();
	}

	
	private void clicked() {
		if (isZiel) {
			this.colorUpdateZiele();
		} else {
			this.colorUpdateTaverne();
		}
	}
	
	private void colorUpdateTaverne() {
		if (this.checkbox.isSelected()) {
			this.checkbox.setBackground(new Color(240, 240, 250));
		} else {
			this.checkbox.setBackground(new Color(190, 190, 190));
		}
	}
	
	private void colorUpdateZiele() {
		if (this.checkbox.isSelected()) {
			if ((status & 2) == 0) {
				status = STATUS_OFFEN;
			}
			if ((status & 4) != 0) { //fertig
				this.checkbox.setBackground(new Color(220, 250, 240));
			} else if ((status & 8) != 0) { //working
				this.checkbox.setBackground(new Color(220, 220, 250));
			} else if ((status & 16) != 0) { //offen
				this.checkbox.setBackground(new Color(250, 220, 240));
			} else { //fehler
				this.checkbox.setBackground(Color.RED);
			}
		} else {
			//inaktiv
			this.status = STATUS_INAKTIV;
			this.checkbox.setBackground(new Color(190, 190, 190));
		}
		
		
		
	}

	public static FoeCheckboxZiel[] createLines(FoeView foeView, Container panelSouth) {
		String[] texte = {"Nachbar", "Gilde", "Freunde", "Taverne"};
		FoeCheckboxZiel[] result = new FoeCheckboxZiel[texte.length];
		JPanel panelCheckboxen = new JPanel(new GridLayout(0, 1));
		
		for (int i = 0; i < result.length; i++) {
			result[i] = new FoeCheckboxZiel(foeView, texte[i], panelCheckboxen, i <= 2);
		}
		
		panelSouth.add(panelCheckboxen, BorderLayout.NORTH);
		return result;
	}
	
	public String toSaveString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.text);
		sb.append(" \tvC1 \t");
		sb.append(this.checkbox.isSelected());
		sb.append(" \t");
		sb.append(this.status);
		sb.append(" \t");
		
		return sb.toString();
	}
	
	public void fromSaveString(String line) {
		String[] data = line.split("\t");
		if (data[1].trim().equals("vC1")) {
			this.checkbox.setSelected(Boolean.parseBoolean(data[2].trim()));
			this.status = Integer.parseInt(data[3].trim());
			this.clicked();
		} else {
			throw new RuntimeException("Unknown Version " + data[1]);
		}
	}}
