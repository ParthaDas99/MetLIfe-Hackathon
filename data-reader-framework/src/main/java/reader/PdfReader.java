package reader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfReader implements FileReaderInterface {

    @Override
    public String read(File file) throws Exception {
        StringBuilder report = new StringBuilder();
        PDDocument document = PDDocument.load(file);
        PDFTextStripper stripper = new PDFTextStripper();

        String text = stripper.getText(document);
        document.close();

        String[] lines = text.split("\\r?\\n");

        Pattern testCasePattern = Pattern.compile("(?i)(SC\\d+_\\d+).*");
        Pattern statusPattern = Pattern.compile("\\b(Passed|Failed|Blocked|Unchecked|Passed but)\\b");

        String currentTestCase = null;
        StringBuilder currentBlock = new StringBuilder();

        for (String line : lines) {
            line = line.trim();

            Matcher testCaseMatcher = testCasePattern.matcher(line);
            Matcher statusMatcher = statusPattern.matcher(line);

            if (testCaseMatcher.find()) {
                // Finish previous test case block
                if (currentTestCase != null) {
                    report.append("🧪 ").append(currentTestCase).append("\n");
                    report.append(currentBlock.toString()).append("\n\n");
                }

                // Start new test case block
                currentTestCase = testCaseMatcher.group(1);
                currentBlock = new StringBuilder();
                currentBlock.append(line).append("\n");

            } else if (currentTestCase != null) {
                currentBlock.append(line).append("\n");

                if (statusMatcher.find()) {
                    currentBlock.append("✅ Status: ").append(statusMatcher.group()).append("\n");
                }
            }
        }

        // Append the last test case block
        if (currentTestCase != null) {
            report.append("🧪 ").append(currentTestCase).append("\n");
            report.append(currentBlock.toString()).append("\n");
        }

        return report.toString();
    }
}
