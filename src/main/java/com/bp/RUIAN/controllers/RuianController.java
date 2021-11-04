package com.bp.RUIAN.controllers;

import com.bp.RUIAN.entities.AdresniMisto;
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

    @GetMapping("/search")
    public List<String> search(@RequestParam("query") String query) throws IOException {
        return esService.search(query);
    }

    @GetMapping("/adresnimisto/{id}")
    public Optional<AdresniMisto> findAdresniMistoById(@PathVariable("id") Long id) {
        return esService.findAdresniMistoById(id);
    }
}
