package foeMoppelJob;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import foeMoppel.FoeMoppel;

public class FoeFensterSchliessen extends FoeMoppelJob {
	int step = 0;
	public FoeFensterSchliessen(FoeMoppel moppel) {
		super(moppel);
	}
	
	@Override
	public boolean doIt() {
		step++;
		try {
			Robot robot = new Robot();
			switch(step) {
			case 2: robot.keyPress(KeyEvent.VK_CONTROL); return true;
			case 3: robot.keyPress(KeyEvent.VK_F4); return true;
			case 4: robot.keyRelease(KeyEvent.VK_F4); return true;
			case 5: robot.keyRelease(KeyEvent.VK_CONTROL); return true;
			case 6: this.completeSuccessful = true; return false;
			default: return true;
			}
		} catch (AWTException err) {
			err.printStackTrace();
			return false;
		}
	}
	
	

}
