/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : survey

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 16/04/2023 14:48:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for link_survey
-- ----------------------------
DROP TABLE IF EXISTS `link_survey`;
CREATE TABLE `link_survey`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '12位随机字符串，不可重复',
  `survey_id` int NOT NULL COMMENT '对应问卷 ID',
  `status` int NOT NULL COMMENT '是否公开发布，0 否，1 是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_answer
-- ----------------------------
DROP TABLE IF EXISTS `t_answer`;
CREATE TABLE `t_answer`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `option_id` int NULL DEFAULT 0 COMMENT '选项 ID，答案对应的选项（默认为0）',
  `question_id` int NOT NULL COMMENT '问题 ID，答案所属的问题',
  `survey_id` int NOT NULL COMMENT '问卷 ID，答案所属的问卷',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '答案的内容（若为选择题，则会传入选项的内容；若为填空题，则会传入输入的字符串）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_option
-- ----------------------------
DROP TABLE IF EXISTS `t_option`;
CREATE TABLE `t_option`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `question_id` int NOT NULL COMMENT '问题 ID，该选项所属问题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '该选项的内容',
  `sort` int NOT NULL COMMENT '问题的选项的顺序，0，1，2，3，...',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_question
-- ----------------------------
DROP TABLE IF EXISTS `t_question`;
CREATE TABLE `t_question`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键 ID，问题 ID',
  `survey_id` int NOT NULL COMMENT '问卷 ID，问题所属于的问卷',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '问题的内容',
  `type` int NOT NULL COMMENT '问题类型，1 单选 2 多选 3 填空 4 评分',
  `required` int NOT NULL DEFAULT 1 COMMENT '是否必答，0 否 1 是',
  `sort` int NOT NULL DEFAULT 0 COMMENT '问题顺序',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_survey
-- ----------------------------
DROP TABLE IF EXISTS `t_survey`;
CREATE TABLE `t_survey`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `user_id` bigint NOT NULL COMMENT '用户 ID，问卷创建者 ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '问卷的标题',
  `status` int NOT NULL DEFAULT 0 COMMENT '是否发布，0 未发布，1 发布',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '问卷描述',
  `is_like` int(0) NOT NULL DEFAULT 0 COMMENT '是否收藏，0 未收藏，1 收藏',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '截止时间',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `deleted` int NULL DEFAULT 0 COMMENT '逻辑删除 0表示未删除，1表示删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `role` int NOT NULL DEFAULT 0 COMMENT '类型，0 普通用户 1 管理员',
  `status` int NULL DEFAULT 0 COMMENT '状态 0 正常 1 异常',
  `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `gender` int NULL DEFAULT 0 COMMENT '性别 0 男 1 女',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` int NOT NULL DEFAULT 0 COMMENT '逻辑删除，0 未删除 1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log`  (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                        `survey_id` int NOT NULL,
                        `create_time` datetime(0) NOT NULL,
                        `update_time` datetime(0) NOT NULL,
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
