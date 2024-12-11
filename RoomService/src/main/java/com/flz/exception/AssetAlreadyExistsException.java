package com.flz.exception;

import java.io.Serial;

public final class AssetAlreadyExistsException extends AbstractAlreadyExistsException {

    @Serial
    private static final long serialVersionUID = 1L;

    public AssetAlreadyExistsException(String message) {

        super(message + " -> this Asset already exists !");
    }
}
