package foeMoppelJob;

import java.awt.Robot;

import foeMoppel.FoeMoppel;
import foeView.FoeBildanalyse;

public class FoeScannen extends FoeMoppelJob {
	int step = 0;
	public FoeScannen(FoeMoppel moppel) {
		super(moppel);
	}
	
	@Override
	public boolean doIt() {
		step++;
		if (step < 3) {
			this.moveMouseDown();
			return true;
		} else {
			this.analysiere();
			this.completeSuccessful = true;
			return false;
		}
	}
	
	private void moveMouseDown() {
		this.mouseCheck();
		this.mxx += (int)(3 * Math.random());
		this.myy += 3 + (int)(3 * Math.random());
		try {
			Robot robot = new Robot();
			robot.mouseMove(this.mxx, this.myy);
		} catch(Exception err) {
			err.printStackTrace();
		}
	}
	
	private void analysiere() {
		FoeBildanalyse analyse = new FoeBildanalyse(this.moppel.getView());
		analyse.execute();
	}

}
