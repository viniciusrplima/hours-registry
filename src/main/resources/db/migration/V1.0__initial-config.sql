create table auth_user (
	id int8 generated by default as identity,
	github_token varchar(255),
	password varchar(255),
	username varchar(255),

	primary key (id)
);

create table effort (
	id int8 generated by default as identity,
	initial timestamp,
	termination timestamp,
	task_id int8,

	primary key (id)
);

create table role (
	id int8 generated by default as identity,
	name varchar(255),

	primary key (id)
);

create table task (
	id int8 generated by default as identity,
	done boolean,
	resume varchar(255),
	user_id int8 not null,

	primary key (id)
);

create table user_roles (
	user_id int8 not null,
	role_id int8 not null
);

alter table if exists auth_user
add constraint unique_username unique (username);

alter table if exists effort
add constraint fk_effort_task foreign key (task_id) references task;

alter table if exists task
add constraint fk_task_user foreign key (user_id) references auth_user;

alter table if exists user_roles
add constraint fk_join_role_user foreign key (role_id) references role;

alter table if exists user_roles
add constraint fk_join_user_role foreign key (user_id) references auth_user;
