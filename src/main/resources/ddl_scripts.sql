create table users
(
    id         bigserial primary key,
    username   varchar(128) unique,
    firstname  varchar(128),
    lastname   varchar(128),
    birth_date date,
    role       varchar(32),
    info       JSONB,
    company_id int references company (id)
);
drop table users;


create table company
(
    id   serial primary key,
    name varchar(64) not null unique
);

create table profile
(
    id       bigserial primary key,
    user_id  bigint not null unique references users (id),
    street   varchar(128),
    language char(2)
);

drop table profile;



create table chat
(
    id   bigserial primary key,
    name varchar(64) not null unique
);

create table users_chat
(
    id         bigserial primary key,
    user_id    bigint references users (id),
    chat_id    bigint references chat (id),
    created_at timestamp    not null,
    created_by varchar(128) not null,
    UNIQUE (user_id, chat_id)
);

drop table users_chat;









