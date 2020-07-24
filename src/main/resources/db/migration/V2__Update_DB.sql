drop table project;
create table project (
    id int8 not null,
    project_name varchar(255),
    project_identifier varchar(255),
    description varchar(255),
    start_date timestamp,
    end_date timestamp,
    created_at timestamp,
    updated_at timestamp,
    primary key (id)
);