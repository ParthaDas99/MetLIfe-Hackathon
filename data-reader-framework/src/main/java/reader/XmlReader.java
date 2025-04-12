
package reader;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XmlReader implements FileReaderInterface {
    public String read(File file) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        doc.getDocumentElement().normalize();
        return traverse(doc.getDocumentElement());
    }

    private String traverse(Node node) {
        StringBuilder sb = new StringBuilder();

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            sb.append("<").append(node.getNodeName()).append(">");
        }

        if (node.getNodeType() == Node.TEXT_NODE) {
            sb.append(node.getTextContent().trim());
        }

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            sb.append(traverse(nodeList.item(i)));
        }

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            sb.append("</").append(node.getNodeName()).append(">\n");
        }

        return sb.toString();
    }
}
