package com.bp.RUIAN.entities;

import com.bp.RUIAN.utils.GeoJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;

import java.util.Date;

/**
 * Container for Obec information
 * @author Denys Peresychanskyi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "obec")
public record Obec(
        @Id
        @Field(type = FieldType.Integer, name = "kod")
        Integer kod,

        @Field(type = FieldType.Text, name = "nazev")
        String nazev,

        @Field(type = FieldType.Boolean, name = "nespravny")
        Boolean nespravny,

        @Field(type = FieldType.Integer, name = "statusKod")
        Integer statusKod,

        @Field(type = FieldType.Integer, name = "kodOkresu")
        Integer kodOkresu,

        @Field(type = FieldType.Integer, name = "kodPou")
        Integer kodPou,

        @Field(type = FieldType.Date, name = "platiOd")
        Date platiOd,

        @Field(type = FieldType.Date, name = "platiDo")
        Date platiDo,

        @Field(type = FieldType.Long, name = "idTransakce")
        Long idTransakce,

        @Field(type = FieldType.Long, name = "globalniIdNavrhuZmeny")
        Long globalniIdNavrhuZmeny,

        @Field(type = FieldType.Object, name = "mluvnickeCharakteristiky")
        MluvnickeCharakteristiky mluvnickeCharakteristiky,

        @Field(type = FieldType.Text, name = "vlajkaText")
        String vlajkaText,

        @Field(type = FieldType.Text, name = "vlajkaObrazek")
        String vlajkaObrazek,

        @Field(type = FieldType.Text, name = "znakText")
        String znakText,

        @Field(type = FieldType.Text, name = "znakObrazek")
        String znakObrazek,

        @Field(type = FieldType.Integer, name = "cleneniSMRozsahKod")
        Integer cleneniSMRozsahKod,

        @Field(type = FieldType.Integer, name = "cleneniSMTypKod")
        Integer cleneniSMTypKod,

        @Field(type = FieldType.Text, name = "nutsLau")
        String nutsLau,

        @JsonDeserialize(using = GeoJsonDeserializer.class)
        @Field(name = "definicniBod")
        GeoJsonPoint definicniBod,

        @Field(type = FieldType.Nested, name = "hranice")
        GeoJsonPolygon hranice,

        @Field(type = FieldType.Object, name = "nespravnyUdaj")
        NespravnyUdaj nespravnyUdaj,

        @Field(type = FieldType.Date, name = "datumVzniku")
        Date datumVzniku
) { }
