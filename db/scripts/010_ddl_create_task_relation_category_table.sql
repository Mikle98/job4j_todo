create table task_relation_category(
    id SERIAL primary key,
    task_id int not null references tasks(id),
    categories_id int not null references categories(id),
    UNIQUE(task_id, categories_id)
);