ALTER TABLE rs_asset
DROP
CONSTRAINT unique_name_constraint;

ALTER TABLE rs_asset
    ADD CONSTRAINT u__rs_asset__name
        UNIQUE (name);