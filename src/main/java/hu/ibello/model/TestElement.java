package hu.ibello.model;

import java.util.ArrayList;
import java.util.List;

public class TestElement extends ParentElement {

	private List<StepElement> step;
	
	public List<StepElement> getStep() {
		if (step == null) {
			step = new ArrayList<>();
		}
		return step;
	}
	
	public void setStep(List<StepElement> step) {
		this.step = step;
	}
	
}
