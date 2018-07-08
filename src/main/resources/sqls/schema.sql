DROP TABLE IF EXISTS `t_data_type`;
DROP TABLE IF EXISTS `t_data_load_history`;
DROP TABLE IF EXISTS `t_index`;
DROP TABLE IF EXISTS `t_index_dimension`;
DROP TABLE IF EXISTS `t_index_metric`;
DROP TABLE IF EXISTS `t_index_type`;
DROP TABLE IF EXISTS `t_join_type`;
DROP TABLE IF EXISTS `t_system_parameter`;
DROP TABLE IF EXISTS `t_table`;
DROP TABLE IF EXISTS `t_table_column`;
DROP TABLE IF EXISTS `t_table_relation`;
DROP TABLE IF EXISTS `t_tag`;
DROP TABLE IF EXISTS `t_tag_value`;
DROP TABLE IF EXISTS `t_task`;
DROP TABLE IF EXISTS `t_task_dependency`;
DROP TABLE IF EXISTS `t_task_execution`;
DROP TABLE IF EXISTS `t_user`;
DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_system_parameter`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '',
  `value` VARCHAR(255) DEFAULT NULL COMMENT '',
  `type_id` BIGINT NOT NULL,
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_data_type`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `value` varchar(100) NOT NULL DEFAULT '' COMMENT '类型英文名',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '类型中文名',
  `parent_id` BIGINT DEFAULT NULL COMMENT '父级类型',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO t_data_type(`value`, `name`, `parent_id`, `description`)
    VALUES ('整形', 'INTEGER', NULL, ''),
      ('长整形', 'BIGINT', NULL, '');

CREATE TABLE `t_table`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '表名英文名',
  `alias` varchar(128) NOT NULL DEFAULT '' COMMENT '表名中文名',
  `create_time` DATETIME NOT NULL COMMENT '父级类型',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) COMMENT '描述',
  `deleted` BIT(1) NOT NULL DEFAULT FALSE,
  `data_type` VARCHAR(20) NOT NULL DEFAULT 'BATCH',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE t_kafka_data_source
(
  `id` BIGINT NOT NULL PRIMARY KEY,
  `table_id` BIGINT NOT NULL,
  `bootstrap_servers` VARCHAR(50) NOT NULL,
  `source_topic`VARCHAR(100) NOT NULL,
  `dest_topic` VARCHAR(100) NOT NULL,
  `auto_commit` BOOLEAN NOT NULL DEFAULT TRUE,
  `key_serializer` VARCHAR(100) NOT NULL,
  `key_deserializer` VARCHAR(100) NOT NULL,
  `value_serializer` VARCHAR(100) NOT NULL,
  `value_deserializer` VARCHAR(100) NOT NULL,
  `auto_offset_reset` VARCHAR(20) NOT NULL,
  `consumer_group` VARCHAR(100) NOT NULL
);

