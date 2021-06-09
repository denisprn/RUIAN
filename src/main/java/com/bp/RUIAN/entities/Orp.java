package com.bp.RUIAN.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.geo.Point;

import java.util.Date;

/**
 * Container for Orp information
 * @author Denys Peresychanskyi
 */
@Document(indexName = "orp")
public record Orp(
        @Id
        @Field(type = FieldType.Integer, name = "Kod")
        Integer kod,

        @Field(type = FieldType.Text, name = "nazev")
        String nazev,

        @Field(type = FieldType.Boolean, name = "nespravny")
        Boolean nespravny,

        @Field(type = FieldType.Integer, name = "spravniObecKod")
        Integer spravniObecKod,

        @Field(type = FieldType.Integer, name = "kodOkresu")
        Integer kodOkresu,

        @Field(type = FieldType.Date, name = "platiOd")
        Date platiOd,

        @Field(type = FieldType.Date, name = "platiDo")
        Date platiDo,

        @Field(type = FieldType.Long, name = "idTransakce")
        Long idTransakce,

        @Field(type = FieldType.Long, name = "globalniIdNavrhuZmeny")
        Long globalniIdNavrhuZmeny,

        /*@Field(type = FieldType.Text, name = "definicniBod")
        String definicniBod,

        @Field(type = FieldType.Text, name = "hranice")
        String hranice,*/

        @Field(name = "pos")
        Point pos,

        @Field(type = FieldType.Object, name = "nespravnyUdaj")
        NespravnyUdaj nespravnyUdaj,

        @Field(type = FieldType.Date, name = "datumVzniku")
        Date datumVzniku
) /*implements ItemWithDefinicniBod, ItemWithHranice*/ {
    /*@Override
    public String getDefinicniBod() {
        return definicniBod;
    }

    @Override
    public String getHranice() {
        return hranice;
    }*/
}
