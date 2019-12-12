create table sys_dept
(
	version int default 0 not null comment '乐观锁',
	create_by varchar(32) null comment '创建人',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_by varchar(32) null comment '更新人',
	update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	deleted int default 0 not null comment '删除标记',
	dept_id varchar(32) not null comment 'ID'
		primary key,
	dept_name varchar(128) not null comment '名称',
	p_id varchar(32) not null comment '父节点id'
)
comment '组织机构';

create table sys_menu
(
	version int default 0 not null comment '乐观锁',
	create_by varchar(32) null,
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_by varchar(32) null,
	update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	deleted int default 0 not null comment '删除标记',
	menu_id varchar(32) not null
		primary key,
	parent_menu_code varchar(128) null,
	menu_code varchar(128) null,
	menu_name varchar(128) null,
	menu_path varchar(128) null,
	menu_index int default 0 not null comment '菜单排序号 100为间隔',
	menu_icon varchar(128) null,
	menu_type varchar(32) null
)
comment '菜单表 ';

create table sys_permission
(
	version int default 0 not null comment '乐观锁',
	create_by varchar(32) null,
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_by varchar(32) null,
	update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	deleted int default 0 not null comment '删除标记',
	permission_id varchar(32) not null
		primary key,
	permission_code varchar(128) null,
	permission_name varchar(32) null
)
comment '权限表 ';

create table sys_role
(
	version int default 0 not null comment '乐观锁',
	create_by varchar(32) null,
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_by varchar(32) null,
	update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	deleted int default 0 not null comment '删除标记',
	role_id varchar(32) not null
		primary key,
	role_code varchar(128) null,
	role_name varchar(128) null
)
comment '角色 ';

create table sys_role_menu
(
	version int default 0 not null comment '乐观锁',
	create_by varchar(32) null,
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_by varchar(32) null,
	update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	deleted int default 0 not null comment '删除标记',
	role_menu_id varchar(32) not null
		primary key,
	role_code varchar(128) null,
	menu_code varchar(128) null
)
comment '角色菜单 ';

create table sys_role_permission
(
	version int default 0 not null comment '乐观锁',
	create_by varchar(32) null,
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_by varchar(32) null,
	update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	deleted int default 0 not null comment '删除标记',
	role_permission_id varchar(32) not null
		primary key,
	role_code varchar(128) null,
	permission_code varchar(128) null
)
comment '角色权限 ';

create table u_login_channel
(
	version int default 0 not null comment '乐观锁',
	create_by varchar(32) null comment '创建人',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_by varchar(32) null comment '更新人',
	update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	deleted int default 0 not null comment '删除标记',
	t_id varchar(32) not null comment 'ID'
		primary key,
	user_id varchar(32) not null comment '用户id',
	login_type varchar(32) not null comment '登录类型',
	login_id varchar(32) not null comment '登录id',
	login_channel_scope varchar(32) not null comment '登录渠道'
)
comment '登录渠道';

create table u_user_dept
(
	version int default 0 not null comment '乐观锁',
	create_by varchar(32) null comment '创建人',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_by varchar(32) null comment '更新人',
	update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	deleted int default 0 not null comment '删除标记',
	t_id varchar(32) not null comment 'ID'
		primary key,
	user_id varchar(32) not null comment '用户id',
	dept_id varchar(32) not null comment '组织id'
)
comment '用户所属组织机构';

create table u_user_info
(
	version int default 0 not null comment '乐观锁',
	create_by varchar(32) null comment '创建人',
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_by varchar(32) null comment '更新人',
	update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	deleted int default 0 not null comment '删除标记',
	user_id varchar(32) not null comment 'ID'
		primary key,
	user_name varchar(128) not null comment '用户名',
	phone varchar(32) not null comment '手机号',
	icnum varchar(32) null comment '身份证号',
	icon varchar(128) null comment '头像',
	login_id varchar(32) null comment '登录id',
	login_pwd varchar(32) null comment '登录密码',
	salt varchar(32) not null comment '加密盐',
	is_locked int default 0 not null comment '是否被锁定',
	unlock_time timestamp default '0000-00-00 00:00:00' not null comment '解锁时间'
)
comment '用户信息';

create table u_user_role
(
	version int default 0 not null comment '乐观锁',
	create_by varchar(32) null,
	create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
	update_by varchar(32) null,
	update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	deleted int default 0 not null comment '删除标记',
	ur_id varchar(32) not null
		primary key,
	user_id varchar(32) null,
	role_code varchar(32) null
)
comment '用户角色 ';

