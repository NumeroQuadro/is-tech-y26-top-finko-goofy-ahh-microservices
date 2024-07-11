create table if not exists cats_friends
(
    cat_id    serial  not null,
    friend_id integer not null,
    primary key (cat_id, friend_id)
);
create table if not exists cats_main_info
(
    cat_birthday date         not null,
    cat_id       serial       not null,
    cat_breed    varchar(255) not null,
    cat_color    varchar(255) not null check (cat_color in ('white', 'semi_color', 'black', 'grey', 'undefined')),
    cat_name     varchar(255) unique,
    primary key (cat_id)
);

alter table if exists cats_friends
    add constraint cats_friends_cat_id_constraint foreign key (cat_id) references cats_main_info on delete cascade;
alter table if exists cats_friends
    add constraint cats_friends_friend_id_constraint foreign key (friend_id) references cats_main_info on delete cascade;
