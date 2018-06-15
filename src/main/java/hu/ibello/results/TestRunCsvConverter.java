package hu.ibello.results;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import hu.ibello.model.Element;
import hu.ibello.model.ParentElement;
import hu.ibello.model.SpecElement;
import hu.ibello.model.StepElement;
import hu.ibello.model.TestElement;
import hu.ibello.model.TestRun;

public class TestRunCsvConverter {
	
	private final static String[] COLUMNS = new String[] {
			"ID",
			"NAME",
			"LEVEL",
			"INDEX",
			"START_TIME",
			"END_TIME",
			"DURATION_MS",
			"OUTCOME",
			"BROWSER",
			"HEADLESS",
			"TAGS",
			"SUCCESS_COUNT",
			"FAILURE_COUNT",
			"ERROR_COUNT",
			"PENDING_COUNT",
			"SPEC_COUNT",
			"TEST_COUNT",
			"STEP_COUNT",
			"EXPECTATION_COUNT",
			"ACTION_COUNT"
	};
	
	private StringBuilder builder = new StringBuilder();
	private boolean newLine = false;
	private String separator = ",";

	public String toCsv(TestRun source) {
		if (source == null)
			throw new IllegalArgumentException();
		builder.delete(0, builder.length());
		newLine = true;
		
		for (String column : COLUMNS) {
			append(column);
		}
		newLine();
		
		append();
		append(source.getName());
		append(0);
		append(0);
		append(source.getStartTime());
		append(source.getEndTime());
		append(source.getDurationMs());
		append(source.getOutcome());
		append(source.getBrowser());
		append(source.isHeadless());
		append(source.getTag());
		append(source.getSuccessCount());
		append(source.getFailureCount());
		append(source.getErrorCount());
		append(source.getPendingCount());
		append(source.getCounters().getSpecifications());
		append(source.getCounters().getTests());
		append(source.getCounters().getSteps());
		append(source.getCounters().getExpectations());
		append(source.getCounters().getActions());
		newLine();
		
		int specIndex = 0;
		for (SpecElement spec : source.getSpec()) {
			appendParent(spec, 1, specIndex++);
			newLine();
			int testIndex = 0;
			for (TestElement test : spec.getTest()) {
				appendParent(test, 2, testIndex++);
				newLine();
				int stepIndex = 0;
				for (StepElement step : test.getStep()) {
					appendStep(step, 3, stepIndex++);
				}
			}
		}
		
		return builder.toString();
	}
	
	private void appendParent(ParentElement source, int level, int index) {
		append(source.getId());
		append(source.getName());
		append(level);
		append(index);
		append();
		append();
		append(source.getDurationMs());
		append(source.getOutcome());
		append();
		append();
		append();
		append(source.getSuccessCount());
		append(source.getFailureCount());
		append(source.getErrorCount());
		append(source.getPendingCount());
		append();
		append();
		append();
		append();
		append();
	}
	
	private void append(Element source, int level, int index) {
		append();
		append(source.getName());
		append(level);
		append(index);
		append();
		append();
		append(source.getDurationMs());
		append(source.getOutcome());
		append();
		append();
		append();
		append();
		append();
		append();
		append();
		append();
		append();
		append();
		append();
		append();
	}
	
	private void appendStep(StepElement step, int level, int index) {
		appendParent(step, level, index);
		newLine();
		int childCount = 0;
		for (Element child : step.getChildren()) {
			if (child instanceof StepElement) {
				appendStep((StepElement)child, level+1, childCount++);
			} else {
				append(child, level+1, childCount++);
				newLine();
			}
		}
	}
	
	private void newLine() {
		builder.append("\n");
		newLine = true;
	}
	
	private void append() {
		comma();
	}
	
	private void append(String text) {
		comma();
		if (text != null) {
			boolean hasSep = text.contains(separator);
			boolean hasQuote = text.contains("\"");
			if (hasSep || hasQuote) {
				builder.append('"');
			}
			if (hasQuote) {
				text = text.replace("\"", "\"\"");
			}
			builder.append(text);
			if (hasSep || hasQuote) {
				builder.append('"');
			}
		}
	}
	
	private void append(int number) {
		comma();
		builder.append(number);
	}
	
	private void append(long number) {
		comma();
		builder.append(number);
	}
	
	private void append(boolean bool) {
		comma();
		builder.append(bool);
	}
	
	private void append(Enum<?> item) {
		comma();
		if (item != null) {
			builder.append(item.name());
		}
	}
	
	private void append(List<String> list) {
		if (list != null && !list.isEmpty()) {
			append(list.stream().collect(Collectors.joining(",")));
		} else {
			comma();
		}
	}
	
	private void append(Date date) {
		if (date != null) {
			String formatted = toISO8601UTC(date);
			append(formatted);
		} else {
			comma();
		}
	}
	
	private String toISO8601UTC(Date date) {
		  TimeZone tz = TimeZone.getTimeZone("UTC");
		  DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		  df.setTimeZone(tz);
		  return df.format(date);
		}
	
	private void comma() {
		if (newLine) {
			newLine = false;
		} else {
			builder.append(separator);
		}
	}
	
}
