INSERT INTO task
(`id`, `description`, `is_reminder_set`, `is_task_open`, `created_on`)
VALUES (111, 'first test todo', false, false, CURRENT_TIME());

INSERT INTO task
(`id`, `description`, `is_reminder_set`, `is_task_open`, `created_on`)
VALUES (112, 'second test todo', true, false, CURRENT_TIME());

INSERT INTO task
(`id`, `description`, `is_reminder_set`, `is_task_open`, `created_on`)
VALUES (113, 'third test todo', true, true, CURRENT_TIME());