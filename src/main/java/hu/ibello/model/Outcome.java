package hu.ibello.model;

public enum Outcome {

	SUCCESS,
	FAILURE,
	ERROR,
	PENDING;
	
	public boolean isFailed() {
		return this==ERROR || this==FAILURE;
	}
	
	public boolean isStrongerThan(Outcome outcome) {
		if (outcome==null) {
			return true;
		} else {
			switch (outcome) {
			case PENDING:
				return this!=PENDING;
			case SUCCESS:
				return this.isFailed();
			case FAILURE:
				return this==Outcome.ERROR;
			case ERROR:
				return false;
			default:
				return false;
			}
		}
	}
	
	public static Outcome getStronger(Outcome outcome1, Outcome outcome2) {
		if (outcome1 == null) {
			return outcome2;
		} else if (outcome1.isStrongerThan(outcome2)) {
			return outcome1;
		} else {
			return outcome2;
		}
	}
}
