package com.flz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AssetNotFoundException extends RuntimeException {

    public AssetNotFoundException() {
        super();
    }

    public AssetNotFoundException(Long id) {
        super("Asset not found ID" + id);
    }

}
