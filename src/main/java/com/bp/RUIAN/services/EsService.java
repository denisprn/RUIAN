package com.bp.RUIAN.services;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.repositories.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Service for API's business logic
 * @author Denys Peresychanskyi
 */
@Service
public class EsService {
    private final RestHighLevelClient esClient;
    private final StatRepository statRepository;
    private final RegionSoudrznostiRepository regionSoudrznostiRepository;
    private final VuscRepository vuscRepository;
    private final OkresRepository okresRepository;
    private final OrpRepository orpRepository;
    private final PouRepository pouRepository;
    private final ObecRepository obecRepository;
    private final SpravniObvodRepository spravniObvodRepository;
    private final MopRepository mopRepository;
    private final MomcRepository momcRepository;
    private final CastObceRepository castObceRepository;
    private final KatastralniUzemiRepository katastralniUzemiRepository;
    private final ZsjRepository zsjRepository;
    private final UliceRepository uliceRepository;
    private final ParcelaRepository parcelaRepository;
    private final VolebniOkrsekRepository volebniOkrsekRepository;
    private final StavebniObjektRepository stavebniObjektRepository;
    private final AdresniMistoRepository adresniMistoRepository;
    private final AddressRepository addressRepository;

    public EsService(RestHighLevelClient esClient, StatRepository statRepository,
                     RegionSoudrznostiRepository regionSoudrznostiRepository,
                     VuscRepository vuscRepository, OkresRepository okresRepository,
                     OrpRepository orpRepository, PouRepository pouRepository,
                     ObecRepository obecRepository, SpravniObvodRepository spravniObvodRepository,
                     MopRepository mopRepository, MomcRepository momcRepository,
                     CastObceRepository castObceRepository, KatastralniUzemiRepository katastralniUzemiRepository,
                     ZsjRepository zsjRepository, UliceRepository uliceRepository,
                     ParcelaRepository parcelaRepository, VolebniOkrsekRepository volebniOkrsekRepository, StavebniObjektRepository stavebniObjektRepository,
                     AdresniMistoRepository adresniMistoRepository, AddressRepository addressRepository) {
        this.esClient = esClient;
        this.statRepository = statRepository;
        this.regionSoudrznostiRepository = regionSoudrznostiRepository;
        this.vuscRepository = vuscRepository;
        this.okresRepository = okresRepository;
        this.orpRepository = orpRepository;
        this.pouRepository = pouRepository;
        this.obecRepository = obecRepository;
        this.spravniObvodRepository = spravniObvodRepository;
        this.mopRepository = mopRepository;
        this.momcRepository = momcRepository;
        this.castObceRepository = castObceRepository;
        this.katastralniUzemiRepository = katastralniUzemiRepository;
        this.zsjRepository = zsjRepository;
        this.uliceRepository = uliceRepository;
        this.parcelaRepository = parcelaRepository;
        this.volebniOkrsekRepository = volebniOkrsekRepository;
        this.stavebniObjektRepository = stavebniObjektRepository;
        this.adresniMistoRepository = adresniMistoRepository;
        this.addressRepository = addressRepository;
    }

    public List<String> search(String searchString) throws IOException {
        return new AddressSearch(esClient).search(searchString);
    }

    public void saveStat(Stat stat) {
        statRepository.save(stat);
    }

    public Optional<Stat> findStatById(Long id) {
        return statRepository.findById(id);
    }

    public void saveRs(RegionSoudrznosti regionSoudrznosti) {
        regionSoudrznostiRepository.save(regionSoudrznosti);
    }

    public Optional<RegionSoudrznosti> findRegionSoudrznostiById(Long id) {
        return regionSoudrznostiRepository.findById(id);
    }

    public void saveVusc(Vusc vusc) {
        vuscRepository.save(vusc);
    }

    public Optional<Vusc> findVuscById(Long id) {
        return vuscRepository.findById(id);
    }

    public void saveOkres(Okres okres) {
        okresRepository.save(okres);
    }

    public Optional<Okres> findOkresById(Long id) {
        return okresRepository.findById(id);
    }

    public void saveOrp(Orp orp) {
        orpRepository.save(orp);
    }

    public Optional<Orp> findOrpById(Long id) {
        return orpRepository.findById(id);
    }

    public void savePou(Pou pou) {
        pouRepository.save(pou);
    }

    public Optional<Pou> findPouById(Long id) {
        return pouRepository.findById(id);
    }

    public void saveObec(Obec obec) {
        obecRepository.save(obec);
    }

    public Optional<Obec> findObecById(Long id) {
        return obecRepository.findById(id);
    }

    public void saveSO(SpravniObvod spravniObvod) {
        spravniObvodRepository.save(spravniObvod);
    }

    public Optional<SpravniObvod> findSpravniObvodById(Long id) {
        return spravniObvodRepository.findById(id);
    }

    public void saveMop(Mop mop) {
        mopRepository.save(mop);
    }

    public Optional<Mop> findMopById(Long id) {
        return mopRepository.findById(id);
    }

    public void saveMomc(Momc momc) {
        momcRepository.save(momc);
    }

    public Optional<Momc> findMomcById(Long id) {
        return momcRepository.findById(id);
    }

    public void saveCastObce(CastObce castObce) {
        castObceRepository.save(castObce);
    }

    public Optional<CastObce> findCastObceById(Long id) {
        return castObceRepository.findById(id);
    }

    public void saveKatastralniUzemi(KatastralniUzemi katastralniUzemi) {
        katastralniUzemiRepository.save(katastralniUzemi);
    }

    public Optional<KatastralniUzemi> findKatastralniUzemiById(Long id) {
        return katastralniUzemiRepository.findById(id);
    }

    public void saveZsj(Zsj zsj) {
        zsjRepository.save(zsj);
    }

    public Optional<Zsj> findZsjById(Long id) {
        return zsjRepository.findById(id);
    }

    public void saveUlice(Ulice ulice) {
        uliceRepository.save(ulice);
    }

    public Optional<Ulice> findUliceById(Long id) {
        return uliceRepository.findById(id);
    }

    public void saveParcela(Parcela parcela) {
        parcelaRepository.save(parcela);
    }

    public Optional<Parcela> findParcelaById(Long id) {
        return parcelaRepository.findById(id);
    }

    public void saveStavebniObjekt(StavebniObjekt stavebniObjekt) {
        stavebniObjektRepository.save(stavebniObjekt);
    }

    public Optional<StavebniObjekt> findStavebniObjektById(Long id) {
        return stavebniObjektRepository.findById(id);
    }

    public void saveVolebniOkrsek(VolebniOkrsek volebniOkrsek) {volebniOkrsekRepository.save(volebniOkrsek);}

    public Optional<VolebniOkrsek> findVolebniOkrsekById(Long id) {
        return volebniOkrsekRepository.findById(id);
    }

    public void saveAdresniMisto(AdresniMisto adresniMisto) {
        adresniMistoRepository.save(adresniMisto);
    }

    public Optional<AdresniMisto> findAdresniMistoById(Long id) {
        return adresniMistoRepository.findById(id);
    }

    public void saveAddress(Address address) {
        addressRepository.save(address);
    }
}
