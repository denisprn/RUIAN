package com.bp.RUIAN.entities;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "zaniklyprvek")
public record ZaniklyPrvek(
        @Field(type = FieldType.Text, name = "typPrvkuKod")
        String typPrvkuKod,

        @Field(type = FieldType.Long, name = "prvekId")
        Long prvekId,

        @Field(type = FieldType.Long, name = "idTransakce")
        Long idTransakce
) { }
