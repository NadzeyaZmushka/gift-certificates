create table if not exists certificate
(
    id               bigserial     not null
        constraint certificate_pk
            primary key,
    name             varchar(100)  not null,
    description      varchar(500)  not null,
    price            numeric(5, 2) not null,
    duration         integer       not null,
    create_date      timestamp     not null,
    last_update_date timestamp     not null
);

alter table certificate
    owner to postgres;

create table if not exists tag
(
    id   bigserial    not null
        constraint tag_pk
            primary key,
    name varchar(100) not null
);

alter table tag
    owner to postgres;

create table if not exists certificate_tag
(
    tag_id         bigint not null
        constraint certificate_tag_tag_id_fk
            references tag
            on update cascade on delete cascade,
    certificate_id bigint not null
        constraint certificate_tag_certificate_id_fk
            references certificate
            on update cascade on delete cascade
);

alter table certificate_tag
    owner to postgres;

create unique index if not exists certificate_tag_tag_id_certificate_id_uindex
    on certificate_tag (tag_id, certificate_id);

insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('certificate1', 'description1', 100.55, 10, '2021-10-10 12:00:00', '2021-10-10 12:30:00');
insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('certificate2', 'description2', 100.55, 20, '2021-10-10 16:00:00', '2021-10-11 12:30:00');
insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('certificate3', 'description3', 200.55, 30, '2021-10-10 10:00:00', '2021-10-10 11:30:00');
insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('certificate4', 'description4', 150.55, 15, '2021-10-11 12:00:00', '2021-10-13 12:30:00');
insert into certificate (name, description, price, duration, create_date, last_update_date)
values ('certificate5', 'description5', 250.55, 10, '2021-10-15 18:00:00', '2021-10-16 12:30:00');

insert into tag (name)
values ('tag1');
insert into tag (name)
values ('tag2');
insert into tag (name)
values ('tag3');
insert into tag (name)
values ('tag4');
insert into tag (name)
values ('tag5');
insert into tag (name)
values ('tag6');
insert into tag (name)
values ('tag7');
insert into tag (name)
values ('tag8');
insert into tag (name)
values ('tag9');
insert into tag (name)
values ('tag10');
insert into tag (name)
values ('tag11');

insert into certificate_tag (tag_id, certificate_id)
values (1, 2);
insert into certificate_tag (tag_id, certificate_id)
values (1, 3);
insert into certificate_tag (tag_id, certificate_id)
values (1, 4);
insert into certificate_tag (tag_id, certificate_id)
values (2, 5);
insert into certificate_tag (tag_id, certificate_id)
values (2, 6);
insert into certificate_tag (tag_id, certificate_id)
values (2, 7);
insert into certificate_tag (tag_id, certificate_id)
values (3, 8);
insert into certificate_tag (tag_id, certificate_id)
values (3, 9);
insert into certificate_tag (tag_id, certificate_id)
values (3, 10);
insert into certificate_tag (tag_id, certificate_id)
values (4, 11);
insert into certificate_tag (tag_id, certificate_id)
values (5, 2);

