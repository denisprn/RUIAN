package com.bp.RUIAN.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.springframework.data.geo.Point;

import java.util.Date;

/**
 * Container for Momc information
 * @author Denys Peresychanskyi
 */
@Document(indexName = "momc")
public record Momc(
        @Id
        @Field(type = FieldType.Integer, name = "Kod")
        Integer kod,

        @Field(type = FieldType.Text, name = "nazev")
        String nazev,

        @Field(type = FieldType.Boolean, name = "nespravny")
        Boolean nespravny,

        @Field(type = FieldType.Integer, name = "kodMop")
        Integer kodMop,

        @Field(type = FieldType.Integer, name = "kodObce")
        Integer kodObce,

        @Field(type = FieldType.Integer, name = "kodSpravniObvod")
        Integer kodSpravniObvod,

        @Field(type = FieldType.Date, name = "platiOd")
        Date platiOd,

        @Field(type = FieldType.Date, name = "platiDo")
        Date platiDo,

        @Field(type = FieldType.Long, name = "idTransakce")
        Long idTransakce,

        @Field(type = FieldType.Long, name = "globalniIdNavrhuZmeny")
        Long globalniIdNavrhuZmeny,

        @Field(type = FieldType.Text, name = "vlajkaText")
        String vlajkaText,

        @Field(type = FieldType.Byte, name = "vlajkaObrazek")
        String vlajkaObrazek,

        @Field(type = FieldType.Object, name = "mluvnickeCharakteristiky")
        MluvnickeCharakteristiky mluvnickeCharakteristiky,

        @Field(type = FieldType.Text, name = "znakText")
        String znakText,

        @Field(type = FieldType.Byte, name = "znakObrazek")
        String znakObrazek,

        @Field(name = "definicniBod")
        GeoJsonPoint definicniBod,

        @Field(type = FieldType.Nested, name = "hranice")
        GeoJsonPolygon hranice,

        @Field(type = FieldType.Object, name = "nespravnyUdaj")
        NespravnyUdaj nespravnyUdaj,

        @Field(type = FieldType.Date, name = "datumVzniku")
        Date datumVzniku
) { }
