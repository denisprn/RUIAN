package com.bp.RUIAN.controllers;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.services.EsService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/ruian")
public class RuianController {

    private final EsService esService;

    public RuianController(EsService esService) {
        this.esService = esService;
    }

    @GetMapping("/")
    public @ResponseBody String greeting() {
        return "Hello, this is a RUIAN REST API!";
    }

    @GetMapping("/staty")
    public Iterable<Stat> listStaty() {
        return esService.findStaty();
    }

    @GetMapping("/stat/{id}")
    public Optional<Stat> getStat(@PathVariable("id") Long id) {
        return esService.findStatById(id);
    }

    @GetMapping("/regionysoudrznosti")
    public Iterable<RegionSoudrznosti> listRs() {return esService.findRs();}

    @GetMapping("/regionsoudrznosti/{id}")
    public Optional<RegionSoudrznosti> getRs(@PathVariable("id") Long id) {
        return esService.findRsById(id);
    }

    @GetMapping("/vusc")
    public Iterable<Vusc> listVusc() {return esService.findVusc();}

    @GetMapping("/vusc/{id}")
    public Optional<Vusc> getVusc(@PathVariable("id") Long id) {
        return esService.findVuscById(id);
    }

    @GetMapping("/okresy")
    public Iterable<Okres> listOkresy() {return esService.findOkresy();}

    @GetMapping("/okres/{id}")
    public Optional<Okres> getOkres(@PathVariable("id") Long id) {
        return esService.findOkresById(id);
    }

    @GetMapping("/orp")
    public Iterable<Orp> listOrp() {return esService.findOrp();}

    @GetMapping("/orp/{id}")
    public Optional<Orp> getOrp(@PathVariable("id") Long id) {
        return esService.findOrpById(id);
    }

    @GetMapping("/pou")
    public Iterable<Pou> listPou() {return esService.findPou();}

    @GetMapping("/pou/{id}")
    public Optional<Pou> getPou(@PathVariable("id") Long id) {
        return esService.findPouById(id);
    }

    @GetMapping("/obce")
    public Iterable<Obec> listObce() {return esService.findObce();}

    @GetMapping("/obec/{id}")
    public Optional<Obec> getObec(@PathVariable("id") Long id) {
        return esService.findObecById(id);
    }

    @GetMapping("/spravniobvody")
    public Iterable<SpravniObvod> listSO() {return esService.findSO();}

    @GetMapping("/spravniobvod/{id}")
    public Optional<SpravniObvod> getSO(@PathVariable("id") Long id) {
        return esService.findSOById(id);
    }

    @GetMapping("/mop")
    public Iterable<Mop> listMop() {return esService.findMop();}

    @GetMapping("/mop/{id}")
    public Optional<Mop> getMop(@PathVariable("id") Long id) {
        return esService.findMopById(id);
    }

    @GetMapping("/momc")
    public Iterable<Momc> listMomc() {return esService.findMomc();}

    @GetMapping("/momc/{id}")
    public Optional<Momc> getMomc(@PathVariable("id") Long id) {
        return esService.findMomcById(id);
    }

    @GetMapping("/castiobce")
    public Iterable<CastObce> listCastiObce() {return esService.findCastiObce();}

    @GetMapping("/castobce/{id}")
    public Optional<CastObce> getCastObce(@PathVariable("id") Long id) {
        return esService.findCastObceById(id);
    }

    @GetMapping("/katastralniuzemi")
    public Iterable<KatastralniUzemi> listKatastralniUzemi() {return esService.findKatastralniUzemi();}

    @GetMapping("/katastralniuzemi/{id}")
    public Optional<KatastralniUzemi> getKatastralniUzemi(@PathVariable("id") Long id) {
        return esService.findKatastralniUzemiById(id);
    }

    @GetMapping("/zsj")
    public Iterable<Zsj> listZsj() {return esService.findZsj();}

    @GetMapping("/zsj/{id}")
    public Optional<Zsj> getZsj(@PathVariable("id") Long id) {
        return esService.findZsjById(id);
    }

    @GetMapping("/ulice")
    public Iterable<Ulice> listUlice() {return esService.findUlice();}

    @GetMapping("/ulice/{id}")
    public Optional<Ulice> getUlice(@PathVariable("id") Long id) {
        return esService.findUliceById(id);
    }

    @GetMapping("/parcely")
    public Iterable<Parcela> listParcely() {return esService.findParcely();}

    @GetMapping("/parcela/{id}")
    public Optional<Parcela> getParcela(@PathVariable("id") Long id) {
        return esService.findParcelaById(id);
    }

    @GetMapping("/stavebniobjekty")
    public Iterable<StavebniObjekt> listStavebniObjekty() {return esService.findStavebniObjekty();}

    @GetMapping("/stavebniobjekt/{id}")
    public Optional<StavebniObjekt> getStavebniobjekt(@PathVariable("id") Long id) {
        return esService.findStavebniObjektById(id);
    }

    @GetMapping("/adresnimista")
    public Iterable<AdresniMisto> listAdresniMista() {return esService.findAdresniMista();}

    @GetMapping("/adresnimisto/{id}")
    public Optional<AdresniMisto> getAdresniMisto(@PathVariable("id") Long id) {
        return esService.findAdresniMistoById(id);
    }

    //Searches
    @GetMapping("/okresy/search")
    public List<Okres> searchOkresy(@RequestParam("query") String query) throws Exception {
        return esService.searchOkresy(query);
    }

    @GetMapping("/obce/search")
    public List<Obec> searchObce(@RequestParam("query") String query) throws Exception {
        return esService.searchObce(query);
    }

    @GetMapping("/castiobce/search")
    public List<CastObce> searchCastiObce(@RequestParam("query") String query) throws Exception {
        return esService.searchCastiObce(query);
    }

    @GetMapping("/ulice/search")
    public List<Ulice> searchUlice(@RequestParam("query") String query) throws Exception {
        return esService.searchUlice(query);
    }

    @GetMapping("/adresnimista/search")
    public List<AdresniMisto> searchAdresniMista(@RequestParam("query") String query) throws Exception {
        return esService.searchAdresniMista(query);
    }
}
