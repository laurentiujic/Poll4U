-- insert minimal vote for poll choice
insert into votes (
    id,
    poll_id,
    choice_id,
    user_id)
values (
    1,
    1,
    2,
    1)
;