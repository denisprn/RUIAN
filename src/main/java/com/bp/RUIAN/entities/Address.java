package com.bp.RUIAN.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "address")
public record Address(
        @Id
        @Field(type = FieldType.Integer, name = "id")
        Integer id,

        @Field(type = FieldType.Integer, name = "municipalityId")
        Integer municipalityId,

        @Field(type = FieldType.Text, name = "municipalityName")
        String municipalityName,

        @Field(type = FieldType.Integer, name = "momcId")
        Integer momcId,

        @Field(type = FieldType.Text, name = "momcName")
        String momcName,

        @Field(type = FieldType.Integer, name = "mopId")
        Integer mopId,

        @Field(type = FieldType.Text, name = "mopName")
        String mopName,

        @Field(type = FieldType.Integer, name = "municipalityPartId")
        Integer municipalityPartId,

        @Field(type = FieldType.Text, name = "municipalityPartName")
        String municipalityPartName,

        @Field(type = FieldType.Integer, name = "streetId")
        Integer streetId,

        @Field(type = FieldType.Text, name = "streetName")
        String streetName,

        @Field(type = FieldType.Text, name = "typeSO")
        String typeSO,

        @Field(type = FieldType.Text, name = "houseNumber")
        String houseNumber,

        @Field(type = FieldType.Text, name = "houseReferenceNumber")
        String houseReferenceNumber,

        @Field(type = FieldType.Text, name = "houseReferenceSign")
        String houseReferenceSign,

        @Field(type = FieldType.Text, name = "zipCode")
        String zipCode,

        @Field(type = FieldType.Text, name = "yCoordinate")
        String yCoordinate,

        @Field(type = FieldType.Text, name = "xCoordinate")
        String xCoordinate,

        @Field(type = FieldType.Date, name = "validSince")
        Date validSince
) { }