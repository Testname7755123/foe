package foeView;

public class FoeBildschirmPosition {
	FoeView view;
	public FoeBildschirmPosition(FoeView view) {
		this.view = view;
	}
	
	public int[] spielerXY(int id) {
		final int rechts = id * 77 + 50;
		final int links = 480 - rechts;
		
		final int[] backXY = ((FoeViewKoordsLine)(view.lines[0])).getKoords();
		final int[] lastXY = ((FoeViewKoordsLine)(view.lines[1])).getKoords();
		
		final int x = (backXY[0] * links + lastXY[0] * rechts) / 480, y = (lastXY[1] * 3 - backXY[1]) / 2;
		
		return new int[] {x, y};

	}
	
	public int[] tabXY(int index) {
		final int rechts = index * 36;
		final int links = 100 - rechts;
		
		final int[] tabXY = ((FoeViewKoordsLine)(view.lines[2])).getKoords();
		final int[] lastXY = ((FoeViewKoordsLine)(view.lines[1])).getKoords();
		
		final int x = (tabXY[0] * links + lastXY[0] * rechts) / 100, y = tabXY[1];
		
		return new int[] {x, y};
		
	}
	
	public int[] taverneXY(int id) {
		final int rechts = id * 77 + 77;
		final int links = 480 - rechts;
		
		final int[] backXY = ((FoeViewKoordsLine)(view.lines[0])).getKoords();
		final int[] lastXY = ((FoeViewKoordsLine)(view.lines[1])).getKoords();
		
		final int x = (backXY[0] * links + lastXY[0] * rechts) / 480, y = lastXY[1];
		
		return new int[] {x, y};
	}
	
	public int[] nachGanzHinten() {
		final int[] lastXY = ((FoeViewKoordsLine)(view.lines[1])).getKoords();
		
		return lastXY;
	}
	
	public int[] weiterNachLinks() {
		final int[] backXY = ((FoeViewKoordsLine)(view.lines[0])).getKoords();
		
		return backXY;
	}
	
}
