package com.flz.exception;

import java.io.Serial;

public final class AssetNotFoundException extends AbstractNotFoundException {

    @Serial
    private static final long serialVersionUID = -5692997481627512574L;

    public AssetNotFoundException(Long id) {

        super("This Asset not found ID = " + id);
    }
}
