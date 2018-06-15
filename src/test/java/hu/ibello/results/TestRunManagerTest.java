package hu.ibello.results;

import static org.assertj.core.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;

import hu.ibello.model.ActionElement;
import hu.ibello.model.BrowserKind;
import hu.ibello.model.Counters;
import hu.ibello.model.ExceptionInfo;
import hu.ibello.model.ExpectationElement;
import hu.ibello.model.LogFile;
import hu.ibello.model.Outcome;
import hu.ibello.model.Screenshot;
import hu.ibello.model.SpecElement;
import hu.ibello.model.StepElement;
import hu.ibello.model.TestRun;
import hu.ibello.model.WindowSize;
import hu.ibello.results.TestRunManager;

public class TestRunManagerTest {
	
	private TestRunManager manager;
	
	@Before
	public void init() throws JAXBException {
		manager = new TestRunManager();
	}

	@Test
	public void toXmlString_throws_exception_if_input_is_null() throws Exception {
		assertThatThrownBy(() -> manager.toXmlString(null))
			.isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public void toXmlString_creates_xml_string() throws Exception {
		TestRun input = new TestRun();
		input.setId("id");
		input.setName("Test run");
		input.setBaseDirectory("/home/xy");
		input.setBrowser(BrowserKind.CHROME);
		input.setDefaultTimeout(5);
		input.setHeadless(true);
		input.setStartTime(new Date());
		input.setEndTime(new Date());
		input.getTag().add("tag1");
		input.getTag().add("tag2");
		
		input.setWindowSize(new WindowSize());
		input.getWindowSize().setWidth(800);
		input.getWindowSize().setHeight(600);
		
		input.setCounters(new Counters());
		input.setSuccessCount(1);
		input.setPendingCount(2);
		input.setFailureCount(3);
		input.setErrorCount(4);
		input.getCounters().setSpecifications(10);
		input.getCounters().setTests(20);
		input.getCounters().setSteps(30);
		input.getCounters().setExpectations(40);
		input.getCounters().setActions(50);
		
		LogFile file1 = new LogFile();
		file1.setId("ibello-0.log");
		file1.setPath("logs/ibello-0.log");
		LogFile file2 = new LogFile();
		file2.setId("selenium-0.log");
		file2.setPath("logs/selenium-0.log");
		input.getLogFile().add(file1);
		input.getLogFile().add(file2);
		
		SpecElement spec = new SpecElement();
		input.getSpec().add(spec);
		spec.setName("Spec 1");
		spec.setOutcome(Outcome.ERROR);
		spec.setDurationMs(1100);
		
		hu.ibello.model.TestElement test = new hu.ibello.model.TestElement();
		spec.getTest().add(test);
		test.setName("Test 1");
		test.setOutcome(Outcome.FAILURE);
		test.setDurationMs(1200);
		
		StepElement step = new StepElement();
		test.getStep().add(step);
		step.setName("Step 1");
		step.setOutcome(Outcome.PENDING);
		step.setDurationMs(1300);
		
		ExpectationElement operation = new ExpectationElement();
		step.getChildren().add(operation);
		operation.setName("Expectation 1");
		operation.setOutcome(Outcome.SUCCESS);
		operation.setDurationMs(1400);
		
		ActionElement action = new ActionElement();
		step.getChildren().add(action);
		action.setName("Action 1");
		action.setOutcome(Outcome.SUCCESS);
		action.setDurationMs(1500);
		
		StepElement step2 = new StepElement();
		step.getChildren().add(step2);
		step2.setName("Step 2");
		step2.setOutcome(Outcome.FAILURE);
		step2.setDurationMs(100);
		ExceptionInfo ex = new ExceptionInfo();
		ex.setTitle("exception");
		ex.setStackTrace(getExceptionStackTrace(new Exception()));
		step2.getException().add(ex);
		Screenshot screenshot = new Screenshot();
		screenshot.setLabel("test");
		screenshot.setUrl("http://localhost");
		screenshot.setPath("x/y/z.jpg");
		step2.getScreenshot().add(screenshot);
		
		StepElement step3 = new StepElement();
		step3.setName("AAAAAAAAAAAAAAAAAAAAAA");
		step2.getChildren().add(step3);
		
		String result = manager.toXmlString(input);
		assertThat(result).isNotNull();
		System.out.println(result);
		
		TestRun obj = manager.fromString(result);
		assertThat(obj).isNotNull();
		assertThat(obj.getSpec()).hasSize(input.getSpec().size());
		
	}
	
	private String getExceptionStackTrace(Throwable ex) {
		StringWriter str = new StringWriter();
		ex.printStackTrace(new PrintWriter(str));
		return str.toString().trim();
	}

}
