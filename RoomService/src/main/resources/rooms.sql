CREATE TABLE ROOMS_APP (
                                        ID SERIAL PRIMARY KEY,
                                        TYPE_ID BIGINT NOT NULL,
                                        NUMBER SMALLINT NOT NULL CHECK (NUMBER >= WHICH_FLOOR * 100),
                                        WHICH_FLOOR SMALLINT NOT NULL,
                                        VIEW VARCHAR(30) NOT NULL,
                                        PRICE REAL,
                                        ASSETS_IDS TEXT[],
                                        NUMBER_OF_PEOPLE_STAYING SMALLINT CHECK ( NUMBER_OF_PEOPLE_STAYING BETWEEN 0 AND 15),
                                        CURRENT_STATUS_OF_THE_ROOM VARCHAR(25) NOT NULL CHECK (CURRENT_STATUS_OF_THE_ROOM IN ('EMPTY', 'FULL', 'RESERVE', 'IN_MAINTENANCE'))
);

ALTER TABLE ROOMS_APP
    ADD FOREIGN KEY (TYPE_ID) REFERENCES ROOM_TYPES(ID);



CREATE TABLE ASSETS (
                                    ID SERIAL PRIMARY KEY,
                                    NAME VARCHAR(50) NOT NULL,
                                    FEE REAL NOT NULL,
                                    BRAND VARCHAR(50) NOT NULL,
                                    COMPANY VARCHAR(50) NOT NULL,
                                    UNIT_PRICE REAL NOT NULL,
                                    DATE_RECEIVED DATE NOT NULL CHECK (DATE_RECEIVED <= CURRENT_DATE),
                                    COLOR VARCHAR(30)
);



CREATE TABLE ROOMS_TYPES_APP (
                                    ID SERIAL PRIMARY KEY,
                                    NAME VARCHAR(50) NOT NULL UNIQUE,
                                    FEE REAL NOT NULL,
                                    ASSETS TEXT[] NOT NULL,
                                    MAX_PEOPLE_TO_STAY SMALLINT NOT NULL CHECK (MAX_PEOPLE_TO_STAY BETWEEN 1 AND 15),
                                    SIZE REAL NOT NULL CHECK (SIZE > 0)
    );




CREATE TABLE INVENTORY (
                                    ID BIGINT PRIMARY KEY,
                                    ASSETS_NAME VARCHAR(50) NOT NULL,
                                    TOTAL_COUNT SMALLINT NOT NULL CHECK (TOTAL_COUNT > 0),
                                    QUANTITY_IN_STOCK SMALLINT NOT NULL,
                                    DESCRIPTION VARCHAR(200)
);

ALTER TABLE INVENTORY
    ADD FOREIGN KEY (ASSETS_NAME) REFERENCES ASSETS(NAME);