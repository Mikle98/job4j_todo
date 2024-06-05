create table todo_user (
    id SERIAL primary key,
    name varchar unique not null,
    login varchar not null,
    password varchar not null
)