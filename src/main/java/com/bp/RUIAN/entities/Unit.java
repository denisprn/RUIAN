package com.bp.RUIAN.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Unit {
    private final AdresniMisto adresniMisto;
    private Ulice ulice;
    private Obec obec;

    public Unit(AdresniMisto adresniMisto, Ulice ulice, Obec obec) {
        this.adresniMisto = adresniMisto;
        this.ulice = ulice;
        this.obec = obec;
    }

    public void setUlice(Ulice ulice) {
        this.ulice = ulice;
    }

    public void setObec(Obec obec) {
        this.obec = obec;
    }
}
