/*
 Navicat Premium Data Transfer

 Source Server         : nguyenduckien
 Source Server Type    : MySQL
 Source Server Version : 100427
 Source Host           : localhost:3306
 Source Schema         : nro

 Target Server Type    : MySQL
 Target Server Version : 100427
 File Encoding         : 65001

 Date: 25/05/2023 21:17:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for momo
-- ----------------------------
DROP TABLE IF EXISTS `momo`;
CREATE TABLE `momo`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `tranId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `io` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `partnerName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `amount` int NOT NULL,
  `comment` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
