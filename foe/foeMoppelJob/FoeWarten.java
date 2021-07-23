package foeMoppelJob;

import foeMoppel.FoeMoppel;

public class FoeWarten extends FoeMoppelJob {
	long zeit, bis;
	boolean separat;
	
	public FoeWarten(FoeMoppel moppel, int extrazeit, boolean separat) {
		super(moppel);
		this.zeit = extrazeit + moppel.getView().getMillisekunden();
		this.bis = System.currentTimeMillis() + zeit;
		this.separat = separat;
	}
	
	@Override
	public boolean doIt() {
		long millis = System.currentTimeMillis();
		if (separat) {
			separat = false;
			this.bis = this.zeit + millis;
		}
		
		if (millis - bis > 0) {
			completeSuccessful = true;
			return false;
		} else {
			return true;
		}
	}

}