CREATE TABLE `t_table_column`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `table_id` BIGINT NOT NULL COMMENT '',
  `type_id` BIGINT NOT NULL COMMENT '',
  `is_primary_key` BIT(1) NOT NULL DEFAULT FALSE,
  `nullable` BIT(1) NOT NULL DEFAULT TRUE,
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '表名英文名',
  `alias` varchar(128) NOT NULL DEFAULT '' COMMENT '表名中文名',
  `create_time` DATETIME NOT NULL COMMENT '父级类型',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_join_type`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(16) NOT NULL COMMENT '',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_table_relation`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `join_type` VARCHAR(16) NOT NULL COMMENT '',
  `left_column_id` BIGINT NOT NULL COMMENT '',
  `right_column_id` BIGINT NOT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_tag`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NOT NULL COMMENT '',
  `table_id` BIGINT NOT NULL COMMENT '',
  `tag_type` VARCHAR(64) NOT NULL DEFAULT 'STANDARD',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  `sql_generate_result` TEXT COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_tag_value`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `tag_id` BIGINT NOT NULL,
  `value` VARCHAR(128) NOT NULL COMMENT '',
  `order` INTEGER NOT NULL COMMENT '',
  `filter_condition` TEXT DEFAULT NULL COMMENT '',
  `compute_condition` TEXT DEFAULT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_tag_group`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(128) NOT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_tag_group_tags`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `group_id` BIGINT NOT NULL COMMENT '',
  `tag_id` BIGINT NOT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_index_type`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(16) NOT NULL DEFAULT 'BASE' COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_index`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL COMMENT '',
  `type` VARCHAR(16) NOT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  `sql_generate_result` TEXT COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_index_dimension`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `index_id` BIGINT NOT NULL COMMENT '',
  `order` INTEGER NOT NULL COMMENT '',
  `name` VARCHAR(255) NOT NULL COMMENT '',
  `expression` TEXT NOT NULL COMMENT '',
  `type_id` BIGINT NOT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_index_metric`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `index_id` BIGINT NOT NULL COMMENT '',
  `order` INTEGER NOT NULL COMMENT '',
  `name` VARCHAR(255) NOT NULL COMMENT '',
  `expression` TEXT NOT NULL COMMENT '',
  `type_id` BIGINT NOT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_tag_upload_log`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `tag_id` BIGINT NOT NULL COMMENT '',
  `path` VARCHAR(255) NOT NULL COMMENT '',
  `file_size` BIGINT NOT NULL COMMENT '大小以KB描述',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_data_load_history`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `table_id` BIGINT NOT NULL COMMENT '',
  `path` VARCHAR(255) NOT NULL COMMENT '',
  `data_size` BIGINT NOT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_task`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `object_id` BIGINT NOT NULL COMMENT '',
  `resources` INTEGER NOT NULL COMMENT '',
  `task_type` VARCHAR(25) NOT NULL COMMENT '',
  `cron_expression` VARCHAR(32) NOT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_task_dependency`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `task_id` BIGINT NOT NULL,
  `dependent_task_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_task_execution`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `task_id` BIGINT NOT NULL COMMENT '',
  `locks` INTEGER NOT NULL COMMENT '',
  `execute_date` DATETIME NOT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `start_time` DATETIME DEFAULT NULL COMMENT '',
  `end_time` DATETIME DEFAULT NULL COMMENT '',
  `status` VARCHAR(32) NOT NULL COMMENT '',
  `execution_seconds` DOUBLE NOT NULL DEFAULT 0 COMMENT '',
  `execute_sqls` TEXT COMMENT '',
  `error_message` TEXT COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_object_reference`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `object_id` BIGINT NOT NULL,
  `object_type` VARCHAR(16) NOT NULL,
  `object_name` VARCHAR(255) NOT NULL,
  `ref_object_id` BIGINT NOT NULL,
  `ref_object_type` VARCHAR(16) NOT NULL,
  `ref_object_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_user`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL COMMENT '',
  `password` VARCHAR(255) NOT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `t_user_role`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL COMMENT '',
  `role` VARCHAR(255) NOT NULL COMMENT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into `test`.`t_data_type` (`value`, `name`, `parent_id`, `description`, `deleted`) values ( 'INT', '整形', null, '', b'0');
insert into `test`.`t_data_type` (`value`, `name`, `parent_id`, `description`, `deleted`) values ( 'BIGINT', '长整形', null, '', b'0');
insert into `test`.`t_data_type` (`value`, `name`, `parent_id`, `description`, `deleted`) values ( 'STRING', '字符串', null, '', b'0');
insert into `test`.`t_data_type` (`value`, `name`, `parent_id`, `description`, `deleted`) values ( 'FLOAT', '浮点型', null, '', b'0');
insert into `test`.`t_data_type` (`value`, `name`, `parent_id`, `description`, `deleted`) values ( 'DOUBLE', '双精度浮点型', null, '', b'0');
insert into `test`.`t_data_type` (`value`, `name`, `parent_id`, `description`, `deleted`) values ( 'BOOLEAN', '布尔型', null, '', b'0');
insert into `test`.`t_data_type` (`value`, `name`, `parent_id`, `description`, `deleted`) values ( 'DATE', '日期型', null, '', b'0');



insert into `test`.`t_join_type` ( `value`) values ('INNER JOIN');
insert into `test`.`t_join_type` ( `value`) values ('LEFT JOIN');
insert into `test`.`t_join_type` ( `value`) values ('RIGHT JOIN');