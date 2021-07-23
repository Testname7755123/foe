package foeMoppelJob;

import java.awt.MouseInfo;
import java.awt.Point;

import foeMoppel.FoeMoppel;

public abstract class FoeMoppelJob {
	
	int mxx = -1, myy = -1;
	protected boolean completeSuccessful = false;
	FoeMoppelJob nextJob;
	FoeMoppel moppel;

	public FoeMoppelJob(FoeMoppel moppel) {
		this.moppel = moppel;
	}
	
	abstract public boolean doIt();
	public boolean isCompletedSuccessful() {return this.completeSuccessful;}
	public FoeMoppelJob getNextJob() {return this.nextJob;}
	public FoeMoppelJob setNextJob(FoeMoppelJob job) {this.nextJob = job; return job;}
	
	public boolean mouseCheck() {
		boolean alwaysTrue = (this.mxx < 0) || (this.myy < 0);
		
		Point mousePoint = MouseInfo.getPointerInfo().getLocation();
		int dmx = mousePoint.x - this.mxx, dmy = mousePoint.y - this.myy;
		this.mxx = mousePoint.x;
		this.myy = mousePoint.y;
		
		return alwaysTrue || (dmx < 10  && dmx > -10 && dmy < 10 && dmy > -10);
	}

}
