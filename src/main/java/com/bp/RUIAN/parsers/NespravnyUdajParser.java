package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.NespravnyUdaj;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.util.Date;

public class NespravnyUdajParser implements TypeParser<NespravnyUdaj> {
    private final Element element;
    private final String prefix;
    private final DateParser dateParser;

    public NespravnyUdajParser(Element element, String prefix) {
        this.element = element;
        this.prefix = prefix;
        this.dateParser = new DateParser(element, prefix);
    }

    @Override
    public NespravnyUdaj parse(String attribute) throws ParseException {
        NodeList nList = element.getElementsByTagName(prefix + ":" + attribute);

        if (nList.getLength() > 0) {
            String nazevUdaje = element.getElementsByTagName("com:NazevUdaje").item(0).getTextContent();
            Date oznacenoDne = dateParser.parse("com:OznacenoDne");
            String oznacenoInfo = element.getElementsByTagName("com:OznacenoInfo").item(0).getTextContent();

            return new NespravnyUdaj(nazevUdaje, oznacenoDne, oznacenoInfo);
        }

        return null;
    }
}
