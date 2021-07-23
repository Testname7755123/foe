package foeMoppelJob;

import java.awt.Color;

import foeMoppel.FoeMoppel;
import foeView.FoeBildschirmPosition;
import foeView.FoeMoppelSpieler;

public class FoeJobGenerieren extends FoeMoppelJob {
	int nullrunden;
	
	public FoeJobGenerieren(FoeMoppel moppel) {
		super(moppel);
		this.nullrunden = 0;
	}
	
	@Override
	public boolean doIt() {
		FoeWarten warten = new FoeWarten(this.moppel, 50, true);
		warten.nextJob = this.nextJob;
		this.nextJob = warten;
		
		int count = this.weiterMoppelnHilfeUndTaverne();
		if (count > 0) {
			FoeScannen scannen = new FoeScannen(this.moppel);
			FoeJobGenerieren nextGenerate = new FoeJobGenerieren(this.moppel);
			
			nextGenerate.nextJob = warten.nextJob;
			scannen.nextJob = nextGenerate;
			warten.nextJob = scannen;	
			this.moppel.getView().feedback("Mopple " + count + " x ", new Color(200, 255, 230));
		} else if (nullrunden < 2) {
			FoeBildschirmPosition position = new FoeBildschirmPosition(this.moppel.getView());
			int[] backStep = position.weiterNachLinks();
			FoeMoppelKlick weiterKlicken = new FoeMoppelKlick(moppel, backStep[0], backStep[1]);
			FoeWarten warten2 = new FoeWarten(this.moppel, 700, false);
			FoeScannen scannen = new FoeScannen(this.moppel);
			FoeJobGenerieren nextGenerate = new FoeJobGenerieren(this.moppel);
			nextGenerate.nullrunden = this.nullrunden + 1;
			
			nextGenerate.nextJob = warten.nextJob;
			warten.nextJob = weiterKlicken;
			weiterKlicken.nextJob = warten2;
			warten2.nextJob = scannen;
			scannen.nextJob = nextGenerate;
			this.moppel.getView().feedback("Weiter nach links " + (nullrunden + 1) + "/2 ", new Color(200, 255, 255));
		} else {
			this.moppel.getView().feedback("Gruppe fertig. ", new Color(200, 240, 240));
			FoeNeueZielGruppe neuesZiel = new FoeNeueZielGruppe(this.moppel);
			warten.nextJob = neuesZiel;
		}
		
		completeSuccessful = true;
		return false;
	}
	
	private int weiterMoppelnHilfeUndTaverne() {
		int count = 0;
		FoeMoppelSpieler[] spieler = this.moppel.getSpieler();
		FoeBildschirmPosition position = new FoeBildschirmPosition(this.moppel.getView());
		for (int i = 0; i < spieler.length; i++) {
			if (spieler[i].isZuTav()) {
				int[] xy = position.taverneXY(i);
				FoeMoppelJob job = new FoeMoppelKlick(this.moppel, xy[0], xy[1]);
				this.prependJob(job, 300);
				count++;
			}
		}

		for (int i = 0; i < spieler.length; i++) {
			if (spieler[i].isZuMoppeln()) {
				int[] xy = position.spielerXY(i);
				FoeMoppelJob job = new FoeMoppelKlick(this.moppel, xy[0], xy[1]);
				this.prependJob(job, 100);
				count++;
			}
		}
		
		return count;
	}
	
	private void prependJob(FoeMoppelJob job, int warteZeitExtra) {
		FoeWarten warten = new FoeWarten(this.moppel, warteZeitExtra, false);
		
		warten.nextJob = this.nextJob;
		job.nextJob = warten;
		this.nextJob = job;
	}
}
