package com.bp.RUIAN.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "zpusobochranyobjektu")
public record ZpusobOchranyObjektu(
        @Id
        @Field(type = FieldType.Integer, name = "Kod")
        Integer kod,

        @Field(type = FieldType.Integer, name = "typOchranyKod")
        Integer typOchranyKod,

        @Field(type = FieldType.Long, name = "idTransakce")
        Long idTransakce,

        @Field(type = FieldType.Long, name = "rizeniId")
        Long rizeniId) {
}
