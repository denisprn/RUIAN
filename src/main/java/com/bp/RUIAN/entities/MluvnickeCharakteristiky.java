package com.bp.RUIAN.entities;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Container for MluvnickeCharakteristiky information
 * @author Denys Peresychanskyi
 */
public record MluvnickeCharakteristiky(
        @Field(type = FieldType.Text, name = "Pad2")
        String Pad2,

        @Field(type = FieldType.Text, name = "Pad3")
        String Pad3,

        @Field(type = FieldType.Text, name = "Pad4")
        String Pad4,

        @Field(type = FieldType.Text, name = "Pad6")
        String Pad6,

        @Field(type = FieldType.Text, name = "Pad7")
        String Pad7
) { }
