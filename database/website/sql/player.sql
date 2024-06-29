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

 Date: 21/05/2023 23:22:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for player
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_id` int NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `head` int NOT NULL DEFAULT 102,
  `gender` int NOT NULL,
  `have_tennis_space_ship` tinyint(1) NULL DEFAULT 0,
  `clan_id_sv1` int NOT NULL DEFAULT -1,
  `clan_id_sv2` int NOT NULL DEFAULT -1,
  `data_inventory` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data_location` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data_point` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data_magic_tree` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `items_body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `items_bag` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `items_box` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `items_box_lucky_round` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `friends` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enemies` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data_intrinsic` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data_item_time` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data_task` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data_mabu_egg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data_charm` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `skills` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `skills_shortcut` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pet` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data_black_ball` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data_side_task` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT current_timestamp,
  `violate` int NOT NULL DEFAULT 0,
  `pointPvp` int NULL DEFAULT 0,
  `NguHanhSonPoint` int NULL DEFAULT 0,
  `vnd` int NOT NULL,
  `tongtien` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account_id`(`account_id` ASC) USING BTREE,
  CONSTRAINT `player_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5018 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
