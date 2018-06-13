package hu.ibello.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType(propOrder={"label", "url", "path"})
public class Screenshot {

	private String url;
	private String path;
	private String label;
	
	public Screenshot() {
	}
	
	public Screenshot(String url, String path, String label) {
		this.url = url;
		this.path = path;
		this.label = label;
	}
	
	public String getUrl() {
		return url;
	}
	
	@XmlAttribute
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getPath() {
		return path;
	}
	
	@XmlValue
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getLabel() {
		return label;
	}
	
	@XmlAttribute
	public void setLabel(String label) {
		this.label = label;
	}
}
