
create table clients
(
    id               bigint auto_increment,
    name             varchar(255),
    taj_number       varchar(9) unique,
    termination_date date,
    primary key (id)
);