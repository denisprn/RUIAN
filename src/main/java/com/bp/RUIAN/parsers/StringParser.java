package com.bp.RUIAN.parsers;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class StringParser implements TypeParser<String>{
    private final Element element;
    private final String prefix;

    public StringParser(Element element, String prefix) {
        this.element = element;
        this.prefix = prefix;
    }

    @Override
    public String parse(String attribute) {
        NodeList nList;

        //check if attribute already has prefix
        if (attribute.contains(":")) {
            nList = element.getElementsByTagName(attribute);
        } else {
            nList = element.getElementsByTagName(prefix + ":" + attribute);
        }

        if (nList.getLength() > 0) {
            return nList.item(0).getTextContent();
        }

        return null;
    }
}
