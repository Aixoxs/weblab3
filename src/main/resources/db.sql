create table if not exists points
(
    id           serial not null
        constraint points_pkey
            primary key,
    inside_area  boolean,
    radius       double precision,
    x_coordinate double precision,
    y_coordinate double precision
);