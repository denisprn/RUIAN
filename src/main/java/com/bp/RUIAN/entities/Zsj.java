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
 * Container for Zsj information
 * @author Denys Peresychanskyi
 */
@Document(indexName = "zsj")
public record Zsj(
        @Id
        @Field(type = FieldType.Integer, name = "Kod")
        Integer kod,

        @Field(type = FieldType.Text, name = "nazev")
        String nazev,

        @Field(type = FieldType.Boolean, name = "nespravny")
        Boolean nespravny,

        @Field(type = FieldType.Integer, name = "katastralniUzemiKod")
        Integer katastralniUzemiKod,

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

        @Field(type = FieldType.Long, name = "vymera")
        Long vymera,

        @Field(type = FieldType.Integer, name = "charakterZsjKod")
        Integer charakterZsjKod,

        @Field(name = "definicniBod")
        GeoJsonPoint definicniBod,

        @Field(type = FieldType.Nested, name = "hranice")
        GeoJsonPolygon hranice,

        @Field(type = FieldType.Object, name = "nespravnyUdaj")
        NespravnyUdaj nespravnyUdaj,

        @Field(type = FieldType.Date, name = "datumVzniku")
        Date datumVzniku
) { }
