package filegeneration;

import java.nio.file.Path;
import javax.annotation.Nonnull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

final class XMLNodeHelper {
    @Nonnull private final Document document;
    @Nonnull private final Element node;

    XMLNodeHelper(@Nonnull Document document, @Nonnull Element node) {
        this.document = document;
        this.node = node;
    }

    void addSimpleNode(@Nonnull String nodeName, @Nonnull Path content) {
        addSimpleNode(nodeName, content.toString());
    }

    void addSimpleNode(@Nonnull String nodeName, @Nonnull String content) {
        Element subNode = document.createElement(nodeName);
        subNode.appendChild(document.createTextNode(content));
        node.appendChild(subNode);
    }
}
