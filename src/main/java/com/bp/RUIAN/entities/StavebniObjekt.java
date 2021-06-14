package com.bp.RUIAN.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPolygon;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Container for StavebniObjekt information
 * @author Denys Peresychanskyi
 */
@Document(indexName = "stavebniobjekt")
public record StavebniObjekt(
        @Id
        @Field(type = FieldType.Integer, name = "Kod")
        Integer kod,

        @Field(type = FieldType.Boolean, name = "nespravny")
        Boolean nespravny,

        @Field(type = FieldType.Integer, name = "cislaDomovni")
        List<Integer> cislaDomovni,

        @Field(type = FieldType.Long, name = "identifikacniParcelaId")
        Long identifikacniParcelaId,

        @Field(type = FieldType.Integer, name = "typStavebnihoObjektuKod")
        Integer typStavebnihoObjektuKod,

        @Field(type = FieldType.Integer, name = "zpusobVyuzitiKod")
        Integer zpusobVyuzitiKod,

        @Field(type = FieldType.Integer, name = "castObceKod")
        Integer castObceKod,

        @Field(type = FieldType.Date, name = "platiOd")
        Date platiOd,

        @Field(type = FieldType.Date, name = "platiDo")
        Date platiDo,

        @Field(type = FieldType.Long, name = "idTransakce")
        Long idTransakce,

        @Field(type = FieldType.Long, name = "globalniIdNavrhuZmeny")
        Long globalniIdNavrhuZmeny,

        @Field(type = FieldType.Long, name = "isknBudovaId")
        Long isknBudovaId,

        @Field(type = FieldType.Date, name = "dokonceni")
        Date dokonceni,

        @Field(type = FieldType.Integer, name = "druhKonstrukceKod")
        Integer druhKonstrukceKod,

        @Field(type = FieldType.Integer, name = "obestavenyProstor")
        Integer obestavenyProstor,

        @Field(type = FieldType.Integer, name = "pocetBytu")
        Integer pocetBytu,

        @Field(type = FieldType.Integer, name = "pocetPodlazi")
        Integer pocetPodlazi,

        @Field(type = FieldType.Integer, name = "podlahovaPlocha")
        Integer podlahovaPlocha,

        @Field(type = FieldType.Integer, name = "pripojeniKanalizaceKod")
        Integer pripojeniKanalizaceKod,

        @Field(type = FieldType.Integer, name = "pripojeniPlynKod")
        Integer pripojeniPlynKod,

        @Field(type = FieldType.Integer, name = "pripojeniVodovodKod")
        Integer pripojeniVodovodKod,

        @Field(type = FieldType.Integer, name = "vybaveniVytahemKod")
        Integer vybaveniVytahemKod,

        @Field(type = FieldType.Integer, name = "zastavenaPlocha")
        Integer zastavenaPlocha,

        @Field(type = FieldType.Integer, name = "zpusobVytapeniKod")
        Integer zpusobVytapeniKod,

        @Field(type = FieldType.Integer, name = "zpusobyOchranyKod")
        List<Integer> zpusobyOchranyKod,

        @Field(type = FieldType.Nested, name = "detailniTea")
        List<DetailniTEA> detailniTea,

        @Field(name = "definicniBod")
        GeoJsonPoint definicniBod,

        @Field(type = FieldType.Nested, name = "hranice")
        GeoJsonPolygon hranice,

        @Field(type = FieldType.Object, name = "nespravnyUdaj")
        NespravnyUdaj nespravnyUdaj
) { }
