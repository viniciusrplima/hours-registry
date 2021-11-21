set session_replication_role = replica;

delete from task;
delete from effort;

insert into task (id, done, resume, user_id) values (1001, false, 'configurar login do sistema', 1);
insert into task (id, done, resume, user_id) values (1002, false, 'adicionar recurso restaurante', 1);
insert into task (id, done, resume, user_id) values (1003, true, 'conserta bug na paginacao', 1);

insert into effort (id, initial, termination, task_id) values (1001, current_timestamp, null, 1001);
insert into effort (id, initial, termination, task_id) values (1002, current_timestamp, current_timestamp, 1001);
insert into effort (id, initial, termination, task_id) values (1003, current_timestamp, current_timestamp, 1002);
insert into effort (id, initial, termination, task_id) values (1004, current_timestamp, null, 1002);

set session_replication_role = default;