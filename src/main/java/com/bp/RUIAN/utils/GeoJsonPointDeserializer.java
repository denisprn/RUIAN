package com.bp.RUIAN.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;

import java.io.IOException;

public class GeoJsonPointDeserializer extends JsonDeserializer<GeoJsonPoint> {
    private final static String GEOJSON_TYPE_POINT = "Point";
    private final static String JSON_KEY_GEOJSON_TYPE = "type";
    private final static String JSON_KEY_GEOJSON_COORDS = "coordinates";

    @Override
    public GeoJsonPoint deserialize(@NotNull JsonParser jp, DeserializationContext ctxt) throws IOException {

        final JsonNode tree = jp.getCodec().readTree(jp);
        final String type = tree.get(JSON_KEY_GEOJSON_TYPE).asText();
        final JsonNode coordsNode = tree.get(JSON_KEY_GEOJSON_COORDS);

        double x = 0;
        double y = 0;

        if (GEOJSON_TYPE_POINT.equalsIgnoreCase(type)) {
            x = coordsNode.get(0).asDouble();
            y = coordsNode.get(1).asDouble();
        }

        return GeoJsonPoint.of(x, y);
    }
}
