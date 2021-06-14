package com.bp.RUIAN.services;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.repositories.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.*;


/**
 * This class tests Elasticsearch service
 * @author Denys Peresychanskyi
 */
public class EsServiceTest {
    private final StatRepository statRepository = Mockito.mock(StatRepository.class);
    private final RegionSoudrznostiRepository regionSoudrznostiRepository = Mockito.mock(RegionSoudrznostiRepository.class);
    private final VuscRepository vuscRepository = Mockito.mock(VuscRepository.class);
    private final OkresRepository okresRepository = Mockito.mock(OkresRepository.class);
    private final OrpRepository orpRepository = Mockito.mock(OrpRepository.class);
    private final PouRepository pouRepository = Mockito.mock(PouRepository.class);
    private final ObecRepository obecRepository = Mockito.mock(ObecRepository.class);
    private final SpravniObvodRepository spravniObvodRepository = Mockito.mock(SpravniObvodRepository.class);
    private final MopRepository mopRepository = Mockito.mock(MopRepository.class);
    private final MomcRepository momcRepository = Mockito.mock(MomcRepository.class);
    private final CastObceRepository castObceRepository = Mockito.mock(CastObceRepository.class);
    private final KatastralniUzemiRepository katastralniUzemiRepository = Mockito.mock(KatastralniUzemiRepository.class);
    private final ZsjRepository zsjRepository = Mockito.mock(ZsjRepository.class);
    private final UliceRepository uliceRepository = Mockito.mock(UliceRepository.class);
    private final ParcelaRepository parcelaRepository = Mockito.mock(ParcelaRepository.class);
    private final StavebniObjektRepository stavebniObjektRepository = Mockito.mock(StavebniObjektRepository.class);
    private final AdresniMistoRepository adresniMistoRepository = Mockito.mock(AdresniMistoRepository.class);

    @Test
    @DisplayName("Should find list of all Stat")
    void findStaty() {
        List<Stat> staty = new ArrayList<>();

        Stat stat = new Stat(1, null,null, null, null,null,
                null, null, null, null, null, null);

        staty.add(stat);

        Mockito.when(statRepository.findAll()).thenReturn(staty);
    }

    @Test
    @DisplayName("Should find Stat by id")
    void shouldFindStatById() {
        Stat stat = new Stat(1, null,null, null, null,null,
                null, null, null, null, null, null);

        Mockito.when(statRepository.findById(1L)).thenReturn(Optional.of(stat));
    }

    @Test
    @DisplayName("Should save Stat")
    void saveStat() {
        Stat stat = new Stat(1, null, null, null, null,null,
                null, null, null, null, null, null);

        statRepository.save(stat);
        Mockito.verify(statRepository, Mockito.times(1)).save(ArgumentMatchers.any(Stat.class));
    }

    @Test
    @DisplayName("Should find a list of Region soudrznosti")
    void findRs() {
        List<RegionSoudrznosti> regionSoudrznostiList = new ArrayList<>();

        RegionSoudrznosti regionSoudrznosti = new RegionSoudrznosti(19, null,null, null, null,
                null, null, null, null, null,
                null, null, null);

        regionSoudrznostiList.add(regionSoudrznosti);
        regionSoudrznostiList.add(regionSoudrznosti);

        Mockito.when(regionSoudrznostiRepository.findAll()).thenReturn(regionSoudrznostiList);
    }

    @Test
    @DisplayName("Should find a Region soudrznosti by id")
    void findRsById() {
        RegionSoudrznosti regionSoudrznosti = new RegionSoudrznosti(19, null, null, 1,
                null, null, null, null, null,
                null, null, null,null);

        Mockito.when(regionSoudrznostiRepository.findById(19L)).thenReturn(Optional.of(regionSoudrznosti));
    }

