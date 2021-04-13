drop table if exists fund;
create table fund
(
    `id`             bigint(20) not null AUTO_INCREMENT comment '主键',
    `name`           varchar(32) default null comment '名称',
    `code`           varchar(16) default null comment '代码',
    `create_date`    datetime    default null comment '创建日期',
    `update_date`    datetime    default null on update current_timestamp comment '更新时间',
    `type`           varchar(16) default null comment '基金类型',
    `manager`        varchar(16) default null comment '基金经理',
    `establish_date` date        default null comment '成立日期',
    primary key (`id`)
);

drop table if exists fund_value;
create table fund_value
(
    `id`            bigint(20) not null AUTO_INCREMENT comment '主键',
    `create_date`   datetime       default null comment '创建日期',
    `update_date`   datetime       default null on update current_timestamp comment '更新时间',
    `fund_id`       bigint(20) not null comment '基金ID',
    `value_date`    date           default null comment '净值日期',
    `value`         decimal(10, 4) default null comment '净值',
    `increase_rate` decimal(10, 2) default 0.00 comment '日增长率',
    primary key (`id`),
    INDEX           `idx_fundId_valueDate`(`fund_id`, `value_date`) USING BTREE
);

drop table if exists virtual_user;
create table virtual_user
(
    `id`          bigint(20) not null AUTO_INCREMENT comment '主键',
    `create_date` datetime default null comment '创建日期',
    `update_date` datetime default null on update current_timestamp comment '更新时间',
    `account`     varchar(32) not null comment '账户',
    `password`    varchar(32) not null comment '密码',
    primary key (`id`)
);

insert into virtual_user values(1, now(), now(), 'root', 'password');

drop table if exists optional_fund_relation;
create table optional_fund_relation
(
    `virtual_user_id` varchar(32) not null comment '用户ID',
    `fund_id`         varchar(32) not null comment '基金ID',
    `create_date`     datetime default null comment '创建日期',
    `update_date`     datetime default null on update current_timestamp comment '更新时间'
);