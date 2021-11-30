package com.bp.RUIAN.controllers;

import com.bp.RUIAN.entities.Address;
import com.bp.RUIAN.services.EsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


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

    @GetMapping("/address/{id}")
    public Optional<Address> findAddressById(@PathVariable("id") Integer id) {
        return esService.findAddressById(id);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
