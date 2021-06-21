package com.bp.RUIAN.parsers;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class ListIntegerParser implements TypeParser<List<Integer>>{
    private final Element element;
    private final String prefix;

    public ListIntegerParser(Element element, String prefix) {
        this.element = element;
        this.prefix = prefix;
    }

    @Override
    public List<Integer> parse(String attribute) {
        NodeList nList;

        if (attribute.contains(":")) {
            nList = element.getElementsByTagName(attribute);
        } else {
            nList = element.getElementsByTagName(prefix + attribute);
        }

        if (nList.getLength() > 0) {
            List<Integer> intList = new ArrayList<>();

            for (int i = 0; i < nList.getLength(); i++) {
                intList.add(Integer.parseInt(nList.item(i).getTextContent()));
            }

            return intList;
        }

        return null;
    }
}
