package hu.ibello.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"successCount", "failureCount", "errorCount", "pendingCount"})
public class ParentElement extends Element {

	private int successCount;
	private int failureCount;
	private int errorCount;
	private int pendingCount;
	
	public int getSuccessCount() {
		return successCount;
	}
	
	@XmlAttribute(name="success-count")
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	
	public int getPendingCount() {
		return pendingCount;
	}
	
	@XmlAttribute(name="pending-count")
	public void setPendingCount(int pendingCount) {
		this.pendingCount = pendingCount;
	}
	
	public int getFailureCount() {
		return failureCount;
	}
	
	@XmlAttribute(name="failure-count")
	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}
	
	public int getErrorCount() {
		return errorCount;
	}
	
	@XmlAttribute(name="error-count")
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
}
