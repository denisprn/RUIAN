package com.bp.RUIAN.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.geo.Point;

import java.util.Date;

/**
 * Container for AdresniMisto information
 * @author Denys Peresychanskyi
 */
@Document(indexName = "adresnimisto")
public record AdresniMisto(
        @Id
        @Field(type = FieldType.Integer, name = "kod")
        Integer kod,

        @Field(type = FieldType.Boolean, name = "nespravny")
        Boolean nespravny,

        @Field(type = FieldType.Text, name = "cisloDomovni")
        String cisloDomovni,

        @Field(type = FieldType.Text, name = "cisloOrientacni")
        String cisloOrientacni,

        @Field(type = FieldType.Text, name = "cisloOrientacniPismeno")
        String cisloOrientacniPismeno,

        @Field(type = FieldType.Text, name = "psc")
        String psc,

        @Field(type = FieldType.Integer, name = "stavebniObjektKod")
        Integer stavebniObjektKod,

        @Field(type = FieldType.Integer, name = "uliceKod")
        Integer uliceKod,

        @Field(type = FieldType.Integer, name = "voKod")
        Integer voKod,

        @Field(type = FieldType.Date, name = "platiOd")
        Date platiOd,

        @Field(type = FieldType.Date, name = "platiDo")
        Date platiDo,

        @Field(type = FieldType.Long, name = "idTransakce")
        Long idTransakce,

        @Field(type = FieldType.Long, name = "globalniIdNavrhuZmeny")
        Long globalniIdNavrhuZmeny,

        @Field(name = "pos")
        Point pos,

        @Field(type = FieldType.Object, name = "nespravnyUdaj")
        NespravnyUdaj nespravnyUdaj
) { }
