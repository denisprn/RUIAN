package com.bp.RUIAN.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.geo.Point;

import java.util.Date;
import java.util.List;

@Document(indexName = "parcela")
public record Parcela(
        @Id
        @Field(type = FieldType.Long, name = "id")
        Long id,

        @Field(type = FieldType.Boolean, name = "nespravny")
        Boolean nespravny,

        @Field(type = FieldType.Integer, name = "kmenoveCislo")
        Integer kmenoveCislo,

        @Field(type = FieldType.Integer, name = "pododdeleniCisla")
        Integer pododdeleniCisla,

        @Field(type = FieldType.Long, name = "vymeraParcely")
        Long vymeraParcely,

        @Field(type = FieldType.Integer, name = "druhCislovaniKod")
        Integer druhCislovaniKod,

        @Field(type = FieldType.Integer, name = "druhPozemkuKod")
        Integer druhPozemkuKod,

        @Field(type = FieldType.Integer, name = "kodKatastralniUzemi")
        Integer kodKatastralniUzemi,

        @Field(type = FieldType.Date, name = "platiOd")
        Date platiOd,

        @Field(type = FieldType.Date, name = "platiDo")
        Date platiDo,

        @Field(type = FieldType.Long, name = "idTransakce")
        Long idTransakce,

        @Field(type = FieldType.Long, name = "rizeniId")
        Long rizeniId,

        @Field(type = FieldType.Object, name = "bonitovanyDil")
        List<BonitovanyDil> bonitovaneDily,

        @Field(type = FieldType.Object, name = "zpusobOchranyPozemku")
        ZpusobOchranyPozemku zpusobOchranyPozemku,

        @Field(name = "pos")
        Point pos,

        @Field(type = FieldType.Object, name = "nespravnyUdaj")
        NespravnyUdaj nespravnyUdaj
) { }
