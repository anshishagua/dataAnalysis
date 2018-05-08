INSERT INTO t_data_type(`value`, `name`, `parent_id`, `description`)
VALUES ('整形', 'INTEGER', NULL, ''),
  ('长整形', 'BIGINT', NULL, ''),
  ('字符串', 'STRING', NULL, '');



INSERT INTO t_table(`name`, `alias`, `create_time`, `last_updated`,`description`)
    VALUES
      ('teacher', '教师', NOW(), NOW(), ''),
      ('student', '学生', NOW(), NOW(), ''),
      ('course', '课程', NOW(), NOW(), '')
;

INSERT INTO t_table_column(  `table_id`,
                             `type_id`,
                             `is_primary_key`,
                             `nullable`,
                             `name`,
                             `alias`,
                             `create_time`,
                             `last_updated`,
                             `description`)
VALUES
  (1, 2, TRUE, FALSE, 'id', '主键', NOW(), NOW(), ''),
  (1, 3, FALSE, FALSE, 'name', '姓名', NOW(), NOW(), ''),
  (1, 1, FALSE, FALSE, 'age', '年龄', NOW(), NOW(), '');


INSERT INTO `t_join_type`(`value`)
    VALUES ('INNER JOIN'), ('LEFT JOIN'), ('RIGHT JOIN');


INSERT INTO `t_table_relation`(`join_type`, `left_column_id`, `right_column_id`, `create_time`, `last_updated`)
    VALUES ('INNER JOIN', 4, 6, NOW(), NOW());


INSERT INTO `t_system_parameter`( `name`,
                                  `value`,
                                  `type_id`,
                                  `create_time`,
                                  `last_updated`)
    VALUES ('JAVA_HOME', '/usr/local/java', 3, NOW(), NOW());