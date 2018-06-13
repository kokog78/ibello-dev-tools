package hu.ibello.results;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import hu.ibello.model.Element;
import hu.ibello.model.SpecElement;
import hu.ibello.model.StepElement;
import hu.ibello.model.TestElement;
import hu.ibello.model.TestRun;

public class TestRunManager {
	
	private JAXBContext jaxbContext;
	private Marshaller jaxbMarshaller;
	private Unmarshaller jaxbUnmarshaller;
	
	public TestRunManager() throws JAXBException {
		jaxbContext = JAXBContext.newInstance(TestRun.class);
		
	}
	
	public byte[] toXmlBytes(TestRun testRun) throws JAXBException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		jaxbMarshaller().marshal(testRun, out);
		return out.toByteArray();
	}

	public String toXmlString(TestRun testRun) throws JAXBException {
		byte[] bytes = toXmlBytes(testRun);
		return new String(bytes, StandardCharsets.UTF_8);
	}
	
	public TestRun fromInputStream(InputStream stream) throws JAXBException {
		TestRun result = (TestRun) jaxbUnmarshaller().unmarshal(stream);
		for (SpecElement spec : result.getSpec()) {
			for (TestElement test : spec.getTest()) {
				for (StepElement step : test.getStep()) {
					updateStep(step);
				}
			}
		}
		return result;
	}
	
	public TestRun fromBytes(byte[] bytes) throws JAXBException {
		ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
		return fromInputStream(stream);
	}
	
	public TestRun fromString(String xml) throws JAXBException {
		byte[] bytes = xml.getBytes(StandardCharsets.UTF_8);
		return fromBytes(bytes);
	}
	
	private void updateStep(StepElement step) {
		List<Element> nodes = new ArrayList<>();
		for (Object obj : step.getChildren()) {
			if (obj instanceof Element) {
				nodes.add((Element)obj);
				if (obj instanceof StepElement) {
					updateStep((StepElement)obj);
				}
			}
		}
		step.setChildren(nodes);
	}
	
	private Marshaller jaxbMarshaller() throws JAXBException {
		if (jaxbMarshaller == null) {
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		}
		return jaxbMarshaller;
	}
	
	private Unmarshaller jaxbUnmarshaller() throws JAXBException {
		if (jaxbUnmarshaller == null) {
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		}
		return jaxbUnmarshaller;
	}
	
}
