/*
Navicat MySQL Data Transfer

Source Server         : MyDB
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : wordless

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2018-08-05 00:45:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rabc_login_info_role
-- ----------------------------
DROP TABLE IF EXISTS `rabc_login_info_role`;
CREATE TABLE `rabc_login_info_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_info_id` bigint(20) DEFAULT NULL COMMENT '关联用户id',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `role_sn` varchar(255) DEFAULT NULL COMMENT '角色编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户-角色表';

-- ----------------------------
-- Table structure for rbac_login_info
-- ----------------------------
DROP TABLE IF EXISTS `rbac_login_info`;
CREATE TABLE `rbac_login_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_admin` bit(1) DEFAULT NULL COMMENT '是否为管理员',
  `is_banned` bit(1) DEFAULT NULL COMMENT '是否禁用 true-启用 false-禁用',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `phone` char(11) NOT NULL COMMENT '手机号',
  `real_name` varchar(36) DEFAULT NULL COMMENT '真实姓名',
  `token` varchar(36) DEFAULT NULL COMMENT '模拟sessionid',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `error_times` int(11) DEFAULT NULL COMMENT '密码错误次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户登录信息表';

-- ----------------------------
-- Table structure for rbac_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `rbac_operation_log`;
CREATE TABLE `rbac_operation_log` (
  `id` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `expr` varchar(255) DEFAULT NULL COMMENT '权限表达式',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `name` varchar(255) DEFAULT NULL COMMENT '操作名称',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `args` text COMMENT '操作上传参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- ----------------------------
-- Table structure for rbac_permission
-- ----------------------------
DROP TABLE IF EXISTS `rbac_permission`;
CREATE TABLE `rbac_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expr` varchar(255) DEFAULT NULL COMMENT '权限表达式',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `redirect_or_not` bit(1) DEFAULT NULL COMMENT '是否需要重定向 true-需要 false-不需要',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Table structure for rbac_role
-- ----------------------------
DROP TABLE IF EXISTS `rbac_role`;
CREATE TABLE `rbac_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `role_sn` varchar(255) DEFAULT NULL COMMENT '角色编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for rbac_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `rbac_role_permission`;
CREATE TABLE `rbac_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_expr` varchar(255) DEFAULT NULL COMMENT '权限表达式',
  `role_sn` varchar(255) DEFAULT NULL COMMENT '角色编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色权限表';
