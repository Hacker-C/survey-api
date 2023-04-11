/*
 Navicat MySQL Data Transfer

 Source Server         : MySQL80
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : survey

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 11/04/2023 21:40:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
  `option_id` int(0) NULL DEFAULT NULL COMMENT '选项 ID，答案对应的选项（若为填空，则为null）',
  `question_id` int(0) NOT NULL COMMENT '问题 ID，答案所属的问题',
  `survey_id` int(0) NOT NULL COMMENT '问卷 ID，答案所属的问卷',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '答案的内容（若为选择题，则会传入选项的内容；若为填空题，则会传入输入的字符串）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
