package foeMoppelJob;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import foeMoppel.FoeMoppel;

public class FoeMoppelKlick extends FoeMoppelJob {
	int mx, my, step = 0;
	
	public FoeMoppelKlick(FoeMoppel moppel, int mx, int my) {
		super(moppel);
		this.mx = mx; 
		this.my = my;
	}

	@Override
	public boolean doIt() {
		step++;
		switch(step) {
		case 1:
		case 3:
		case 5:
			moveMouse(); 
			return true; 
		case 2:
		case 4:
			mx += (int)(3.5 * (Math.random() - Math.random()));
			my += (int)(2.5 * (Math.random() - Math.random()));
			return true;
		case 6:
			mouseClick(true);
			return true;
		case 7: 
			mouseClick(false);
			return true;
		case 9:
			this.completeSuccessful = true;
			return false;
		}
		return true;
	}

	private void moveMouse() {
		try {
			Robot robot = new Robot();
			robot.mouseMove(mx, my);
			this.mxx = this.mx;
			this.myy = this.my;
		} catch (AWTException err) {
			err.printStackTrace();
		}
	}
	
	private void mouseClick(boolean down) {
		try {
			Robot robot = new Robot();
			if (down) {
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			} else {
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			}
		} catch (AWTException err) {
			err.printStackTrace();
		}
	}

}
