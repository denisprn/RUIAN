package com.bp.RUIAN.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Container for ZpusobOchranyPozemku information
 * @author Denys Peresychanskyi
 */
public record ZpusobOchranyPozemku(
        @Field(type = FieldType.Integer, name = "KodZOP")
        Integer kod,

        @Field(type = FieldType.Integer, name = "typOchranyKodZOP")
        Integer typOchranyKod,

        @Field(type = FieldType.Long, name = "idTransakceZOP")
        Long idTransakce,

        @Field(type = FieldType.Long, name = "rizeniIdZOP")
        Long rizeniId
) { }
