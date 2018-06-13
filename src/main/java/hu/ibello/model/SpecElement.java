package hu.ibello.model;

import java.util.ArrayList;
import java.util.List;

public class SpecElement extends ParentElement {

	private List<TestElement> test;
	
	public List<TestElement> getTest() {
		if (test == null) {
			test = new ArrayList<>();
		}
		return test;
	}
	
	public void setTest(List<TestElement> test) {
		this.test = test;
	}
}
