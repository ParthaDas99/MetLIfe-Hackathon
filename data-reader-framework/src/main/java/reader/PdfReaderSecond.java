package reader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfReaderSecond implements FileReaderInterface {

    @Override
    public String read(File file) throws Exception {
        StringBuilder output = new StringBuilder();
        output.append("=== Test Report Summary ===\n");

        PDDocument document = PDDocument.load(file);
        PDFTextStripper stripper = new PDFTextStripper();
        String content = stripper.getText(document);
        document.close();

        // Normalize line endings and split by line
        String[] lines = content.split("\\r?\\n");

        String currentTestCase = null;
        String currentStatus = null;
        String reason = null;

        for (String line : lines) {
            line = line.trim();

            // Match test case ID or name (example: SC1_001, SC2_005 etc.)
            if (line.matches("SC\\d+_\\d+.*")) {
                // flush last case if exists
                if (currentTestCase != null) {
                    output.append("Test Case: ").append(currentTestCase).append("\n");
                    output.append("Status: ").append(currentStatus != null ? currentStatus : "Unknown").append("\n");
                    if (reason != null) output.append("Note: ").append(reason).append("\n");
                    output.append("\n");
                }

                currentTestCase = line;
                currentStatus = null;
                reason = null;
            }

            // Match status keywords
            if (line.matches("(?i).*(Passed but|Passed|Failed|Blocked|Unchecked).*")) {
                // Get first match of status in line
                Matcher matcher = Pattern.compile("(?i)(Passed but|Passed|Failed|Blocked|Unchecked)").matcher(line);
                if (matcher.find()) {
                    currentStatus = matcher.group(1);
                }
            }

            // Attempt to extract failure reason or additional notes
            if (line.toLowerCase().contains("defect") || line.toLowerCase().contains("issue") || line.toLowerCase().contains("reason")) {
                reason = line;
            }
        }

        // Append last one
        if (currentTestCase != null) {
            output.append("Test Case: ").append(currentTestCase).append("\n");
            output.append("Status: ").append(currentStatus != null ? currentStatus : "Unknown").append("\n");
            if (reason != null) output.append("Note: ").append(reason).append("\n");
            output.append("\n");
        }

        return output.toString();
    }
}

