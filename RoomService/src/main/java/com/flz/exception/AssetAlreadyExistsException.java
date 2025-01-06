package com.flz.exception;


import java.io.Serial;

public final class AssetAlreadyExistsException extends AbstractAlreadyExistsException {

    @Serial
    private static final long serialVersionUID = -2439099375389237153L;

    public AssetAlreadyExistsException(String message) {

        super(message + " -> this Asset already exists !");
    }
}
