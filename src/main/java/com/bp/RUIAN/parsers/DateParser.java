package com.bp.RUIAN.parsers;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateParser implements TypeParser<Date>{
    private final Element element;
    private final String prefix;
    private final SimpleDateFormat sdf;

    public DateParser(Element element, String prefix) {
        this.element = element;
        this.prefix = prefix;
        this.sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        this.sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public Date parse(String attribute) throws ParseException {
        NodeList nList;

        //if attribute already has prefix
        if (attribute.contains(":")) {
            nList = element.getElementsByTagName(attribute);
        } else {
            nList = element.getElementsByTagName(prefix + ":" + attribute);
        }

        if (nList.getLength() > 0) {
            return sdf.parse(nList.item(0).getTextContent());
        }

        return null;
    }
}
