create table if not exists users
(
    user_id  serial       not null,
    username varchar(100) unique not null,
    password text         not null,
    role     varchar(20)  not null,
    primary key (user_id)
);