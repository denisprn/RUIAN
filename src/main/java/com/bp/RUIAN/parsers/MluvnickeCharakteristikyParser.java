package com.bp.RUIAN.parsers;

import com.bp.RUIAN.entities.MluvnickeCharakteristiky;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MluvnickeCharakteristikyParser implements TypeParser<MluvnickeCharakteristiky>{
    private final Element element;
    private final String prefix;

    public MluvnickeCharakteristikyParser(Element element, String prefix) {
        this.element = element;
        this.prefix = prefix;
    }

    @Override
    public MluvnickeCharakteristiky parse(String attribute) {
        NodeList nList = element.getElementsByTagName(prefix + ":" + attribute);

        if (nList.getLength() > 0) {
            String pad2 = element.getElementsByTagName("com:Pad2").item(0).getTextContent();
            String pad3 = element.getElementsByTagName("com:Pad3").item(0).getTextContent();
            String pad4 = element.getElementsByTagName("com:Pad4").item(0).getTextContent();
            String pad6 = element.getElementsByTagName("com:Pad6").item(0).getTextContent();
            String pad7 = element.getElementsByTagName("com:Pad7").item(0).getTextContent();
            return new MluvnickeCharakteristiky(pad2, pad3, pad4, pad6, pad7);
        }

        return null;
    }
}
