create table operation_history
(
    id             bigint not null
        primary key,
    user_id        bigint not null
        constraint fk6lxpducsid8hmaisy5lpdqs3r
            references "user",
    operation_type integer,
    summary        double precision,
    date           timestamp
);

alter table operation_history
    owner to postgres;

INSERT INTO bank.operation_history (id, date, operation_type, summary, user_id) VALUES (1, '2022-11-13 15:26:56.832220', 1, 7, 1);
INSERT INTO bank.operation_history (id, date, operation_type, summary, user_id) VALUES (2, '2022-11-13 15:26:57.429069', 1, 7, 1);
INSERT INTO bank.operation_history (id, date, operation_type, summary, user_id) VALUES (3, '2022-11-13 15:26:57.930567', 1, 7, 1);
INSERT INTO bank.operation_history (id, date, operation_type, summary, user_id) VALUES (4, '2022-11-13 15:27:00.409606', 1, 7, 1);
INSERT INTO bank.operation_history (id, date, operation_type, summary, user_id) VALUES (5, '2022-11-13 16:40:08.144713', 2, 2323, 2);
INSERT INTO bank.operation_history (id, date, operation_type, summary, user_id) VALUES (6, '2022-11-13 16:40:18.596430', 1, 3213, 2);
INSERT INTO bank.operation_history (id, date, operation_type, summary, user_id) VALUES (7, '2022-11-13 16:42:14.670487', 1, 3213, 2);
