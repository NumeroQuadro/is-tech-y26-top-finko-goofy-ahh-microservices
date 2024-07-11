create table if not exists owners
(
    user_id        int       not null,
    owner_name     varchar(255) not null unique,
    owner_birthday date         not null,
    primary key (user_id)
);

create table if not exists owners_with_cats
(
    cat_id  integer not null,
    user_id integer not null,
    primary key (cat_id, user_id)
);
alter table if exists owners_with_cats
    add constraint owners_with_cats_owner_id_constraint foreign key (user_id) references owners on delete cascade;
