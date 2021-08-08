package foeMoppel;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import foeMoppelJob.FoeJobGenerieren;
import foeMoppelJob.FoeMoppelJob;
import foeMoppelJob.FoeScannen;
import foeView.FoeCheckboxZiel;
import foeView.FoeMoppelSpieler;
import foeView.FoeView;

public class FoeMoppel {
	FoeView view;
	boolean isActive = false;
	int mx, my;
	int action = 0;
	FoeMoppelJob nextJob = null;
	FoeMoppelSpieler[] spieler;

	public FoeMoppel(FoeView view, FoeMoppelSpieler[] spieler) {
		Timer timer = new Timer();
		this.view = view;
		this.isActive = false;
		this.spieler = spieler;
		timer.schedule(new TimerTask() {public void run() {if (isActive) {timern();}}}, 100, 100);
	}

	private void timern() {
		if (nextJob == null) {
			this.isActive = false;
			return;
		}
		if (!nextJob.mouseCheck()) {
			nextJob = null;
			view.feedback("Abbruch nach Mausbewegung", Color.RED);
			this.isActive = false;
			return;
		}
		boolean continueJob = nextJob.doIt();
		if (!continueJob) {
			if (nextJob.isCompletedSuccessful()) {
				nextJob = nextJob.getNextJob();
			} else {
				nextJob = null;
			}
		}
	}

	public void starten() {
		this.view.feedback("Starten...", Color.WHITE);
		this.serverZuruecksetzen();
		weitermachen();
	}
	
	public void serverZuruecksetzen() {
		for (FoeCheckboxZiel ziel : this.getView().getCheckboxZiel()) {
			ziel.restart();
		}
	}

	public void weiter() {
		this.view.feedback("Weiter...", Color.WHITE);
		this.weitermachen();
	}
	private void weitermachen() {
		FoeMoppelJob scannen = new FoeScannen(this);
		FoeJobGenerieren nextGenerate = new FoeJobGenerieren(this);
		this.nextJob = scannen;
		scannen.setNextJob(nextGenerate); 
		this.isActive = true;
	}
	
	public FoeView getView() {return this.view;}
	public FoeMoppelSpieler[] getSpieler() {return this.spieler;}

	public void scanTest() {
		this.view.feedback("ScanTest...", Color.WHITE);
		FoeMoppelJob scannen = new FoeScannen(this);
		this.nextJob = scannen;
		this.isActive = true;
		
	}

	public void windowLostFocus(FoeCheckboxZiel checkboxManager) {
		if (this.isActive) {
			checkboxManager.colorWorking();
		} else {
			checkboxManager.colorProblem();
		}
		
	}

}
