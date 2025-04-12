package main;

import reader.*;
import utils.FileWriterUtil;

import java.io.File;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        // Map extensions to reader classes
        HashMap<String, FileReaderInterface> readerMap = new HashMap<>();
        readerMap.put("pdf", new PdfReader());
        readerMap.put("xlsx", new ExcelReader());
        readerMap.put("html", new HtmlReader());
        readerMap.put("xml", new XmlReader());

        // ✅ Create output directory if it doesn't exist
        new File("output").mkdirs();

        // Read files from input folder
        File[] files = new File("input/").listFiles();
        if (files == null) {
            System.out.println("No files found in input/ folder.");
            return;
        }

        // Process each file
        for (File file : files) {
            String extension = getFileExtension(file);
            FileReaderInterface reader = readerMap.get(extension.toLowerCase());

            if (reader != null) {
                System.out.println("Reading: " + file.getName());
                String data = reader.read(file);
                FileWriterUtil.writeToFile("===== " + file.getName() + " =====\n" + data, "output/result.txt");
            } else {
                System.out.println("Unsupported file type: " + extension);
            }
        }

        System.out.println("✅ Data extraction complete. Check output/result.txt");
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int dotIndex = name.lastIndexOf('.');
        return (dotIndex == -1) ? "" : name.substring(dotIndex + 1);
    }
}
