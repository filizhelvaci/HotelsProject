ALTER TABLE rs_asset
    ADD CONSTRAINT unique_name_constraint
        UNIQUE (name);