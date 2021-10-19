create schema gifts;
create table if not exists gifts.certificate
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

create table if not exists gifts.tag
(
    id   bigserial    not null
        constraint tag_pk
            primary key,
    name varchar(100) not null
);


create table if not exists gifts.certificate_tag
(
    tag_id         bigint not null,
    certificate_id bigint not null
);

create unique index if not exists certificate_tag_tag_id_certificate_id_uindex
    on gifts.certificate_tag (tag_id, certificate_id);

insert into gifts.certificate (name, description, price, duration, create_date, last_update_date)
values ('certificate1', 'description1', 100.55, 10, '2021-10-10 12:00:00', '2021-10-10 12:30:00');
insert into gifts.certificate(name, description, price, duration, create_date, last_update_date)
values ('certificate2', 'description2', 100.55, 20, '2021-10-10 16:00:00', '2021-10-11 12:30:00');
insert into gifts.certificate (name, description, price, duration, create_date, last_update_date)
values ('certificate3', 'description3', 200.55, 30, '2021-10-10 10:00:00', '2021-10-10 11:30:00');
insert into gifts.certificate (name, description, price, duration, create_date, last_update_date)
values ('certificate4', 'description4', 150.55, 15, '2021-10-11 12:00:00', '2021-10-13 12:30:00');
insert into gifts.certificate (name, description, price, duration, create_date, last_update_date)
values ('certificate5', 'description5', 250.55, 10, '2021-10-15 18:00:00', '2021-10-16 12:30:00');

insert into gifts.tag (name)
values ('tag1');
insert into gifts.tag (name)
values ('tag2');
insert into gifts.tag (name)
values ('tag3');
insert into gifts.tag (name)
values ('tag4');
insert into gifts.tag (name)
values ('tag5');
insert into gifts.tag (name)
values ('tag6');
insert into gifts.tag (name)
values ('tag7');

insert into gifts.certificate_tag (tag_id, certificate_id)
values (7, 1);
insert into gifts.certificate_tag (tag_id, certificate_id)
values (4, 1);
insert into gifts.certificate_tag (tag_id, certificate_id)
values (5, 2);
insert into gifts.certificate_tag (tag_id, certificate_id)
values (4, 2);
insert into gifts.certificate_tag (tag_id, certificate_id)
values (7, 2);
insert into gifts.certificate_tag (tag_id, certificate_id)
values (2, 3);
insert into gifts.certificate_tag (tag_id, certificate_id)
values (6, 3);
insert into gifts.certificate_tag (tag_id, certificate_id)
values (3, 3);
insert into gifts.certificate_tag (tag_id, certificate_id)
values (3, 4);
insert into gifts.certificate_tag (tag_id, certificate_id)
values (2, 5);
insert into gifts.certificate_tag (tag_id, certificate_id)
values (4, 5);

