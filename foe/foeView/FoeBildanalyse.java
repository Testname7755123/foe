package foeView;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class FoeBildanalyse {
	FoeView view;
	FoeBildschirmPosition position;
	
	static final int[][] wunsch = {{239, 219, 166}, {148, 80, 31}};
    static final int[][] taverne = new int[][] {{ 80, 45, 19, 80, 45, 19, 79, 45, 19, 80, 46, 20, 80, 46, 20, 79, 46, 20, 79, 45, 20, 78, 45, 20, 78, 45, 20, 78, 46, 21, 77, 45, 19, 77, 45, 20, 76, 44, 19, 76, 44, 19, 66, 41, 19, 45, 35, 24, 47, 37, 25, 63, 39, 19, 71, 41, 17, 70, 40, 17}, { 88, 53, 25, 86, 51, 23, 87, 50, 24, 86, 51, 23, 87, 52, 24, 87, 52, 24, 87, 51, 24, 86, 51, 24, 85, 52, 24, 86, 52, 25, 86, 51, 26, 84, 50, 23, 84, 49, 23, 72, 45, 22, 45, 35, 24, 117, 102, 72, 120, 106, 74, 47, 37, 25, 67, 41, 21, 69, 40, 17}, { 48, 35, 22, 43, 33, 22, 43, 33, 22, 43, 33, 22, 43, 33, 22, 43, 33, 22, 43, 33, 22, 43, 34, 22, 61, 41, 23, 85, 51, 25, 77, 47, 24, 84, 50, 23, 71, 45, 22, 45, 35, 24, 117, 102, 72, 148, 132, 93, 148, 132, 93, 120, 106, 74, 47, 37, 25, 66, 38, 17}, { 175, 163, 134, 142, 122, 82, 122, 101, 60, 125, 104, 63, 125, 104, 63, 125, 104, 63, 125, 104, 63, 124, 103, 63, 48, 37, 24, 72, 46, 24, 66, 55, 38, 43, 33, 22, 45, 35, 24, 117, 102, 72, 148, 132, 93, 148, 132, 93, 148, 132, 93, 148, 132, 93, 96, 83, 58, 56, 36, 19}, { 182, 170, 141, 144, 125, 84, 123, 102, 60, 125, 104, 63, 125, 104, 63, 125, 104, 63, 125, 104, 63, 125, 104, 63, 49, 38, 25, 56, 38, 22, 113, 99, 69, 108, 94, 66, 117, 103, 72, 148, 132, 93, 148, 132, 93, 148, 132, 93, 148, 132, 93, 117, 103, 72, 45, 35, 24, 65, 38, 17}, { 180, 169, 140, 147, 127, 86, 121, 100, 61, 120, 99, 60, 125, 104, 63, 125, 104, 63, 125, 104, 63, 124, 103, 63, 49, 39, 25, 50, 36, 22, 127, 113, 79, 148, 132, 93, 148, 132, 93, 148, 132, 93, 148, 132, 93, 148, 132, 93, 117, 103, 72, 45, 35, 24, 67, 42, 21, 66, 39, 17}, { 181, 169, 140, 147, 127, 86, 85, 70, 43, 66, 52, 33, 68, 55, 35, 65, 52, 33, 65, 52, 33, 121, 101, 61, 51, 40, 26, 46, 34, 22, 141, 126, 89, 148, 132, 93, 148, 132, 93, 148, 132, 93, 148, 132, 93, 117, 103, 72, 45, 35, 24, 68, 42, 21, 76, 45, 21, 66, 39, 17}, { 182, 170, 141, 149, 128, 88, 67, 54, 34, 71, 43, 22, 69, 43, 22, 70, 45, 24, 43, 33, 22, 119, 99, 60, 51, 40, 27, 50, 40, 27, 148, 132, 93, 148, 132, 93, 148, 132, 93, 148, 132, 93, 137, 122, 86, 46, 36, 24, 68, 43, 22, 77, 45, 22, 76, 45, 22, 65, 39, 16}, { 182, 170, 141, 149, 131, 91, 69, 56, 35, 69, 44, 23, 81, 47, 23, 79, 48, 22, 44, 34, 22, 120, 99, 60, 50, 39, 26, 64, 53, 36, 148, 132, 93, 148, 132, 93, 148, 132, 93, 148, 132, 93, 148, 132, 93, 108, 94, 66, 44, 34, 23, 76, 45, 21, 75, 45, 21, 65, 39, 17}, { 181, 169, 140, 147, 130, 89, 67, 53, 34, 70, 43, 22, 77, 46, 21, 77, 47, 22, 44, 34, 22, 113, 94, 57, 51, 40, 26, 73, 61, 42, 124, 109, 77, 110, 96, 67, 96, 83, 58, 82, 70, 48, 68, 57, 39, 54, 43, 30, 43, 33, 22, 76, 46, 21, 75, 46, 22, 66, 38, 16}, { 180, 168, 139, 148, 132, 93, 76, 62, 39, 48, 37, 24, 49, 39, 25, 49, 38, 25, 50, 39, 25, 110, 91, 55, 70, 57, 36, 44, 34, 22, 45, 34, 23, 52, 36, 23, 58, 39, 22, 64, 41, 21, 68, 43, 21, 73, 44, 21, 76, 46, 22, 76, 45, 21, 75, 44, 21, 65, 38, 16}, { 181, 169, 140, 147, 134, 106, 129, 109, 74, 123, 102, 62, 125, 104, 63, 125, 104, 63, 125, 104, 63, 125, 104, 63, 125, 104, 63, 124, 103, 63, 101, 83, 51, 69, 56, 35, 53, 41, 27, 66, 41, 22, 76, 46, 21, 77, 46, 22, 76, 45, 21, 75, 45, 21, 74, 44, 21, 64, 38, 17}, { 180, 168, 139, 190, 177, 147, 184, 170, 139, 149, 132, 93, 121, 101, 61, 120, 99, 60, 122, 101, 61, 120, 100, 62, 119, 99, 59, 118, 97, 59, 118, 98, 59, 118, 97, 59, 96, 80, 49, 45, 34, 23, 76, 45, 22, 75, 44, 21, 75, 45, 22, 75, 45, 22, 74, 44, 21, 64, 38, 16}, { 179, 167, 139, 140, 130, 107, 148, 137, 114, 185, 174, 146, 165, 149, 112, 96, 76, 45, 92, 74, 44, 94, 75, 45, 94, 75, 45, 94, 75, 45, 91, 73, 44, 90, 72, 44, 107, 88, 54, 55, 43, 28, 70, 43, 21, 75, 45, 21, 76, 46, 22, 75, 45, 22, 74, 44, 21, 64, 38, 17}, { 178, 166, 138, 110, 99, 81, 60, 50, 37, 139, 128, 108, 176, 162, 125, 88, 71, 42, 47, 37, 24, 47, 36, 24, 46, 35, 23, 44, 34, 23, 45, 33, 22, 74, 60, 38, 120, 100, 60, 53, 42, 27, 71, 43, 21, 75, 44, 20, 75, 45, 21, 75, 45, 22, 74, 44, 21, 64, 38, 17}, { 178, 166, 137, 101, 91, 73, 59, 39, 21, 118, 108, 90, 174, 159, 122, 84, 68, 40, 58, 38, 21, 73, 45, 21, 75, 44, 21, 74, 44, 22, 62, 41, 21, 74, 60, 38, 117, 97, 59, 53, 41, 27, 71, 45, 22, 75, 44, 20, 75, 45, 21, 74, 45, 22, 73, 43, 21, 64, 38, 16}, { 178, 166, 137, 99, 88, 71, 60, 39, 21, 117, 107, 88, 173, 158, 120, 83, 67, 40, 57, 39, 22, 75, 44, 21, 75, 44, 21, 74, 43, 20, 63, 40, 21, 73, 59, 37, 114, 94, 57, 53, 41, 27, 72, 45, 22, 75, 45, 21, 75, 45, 22, 74, 45, 22, 73, 43, 20, 64, 37, 16}, { 184, 172, 143, 99, 89, 72, 60, 39, 21, 116, 106, 87, 173, 158, 122, 83, 67, 40, 57, 38, 21, 75, 44, 21, 75, 45, 22, 75, 44, 21, 63, 40, 22, 72, 58, 37, 112, 93, 56, 53, 41, 27, 71, 43, 22, 75, 45, 21, 76, 46, 22, 75, 45, 22, 73, 43, 21, 64, 38, 16}};

	public FoeBildanalyse(FoeView view) {
		this.view = view;
		this.position = new FoeBildschirmPosition(view);
	}
	
	
	public void execute() {
		FoeMoppelSpieler[] spieler = this.view.actionAndFeedback.spieler;
		for (int i = 0; i < spieler.length; i++) {
			int[] xy = this.position.spielerXY(i);
			view.actionAndFeedback.spieler[i].setMoppel(this.schaueAufMoppel(xy, view.actionAndFeedback.spieler[i].labelIcon));
			xy = this.position.taverneXY(i);
			view.actionAndFeedback.spieler[i].setTaverne(this.schaueAufTaverne(xy));
		}
	}
	
	public boolean schaueAufTaverne(int[] xy) {
		try {
			BufferedImage image = (new Robot()).createScreenCapture(new Rectangle(xy[0] - 15, xy[1] - 15, 30, 30));
			int[] data = image.getRaster().getPixels(0, 0, 29, 29, (int[])null);
			return this.diffTav(data) < 50000;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return false;
	}
	
	public boolean schaueAufMoppel(int[] xy, JLabel labelIcon) {
		try {
			BufferedImage image = (new Robot()).createScreenCapture(new Rectangle(xy[0] - 15, xy[1] - 8, 30, 16));
			labelIcon.setIcon(new ImageIcon(image));
			int[] data = image.getRaster().getPixels(0, 0, 29, 15, (int[])null);
			int[] countGood = {0, 0};
			for (int i = 0; i < data.length; i+=3) {
				for (int w = 0; w < wunsch.length; w++) {
					int dd = diff(data, i, w);
					if (dd < 50) {
						countGood[w] += 100;
					} else if (dd < 150) {
						countGood[w] += 10;
					} else if (dd < 500) {
						countGood[w]++;
					}
				}
			}
			labelIcon.setToolTipText("Schrift: " + countGood[0] + " / 2000  und Hintergrund: " + countGood[1] + " / 450 ");
			return countGood[0] >= 2000 && countGood[1] >= 450;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return false;
	}
	
	private int diff(int[] rgb, int offset, int wunschIndex) {
		int result = 0, dd;
		for (int di = 0; di <= 2; di++) {
			dd = FoeBildanalyse.wunsch[wunschIndex][di] - rgb[offset + di];
			result += dd * dd;
		}
		return result;
	}
	
	private long diffTav(int[] rgb) {
		long result = Integer.MAX_VALUE, result2, dd;
		int dxBis = 29 - taverne[0].length / 3;
		// rgb = 29 x 29   x3 Farben
		// tav = 20 x 18   x3 Farben
		for (int dy = 29 - taverne.length - 1; dy >= 0; dy--) {
			for (int dx = 0; dx < dxBis; dx++) {
				result2 = 0;
				for (int y = 0; y < taverne.length; y++) {
					for (int x = 0; x < taverne[0].length; x += 3) {
						for (int di = 0; di <= 2; di++) {
							dd = FoeBildanalyse.taverne[y][x + di] - rgb[(y + dy) * 29 * 3 + dx * 3 + x + di]; 
							result2 += dd * dd;
						}
					}
				}
				if (result2 < result) {
					result = result2;
				}
			}
		}
		return result;
	}
}
