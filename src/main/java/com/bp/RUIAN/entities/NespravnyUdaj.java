package com.bp.RUIAN.entities;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Container for NespravnyUdaj information
 * @author Denys Peresychanskyi
 */
public record NespravnyUdaj(
        @Field(type = FieldType.Text, name = "nazevUdaje")
        String nazevUdaje,

        @Field(type = FieldType.Date, name = "oznacenoDne")
        Date oznacenoDne,

        @Field(type = FieldType.Text, name = "oznacenoInfo")
        String oznacenoInfo) {
}
