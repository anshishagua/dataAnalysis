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
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_table_column`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `table_id` BIGINT NOT NULL COMMENT '',
  `type_id` BIGINT NOT NULL COMMENT '',
  `order` INTEGER NOT NULL COMMENT '',
  `is_primary_key` BIT(1) NOT NULL DEFAULT FALSE,
  `nullable` BIT(1) NOT NULL DEFAULT TRUE,
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '表名英文名',
  `alias` varchar(128) NOT NULL DEFAULT '' COMMENT '表名中文名',
  `create_time` DATETIME NOT NULL COMMENT '父级类型',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
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
  `group_id` BIGINT DEFAULT NULL COMMENT '',
  `table_id` BIGINT NOT NULL COMMENT '',
  `filter_condition` TEXT DEFAULT NULL COMMENT '',
  `compute_condition` TEXT NOT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_tag_group`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(128) NOT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`ID`)
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
  PRIMARY KEY (`ID`)
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
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_index_dimension`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `index_id` BIGINT NOT NULL COMMENT '',
  `name` VARCHAR(255) NOT NULL COMMENT '',
  `expression` TEXT NOT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_index_metric`
(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `index_id` BIGINT NOT NULL COMMENT '',
  `name` VARCHAR(255) NOT NULL COMMENT '',
  `expression` TEXT NOT NULL COMMENT '',
  `create_time` DATETIME NOT NULL COMMENT '',
  `last_updated` DATETIME NOT NULL COMMENT '',
  `description` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '描述',
  `deleted` BIT(1) DEFAULT FALSE,
  PRIMARY KEY (`ID`)
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
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;