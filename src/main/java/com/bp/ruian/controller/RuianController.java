package com.bp.ruian.controller;

import com.bp.ruian.record.Address;
import com.bp.ruian.services.EsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller
 * @author denisprn
 */
@RestController
@RequestMapping("/ruian")
public class RuianController {

    private final EsServiceImpl esServiceImpl;

    @Autowired
    public RuianController(EsServiceImpl esServiceImpl) {
        this.esServiceImpl = esServiceImpl;
    }

    @GetMapping(value = "/address/{id}", produces = "application/json")
    public ResponseEntity<Address> findAddressById(@PathVariable("id") Integer id) {
        Optional<Address> address = esServiceImpl.findAddressById(id);

        return address.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/search", produces = "application/json")
    public List<Address> findAddressesBySearchString(@RequestParam("query") String searchString) {
        return esServiceImpl.findAddressesBySearchString(searchString);
    }
}
