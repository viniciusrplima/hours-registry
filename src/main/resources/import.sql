insert into task (id, done, resume) values (1, false, 'configurar login do sistema');
insert into task (id, done, resume) values (2, false, 'adicionar recurso restaurante');
insert into task (id, done, resume) values (3, true, 'conserta bug na paginacao');

insert into effort (id, initial, termination, task_id) values (1, '10/11/2021 15:20', null, 1);
insert into effort (id, initial, termination, task_id) values (2, '10/11/2021 9:20', '10/11/2021 12:10', 1);
insert into effort (id, initial, termination, task_id) values (3, '10/11/2021 9:20', '10/11/2021 12:10', 2);
insert into effort (id, initial, termination, task_id) values (3, '10/11/2021 9:20', null, 2);

