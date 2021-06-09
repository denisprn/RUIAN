package com.bp.RUIAN.controllers;

import com.bp.RUIAN.entities.*;
import com.bp.RUIAN.services.EsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Controller for RUIAN REST endpoints
 * <p>
 *     This class handles the GET operations for RUIAN entities, via HTTP actions.
 * </p>
 * <p>
 *     This class also serves fuzzy searches for Okres, Obec, CastObce, Ulice and AdresniMisto records
 * </p>
 * @author Denys Peresychanskyi
 */
@RestController
@RequestMapping("/ruian")
public class RuianController {

    private final EsService esService;

    public RuianController(EsService esService) {
        this.esService = esService;
    }

    /**
     * Handle the / endpoint
     * @return String
     */
    @GetMapping("/")
    public @ResponseBody String greeting() {
        return "Hello, this is a RUIAN REST API!";
    }

    /**
     * Handle the /staty endpoint
     * @return Iterable of Stat record
     */
    @GetMapping("/staty")
    public Iterable<Stat> listStaty() {
        return esService.findStaty();
    }

    /**
     * Handle the /stat/{id} endpoint
     * @param id id of the Stat record
     * @return Optional of the Stat record
     */
    @GetMapping("/stat/{id}")
    public Optional<Stat> getStat(@PathVariable("id") Long id) {
        return esService.findStatById(id);
    }

    /**
     * Handle the /regionysoudrznosti endpoint
     * @return Iterable of the RegionSoudrznosti record
     */
    @GetMapping("/regionysoudrznosti")
    public Iterable<RegionSoudrznosti> listRs() {return esService.findRs();}

    /**
     * Handle the /regionsoudrznosti/{id} endpoint
     * @param id id of the RegionSoudrznosti record
     * @return Optional of the RegionSoudrznosti record
     */
    @GetMapping("/regionsoudrznosti/{id}")
    public Optional<RegionSoudrznosti> getRs(@PathVariable("id") Long id) {
        return esService.findRsById(id);
    }

    /**
     * Handle the /vusc endpoint
     * @return Iterable of the Vusc record
     */
    @GetMapping("/vusc")
    public Iterable<Vusc> listVusc() {return esService.findVusc();}

    /**
     * Handle the /vusc/{id} endpoint
     * @param id id of the Vusc record
     * @return Optional of the Vusc record
     */
    @GetMapping("/vusc/{id}")
    public Optional<Vusc> getVusc(@PathVariable("id") Long id) {
        return esService.findVuscById(id);
    }

    /**
     * Handle the /okresy endpoint
     * @return Iterable of the Okres record
     */
    @GetMapping("/okresy")
    public Iterable<Okres> listOkresy() {return esService.findOkresy();}

    /**
     * Handle the /okres/{id} endpoint
     * @param id id of the Okres record
     * @return Optional of Okres record
     */
    @GetMapping("/okres/{id}")
    public Optional<Okres> getOkres(@PathVariable("id") Long id) {
        return esService.findOkresById(id);
    }

    /**
     * Handle the /orp endpoint
     * @return Iterable of the Orp record
     */
    @GetMapping("/orp")
    public Iterable<Orp> listOrp() {return esService.findOrp();}

    /**
     * Handle the /orp/{id} endpoint
     * @param id id of the Orp record
     * @return Optional of the Orp record
     */
    @GetMapping("/orp/{id}")
    public Optional<Orp> getOrp(@PathVariable("id") Long id) {
        return esService.findOrpById(id);
    }

    /**
     * Handle the /pou endpoint
     * @return Iterable of the Pou record
     */
    @GetMapping("/pou")
    public Iterable<Pou> listPou() {return esService.findPou();}

    /**
     * Handle the /pou/{id} endpoint
     * @param id id of the Pou record
     * @return Optional of the Pou record
     */
    @GetMapping("/pou/{id}")
    public Optional<Pou> getPou(@PathVariable("id") Long id) {
        return esService.findPouById(id);
    }

