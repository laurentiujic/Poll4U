-- insert minimal security roles
insert into roles (
    id,
    name)
values (
    1,
    'ROLE_ADMIN')
;

insert into roles (
    id,
    name)
values (
    2,
    'ROLE_USER')
;

-- insert a basic user account
insert into users (
    id,
    email,
    password,
    username)
values (
    1,
    'user@web.de',
    'Abc123',
    'user')
;

-- map security role to user
insert into user_roles (
    role_id,
    user_id)
values (
    2,
    1)
;