    @Test
    @DisplayName("Should save Region Soudrznosti")
    void saveRs() {
        RegionSoudrznosti regionSoudrznosti = new RegionSoudrznosti(19, null, null, null,
                null, null, null, null, null,
                null, null, null,null);

        regionSoudrznostiRepository.save(regionSoudrznosti);
        Mockito.verify(regionSoudrznostiRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(RegionSoudrznosti.class));
    }

    @Test
    @DisplayName("Should find a list of Vusc")
    void findVusc() {
        List<Vusc> vuscList = new ArrayList<>();

        Vusc vusc = new Vusc(19, null, null, null,
                null, null, null, null, null,
                null, null,null, null);

        vuscList.add(vusc);
        vuscList.add(vusc);

        Mockito.when(vuscRepository.findAll()).thenReturn(vuscList);
    }

    @Test
    @DisplayName("Should find a Vusc by id")
    void findVuscById() {
        Vusc vusc = new Vusc(19, null, null, null,
                null, null, null, null, null,
                null, null,null, null);

        Mockito.when(vuscRepository.findById(19L)).thenReturn(Optional.of(vusc));
    }

    @Test
    @DisplayName("Should save Vusc")
    void saveVusc() {
        Vusc vusc = new Vusc(19, null, null, null,
                null, null, null, null, null,
                null, null,null, null);

        vuscRepository.save(vusc);
        Mockito.verify(vuscRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Vusc.class));
    }

    @Test
    @DisplayName("Should find a list of Okres")
    void findOkresy() {
        List<Okres> okresList = new ArrayList<>();

        Okres okres = new Okres(19, null, null, null,
                null, null, null, null, null,
                null, null, null,null);

        okresList.add(okres);
        okresList.add(okres);

        Mockito.when(okresRepository.findAll()).thenReturn(okresList);
    }

    @Test
    @DisplayName("Should find an Okres by id")
    void findOkresById() {
        Okres okres = new Okres(19, null, null, null,
                null, null, null, null, null,
                null, null,null, null);

        Mockito.when(okresRepository.findById(19L)).thenReturn(Optional.of(okres));
    }

    @Test
    @DisplayName("Should save Okres")
    void saveOkres() {
        Okres okres = new Okres(19, null, null, null,
                null, null, null, null, null,
                null, null, null,null);

        okresRepository.save(okres);
        Mockito.verify(okresRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Okres.class));
    }

    @Test
    @DisplayName("Should find a list of Orp")
    void findOrp() {
        List<Orp> orpList = new ArrayList<>();

        Orp orp = new Orp(315, null, null, null, null,
                null, null, null, null,
                null, null, null,null);

        orpList.add(orp);
        orpList.add(orp);

        Mockito.when(orpRepository.findAll()).thenReturn(orpList);
    }

    @Test
    @DisplayName("Should find an Orp by id")
    void findOrpById() {
       Orp orp = new Orp(315, null, null, null, null,
               null, null, null, null,
               null, null, null, null);

        Mockito.when(orpRepository.findById(315L)).thenReturn(Optional.of(orp));
    }

    @Test
    @DisplayName("Should save Orp")
    void saveOrp() {
        Orp orp = new Orp(315, null, null, null, null,
                null, null, null, null,
                null, null, null,null);

        orpRepository.save(orp);
        Mockito.verify(orpRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Orp.class));
    }

    @Test
    @DisplayName("Should find a list of Pou")
    void findPou() {
        List<Pou> pouList = new ArrayList<>();

        Pou pou = new Pou(3163, null, null, null, null,
                null, null, null, null,
                null, null,null, null);

        pouList.add(pou);
        pouList.add(pou);

        Mockito.when(pouRepository.findAll()).thenReturn(pouList);
    }

    @Test
    @DisplayName("Should find a Pou by id")
    void findPouById() {
        Pou pou = new Pou(3163, null, null, null, null,
                null, null, null, null,
                null, null,null, null);

        Mockito.when(pouRepository.findById(315L)).thenReturn(Optional.of(pou));
    }

    @Test
    @DisplayName("Should save Pou")
    void savePou() {
        Pou pou = new Pou(3163, null, null, null, null,
                null, null, null, null,
                null, null,null, null);

        pouRepository.save(pou);
        Mockito.verify(pouRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Pou.class));
    }

    @Test
    @DisplayName("Should find a list of Obec")
    void findObce() {
        List<Obec> obecList = new ArrayList<>();

        Obec obec = new Obec(3163, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null, null, null,null, null, null, null);

        obecList.add(obec);
        obecList.add(obec);

        Mockito.when(obecRepository.findAll()).thenReturn(obecList);
    }

    @Test
    @DisplayName("Should find an Obec by id")
    void findObecById() {
        Obec obec = new Obec(3163, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null, null, null,null, null, null, null);

        Mockito.when(obecRepository.findById(3163L)).thenReturn(Optional.of(obec));
    }

    @Test
    @DisplayName("Should save Obec")
    void saveObec() {
        Obec obec = new Obec(3163, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null, null,null, null,null, null, null);

        obecRepository.save(obec);
        Mockito.verify(obecRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Obec.class));
    }

    @Test
    @DisplayName("Should find a list of Spravni obvod")
    void findSO() {
        List<SpravniObvod> spravniObvodList = new ArrayList<>();

        SpravniObvod spravniObvod = new SpravniObvod(1515, null, null, null, null,
                null, null, null, null,null, null,
                null, null);

        spravniObvodList.add(spravniObvod);
        spravniObvodList.add(spravniObvod);

        Mockito.when(spravniObvodRepository.findAll()).thenReturn(spravniObvodList);
    }

    @Test
    @DisplayName("Should find a Spravni obvod by id")
    void findSOById() {
        SpravniObvod spravniObvod = new SpravniObvod(1515, null, null, null, null,
                null, null, null, null,null, null,
                null, null);

        Mockito.when(spravniObvodRepository.findById(1515L)).thenReturn(Optional.of(spravniObvod));
    }

    @Test
    @DisplayName("Should save Spravni obvod")
    void saveSO() {
        SpravniObvod spravniObvod = new SpravniObvod(1515, null, null, null, null,
                null, null, null, null,null, null,
                null, null);

        spravniObvodRepository.save(spravniObvod);
        Mockito.verify(spravniObvodRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(SpravniObvod.class));
    }

    @Test
    @DisplayName("Should find a list of Mop")
    void findMop() {
        List<Mop> mopList = new ArrayList<>();

        Mop mop = new Mop(513, null, null, null, null,
                null, null, null,null, null, null,
                null);

        mopList.add(mop);
        mopList.add(mop);

        Mockito.when(mopRepository.findAll()).thenReturn(mopList);
    }

    @Test
    @DisplayName("Should find a Mop by id")
    void findMopById() {
        Mop mop = new Mop(513, null, null, null, null,
                null, null, null, null, null, null,
                null);

        Mockito.when(mopRepository.findById(513L)).thenReturn(Optional.of(mop));
    }

    @Test
    @DisplayName("Should save Mop")
    void saveMop() {
        Mop mop = new Mop(513, null, null, null, null,
                null, null, null,null, null, null,
                null);

        mopRepository.save(mop);
        Mockito.verify(mopRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Mop.class));
    }

    @Test
    @DisplayName("Should find a list of Momc")
    void findMomc() {
        List<Momc> momcList = new ArrayList<>();

        Momc momc = new Momc(1515, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null);

        momcList.add(momc);
        momcList.add(momc);

        Mockito.when(momcRepository.findAll()).thenReturn(momcList);
    }

    @Test
    @DisplayName("Should find a Momc by id")
    void findMomcById() {
        Momc momc = new Momc(1515, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null, null,null, null);

        Mockito.when(momcRepository.findById(1515L)).thenReturn(Optional.of(momc));
    }

    @Test
    @DisplayName("Should save Momc")
    void saveMomc() {
        Momc momc = new Momc(1515, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null, null, null,null);

        momcRepository.save(momc);
        Mockito.verify(momcRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Momc.class));
    }

    @Test
    @DisplayName("Should find a list of Cast obce")
    void findCastiObce() {
        List<CastObce> castObceList = new ArrayList<>();

        CastObce castObce = new CastObce(1515, null, null, null, null,
                null, null, null, null, null, null,
                null, null);

        castObceList.add(castObce);
        castObceList.add(castObce);

        Mockito.when(castObceRepository.findAll()).thenReturn(castObceList);
    }

    @Test
    @DisplayName("Should find a Cast obce by id")
    void findCastObceById() {
        CastObce castObce = new CastObce(1515, null, null, null, null,
                null, null,null, null, null, null,
                null, null);

        Mockito.when(castObceRepository.findById(1515L)).thenReturn(Optional.of(castObce));
    }

    @Test
    @DisplayName("Should save Cast obce")
    void saveCastObce() {
        CastObce castObce = new CastObce(1515, null, null, null, null,
                null, null,null, null, null, null,
                null, null);

        castObceRepository.save(castObce);
        Mockito.verify(castObceRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(CastObce.class));
    }

    @Test
    @DisplayName("Should find a list of Katastralni uzemi")
    void findKatastralniUzemi() {
        List<KatastralniUzemi> katastralniUzemiList = new ArrayList<>();

        KatastralniUzemi katastralniUzemi = new KatastralniUzemi(1515, null, null, null, null,
                null, null, null,null, null, null,
                null, null, null, null);

        katastralniUzemiList.add(katastralniUzemi);
        katastralniUzemiList.add(katastralniUzemi);

        Mockito.when(katastralniUzemiRepository.findAll()).thenReturn(katastralniUzemiList);
    }

    @Test
    @DisplayName("Should find a Katastralni uzemi by id")
    void findKatastralniUzemiById() {
        KatastralniUzemi katastralniUzemi = new KatastralniUzemi(1515, null, null, null, null,
                null, null, null,null, null, null,
                null, null, null, null);

        Mockito.when(katastralniUzemiRepository.findById(1515L)).thenReturn(Optional.of(katastralniUzemi));
    }

    @Test
    @DisplayName("Should save Katastralni uzemi")
    void saveKatastralniUzemi() {
        KatastralniUzemi katastralniUzemi = new KatastralniUzemi(1515, null, null, null, null,
                null, null, null,null, null, null,
                null, null, null, null);

        katastralniUzemiRepository.save(katastralniUzemi);
        Mockito.verify(katastralniUzemiRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(KatastralniUzemi.class));
    }

    @Test
    @DisplayName("Should find a list of Zsj")
    void findZsj() {
        List<Zsj> zsjList = new ArrayList<>();

        Zsj zsj = new Zsj(1515, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null);

        zsjList.add(zsj);
        zsjList.add(zsj);

        Mockito.when(zsjRepository.findAll()).thenReturn(zsjList);
    }

    @Test
    @DisplayName("Should find a Zsj by id")
    void findZsjById() {
        Zsj zsj = new Zsj(1515, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null);

        Mockito.when(zsjRepository.findById(1515L)).thenReturn(Optional.of(zsj));
    }

    @Test
    @DisplayName("Should save Zsj")
    void saveZsj() {
        Zsj zsj = new Zsj(1515, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null);

        zsjRepository.save(zsj);
        Mockito.verify(zsjRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Zsj.class));
    }

    @Test
    @DisplayName("Should find a list of Ulice")
    void findUlice() {
        List<Ulice> uliceList = new ArrayList<>();

        Ulice ulice = new Ulice(1515, null, null, null, null, null,
                null, null, null, null);

        uliceList.add(ulice);
        uliceList.add(ulice);

        Mockito.when(uliceRepository.findAll()).thenReturn(uliceList);
    }

    @Test
    @DisplayName("Should find an Ulice by id")
    void findUliceById() {
        Ulice ulice = new Ulice(1515, null, null, null, null, null,
                null, null, null, null);

        Mockito.when(uliceRepository.findById(1515L)).thenReturn(Optional.of(ulice));
    }

    @Test
    @DisplayName("Should save Ulice")
    void saveUlice() {
        Ulice ulice = new Ulice(1515, null, null, null, null, null,
                null, null, null, null);

        uliceRepository.save(ulice);
        Mockito.verify(uliceRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Ulice.class));
    }

    @Test
    @DisplayName("Should find a list of Parcela")
    void findParcely() {
        List<Parcela> parcelaList = new ArrayList<>();

        Parcela parcela = new Parcela(161L,null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null);

        parcelaList.add(parcela);
        parcelaList.add(parcela);

        Mockito.when(parcelaRepository.findAll()).thenReturn(parcelaList);
    }

    @Test
    @DisplayName("Should find a parcela by id")
    void findParcelaById() {
        Parcela parcela = new Parcela(161L,null,null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null);

        Mockito.when(parcelaRepository.findById(1515L)).thenReturn(Optional.of(parcela));
    }

    @Test
    @DisplayName("Should save Parcela")
    void saveParcela() {
        Parcela parcela = new Parcela(161L,null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null,
                null);

        parcelaRepository.save(parcela);
        Mockito.verify(parcelaRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Parcela.class));
    }

    @Test
    @DisplayName("Should find a list of Stavebni objekt")
    void findStavebniObjekty() {
        List<StavebniObjekt> stavebniObjektList = new ArrayList<>();

        StavebniObjekt stavebniObjekt = new StavebniObjekt(1515, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null, null);

        stavebniObjektList.add(stavebniObjekt);
        stavebniObjektList.add(stavebniObjekt);

        Mockito.when(stavebniObjektRepository.findAll()).thenReturn(stavebniObjektList);
    }

    @Test
    @DisplayName("Should find a stavebni objekt by id")
    void findStavebniObjektById() {
        StavebniObjekt stavebniObjekt = new StavebniObjekt(1515, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null, null);

        Mockito.when(stavebniObjektRepository.findById(1515L)).thenReturn(Optional.of(stavebniObjekt));
    }

    @Test
    @DisplayName("Should save Stavebni objekt")
    void saveStavebniObjekt() {
        StavebniObjekt stavebniObjekt = new StavebniObjekt(1515, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null, null);

        stavebniObjektRepository.save(stavebniObjekt);
        Mockito.verify(stavebniObjektRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(StavebniObjekt.class));
    }

    @Test
    @DisplayName("Should find a list of Adresni misto")
    void findAdresniMista() {
        List<AdresniMisto> adresniMistoList = new ArrayList<>();

        AdresniMisto adresniMisto = new AdresniMisto(1515, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null);

        adresniMistoList.add(adresniMisto);
        adresniMistoList.add(adresniMisto);

        Mockito.when(adresniMistoRepository.findAll()).thenReturn(adresniMistoList);
    }

    @Test
    @DisplayName("Should find an adresni misto by id")
    void findAdresniMistoById() {
        AdresniMisto adresniMisto = new AdresniMisto(1515, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null);

        Mockito.when(adresniMistoRepository.findById(1515L)).thenReturn(Optional.of(adresniMisto));
    }

    @Test
    @DisplayName("Should save Adresni misto")
    void saveAdresniMisto() {
        AdresniMisto adresniMisto = new AdresniMisto(1515, null, null, null, null,
                null, null, null, null, null,
                null, null, null, null, null);

        adresniMistoRepository.save(adresniMisto);
        Mockito.verify(adresniMistoRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(AdresniMisto.class));
    }
}