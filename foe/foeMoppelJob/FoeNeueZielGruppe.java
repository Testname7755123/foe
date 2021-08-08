package foeMoppelJob;

import foeMoppel.FoeMoppel;
import foeView.FoeBildschirmPosition;
import foeView.FoeCheckboxZiel;

public class FoeNeueZielGruppe extends FoeMoppelJob {
	public FoeNeueZielGruppe(FoeMoppel moppel) {
		super(moppel);
	}
	
	@Override
	public boolean doIt() {
		FoeCheckboxZiel[] ziele  = moppel.getView().getCheckboxZiel();
		for (int i = ziele.length - 1; i >= 0; i--) {
			if (ziele[i].grabWorkingIfPossible()) {
				this.starteZiel(i);
				this.completeSuccessful = true;
				return false;
			}
		}
		
		if (this.sollNeuerServerGestartetWerden()) {
			this.starteNeuenServer();
		} else if (this.sollProgrammBeendetWerden()) {
			System.exit(0);
		}
		completeSuccessful = true;
		return false;
	}

	private void starteZiel(int index) {
		FoeBildschirmPosition position = new FoeBildschirmPosition(this.moppel.getView());
		int[] posXYTab = position.tabXY(index);
		int[] posXYHinten = position.nachGanzHinten();
		
		FoeMoppelKlick tabKlick = new FoeMoppelKlick(this.moppel, posXYTab[0], posXYTab[1]);
		FoeMoppelKlick hintenKlick = new FoeMoppelKlick(this.moppel, posXYHinten[0], posXYHinten[1]);
		FoeWarten warten = new FoeWarten(this.moppel, 250, true);
		FoeScannen scannen = new FoeScannen(this.moppel);
		FoeJobGenerieren nextGenerate = new FoeJobGenerieren(this.moppel);
		
		nextGenerate.nextJob = this.nextJob;
		
		this.setNextJob(tabKlick).setNextJob(hintenKlick).setNextJob(warten).setNextJob(scannen).setNextJob(nextGenerate);
	}
	
	private boolean sollNeuerServerGestartetWerden() {
		return this.moppel.getView().serverMinus1();
	}
	
	private boolean sollProgrammBeendetWerden() {
		return this.moppel.getView().getQuitWhenDone();
	}
	
	private void starteNeuenServer() {
		FoeFensterSchliessen fensterSchliessen = new FoeFensterSchliessen(this.moppel);
		FoeWarten warten = new FoeWarten(this.moppel, 1, false);
		FoeScannen scannen = new FoeScannen(this.moppel);
		FoeJobGenerieren nextGenerate = new FoeJobGenerieren(this.moppel);
		
		this.setNextJob(fensterSchliessen).setNextJob(warten).setNextJob(scannen).setNextJob(nextGenerate);
		
		this.moppel.serverZuruecksetzen();
	}

}
