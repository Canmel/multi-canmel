DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名，用户登录使用',
  `password` varchar(255) NOT NULL COMMENT '密码 用户登录使用',
  `nickname` varchar(255) DEFAULT '' COMMENT '昵称，现在在页面上的友情提示信息',
  `email` varchar(255) NOT NULL COMMENT '邮箱，发送邮件信息',
  `mobile` varchar(255) DEFAULT NULL COMMENT '移动电话，联系方式',
  `remarke` varchar(30) DEFAULT NULL COMMENT '简短的备注',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态',
  `is_del` int(11) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `address` varchar(255) DEFAULT NULL COMMENT '地址坐标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `rolename` varchar(255) NOT NULL COMMENT '角色名称',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 正常1 ',
  `description` varchar(50) NOT NULL COMMENT '简短的描述',
  `is_del` int(2) NOT NULL DEFAULT '0' COMMENT '删除状态 1 删除 0 未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menuname` varchar(255) NOT NULL COMMENT '菜单名称',
  `description` varchar(255) NOT NULL COMMENT '简短的描述文字',
  `pid` int(11) DEFAULT NULL COMMENT '上级菜单',
  `mindex` int(11) NOT NULL DEFAULT '1' COMMENT '简单的排序字段',
  `level` int(11) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态',
  `address` varchar(255) DEFAULT NULL COMMENT '目录地址',
  `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1是 0 否',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
