package foeMain;

import foeView.FoeView;

public class FoeMoppelStart {

	public static void main(String[] args) {
		new FoeView(searchFileParam(args));
	}
	
	private static String searchFileParam(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null && args[i].equalsIgnoreCase("FILE") && i + 1 < args.length) {
				return args[i + 1];
			}
		}
		return null;
	}

}
