package com.bp.RUIAN.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

public record DetailniTEA(
        @Field(type = FieldType.Integer, name = "kod")
        Integer kod,

        @Field(type = FieldType.Date, name = "platiOd")
        Date platiOd,

        @Field(type = FieldType.Boolean, name = "nespravny")
        Boolean nespravny,

        @Field(type = FieldType.Long, name = "globalniIdNavrhuZmeny")
        Long globalniIdNavrhuZmeny,

        @Field(type = FieldType.Integer, name = "druhKonstrukceKod")
        Integer druhKonstrukceKod,

        @Field(type = FieldType.Integer, name = "pocetBytu")
        Integer pocetBytu,

        @Field(type = FieldType.Integer, name = "pocetPodlazi")
        Integer pocetPodlazi,

        @Field(type = FieldType.Integer, name = "pripojeniKanalizaceKod")
        Integer pripojeniKanalizaceKod,

        @Field(type = FieldType.Integer, name = "pripojeniPlynKod")
        Integer pripojeniPlynKod,

        @Field(type = FieldType.Integer, name = "pripojeniVodovodKod")
        Integer pripojeniVodovodKod,

        @Field(type = FieldType.Integer, name = "zpusobVytapeniKod")
        Integer zpusobVytapeniKod,

        @Field(type = FieldType.Integer, name = "adresniMistoKod")
        Integer adresniMistoKod
) { }
