TRUNCATE TABLE `sys_user`;
INSERT INTO `sys_user` VALUES
('1', 'demoUser1', '$2a$10$A8YhZjAeqXZa9zo2pa8paON1lft95H6.My0m3JFqp1ioWM8bffRom', '张晓明', 'xiaoming@163.com', '18338382817', '我是一个斌  来自老百姓sss', '1', '0', '2018-11-15 10:09:12', '浙江省杭州市'),
('2', 'admin', '$2a$10$9EEPJ/G5VQ44mbG2WctG7urQ7aNo8R1MEf.jzBoc.INUGmIiEux5G', '神', 'admin@ad.min', '18378788111', '我是一个斌  来自老百姓sss', '1', '0', '2018-11-15 14:49:42', '浙江省杭州市1'),
('22', '心急', '$2a$10$pimChj7dP38VQXpU8wMA6OmiBuexuEAEnsSHzhNJ9Oh8Zo/66HWJW', 'wed', 'qwd@sw.com', '13877772211', '我是一个斌  来自老百姓sss', '1', '0', '2018-11-15 14:14:06', '浙江省杭州市1'),
('24', '张小斐', '$2a$10$Kl7GsgnJRTytPoTWWl1Ac.TYxn4lYVyYdZPmT.fcUImf1qfWjgRW6', '小斐斐', 'feifei@163.com', '13987871231', '我是大家闺秀', '1', '0', '2018-11-15 14:41:03', '浙江省杭州市'),
('25', '朱允文', '$2a$10$zAJOoYSg3UU8yxVnZQApPOaHxAPJfahqMHFGZiVOiYZvkC8Z.3QVy', '文文', 'wenwen@163.com', '13788881828', '文文啊，我是文文啊', '1', '0', '2018-11-15 14:38:55', '洛阳');

TRUNCATE TABLE `sys_role`;
INSERT INTO `sys_role` VALUES
 ('1', 'admin', '1', '系统管理员', '0'),
 ('2', 'service_manager', '1', '业务管理员', '0'),
 ('3', 'manager', '1', '管理员', '0'),
 ('4', 'user', '1', '用户', '0');

TRUNCATE TABLE `sys_menu`;
INSERT INTO `sys_menu` VALUES
 ('1', '系统管理', '系统管理', null, '1', '1', '1', null, '0', 'icon-screen-desktop icon'),
 ('2', '用户管理', '系统管理', '1', '1', '2', '1', '/app/users', '0', 'fa fa-angle-right text-xs'),
 ('3', '角色管理', '系统管理', '1', '2', '2', '1', '/app/roles', '0', 'fa fa-angle-right text-xs'),
 ('4', '菜单管理', '菜单管理', '1', '2', '2', '1', '/app/menus', '0', 'fa fa-angle-right text-xs'),
 ('5', '日志管理', '测试管理1', '1', '3', '2', '1', '/app/logs', '0', 'fa fa-angle-right text-xs'),
 ('6', '流程管理', '流程管理', '1', '1', '1', '1', '', '0', 'icon-shuffle icon'),
 ('7', '工作流', '工作流管理', '6', '1', '2', '1', '/app/workflows', '0', 'fa fa-angle-right text-xs'),
 ('11', '财务管理', '财务管理', null, '1', '1', '1', null, '0', 'icon-disc icon'),
 ('12', '报销管理', '报销管理', '11', '1', '2', '1', '/app/reimbursement', '0', 'fa fa-angle-right text-xs');

TRUNCATE TABLE `sys_user_role`;
INSERT INTO `sys_user_role` VALUES
('5', '23', '2'),
('6', '23', '3'),
('7', '2', '1'),
('8', '2', '2'),
('9', '1', '1'),
('10', '1', '2'),
('11', '1', '3'),
('12', '1', '4');

TRUNCATE TABLE `sys_user_role`;
INSERT INTO `sys_role_menu` VALUES
('5', '4', '2'),
('6', '4', '3'),
('7', '4', '4'),
('8', '4', '5');

TRUNCATE TABLE `sys_log`;
INSERT INTO `sys_log` VALUES (1, 'index', '[Ljava.lang.reflect.Parameter;@102f3ff', '分页查询菜单信息', '日志信息菜单查询', 1, NULL, '2018-11-26 10:01:47');
INSERT INTO `sys_log` VALUES (2, 'index', '[Ljava.lang.reflect.Parameter;@2ef8e', '分页查询菜单信息', '日志信息菜单查询', 1, NULL, '2018-11-26 10:54:13');
INSERT INTO `sys_log` VALUES (3, 'index', '[Ljava.lang.reflect.Parameter;@77973f', '分页查询菜单信息', '日志信息菜单查询', 1, NULL, '2018-11-26 11:17:31');
INSERT INTO `sys_log` VALUES (4, 'index', '[Ljava.lang.reflect.Parameter;@6799b2', '分页查询用户信息', '用户信息查询', 1, NULL, '2018-11-26 11:35:53');
INSERT INTO `sys_log` VALUES (5, 'index', '[Ljava.lang.reflect.Parameter;@1fc38a6', '分页查询菜单信息', '日志信息菜单查询', 1, NULL, '2018-11-26 11:45:30');
INSERT INTO `sys_log` VALUES (6, 'update', '[Ljava.lang.reflect.Parameter;@11ad751', '修改用户', '修改用户', 1, NULL, '2018-12-10 10:15:24');