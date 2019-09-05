USE tasks;
-- Insert test data
insert into task (task_name, task_due, task_completed, task_cancelled) values ("homework 1", curdate(), true, false);
insert into task (task_name, task_due, task_completed, task_cancelled) ("homework 2", curdate() + interval 1 day, false, true);
insert into task (task_name, task_due, task_completed, task_cancelled) ("homework 3", curdate() + interval 2 day, false, false);
insert into task (task_name, task_due, task_completed, task_cancelled) ("homework 4", curdate() + interval 7 day, false, false);
insert into task (task_name, task_due, task_completed, task_cancelled) ("get groceries", null, false, false);
insert into task (task_name, task_due, task_completed, task_cancelled) ("call mom", null, false, false);
insert into task (task_name, task_due, task_completed, task_cancelled) ("email professor", null, false, false);

insert into tag (tag_name) values ("homework");
insert into tag (tag_name) values ("personal");
insert into tag (tag_name) values ("school");

insert into tag_ref (tag_id, task_id) values (1, 1);
insert into tag_ref (tag_id, task_id) values (3, 1);
insert into tag_ref (tag_id, task_id) values (1, 2);
insert into tag_ref (tag_id, task_id) values (3, 2);
insert into tag_ref (tag_id, task_id) values (1, 3);
insert into tag_ref (tag_id, task_id) values (3, 3);
insert into tag_ref (tag_id, task_id) values (1, 4);
insert into tag_ref (tag_id, task_id) values (3, 4);
insert into tag_ref (tag_id, task_id) values (2, 5);
insert into tag_ref (tag_id, task_id) values (2, 6);
insert into tag_ref (tag_id, task_id) values (3, 7);
