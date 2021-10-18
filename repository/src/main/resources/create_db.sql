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
    tag_id         bigint not null
        constraint certificate_tag_tag_id_fk
            references gifts.tag
            on update cascade on delete cascade,
    certificate_id bigint not null
        constraint certificate_tag_certificate_id_fk
            references gifts.certificate
            on update cascade on delete cascade
);

create unique index if not exists certificate_tag_tag_id_certificate_id_uindex
    on gifts.certificate_tag (tag_id, certificate_id);

insert into gifts.certificate (name, description, price, duration, create_date, last_update_date)
values ('certificate1', 'description1', 100.55, 10, '2021-10-10 12:00:00', '2021-10-10 12:30:00');
insert into gifts.certificate(name, description, price, duration, create_date, last_update_date)
values ('certificate2', 'description2', 100.55, 20, '2021-10-10 16:00:00', '2021-10-11 12:30:00');
insert into gifts.certificate (name, description, price, duration, create_date, last_update_date)
values ('certificate3', 'description3', 200.55, 30, '2021-10-10 10:00:00', '2021-10-10 11:30:00');

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


-- insert into gifts.certificate_tag (tag_id, certificate_id)
-- values (1, 2);
-- insert into gifts.certificate_tag (tag_id, certificate_id)
-- values (1, 3);
-- insert into gifts.certificate_tag (tag_id, certificate_id)
-- values (1, 4);
-- insert into gifts.certificate_tag (tag_id, certificate_id)
-- values (2, 5);
-- insert into gifts.certificate_tag (tag_id, certificate_id)
-- values (2, 6);
-- insert into gifts.certificate_tag (tag_id, certificate_id)
-- values (2, 7);
-- insert into gifts.certificate_tag (tag_id, certificate_id)
-- values (3, 8);
-- insert into gifts.certificate_tag (tag_id, certificate_id)
-- values (3, 9);
-- insert into gifts.certificate_tag (tag_id, certificate_id)
-- values (3, 10);
-- insert into gifts.certificate_tag (tag_id, certificate_id)
-- values (4, 11);
-- insert into gifts.certificate_tag (tag_id, certificate_id)
-- values (5, 2);

