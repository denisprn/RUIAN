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

    @GetMapping("/")
    public @ResponseBody String greeting() {
        return "Hello, this is a RUIAN REST API!";
    }

    @GetMapping("/stat/{id}")
    public Optional<Stat> getStat(@PathVariable("id") Long id) {
        return esService.findStatById(id);
    }

    @GetMapping("/regionsoudrznosti/{id}")
    public Optional<RegionSoudrznosti> getRs(@PathVariable("id") Long id) {
        return esService.findRsById(id);
    }

    @GetMapping("/vusc/{id}")
    public Optional<Vusc> getVusc(@PathVariable("id") Long id) {
        return esService.findVuscById(id);
    }

    @GetMapping("/okres/{id}")
    public Optional<Okres> getOkres(@PathVariable("id") Long id) {
        return esService.findOkresById(id);
    }

    @GetMapping("/orp/{id}")
    public Optional<Orp> getOrp(@PathVariable("id") Long id) {
        return esService.findOrpById(id);
    }

    @GetMapping("/pou/{id}")
    public Optional<Pou> getPou(@PathVariable("id") Long id) {
        return esService.findPouById(id);
    }

    @GetMapping("/obec/{id}")
    public Optional<Obec> getObec(@PathVariable("id") Long id) {
        return esService.findObecById(id);
    }

    @GetMapping("/spravniobvod/{id}")
    public Optional<SpravniObvod> getSO(@PathVariable("id") Long id) {
        return esService.findSOById(id);
    }

    @GetMapping("/mop/{id}")
    public Optional<Mop> getMop(@PathVariable("id") Long id) {
        return esService.findMopById(id);
    }

    @GetMapping("/momc/{id}")
    public Optional<Momc> getMomc(@PathVariable("id") Long id) {
        return esService.findMomcById(id);
    }

    @GetMapping("/castobce/{id}")
    public Optional<CastObce> getCastObce(@PathVariable("id") Long id) {
        return esService.findCastObceById(id);
    }

    @GetMapping("/katastralniuzemi/{id}")
    public Optional<KatastralniUzemi> getKatastralniUzemi(@PathVariable("id") Long id) {
        return esService.findKatastralniUzemiById(id);
    }

    @GetMapping("/zsj/{id}")
    public Optional<Zsj> getZsj(@PathVariable("id") Long id) {
        return esService.findZsjById(id);
    }

    @GetMapping("/ulice/{id}")
    public Optional<Ulice> getUlice(@PathVariable("id") Long id) {
        return esService.findUliceById(id);
    }

    @GetMapping("/parcela/{id}")
    public Optional<Parcela> getParcela(@PathVariable("id") Long id) {
        return esService.findParcelaById(id);
    }

    @GetMapping("/stavebniobjekt/{id}")
    public Optional<StavebniObjekt> getStavebniobjekt(@PathVariable("id") Long id) {
        return esService.findStavebniObjektById(id);
    }

    @GetMapping("/adresnimisto/{id}")
    public Optional<AdresniMisto> getAdresniMisto(@PathVariable("id") Long id) {
        return esService.findAdresniMistoById(id);
    }

    @GetMapping("/search")
    public List<Object> search(@RequestParam("query") String query) throws IOException {
        return esService.search(query);
    }
}
