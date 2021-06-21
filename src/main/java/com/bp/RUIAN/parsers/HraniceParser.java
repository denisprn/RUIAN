package com.bp.RUIAN.parsers;

import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.springframework.data.geo.Point;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class HraniceParser implements TypeParser<GeoJsonPolygon>{
    private final Element element;

    public HraniceParser(Element element) {
        this.element = element;
    }

    @Override
    public GeoJsonPolygon parse(String attribute) {
        NodeList nList = element.getElementsByTagName("gml:" + attribute);

        if (nList.getLength() > 0) {
            List<Point> points = new ArrayList<>();

            String[] posList = nList.item(0).getTextContent().split(" ");

            for (int i = 0; i < posList.length; i++) {
                double x = Double.parseDouble(posList[i]);
                double y = Double.parseDouble(posList[++i]);
                Point point = new Point(x, y);
                points.add(point);
            }

            return GeoJsonPolygon.of(points);
        }

        return null;
    }
}
