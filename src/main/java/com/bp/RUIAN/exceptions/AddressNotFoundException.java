package com.bp.RUIAN.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Address Not Found")
public class AddressNotFoundException extends ResponseStatusException {
    public AddressNotFoundException(String message){
        super(HttpStatus.NOT_FOUND, message);
    }
}
