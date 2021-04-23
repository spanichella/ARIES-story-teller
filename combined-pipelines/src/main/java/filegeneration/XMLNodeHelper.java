package filegeneration;

import java.math.BigDecimal;
import java.nio.file.Path;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

final class XMLNodeHelper {
    private final Document document;
    private final Element node;

    XMLNodeHelper(Document document, Element node) {
        this.document = document;
        this.node = node;
    }

    void addSimpleNode(String nodeName, Path content) {
        addSimpleNode(nodeName, content.toString());
    }

    void addSimpleNode(String nodeName, String content) {
        Element subNode = document.createElement(nodeName);
        subNode.appendChild(document.createTextNode(content));
        node.appendChild(subNode);
    }
}
