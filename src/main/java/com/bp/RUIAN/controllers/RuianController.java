package com.bp.RUIAN.controllers;

import com.bp.RUIAN.services.EsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/{id}")
    public List<Object> getObjectsById(@PathVariable("id") Long id) {
        return esService.findById(id);
    }

    @GetMapping("/search")
    public List<Object> search(@RequestParam("query") String query) throws IOException {
        return esService.search(query);
    }
}
