
create table measurements
(
    id               bigint auto_increment,
    type             varchar (255),
    measurement_time datetime,
    result           double,
    client_id        bigint,
    primary key (id),
    foreign key (client_id) REFERENCES clients(id)
);