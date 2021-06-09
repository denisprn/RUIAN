package com.bp.RUIAN.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.geo.Point;
import java.util.Date;

/**
 * Container for Stat information
 * @author Denys Peresychanskyi
 */
@Document(indexName = "stat")
public record Stat(
        @Id
        @Field(type = FieldType.Integer, name = "Kod")
        Integer kod,

        @Field(type = FieldType.Text, name = "Nazev")
        String nazev,

        @Field(type = FieldType.Boolean, name = "nespravny")
        Boolean nespravny,

        @Field(type = FieldType.Date, name = "PlatiOd")
        Date platiOd,

        @Field(type = FieldType.Date, name = "platiDo")
        Date platiDo,

        @Field(type = FieldType.Long, name = "IdTransakce")
        Long idTransakce,

        @Field(type = FieldType.Long, name = "GLobalniIdNavrhuZmeny")
        Long globalniIdNavrhuZmeny,

        @Field(type = FieldType.Text, name = "NutsLau")
        String nutsLau,

        @Field(name = "pos")
        Point pos,

        @Field(type = FieldType.Object, name = "nespravnyUdaj")
        NespravnyUdaj nespravnyUdaj,

        @Field(type = FieldType.Date, name = "DatumVzniku")
        Date datumVzniku

) { }
