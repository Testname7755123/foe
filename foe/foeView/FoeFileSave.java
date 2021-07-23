package foeView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FoeFileSave {
	private final JTextField tf;
	FoeView view;
	
	public FoeFileSave(FoeView foeView, Container panelSouth, String fileName) {
		this.view = foeView;
		this.tf = new JTextField((fileName == null) ? "C:\\temp\\foemoppel.tsv" : fileName);
		JPanel panelSave = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelSave.add(tf);
		JButton buttonLaden = new JButton("Load");
		buttonLaden.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent evt) {
			laden_click();
		}});
		JButton buttonSpeichern = new JButton("Save");
		buttonSpeichern.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent evt) {
			speichern_click();
		}});
		
		panelSave.add(buttonLaden);
		panelSave.add(buttonSpeichern);
		
		panelSouth.add(panelSave, BorderLayout.CENTER);
	}

	private File checkFile(boolean mustExist) {
		String fileName = this.tf.getText();
		if (fileName.isBlank()) {
			this.tf.setBackground(Color.RED);
			this.view.feedback("Dateiname darf nicht leer sein", Color.RED);
			return null;
		}
		File file = new File(fileName);
		if (file.exists() || !mustExist) {
			this.tf.setBackground(new Color(250, 255, 254));
			return file;
		}
		this.tf.setBackground(Color.RED);
		this.view.feedback("Datei nicht gefunden", Color.RED);
		return null;
	}
	
	public void laden_click() {
		File file = checkFile(true);
		if (file == null) {return;}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			@SuppressWarnings("unused")
			String versionLine = reader.readLine();
			String[] timestampLine = reader.readLine().split("\t");
			if (timestampLine.length < 2) {
				this.view.feedback("Fehler beim Timestamp", Color.RED);
			}
			
			FoeViewLine[] textLines = this.view.lines;
			for (int i = 0; i < textLines.length; i++) {
				textLines[i].fromSaveString(reader.readLine());
			}
			
			FoeCheckboxZiel[] checkboxen = this.view.getCheckboxZiel();
			for (int i = 0; i < checkboxen.length; i++) {
				checkboxen[i].fromSaveString(reader.readLine());
			}
			reader.close();
			
			this.view.feedback("Geladen vom " + timestampLine[1], Color.GREEN);
 		} catch (IOException err) {
			Color colorFehler = new Color(255, 0, 100);
			this.tf.setBackground(colorFehler);
			this.view.feedback("IO-Fehler: " + err.getMessage(), colorFehler);
			err.printStackTrace();
		}
	}

	private void speichern_click() {
		File file = checkFile(false);
		if (file == null) {return;}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("FoeMoppel Config File Version vom 23.07.2021");
			writer.newLine();
			writer.write("Timestamp \t");
			writer.write((new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss")).format((new GregorianCalendar() ).getTime() ));
			writer.write(" \t");
			writer.newLine();
			FoeViewLine[] textLines = this.view.lines;
			for (int i = 0; i < textLines.length; i++) {
				writer.write(textLines[i].toSaveString());
				writer.newLine();
			}
			FoeCheckboxZiel[] checkboxen = this.view.getCheckboxZiel();
			for (int i = 0; i < checkboxen.length; i++) {
				writer.write(checkboxen[i].toSaveString());
				writer.newLine();
			}
			writer.write("Ende");
			writer.newLine();
			writer.close();
		} catch(IOException err) {
			Color colorFehler = new Color(255, 0, 100);
			this.tf.setBackground(colorFehler);
			this.view.feedback("IO-Fehler: " + err.getMessage(), colorFehler);
			err.printStackTrace();
		}
	}
	
}
