package com.bp.RUIAN.parsers;

import org.springframework.data.elasticsearch.core.geo.GeoJsonLineString;
import org.springframework.data.elasticsearch.core.geo.GeoJsonMultiLineString;
import org.springframework.data.geo.Point;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class DefinicniCaraParser implements TypeParser<GeoJsonMultiLineString>{
    private final Element element;

    public DefinicniCaraParser(Element element) {
        this.element = element;
    }

    @Override
    public GeoJsonMultiLineString parse(String attribute) {
        NodeList nList = element.getElementsByTagName("gml:" + attribute);

        if (nList.getLength() > 0) {
            List<GeoJsonLineString> definicniCara = new ArrayList<>();

            for (int i = 0; i < nList.getLength(); i++) {
                List<Point> points = new ArrayList<>();

                String[] posList = nList.item(i).getTextContent().split(" ");

                for (int j = 0; j < posList.length; j++) {
                    double x = Double.parseDouble(posList[j]);
                    double y = Double.parseDouble(posList[++j]);
                    Point point = new Point(x, y);
                    points.add(point);
                }

                definicniCara.add(GeoJsonLineString.of(points));
            }

            return GeoJsonMultiLineString.of(definicniCara);
        }

        return null;
    }
}
