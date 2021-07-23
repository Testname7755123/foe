package foeView;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FoeViewLine {
	public static final int ZEILE_MILLISEKUDNEN = 3;
	public static final int ZEILE_SERVER = 4;
	FoeView view;
	JTextField[] tf;
	String text;
	
	public FoeViewLine(FoeView view, String text, JPanel panelInto, GridBagConstraints ct) {
		JLabel label = new JLabel(text);
		this.view = view;
		this.text = text;
		ct.gridx = 0;
		ct.gridwidth = 1;
		ct.gridy++;
		ct.weightx = 0.5;
		panelInto.add(label, ct);
		this.addComponents(panelInto, ct);
	}
	
	protected void addComponents(JPanel panelInto, GridBagConstraints ct) {
		this.tf = new JTextField[1];
		this.tf[0] = new JTextField(7);
		ct.weightx = 0.5;
		ct.gridx++;
		ct.gridwidth = GridBagConstraints.REMAINDER;
		panelInto.add(this.tf[0], ct);
	}
	
	static FoeViewLine[] createLines(FoeView view) {
		FoeViewLine[] result = new FoeViewLine[5];
		JPanel panelInto = new JPanel(new GridBagLayout());
		GridBagConstraints ct = new GridBagConstraints();
		ct.fill = GridBagConstraints.BOTH;
		
		result[0] = new FoeViewKoordsLine(view, "Button back [<<]", panelInto, ct);
		result[1] = new FoeViewKoordsLine(view, "Button last [>|]", panelInto, ct);
		result[2] = new FoeViewKoordsLine(view, "Button Nachbar", panelInto, ct);
		result[ZEILE_MILLISEKUDNEN] = new FoeViewLine(view, "Millisekunden", panelInto, ct);
		result[ZEILE_MILLISEKUDNEN].tf[0].setText("1000");
		result[ZEILE_SERVER] = new FoeViewLine(view, "Anzahl Server", panelInto, ct);
		result[ZEILE_SERVER].tf[0].setText("1");
		
		view.frame.add(panelInto, BorderLayout.CENTER);
		return result;
	}
	
	void grabFocus() {
		this.tf[0].grabFocus();
	}

	public int getZahl(int i) {
		return Integer.parseInt(this.tf[0].getText());
	}

	public String toSaveString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.text);
		sb.append(" \tvT1 \t");
		
		for (int i = 0; i < this.tf.length; i++) {
			sb.append(this.tf[i].getText());
			sb.append(" \t");
		}
		return sb.toString();
	}
	
	public void fromSaveString(String line) {
		String[] data = line.split("\t");
		if (data[1].trim().equals("vT1")) {
			for (int i = 0; i < tf.length && i < data.length - 2; i++) {
				tf[i].setText(data[i + 2].trim());
			}
		} else {
			throw new RuntimeException("Unknown Version " + data[1]);
		}
	}
}
