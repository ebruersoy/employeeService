
create sequence seq_department increment by 1 minvalue 1;

create table department(
  id bigint primary key not null ,
  name varchar not null
);

create table employee(
  uuid uuid primary key not null ,
  email varchar not null ,
  full_name varchar not null ,
  birthday date not null ,
  department_id bigint not null
);
