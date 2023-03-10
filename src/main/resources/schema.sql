create table project(
    id BIGINT not null,
    name varchar(255) not null,
    description varchar(255) not null,
    category varchar(255) not null,
    impressions int not null,
    likes int not null,
    primary key (id)
);