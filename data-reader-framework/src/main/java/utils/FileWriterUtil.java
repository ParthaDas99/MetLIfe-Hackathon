
package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class FileWriterUtil {
    public static void writeToFile(String data, String filePath) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(data);
        writer.newLine();
        writer.close();
    }
}
