package hu.ibello;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.xml.bind.JAXBException;

import hu.ibello.model.TestRun;
import hu.ibello.results.TestRunCsvConverter;
import hu.ibello.results.TestRunManager;

public class Tools {
	
	private static void xml2csv(File xmlFile, File csvFile) throws JAXBException, IOException {
		TestRun testRun = null;
		TestRunManager manager = new TestRunManager();
		try (FileInputStream stream = new FileInputStream(xmlFile)) {
			testRun = manager.fromInputStream(stream);
		}
		if (testRun != null) {
			TestRunCsvConverter converter = new TestRunCsvConverter();
			String result = converter.toCsv(testRun);
			if (csvFile != null) {
				csvFile.createNewFile();
				Files.write(csvFile.toPath(), result.getBytes(StandardCharsets.UTF_8));
			} else {
				System.out.println(result);
			}
		}
	}
	
	private static void error(String msg) {
		System.err.println(msg);
		System.exit(1);
	}

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			error("Missing argument!");
		}
		switch (args[0]) {
		case "xml2csv":
			if (args.length == 1) {
				error("Missing argument: XML file path!");
			}
			File xmlFile = new File(args[1]);
			File csvFile = null;
			if (args.length > 2) {
				csvFile = new File(args[2]);
			}
			xml2csv(xmlFile, csvFile);
			break;
		default:
			error("Unknown command: " + args[0]);
		}
	}
	
}
