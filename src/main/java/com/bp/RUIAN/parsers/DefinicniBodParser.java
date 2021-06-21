package com.bp.RUIAN.parsers;

import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DefinicniBodParser implements TypeParser<GeoJsonPoint>{
    private final Element element;

    public DefinicniBodParser(Element element) {
        this.element = element;
    }

    @Override
    public GeoJsonPoint parse(String attribute) {
        NodeList nList = element.getElementsByTagName("gml:" + attribute);

        if (nList.getLength() > 0) {
            String definicniBod = nList.item(0).getTextContent();
            double x = Double.parseDouble(definicniBod.split(" ")[0]);
            double y = Double.parseDouble(definicniBod.split(" ")[1]);

            return GeoJsonPoint.of(x,y);
        }

        return null;
    }
}
