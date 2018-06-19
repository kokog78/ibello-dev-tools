package hu.ibello.results;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.junit.Test;

import hu.ibello.model.BrowserKind;
import hu.ibello.model.Counters;
import hu.ibello.model.Element;
import hu.ibello.model.ExpectationElement;
import hu.ibello.model.Outcome;
import hu.ibello.model.ParentElement;
import hu.ibello.model.SpecElement;
import hu.ibello.model.StepElement;
import hu.ibello.model.TestElement;
import hu.ibello.model.TestRun;

public class TestRunCsvConverterTest {
	
	private TestRunCsvConverter converter = new TestRunCsvConverter();

	@Test
	public void toCsv_throws_exception_for_null() throws Exception {
		assertThatThrownBy(() -> converter.toCsv(null))
			.isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public void toCsv_returns_single_line() throws Exception {
		TestRun input = testRun();
		String result = converter.toCsv(input);
		assertThat(result.split("\n")).containsExactly(
				"ID,NAME,VERSION,LEVEL,INDEX,START_TIME,END_TIME,DURATION_MS,OUTCOME,BROWSER,HEADLESS,TAGS,SUCCESS_COUNT,FAILURE_COUNT,ERROR_COUNT,PENDING_COUNT,SPEC_COUNT,TEST_COUNT,STEP_COUNT,EXPECTATION_COUNT,ACTION_COUNT",
				"id,test,,0,0,2000-01-01T00:00:00.000Z,2000-01-02T00:00:00.000Z,30000,SUCCESS,CHROME,true,\"one,two\",1,2,3,4,5,6,7,8,9"
			);
	}
	
	@Test
	public void toCsv_uses_double_quotes() throws Exception {
		TestRun input = testRun();
		input.setName("a \"name\"");
		String result = converter.toCsv(input);
		assertThat(result.split("\n")).contains(
				"id,\"a \"\"name\"\"\",,0,0,2000-01-01T00:00:00.000Z,2000-01-02T00:00:00.000Z,30000,SUCCESS,CHROME,true,\"one,two\",1,2,3,4,5,6,7,8,9"
			);
	}
	
	@Test
	public void toCsv_appends_specifications() throws Exception {
		TestRun input = testRun();
		SpecElement spec1 = fillParent(new SpecElement());
		spec1.setVersion("1.0");
		input.getSpec().add(spec1);
		SpecElement spec2 = fillParent(new SpecElement());
		spec2.setVersion("2.0");
		input.getSpec().add(spec2);
		String result = converter.toCsv(input);
		assertThat(result.split("\n")).contains(
				"id,SpecElement,1.0,1,0,,,20000,ERROR,,,,10,11,12,13,,,,,",
				"id,SpecElement,2.0,1,1,,,20000,ERROR,,,,10,11,12,13,,,,,"
			);
	}
	
	@Test
	public void toCsv_appends_tests() throws Exception {
		TestRun input = testRun();
		SpecElement spec1 = fillParent(new SpecElement());
		input.getSpec().add(spec1);
		TestElement test1 = fillParent(new TestElement());
		spec1.getTest().add(test1);
		TestElement test2 = fillParent(new TestElement());
		spec1.getTest().add(test2);
		String result = converter.toCsv(input);
		assertThat(result.split("\n")).contains(
				"id,TestElement,,2,0,,,20000,ERROR,,,,10,11,12,13,,,,,",
				"id,TestElement,,2,1,,,20000,ERROR,,,,10,11,12,13,,,,,"
			);
	}
	
	@Test
	public void toCsv_appends_steps() throws Exception {
		TestRun input = testRun();
		SpecElement spec1 = fillParent(new SpecElement());
		input.getSpec().add(spec1);
		TestElement test1 = fillParent(new TestElement());
		spec1.getTest().add(test1);
		StepElement step1 = fillParent(new StepElement());
		test1.getStep().add(step1);
		StepElement step2 = fillParent(new StepElement());
		test1.getStep().add(step2);
		String result = converter.toCsv(input);
		assertThat(result.split("\n")).contains(
				"id,StepElement,,3,0,,,20000,ERROR,,,,10,11,12,13,,,,,",
				"id,StepElement,,3,1,,,20000,ERROR,,,,10,11,12,13,,,,,"
			);
	}
	
	@Test
	public void toCsv_appends_child_steps() throws Exception {
		TestRun input = testRun();
		SpecElement spec1 = fillParent(new SpecElement());
		input.getSpec().add(spec1);
		TestElement test1 = fillParent(new TestElement());
		spec1.getTest().add(test1);
		StepElement step1 = fillParent(new StepElement());
		test1.getStep().add(step1);
		StepElement step2 = fillParent(new StepElement());
		step1.getChildren().add(step2);
		String result = converter.toCsv(input);
		assertThat(result.split("\n")).contains(
				"id,StepElement,,3,0,,,20000,ERROR,,,,10,11,12,13,,,,,",
				"id,StepElement,,4,0,,,20000,ERROR,,,,10,11,12,13,,,,,"
			);
	}
	
	@Test
	public void toCsv_appends_expectation() throws Exception {
		TestRun input = testRun();
		SpecElement spec1 = fillParent(new SpecElement());
		input.getSpec().add(spec1);
		TestElement test1 = fillParent(new TestElement());
		spec1.getTest().add(test1);
		StepElement step1 = fillParent(new StepElement());
		test1.getStep().add(step1);
		ExpectationElement expect1 = fill(new ExpectationElement());
		step1.getChildren().add(expect1);
		ExpectationElement expect2 = fill(new ExpectationElement());
		step1.getChildren().add(expect2);
		String result = converter.toCsv(input);
		assertThat(result.split("\n")).contains(
				",ExpectationElement,,4,0,,,20000,ERROR,,,,,,,,,,,,",
				",ExpectationElement,,4,1,,,20000,ERROR,,,,,,,,,,,,"
			);
	}
	
	private Date date(int day) {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.set(2000, 0, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	private List<String> tags(String ... tags) {
		return Arrays.stream(tags).collect(Collectors.toList());
	}
	
	private TestRun testRun() {
		TestRun result = new TestRun();
		result.setId("id");
		result.setName("test");
		result.setStartTime(date(1));
		result.setEndTime(date(2));
		result.setDurationMs(30000);
		result.setBrowser(BrowserKind.CHROME);
		result.setHeadless(true);
		result.setOutcome(Outcome.SUCCESS);
		result.setSuccessCount(1);
		result.setFailureCount(2);
		result.setErrorCount(3);
		result.setPendingCount(4);
		result.setCounters(new Counters());
		result.getCounters().setSpecifications(5);
		result.getCounters().setTests(6);
		result.getCounters().setSteps(7);
		result.getCounters().setExpectations(8);
		result.getCounters().setActions(9);
		result.setTag(tags("one", "two"));
		return result;
	}
	
	private <T extends ParentElement> T fillParent(T parent) {
		parent.setId("id");
		parent.setSuccessCount(10);
		parent.setFailureCount(11);
		parent.setErrorCount(12);
		parent.setPendingCount(13);
		fill(parent);
		return parent;
	}
	
	private <T extends Element> T fill(T element) {
		element.setName(element.getClass().getSimpleName());
		element.setDurationMs(20000);
		element.setOutcome(Outcome.ERROR);
		return element;
	}
	
}