    /**
     * Handle the /obce endpoint
     * @return Iterable of the Obec record
     */
    @GetMapping("/obce")
    public Iterable<Obec> listObce() {return esService.findObce();}

    /**
     * Handle the /obec/{id} endpoint
     * @param id id of the Obec record
     * @return Optional of the Obec record
     */
    @GetMapping("/obec/{id}")
    public Optional<Obec> getObec(@PathVariable("id") Long id) {
        return esService.findObecById(id);
    }

    /**
     * Handle the /spravniobvody endpoint
     * @return Iterable of the SpravniObvod record
     */
    @GetMapping("/spravniobvody")
    public Iterable<SpravniObvod> listSO() {return esService.findSO();}

    /**
     * Handle the /spravniobvody endpoint
     * @param id id of the SpravniObvod record
     * @return Optional of the SpravniObvod record
     */
    @GetMapping("/spravniobvod/{id}")
    public Optional<SpravniObvod> getSO(@PathVariable("id") Long id) {
        return esService.findSOById(id);
    }

    /**
     * Handle the /mop endpoint
     * @return Iterable of the Mop record
     */
    @GetMapping("/mop")
    public Iterable<Mop> listMop() {return esService.findMop();}

    /**
     * Handle the /mop/{id}
     * @param id id of the Mop record
     * @return Optional of the Mop record
     */
    @GetMapping("/mop/{id}")
    public Optional<Mop> getMop(@PathVariable("id") Long id) {
        return esService.findMopById(id);
    }

    /**
     * Handle the /momc endpoint
     * @return Iterable of the Momc record
     */
    @GetMapping("/momc")
    public Iterable<Momc> listMomc() {return esService.findMomc();}

    /**
     * Handle the /spravniobvody endpoint
     * @param id id of the Momc record
     * @return Optional of the Momc record
     */
    @GetMapping("/momc/{id}")
    public Optional<Momc> getMomc(@PathVariable("id") Long id) {
        return esService.findMomcById(id);
    }

    /**
     * Handle the /castiobce endpoint
     * @return Iterable of the CastObce record
     */
    @GetMapping("/castiobce")
    public Iterable<CastObce> listCastiObce() {return esService.findCastiObce();}

    /**
     * Handle the /castobce/{id} endpoint
     * @param id id of the CastObce record
     * @return Optional of the CastObce record
     */
    @GetMapping("/castobce/{id}")
    public Optional<CastObce> getCastObce(@PathVariable("id") Long id) {
        return esService.findCastObceById(id);
    }

    /**
     * Handle the /katastralniuzemi endpoint
     * @return Iterable of the KatastralniUzemi record
     */
    @GetMapping("/katastralniuzemi")
    public Iterable<KatastralniUzemi> listKatastralniUzemi() {return esService.findKatastralniUzemi();}

    /**
     * Handle the /katastralniuzemi/{id} endpoint
     * @param id id of the KatastralniUzemi record
     * @return Optional of the KatastralniUzemi record
     */
    @GetMapping("/katastralniuzemi/{id}")
    public Optional<KatastralniUzemi> getKatastralniUzemi(@PathVariable("id") Long id) {
        return esService.findKatastralniUzemiById(id);
    }

    /**
     * Handle the /zsj endpoint
     * @return Iterable of the Zsj record
     */
    @GetMapping("/zsj")
    public Iterable<Zsj> listZsj() {return esService.findZsj();}

    /**
     * Handle the /zsj/{id} endpoint
     * @param id id of the Zsj record
     * @return Optional of the Zsj record
     */
    @GetMapping("/zsj/{id}")
    public Optional<Zsj> getZsj(@PathVariable("id") Long id) {
        return esService.findZsjById(id);
    }

    /**
     * Handle the /ulice endpoint
     * @return Iterable of the Ulice record
     */
    @GetMapping("/ulice")
    public Iterable<Ulice> listUlice() {return esService.findUlice();}

