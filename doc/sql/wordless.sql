/*
Navicat MySQL Data Transfer

Source Server         : MyDB
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : wordless

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2018-07-31 21:42:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rabc_login_info_role
-- ----------------------------
DROP TABLE IF EXISTS `rabc_login_info_role`;
CREATE TABLE `rabc_login_info_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `login_info_id` bigint(20) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `role_sn` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rbac_login_info
-- ----------------------------
DROP TABLE IF EXISTS `rbac_login_info`;
CREATE TABLE `rbac_login_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `is_admin` bit(1) DEFAULT NULL,
  `is_banned` bit(1) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `password` varchar(32) DEFAULT NULL,
  `phone` char(11) NOT NULL COMMENT '手机号',
  `real_name` varchar(36) DEFAULT NULL COMMENT '真实姓名',
  `token` varchar(36) DEFAULT NULL COMMENT '模拟sessionid',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `error_times` int(11) DEFAULT NULL COMMENT '密码错误次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rbac_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `rbac_operation_log`;
CREATE TABLE `rbac_operation_log` (
  `id` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `expr` varchar(255) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rbac_permission
-- ----------------------------
DROP TABLE IF EXISTS `rbac_permission`;
CREATE TABLE `rbac_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `expr` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `redirect_or_not` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rbac_role
-- ----------------------------
DROP TABLE IF EXISTS `rbac_role`;
CREATE TABLE `rbac_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `role_sn` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rbac_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `rbac_role_permission`;
CREATE TABLE `rbac_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_expr` varchar(255) DEFAULT NULL,
  `role_sn` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
