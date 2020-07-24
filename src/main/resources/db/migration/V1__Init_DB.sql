create table project (
    id int8 not null,
    projectName varchar(255),
    projectIdentifier varchar(255),
    description varchar(255),
    start_date timestamp,
    end_date timestamp,
    created_at timestamp,
    updated_at timestamp,
    primary key (id)
);