--- USER ---
DROP TABLE IF EXISTS public.users CASCADE;
CREATE TABLE public.users
(
    id              bigint NOT NULL,
    name            varchar(50) NOT NULL,
    email           varchar(100) NOT NULL,
    phone_number    bigint NOT NULL,
    address         varchar(255),

    CONSTRAINT user_pkey PRIMARY KEY (id)
);


--- DRIVER ---
DROP TABLE IF EXISTS public.drivers CASCADE;
CREATE TABLE public.drivers
(
    id              bigint NOT NULL,
    name            varchar(50) NOT NULL,
    email           varchar(60) NOT NULL,
    phone_number    bigint NOT NULL,
    rating          real NOT NULL,

    CONSTRAINT driver_pkey PRIMARY KEY (id),
    CONSTRAINT email_key UNIQUE (email),
    CONSTRAINT phone_key UNIQUE (phone_number)
);


-- VEHICLE ---
DROP TABLE IF EXISTS public.vehicles;
CREATE TABLE public.vehicles
(
    id                  bigint NOT NULL,
    name                varchar(50) NOT NULL,
    brand               varchar(50) NOT NULL,
    vin                 varchar(17) NOT NULL,
    license_plate       varchar(8) NOT NULL,
    color               varchar(50) NOT NULL,
    register_date       date NOT NULL,
    created_date        timestamp NOT NULL,
    driver_id           bigint NOT NULL,
    state               varchar(50) NOT NULL,

    CONSTRAINT vehicle_id PRIMARY KEY (id),
    CONSTRAINT vin_key UNIQUE (vin),
    CONSTRAINT license_plate_key UNIQUE (license_plate),
    CONSTRAINT driver_fk FOREIGN KEY (driver_id)
            REFERENCES public.drivers (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
);



-- VEHICLE ---
DROP TABLE IF EXISTS public.trips;
CREATE TABLE public.trips
(
    id                  bigint NOT NULL,
    price               numeric(20, 2) NOT NULL,
    start_lat           numeric(10,10) NOT NULL,
    start_long          numeric(10,10) NOT NULL,
    end_lat             numeric(10,10) NOT NULL,
    end_long            numeric(10,10) NOT NULL,
    user_id             bigint NOT NULL,
    driver_id           bigint,

    CONSTRAINT trips_id PRIMARY KEY (id),
    CONSTRAINT user_fk FOREIGN KEY (user_id)
            REFERENCES public.users (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION,
    CONSTRAINT driver_fk FOREIGN KEY (driver_id)
                REFERENCES public.drivers (id) MATCH SIMPLE
                ON UPDATE NO ACTION
                ON DELETE NO ACTION
);

-- PAYMENTS ---
DROP TABLE IF EXISTS public.payments;
CREATE TABLE public.payments
(
    id                  bigint NOT NULL,
    paid_price          numeric(20, 2) NOT NULL,
    start_initiation    timestamp NOT NULL,
    end_confirmation    timestamp NOT NULL,

    CONSTRAINT payments_pkey PRIMARY KEY (id)
);









