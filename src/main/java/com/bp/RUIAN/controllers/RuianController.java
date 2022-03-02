package com.bp.RUIAN.controllers;

import com.bp.RUIAN.entities.Address;
import com.bp.RUIAN.exceptions.AddressNotFoundException;
import com.bp.RUIAN.services.EsService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/ruian")
public class RuianController {
    @Autowired
    private EsService esService;

    @GetMapping("/search")
    public List<String> search(@RequestParam("query") String query) throws IOException {
        return esService.search(query);
    }

    @GetMapping("/address/{id}")
    public Optional<Address> findAddressById(@PathVariable("id") Integer id) {
        try {
            if (esService.findAddressById(id).isEmpty()) {
                throw new AddressNotFoundException("Address with id " + id + " not found");
            }

            return esService.findAddressById(id);
        } catch (AddressNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getReason(), ex);
        }
    }
}
