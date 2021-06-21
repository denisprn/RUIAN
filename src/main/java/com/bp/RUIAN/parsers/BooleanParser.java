package com.bp.RUIAN.parsers;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class BooleanParser implements TypeParser<Boolean>{
    private final Element element;
    private final String prefix;

    public BooleanParser(Element element, String prefix) {
        this.element = element;
        this.prefix = prefix;
    }

    @Override
    public Boolean parse(String attribute) {
        NodeList nList = element.getElementsByTagName(prefix + ":" + attribute);

        if (nList.getLength() > 0) {
            return Boolean.parseBoolean(nList.item(0).getTextContent());
        }

        return null;
    }
}
