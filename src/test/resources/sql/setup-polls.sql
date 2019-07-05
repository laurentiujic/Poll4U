-- insert a basic poll
insert into polls (
    id,
    created_at,
    created_by,
    expiration,
    question,
    updated_at,
    updated_by)
values (
    1,
    '2019-07-05T08:10:46.735Z',
    1,
    '2019-07-05T08:10:46.735Z',
    'What''s your favorite color?',
    '2019-07-05T08:10:46.735Z',
    1)
;

-- insert related choice
insert into choices (
    id,
    description,
    poll_id)
values (
    1,
    'Green',
    1)
;

insert into choices (
    id,
    description,
    poll_id)
values (
    2,
    'Red',
    1)
;



