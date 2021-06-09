package com.bp.RUIAN.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "volebniokrsek")
public record VolebniOkrsek(
        @Id
        @Field(type = FieldType.Integer, name = "Kod")
        Integer kod,

        @Field(type = FieldType.Date, name = "platiOd")
        Date platiOd,

        @Field(type = FieldType.Date, name = "platiDo")
        Date platiDo,

        @Field(type = FieldType.Long, name = "idTransakce")
        Long idTransakce,

        @Field(type = FieldType.Long, name = "globalniIdNavrhuZmeny")
        Long globalniIdNavrhuZmeny,

        @Field(type = FieldType.Integer, name = "cislo")
        Integer cislo,

        @Field(type = FieldType.Boolean, name = "nespravny")
        Boolean nespravny,

        @Field(type = FieldType.Integer, name = "obecKod")
        Integer[] obecKod,

        @Field(type = FieldType.Integer, name = "momcKod")
        Integer[] momcKod,

        @Field(type = FieldType.Text, name = "poznamka")
        String poznamka
)  { }
