package hu.ibello.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class ExceptionInfo {

	private String title;
	private String stackTrace;
	
	public String getTitle() {
		return title;
	}
	
	@XmlAttribute
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getStackTrace() {
		return stackTrace;
	}
	
	@XmlValue
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
}
