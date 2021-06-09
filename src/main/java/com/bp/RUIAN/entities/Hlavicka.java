package com.bp.RUIAN.entities;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

public record Hlavicka(
        @Field(type = FieldType.Text, name = "zachranka")
        String verzeVfr,

        @Field(type = FieldType.Text, name = "typZaznamu")
        String typZaznamu,

        @Field(type = FieldType.Text, name = "typDavky")
        String typDavky,

        @Field(type = FieldType.Text, name = "typSouboru")
        String typSouboru,

        @Field(type = FieldType.Date, name = "datum")
        Date datum,

        @Field(type = FieldType.Integer, name = "transakceOdId")
        Integer transakceOdId,

        @Field(type = FieldType.Date, name = "transakceOdZapsano")
        Date transakceOdZapsano,

        @Field(type = FieldType.Integer, name = "transakceDoId")
        Integer transakceDoId,

        @Field(type = FieldType.Date, name = "transakceDoZapsano")
        Date transakceDoZapsano,

        @Field(type = FieldType.Text, name = "hrefSoubor")
        String hrefSoubor,

        @Field(type = FieldType.Text, name = "metadataTyp")
        String metadataTyp,

        @Field(type = FieldType.Date, name = "platnostDatKIsui")
        Date platnostDatKIsui,

        @Field(type = FieldType.Date, name = "platnostDatKIskn")
        Date platnostDatKIskn
) { }
