package hu.ibello.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"name", "outcome", "durationMs", "exception", "screenshot"})
public class Element {

	private String name;
	private Outcome outcome;
	private long durationMs;
	private List<ExceptionInfo> exception;
	private List<Screenshot> screenshot;
	
	public String getName() {
		return name;
	}
	
	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}
	
	public Outcome getOutcome() {
		return outcome;
	}
	
	@XmlAttribute
	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
	}
	
	public long getDurationMs() {
		return durationMs;
	}
	
	@XmlAttribute(name="duration-ms")
	public void setDurationMs(long durationMs) {
		this.durationMs = durationMs;
	}
	
	public List<ExceptionInfo> getException() {
		if (exception == null) {
			exception = new ArrayList<>();
		}
		return exception;
	}
	
	public void setException(List<ExceptionInfo> exception) {
		this.exception = exception;
	}
	
	public List<Screenshot> getScreenshot() {
		if (screenshot == null) {
			screenshot = new ArrayList<>();
		}
		return screenshot;
	}
	
	public void setScreenshot(List<Screenshot> screenshot) {
		this.screenshot = screenshot;
	}
	
}
