package com.bp.RUIAN.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.elasticsearch.core.geo.GeoJsonLineString;
import org.springframework.data.elasticsearch.core.geo.GeoJsonMultiLineString;
import org.springframework.data.geo.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeoJsonMultiLineStringDeserializer extends JsonDeserializer<GeoJsonMultiLineString> {
    private final static String GEOJSON_TYPE_LINESTRING = "MultiLineString";
    private final static String JSON_KEY_GEOJSON_TYPE = "type";
    private final static String JSON_KEY_GEOJSON_COORDS = "coordinates";

    @Override
    public GeoJsonMultiLineString deserialize(@NotNull JsonParser jp, DeserializationContext ctxt) throws IOException {
        List<GeoJsonLineString> geoJsonLineStrings = new ArrayList<>();
        List<Point> points = new ArrayList<>();
        Point point;
        JsonNode node;
        final JsonNode tree = jp.getCodec().readTree(jp);
        final String type = tree.get(JSON_KEY_GEOJSON_TYPE).asText();
        final JsonNode coordsNode = tree.get(JSON_KEY_GEOJSON_COORDS);
        double x, y;

        if (GEOJSON_TYPE_LINESTRING.equalsIgnoreCase(type)) {
            for (int i = 0; i < coordsNode.size(); i++) {
                node = coordsNode.get(i);

                for (int j = 0; j < node.size(); j++) {
                    x = node.get(j).get(0).asDouble();
                    y = node.get(j).get(1).asDouble();

                    point = new Point(x, y);
                    points.add(point);
                }

                geoJsonLineStrings.add(GeoJsonLineString.of(points));

                if (i != (coordsNode.size() - 1)) {
                    points.clear();
                }
            }
        }

        return GeoJsonMultiLineString.of(geoJsonLineStrings);
    }
}
