package hu.ibello.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"width", "height"})
public class WindowSize {

	private int width;
	private int height;
	
	public int getWidth() {
		return width;
	}
	
	@XmlAttribute
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	@XmlAttribute
	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
