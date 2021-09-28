package com.bp.RUIAN.entities;

import com.bp.RUIAN.utils.GeoJsonMultiLineStringDeserializer;
import com.bp.RUIAN.utils.GeoJsonPointDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoJsonMultiLineString;

import java.text.ParseException;
import java.util.Date;

/**
 * Container for Ulice information
 * @author Denys Peresychanskyi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "ulice")
public record Ulice(
        @Id
        @Field(type = FieldType.Integer, name = "kod")
        Integer kod,

        @Field(type = FieldType.Text, name = "nazev")
        String nazev,

        @Field(type = FieldType.Boolean, name = "nespravny")
        Boolean nespravny,

        @Field(type = FieldType.Integer, name = "kodObce")
        Integer kodObce,

        @Field(type = FieldType.Date, name = "platiOd")
        Date platiOd,

        @Field(type = FieldType.Date, name = "platiDo")
        Date platiDo,

        @Field(type = FieldType.Long, name = "idTransakce")
        Long idTransakce,

        @Field(type = FieldType.Long, name = "globalniIdNavrhuZmeny")
        Long globalniIdNavrhuZmeny,

        @JsonDeserialize(using = GeoJsonMultiLineStringDeserializer.class)
        @Field(name = "definicniCara")
        GeoJsonMultiLineString definicniCara,

        @Field(type = FieldType.Object, name = "nespravnyUdaj")
        NespravnyUdaj nespravnyUdaj
) {
        public static Ulice create() {
                return new Ulice(171441, "Masarykovo namesti", false,586846,
                        null, null, 1850590L, 1368563L,
                        null, null);
        }
}
