package hu.ibello.model;

public enum BrowserKind {

	CHROME,
	FIREFOX,
	EDGE,
	OPERA;
	
	public static BrowserKind fromString(String name) {
		return BrowserKind.valueOf(name.toUpperCase());
	}
	
	public String getFullName() {
		switch (this) {
		case CHROME:
			return "Google Chrome";
		case FIREFOX:
			return "Mozilla Firefox";
		case EDGE:
			return "Microsoft Edge";
		case OPERA:
			return "Opera";
		}
		return null;
	}
}
