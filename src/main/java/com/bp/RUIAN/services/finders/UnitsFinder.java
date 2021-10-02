package com.bp.RUIAN.services.finders;

import com.bp.RUIAN.converters.Converter;
import com.bp.RUIAN.entities.Obec;
import com.bp.RUIAN.entities.Ulice;
import com.bp.RUIAN.entities.Unit;
import com.bp.RUIAN.repositories.ObecRepository;
import com.bp.RUIAN.repositories.UliceRepository;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;

public class UnitsFinder {
    private final List<SearchHit> obecHits;
    private final List<SearchHit> uliceHits;
    private final List<SearchHit> amHits;
    private final List<Unit> found;
    private final Converter converter;
    private final UliceRepository uliceRepository;
    private final ObecRepository obecRepository;
    private final boolean obecExist;
    private final boolean uliceExist;
    private final boolean amExist;

    public UnitsFinder(List<SearchHit> obecHits, List<SearchHit> uliceHits, List<SearchHit> amHits,
                       UliceRepository uliceRepository, ObecRepository obecRepository) {
        this.obecHits = obecHits;
        this.uliceHits = uliceHits;
        this.amHits = amHits;
        this.uliceRepository = uliceRepository;
        this.obecRepository = obecRepository;
        found = new ArrayList<>();
        converter = new Converter();
        obecExist = !obecHits.isEmpty();
        uliceExist = !uliceHits.isEmpty();
        amExist = !amHits.isEmpty();
    }

    public List<Unit> find() {
        if (obecExist) {
            if (uliceExist && amExist) {
                return findIfAllExist();
            } else if (uliceExist) {
                return findIfOnlyObecAndUliceExist();
            } else if (amExist) {
                return findIfOnlyObecAndAdresniMistoExist();
            } else {
                return findIfOnlyObecExist();
            }
        } else if (uliceExist) {
            if (amExist) {
                return findIfOnlyUliceAndAdresniMistoExist();
            } else {
                return findIfOnlyUliceExist();
            }
        } else if (amExist) {
            return findIfOnlyAdresniMistoExist();
        }

        return null;
    }

    private List<Unit> findIfAllExist() {
        for (SearchHit obecHit : obecHits) {
            String kodObce = obecHit.getId();

            for (SearchHit uliceHit : uliceHits) {
                String kodObceUlice = uliceHit.getSourceAsMap().get("kodObce").toString();

                if (kodObce.equals(kodObceUlice)) {
                    String kodUlice = uliceHit.getId();

                    for (SearchHit adresniMistoHit : amHits) {
                        String kodUliceAM = adresniMistoHit.getSourceAsMap().get("uliceKod").toString();

                        if (kodUliceAM.equals(kodUlice)) {
                            Unit unit = converter.convertHitsToUnit(obecHit, uliceHit, adresniMistoHit);
                            found.add(unit);
                        }
                    }
                }
            }
        }

        return found;
    }

    private List<Unit> findIfOnlyObecAndUliceExist() {
        for (SearchHit obecHit : obecHits) {
            String kodObce = obecHit.getId();

            for (SearchHit uliceHit : uliceHits) {
                String kodObceUlice = uliceHit.getSourceAsMap().get("kodObce").toString();

                if (kodObce.equals(kodObceUlice)) {
                    Unit unit = converter.convertHitsToUnit(obecHit, uliceHit, null);
                    found.add(unit);
                }
            }
        }

        return found;
    }

    private List<Unit> findIfOnlyObecAndAdresniMistoExist() {
        for (SearchHit obecHit : obecHits) {
            String kodObce = obecHit.getId();
            List<Ulice> uliceList = uliceRepository.findUlicesByKodObce(Integer.parseInt(kodObce));

            for (Ulice ulice : uliceList) {
                String kodObceUlice = ulice.kodObce().toString();

                if (kodObce.equals(kodObceUlice)) {
                    String kodUlice = ulice.kod().toString();

                    for (SearchHit adresniMistoHit : amHits) {
                        String kodUliceAM = adresniMistoHit.getSourceAsMap().get("uliceKod").toString();

                        if (kodUliceAM.equals(kodUlice)) {
                            Unit unit = converter.convertHitsToUnit(obecHit, null, adresniMistoHit);
                            unit.setUlice(ulice);
                            found.add(unit);
                        }
                    }
                }
            }
        }

        return found;
    }

    private List<Unit> findIfOnlyUliceAndAdresniMistoExist() {
        for (SearchHit uliceHit : uliceHits) {
            String kodUlice = uliceHit.getId();
            String kodObceUlice = uliceHit.getSourceAsMap().get("kodObce").toString();
            Obec obec = obecRepository.findObecByKod(Integer.parseInt(kodObceUlice));

            for (SearchHit adresniMistoHit : amHits) {
                String kodUliceAM = adresniMistoHit.getSourceAsMap().get("uliceKod").toString();

                if (kodUliceAM.equals(kodUlice)) {
                    Unit unit = converter.convertHitsToUnit(null, uliceHit, adresniMistoHit);
                    unit.setObec(obec);
                    found.add(unit);
                }
            }
        }

        return found;
    }

    private List<Unit> findIfOnlyObecExist() {
        for (SearchHit obecHit : obecHits) {
            Unit unit = converter.convertHitsToUnit(obecHit, null, null);
            found.add(unit);
        }

        return found;
    }

    private List<Unit> findIfOnlyUliceExist() {
        for (SearchHit uliceHit : uliceHits) {
            String kodObceUlice = uliceHit.getSourceAsMap().get("kodObce").toString();
            Obec obec = obecRepository.findObecByKod(Integer.parseInt(kodObceUlice));
            Unit unit = converter.convertHitsToUnit(null, uliceHit, null);
            unit.setObec(obec);
            found.add(unit);
        }

        return found;
    }

    private List<Unit> findIfOnlyAdresniMistoExist() {
        for (SearchHit adresniMistoHit : amHits) {
            String kodUliceAM = adresniMistoHit.getSourceAsMap().get("uliceKod").toString();
            Ulice ulice = uliceRepository.findUliceByKod(Integer.parseInt(kodUliceAM));
            String kodObceUlice = ulice.kodObce().toString();
            Obec obec = obecRepository.findObecByKod(Integer.parseInt(kodObceUlice));
            Unit unit = converter.convertHitsToUnit(null, null, adresniMistoHit);
            unit.setObec(obec);
            unit.setUlice(ulice);
            found.add(unit);
        }

        return found;
    }
}
