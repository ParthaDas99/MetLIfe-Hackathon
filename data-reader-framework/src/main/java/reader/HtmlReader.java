
package reader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;

public class HtmlReader implements FileReaderInterface {
    public String read(File file) throws Exception {
        Document doc = Jsoup.parse(file, "UTF-8");
        return doc.text();
    }
}
