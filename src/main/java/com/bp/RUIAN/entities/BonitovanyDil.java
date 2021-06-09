package com.bp.RUIAN.entities;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public record BonitovanyDil(
        @Field(type = FieldType.Long, name = "vymera")
        Long vymera,

        @Field(type = FieldType.Integer, name = "bonitovaJednotkaKod")
        Integer bonitovaJednotkaKod,

        @Field(type = FieldType.Long, name = "idTransakce")
        Long idTransakce,

        @Field(type = FieldType.Long, name = "rizeniId")
        Long rizeniId
) { }
