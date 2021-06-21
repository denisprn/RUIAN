package com.bp.RUIAN.parsers;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class LongParser implements TypeParser<Long>{
    private final Element element;
    private final String prefix;

    public LongParser(Element element, String prefix) {
        this.element = element;
        this.prefix = prefix;
    }

    @Override
    public Long parse(String attribute) {
        NodeList nList;

        //check if attribute already has prefix
        if (attribute.contains(":")) {
            nList = element.getElementsByTagName(attribute);
        } else {
            nList = element.getElementsByTagName(prefix + ":" + attribute);
        }

        if (nList.getLength() > 0) {
            return Long.parseLong(nList.item(0).getTextContent());
        }

        return null;
    }
}
