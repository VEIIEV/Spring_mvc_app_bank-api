create table "user"
(
    id      bigint           not null
        primary key,
    balance double precision not null
);

alter table "user"
    owner to postgres;

INSERT INTO bank."user" (id, balance) VALUES (1, 643535);
INSERT INTO bank."user" (id, balance) VALUES (2, 4352353);
