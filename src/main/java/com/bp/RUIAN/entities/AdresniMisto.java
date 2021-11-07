package com.bp.RUIAN.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoJsonPoint;

import java.util.Date;

/**
 * Container for AdresniMisto information
 * @author Denys Peresychanskyi
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "adresnimisto")
public record AdresniMisto(
        @Id
        @Field(type = FieldType.Integer, name = "kod")
        Integer kod,

        @Field(type = FieldType.Boolean, name = "nespravny")
        Boolean nespravny,

        @Field(type = FieldType.Text, name = "cisloDomovni")
        String cisloDomovni,

        @Field(type = FieldType.Text, name = "cisloOrientacni")
        String cisloOrientacni,

        @Field(type = FieldType.Text, name = "cisloOrientacniPismeno")
        String cisloOrientacniPismeno,

        @Field(type = FieldType.Text, name = "psc")
        String psc,

        @Field(type = FieldType.Integer, name = "stavebniObjektKod")
        Integer stavebniObjektKod,

        @Field(type = FieldType.Integer, name = "uliceKod")
        Integer uliceKod,

        @Field(type = FieldType.Integer, name = "voKod")
        Integer voKod,

        @Field(type = FieldType.Date, name = "platiOd")
        Date platiOd,

        @Field(type = FieldType.Date, name = "platiDo")
        Date platiDo,

        @Field(type = FieldType.Long, name = "idTransakce")
        Long idTransakce,

        @Field(type = FieldType.Long, name = "globalniIdNavrhuZmeny")
        Long globalniIdNavrhuZmeny,

        //@JsonDeserialize(using = GeoJsonPointDeserializer.class)
        @Field(name = "definicniBod")
        GeoJsonPoint definicniBod,

        @Field(type = FieldType.Object, name = "nespravnyUdaj")
        NespravnyUdaj nespravnyUdaj
) {
        public static AdresniMisto create() {
                return new AdresniMisto(12331201, false,"65", "16",
                        "A", "58601", 12263214, 171441, 18293,
                        null, null, 2554788L, 1844739L,
                        GeoJsonPoint.of(-669029.19, -1130284.05), null);
        }
}