    /**
     * Handle the /ulice/{id} endpoint
     * @param id id of the Ulice record
     * @return Optionalof the Ulice record
     */
    @GetMapping("/ulice/{id}")
    public Optional<Ulice> getUlice(@PathVariable("id") Long id) {
        return esService.findUliceById(id);
    }

    /**
     * Handle the /parcely endpoint
     * @return Iterable of the Parcela record
     */
    @GetMapping("/parcely")
    public Iterable<Parcela> listParcely() {return esService.findParcely();}

    /**
     * Handle the /parcely/{id} endpoint
     * @param id id of the Parcela record
     * @return Optional of the Parcela record
     */
    @GetMapping("/parcela/{id}")
    public Optional<Parcela> getParcela(@PathVariable("id") Long id) {
        return esService.findParcelaById(id);
    }

    /**
     * Handle the /stavebniobjekty endpoint
     * @return Iterable of the StavebniObjekt record
     */
    @GetMapping("/stavebniobjekty")
    public Iterable<StavebniObjekt> listStavebniObjekty() {return esService.findStavebniObjekty();}

    /**
     * Handle the /stavebniobjekty/{id} endpoint
     * @param id id of the StavebniObjekt record
     * @return Optional of the StavebniObjekt record
     */
    @GetMapping("/stavebniobjekt/{id}")
    public Optional<StavebniObjekt> getStavebniobjekt(@PathVariable("id") Long id) {
        return esService.findStavebniObjektById(id);
    }

    /**
     * Handle the /adresnimista endpoint
     * @return Iterable of the AdresniMisto record
     */
    @GetMapping("/adresnimista")
    public Iterable<AdresniMisto> listAdresniMista() {return esService.findAdresniMista();}

    /**
     * Handle the /adresnimisto/{id} endpoint
     * @param id id of the AdresniMisto record
     * @return Optional of the AdresniMisto record
     */
    @GetMapping("/adresnimisto/{id}")
    public Optional<AdresniMisto> getAdresniMisto(@PathVariable("id") Long id) {
        return esService.findAdresniMistoById(id);
    }

    /**
     * Handle the /search endpoint
     * @param query query string
     * @return List of founded objects
     * @throws IOException if input/output error occurs
     */
    @GetMapping("/search")
    public List<Object> search(@RequestParam("query") String query) throws IOException {
        return esService.search(query);
    }

    /**
     * Handle the /okresy/search endpoint
     * @param query query for searching
     * @return List of Okres record
     * @throws IOException if input/output error occurs
     */
    @GetMapping("/okresy/search")
    public List<Okres> searchOkresy(@RequestParam("query") String query) throws IOException {
        return esService.searchOkresy(query);
    }

    /**
     * Handle the /obce/search endpoint
     * @param query query for searching
     * @return List of Obec record
     * @throws IOException if input/output error occurs
     */
    @GetMapping("/obce/search")
    public List<Obec> searchObce(@RequestParam("query") String query) throws IOException {
        return esService.searchObce(query);
    }

    /**
     * Handle the /castiobce/search endpoint
     * @param query query for searching
     * @return List of CastObce record
     * @throws IOException if input/output error occurs
     */
    @GetMapping("/castiobce/search")
    public List<CastObce> searchCastiObce(@RequestParam("query") String query) throws IOException {
        return esService.searchCastiObce(query);
    }

    /**
     * Handle the /ulice/search endpoint
     * @param query query for searching
     * @return List of Ulice record
     * @throws IOException if input/output error occurs
     */
    @GetMapping("/ulice/search")
    public List<Ulice> searchUlice(@RequestParam("query") String query) throws IOException {
        return esService.searchUlice(query);
    }

    /**
     * Handle the /adresnimista/search endpoint
     * @param query query for searching
     * @return List of AdresniMisto record
     * @throws IOException if input/output error occurs
     */
    @GetMapping("/adresnimista/search")
    public List<AdresniMisto> searchAdresniMista(@RequestParam("query") String query) throws IOException {
        return esService.searchAdresniMista(query);
    }
}
