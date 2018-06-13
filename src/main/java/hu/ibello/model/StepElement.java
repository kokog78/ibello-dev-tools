package hu.ibello.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="step")
public class StepElement extends ParentElement {

	private List<Element> children;
	
	public List<Element> getChildren() {
		if (children == null) {
			children = new ArrayList<>();
		}
		return children;
	}
	
	@XmlElementRefs({
		@XmlElementRef(name="step", type=StepElement.class),
		@XmlElementRef(name="action", type=ActionElement.class),
		@XmlElementRef(name="expectation", type=ExpectationElement.class)
	})
	public void setChildren(List<Element> children) {
		this.children = children;
	}
	
}
