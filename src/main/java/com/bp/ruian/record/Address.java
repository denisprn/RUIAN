package com.bp.ruian.record;

import com.bp.ruian.utils.EsAnalyzerNames;
import com.bp.ruian.utils.EsFieldNames;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * RUIAN's address record
 * @author denisprn
 */
@Document(indexName = "address")
@Setting(settingPath = "es-config/elastic-analyzer.json")
public record Address(

        @Id
        @Field(type = FieldType.Integer,
                name = EsFieldNames.ID)
        Integer id,

        @Field(type = FieldType.Integer,
                name = EsFieldNames.MUNICIPALITY_ID)
        Integer municipalityId,

        @Field(type = FieldType.Text,
                name = EsFieldNames.MUNICIPALITY_NAME,
                analyzer = EsAnalyzerNames.AUTOCOMPLETE_INDEX,
                searchAnalyzer = EsAnalyzerNames.AUTOCOMPLETE_SEARCH)
        String municipalityName,

        @Field(type = FieldType.Integer,
                name = EsFieldNames.MOMC_ID)
        Integer momcId,

        @Field(type = FieldType.Text,
                name = EsFieldNames.MOMC_NAME)
        String momcName,

        @Field(type = FieldType.Integer,
                name = EsFieldNames.MOP_ID)
        Integer mopId,

        @Field(type = FieldType.Text,
                name = EsFieldNames.MOP_NAME)
        String mopName,

        @Field(type = FieldType.Integer,
                name = EsFieldNames.MUNICIPALITY_PART_ID)
        Integer municipalityPartId,

        @Field(type = FieldType.Text,
                name = EsFieldNames.MUNICIPALITY_PART_NAME,
                analyzer = EsAnalyzerNames.AUTOCOMPLETE_INDEX,
                searchAnalyzer = EsAnalyzerNames.AUTOCOMPLETE_SEARCH)
        String municipalityPartName,

        @Field(type = FieldType.Integer,
                name = EsFieldNames.STREET_ID)
        Integer streetId,

        @Field(type = FieldType.Text,
                name = EsFieldNames.STREET_NAME,
                analyzer = EsAnalyzerNames.AUTOCOMPLETE_INDEX,
                searchAnalyzer = EsAnalyzerNames.AUTOCOMPLETE_SEARCH)
        String streetName,

        @Field(type = FieldType.Text,
                name = EsFieldNames.TYPE_CO,
                analyzer = EsAnalyzerNames.STANDARD,
                searchAnalyzer = EsAnalyzerNames.STANDARD)
        String typeCO,

        @Field(type = FieldType.Text,
                name = EsFieldNames.HOUSE_NUMBER,
                analyzer = EsAnalyzerNames.HOUSE_NUMBERS,
                searchAnalyzer = EsAnalyzerNames.HOUSE_NUMBERS)
        String houseNumber,

        @Field(type = FieldType.Text,
                name = EsFieldNames.HOUSE_REFERENCE_NUMBER,
                analyzer = EsAnalyzerNames.HOUSE_NUMBERS,
                searchAnalyzer = EsAnalyzerNames.HOUSE_NUMBERS)
        String houseReferenceNumber,

        @Field(type = FieldType.Text,
                name = EsFieldNames.HOUSE_REFERENCE_SIGN,
                analyzer = EsAnalyzerNames.HOUSE_REFERENCE_SIGN,
                searchAnalyzer = EsAnalyzerNames.HOUSE_REFERENCE_SIGN)
        String houseReferenceSign,

        @Field(type = FieldType.Text,
                name = EsFieldNames.ZIP_CODE,
                analyzer = EsAnalyzerNames.ZIP_CODE,
                searchAnalyzer = EsAnalyzerNames.ZIP_CODE)
        String zipCode,

        @Field(type = FieldType.Text,
                name = EsFieldNames.Y_COORDINATE)
        String yCoordinate,

        @Field(type = FieldType.Text,
                name = EsFieldNames.X_COORDINATE)
        String xCoordinate,

        @Field(type = FieldType.Text,
                name = EsFieldNames.VALID_SINCE)
        String validSince
) { }