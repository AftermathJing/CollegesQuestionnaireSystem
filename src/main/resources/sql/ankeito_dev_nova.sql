/*
 Navicat Premium Data Transfer

 Source Server         : ROOT
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : questionnaire_dev

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 24/06/2023 02:50:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for option
-- ----------------------------
DROP TABLE IF EXISTS `option`;
CREATE TABLE `option`  (
  `id` int NOT NULL,
  `question_id` int NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `qnnre_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`, `question_id`, `qnnre_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of option
-- ----------------------------
INSERT INTO `option` VALUES (0, 0, '如如', '30c14452-c16d-4a53-b356-ffbe6e1e0e64');
INSERT INTO `option` VALUES (0, 0, '是', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `option` VALUES (0, 0, '是', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `option` VALUES (0, 0, '为什么我不可以是个废物啊？', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b');
INSERT INTO `option` VALUES (0, 1, '来了', '30c14452-c16d-4a53-b356-ffbe6e1e0e64');
INSERT INTO `option` VALUES (0, 1, '伊普西龙', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `option` VALUES (0, 1, '是似搜属', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `option` VALUES (0, 2, '1 + 11 = 哈哈哈', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `option` VALUES (1, 0, '如来', '30c14452-c16d-4a53-b356-ffbe6e1e0e64');
INSERT INTO `option` VALUES (1, 0, '不是', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `option` VALUES (1, 0, '不是', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `option` VALUES (1, 0, '为什么我不可以是个废物呢！', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b');
INSERT INTO `option` VALUES (1, 1, '没来', '30c14452-c16d-4a53-b356-ffbe6e1e0e64');
INSERT INTO `option` VALUES (1, 1, '二龙戏珠', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `option` VALUES (1, 1, '锟斤拷平', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `option` VALUES (1, 2, '9 * 78 = 1212', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `option` VALUES (2, 0, '若至', '30c14452-c16d-4a53-b356-ffbe6e1e0e64');
INSERT INTO `option` VALUES (2, 0, '为什么我不可以是个废物呀？', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b');
INSERT INTO `option` VALUES (2, 1, '如来', '30c14452-c16d-4a53-b356-ffbe6e1e0e64');
INSERT INTO `option` VALUES (2, 1, '柳林路了', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `option` VALUES (2, 1, '说的道理', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `option` VALUES (2, 2, '78 / 2213 = 0', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `option` VALUES (3, 0, '弱智', '30c14452-c16d-4a53-b356-ffbe6e1e0e64');
INSERT INTO `option` VALUES (3, 0, '为什么我不可以是个废物nya？', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b');
INSERT INTO `option` VALUES (3, 1, '好像来了', '30c14452-c16d-4a53-b356-ffbe6e1e0e64');
INSERT INTO `option` VALUES (3, 1, '聋弄尼欧', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `option` VALUES (3, 1, '肌肉紧张', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `option` VALUES (3, 2, '0 / 0 =INF', '94eb677d-f8c8-41e7-b5fc-563c13867454');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `person_in_charge` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `project_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `project_content` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `created_by` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `last_updated_by` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `create_time` datetime NOT NULL,
  `last_update_date` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('448ce5a6-4aaf-4b58-9e81-dede5918737e', 'TODO', '你是人类吗？', '本次调查目的在于调查参与者是否为人类。', 'aftermarhjing', 'aftermarhjing', '2023-06-23 15:47:41', '2023-06-23 15:47:41');
INSERT INTO `project` VALUES ('c955cebf-36b5-4dfa-82cf-d4115a933e09', 'TODO', '如来真来了吗?', '中国人认为宇宙万法...', 'aftermarhjing', 'aftermarhjing', '2023-06-23 15:31:49', '2023-06-23 15:31:49');

-- ----------------------------
-- Table structure for qnnre
-- ----------------------------
DROP TABLE IF EXISTS `qnnre`;
CREATE TABLE `qnnre`  (
  `id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `project_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `start_time` datetime NOT NULL,
  `stop_time` datetime NOT NULL,
  `qnnre_status` enum('DRAFT','PUBLISHED','CLOSED','DELETED') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'DRAFT',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of qnnre
-- ----------------------------
INSERT INTO `qnnre` VALUES ('30c14452-c16d-4a53-b356-ffbe6e1e0e64', 'c955cebf-36b5-4dfa-82cf-d4115a933e09', '关于如来是否真的来了的调查', '我们将进行一项关于如来是否真的来了的调查。此调查旨在了解人们对如来是否存在的看法和信仰。调查包括提问参与者是否相信如来的存在，并询问他们对如来来临的态度和期望。我们希望通过这项调查获得广泛的观点，并了解公众对如来来临的态度和意见。这项调查采用匿名方式，所有个人信息将被保密并仅用于统计分析。调查结果将帮助我们更好地理解人们对如来的看法，促进对相关话题的讨论和研究。感谢您参与我们的调查！', '2023-06-23 08:00:00', '2023-06-30 08:00:00', 'DRAFT');
INSERT INTO `qnnre` VALUES ('706d1d05-75cb-4c43-a1f4-29cc944e1c7a', '448ce5a6-4aaf-4b58-9e81-dede5918737e', '你是人类吗?2', '111', '2023-06-24 08:00:00', '2023-06-26 08:00:00', 'DRAFT');
INSERT INTO `qnnre` VALUES ('94eb677d-f8c8-41e7-b5fc-563c13867454', '448ce5a6-4aaf-4b58-9e81-dede5918737e', '你是人类吗1？', '01010100010111110111011110', '2023-06-23 08:00:00', '2023-06-28 08:00:00', 'PUBLISHED');
INSERT INTO `qnnre` VALUES ('d2771e99-2b77-4b00-bc13-eccb42b34d9b', '448ce5a6-4aaf-4b58-9e81-dede5918737e', '为什么我不可以是个废物啊？', '为什么我不可以是个废物啊？为什么我不可以是个废物啊？为什么我不可以是个废物啊？为什么我不可以是个废物啊？为什么我不可以是个废物啊？为什么我不可以是个废物啊？', '2023-06-30 08:00:00', '2023-09-14 08:00:00', 'DRAFT');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int NOT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `required` enum('REQUIRED','OPTIONAL') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'OPTIONAL',
  `type` enum('SINGLE_CHOICE_QUESTION','MULTIPLE_CHOICE_QUESTION') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'MULTIPLE_CHOICE_QUESTION',
  `qnnre_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  PRIMARY KEY (`id`, `qnnre_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (0, '中国人认为宇宙万法的那个源头是什么？', 'REQUIRED', 'SINGLE_CHOICE_QUESTION', '30c14452-c16d-4a53-b356-ffbe6e1e0e64');
INSERT INTO `question` VALUES (0, '你是人类吗？', 'REQUIRED', 'SINGLE_CHOICE_QUESTION', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `question` VALUES (0, '你是人类吗？', 'REQUIRED', 'SINGLE_CHOICE_QUESTION', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `question` VALUES (0, '为什么我不可以是个废物啊？', 'REQUIRED', 'SINGLE_CHOICE_QUESTION', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b');
INSERT INTO `question` VALUES (1, '如来真来了吗？', 'REQUIRED', 'SINGLE_CHOICE_QUESTION', '30c14452-c16d-4a53-b356-ffbe6e1e0e64');
INSERT INTO `question` VALUES (1, '请识别下列的词语，指出哪些不是人类常用词语？', 'REQUIRED', 'MULTIPLE_CHOICE_QUESTION', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `question` VALUES (1, '请识别下列的词语，指出哪些不是人类常用词语？', 'OPTIONAL', 'SINGLE_CHOICE_QUESTION', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `question` VALUES (2, '下列算式中，那些是美味的？', 'REQUIRED', 'SINGLE_CHOICE_QUESTION', '94eb677d-f8c8-41e7-b5fc-563c13867454');

-- ----------------------------
-- Table structure for response_option
-- ----------------------------
DROP TABLE IF EXISTS `response_option`;
CREATE TABLE `response_option`  (
  `response_sheet_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `option_id` int NOT NULL,
  `question_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `qnnre_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of response_option
-- ----------------------------
INSERT INTO `response_option` VALUES ('1a3cd048-eb7f-43e9-b077-d35ada8370b3', 1, '0', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('1a3cd048-eb7f-43e9-b077-d35ada8370b3', 1, '1', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('1a3cd048-eb7f-43e9-b077-d35ada8370b3', 1, '2', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('1a3cd048-eb7f-43e9-b077-d35ada8370b3', 3, '2', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('c2bae578-f6d7-480a-9609-97c3c7624305', 1, '0', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('c2bae578-f6d7-480a-9609-97c3c7624305', 2, '1', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('c2bae578-f6d7-480a-9609-97c3c7624305', 2, '2', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('9bf8cabf-e311-4824-b565-a8b1a3ddbb78', 1, '0', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('9bf8cabf-e311-4824-b565-a8b1a3ddbb78', 1, '1', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('9bf8cabf-e311-4824-b565-a8b1a3ddbb78', 2, '2', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('9bf8cabf-e311-4824-b565-a8b1a3ddbb78', 3, '2', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('56fedcfd-950a-4622-9c2f-7046a13cc654', 0, '0', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('56fedcfd-950a-4622-9c2f-7046a13cc654', 1, '1', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('56fedcfd-950a-4622-9c2f-7046a13cc654', 2, '1', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('56fedcfd-950a-4622-9c2f-7046a13cc654', 1, '2', '94eb677d-f8c8-41e7-b5fc-563c13867454');
INSERT INTO `response_option` VALUES ('368b6253-e1ac-4a8d-b3fc-9532154c1bfe', 0, '0', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `response_option` VALUES ('368b6253-e1ac-4a8d-b3fc-9532154c1bfe', 1, '1', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `response_option` VALUES ('368b6253-e1ac-4a8d-b3fc-9532154c1bfe', 2, '1', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `response_option` VALUES ('4181ab14-968d-4959-bbbd-79d910d70113', 1, '0', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `response_option` VALUES ('4181ab14-968d-4959-bbbd-79d910d70113', 1, '1', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `response_option` VALUES ('4181ab14-968d-4959-bbbd-79d910d70113', 2, '1', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `response_option` VALUES ('e5f8fb9c-5d27-4e66-a7ec-37806fc6f6ff', 0, '0', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `response_option` VALUES ('e5f8fb9c-5d27-4e66-a7ec-37806fc6f6ff', 0, '1', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `response_option` VALUES ('e5f8fb9c-5d27-4e66-a7ec-37806fc6f6ff', 3, '1', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a');
INSERT INTO `response_option` VALUES ('4cb1dfbe-92f3-42b2-990f-23b323a08ef3', 1, '0', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b');
INSERT INTO `response_option` VALUES ('a6894c67-cb86-43ae-a386-1667ef369af9', 2, '0', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b');
INSERT INTO `response_option` VALUES ('0d5519c4-b308-4297-8627-094b4357705b', 1, '0', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b');
INSERT INTO `response_option` VALUES ('5cea7c4f-fe5a-4273-a535-de1b075d2af7', 3, '0', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b');
INSERT INTO `response_option` VALUES ('d9da35d8-c993-47f8-bac2-98945e0a40d5', 2, '0', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b');
INSERT INTO `response_option` VALUES ('d9da35d8-c993-47f8-bac2-98945e0a40d5', 0, '0', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b');
INSERT INTO `response_option` VALUES ('d9da35d8-c993-47f8-bac2-98945e0a40d5', 2, '0', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b');

-- ----------------------------
-- Table structure for response_sheet
-- ----------------------------
DROP TABLE IF EXISTS `response_sheet`;
CREATE TABLE `response_sheet`  (
  `id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `qnnre_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `qnnre_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `respondent_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `respondent_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `finished_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of response_sheet
-- ----------------------------
INSERT INTO `response_sheet` VALUES ('0d5519c4-b308-4297-8627-094b4357705b', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '为什么我不可以是个废物啊？', '7549ffdc-0368-4839-8737-f7fab53e65f5', 'TempUser78289477121661286113', '2023-06-24 02:46:28');
INSERT INTO `response_sheet` VALUES ('18c11ee1-f6f0-4609-8796-698f58cc4f3e', '94eb677d-f8c8-41e7-b5fc-563c13867454', '你是人类吗1？', 'aed921bd-a8b8-4899-b437-0afd76de71a6', 'TempUser11052479167112652647', '2023-06-24 02:01:08');
INSERT INTO `response_sheet` VALUES ('1a3cd048-eb7f-43e9-b077-d35ada8370b3', '94eb677d-f8c8-41e7-b5fc-563c13867454', '你是人类吗1？', 'd71245b8-1d0f-4aa8-be7a-de713270b4b3', 'TempUser46644429671640135918', '2023-06-23 17:13:32');
INSERT INTO `response_sheet` VALUES ('368b6253-e1ac-4a8d-b3fc-9532154c1bfe', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a', '你是人类吗?2', 'be0743a2-e96d-4ef8-bc12-e0d0ba714c07', 'TempUser51788434071119111013', '2023-06-24 02:02:52');
INSERT INTO `response_sheet` VALUES ('4181ab14-968d-4959-bbbd-79d910d70113', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a', '你是人类吗?2', 'caa51956-4afa-48bb-8d5e-727f6def347f', 'TempUser64984371376556348302', '2023-06-24 02:02:57');
INSERT INTO `response_sheet` VALUES ('4cb1dfbe-92f3-42b2-990f-23b323a08ef3', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '为什么我不可以是个废物啊？', '8f5a3df2-6db5-478a-8ad6-895b3f36a7cb', 'TempUser29912376652164500778', '2023-06-24 02:46:20');
INSERT INTO `response_sheet` VALUES ('56fedcfd-950a-4622-9c2f-7046a13cc654', '94eb677d-f8c8-41e7-b5fc-563c13867454', '你是人类吗1？', 'aec415aa-3f8c-4e7b-abf7-ae47dbec14d4', 'TempUser10091262900531447950', '2023-06-23 17:13:59');
INSERT INTO `response_sheet` VALUES ('5cea7c4f-fe5a-4273-a535-de1b075d2af7', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '为什么我不可以是个废物啊？', '01e7ac2f-e6c3-4ca7-8649-0510b8cec360', 'TempUser54092583610660060305', '2023-06-24 02:46:30');
INSERT INTO `response_sheet` VALUES ('9bf8cabf-e311-4824-b565-a8b1a3ddbb78', '94eb677d-f8c8-41e7-b5fc-563c13867454', '你是人类吗1？', '4ba91aea-55f1-4248-a0b6-16c6a126abab', 'TempUser28105534388484755343', '2023-06-23 17:13:54');
INSERT INTO `response_sheet` VALUES ('a6894c67-cb86-43ae-a386-1667ef369af9', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '为什么我不可以是个废物啊？', 'a3a4b5a3-0811-411a-94fc-2f322c68d6eb', 'TempUser26190967882616256420', '2023-06-24 02:46:25');
INSERT INTO `response_sheet` VALUES ('aeca7905-3841-4c95-ba67-289355104910', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '为什么我不可以是个废物啊？', 'a3af70a8-fe10-465a-9fe4-7de5eaf7ab95', 'TempUser01313974079480340110', '2023-06-24 02:46:33');
INSERT INTO `response_sheet` VALUES ('c2bae578-f6d7-480a-9609-97c3c7624305', '94eb677d-f8c8-41e7-b5fc-563c13867454', '你是人类吗1？', '3010711a-8fcd-4b36-b8fe-a1ef30f9cbb9', 'TempUser29731528634095479620', '2023-06-23 17:13:49');
INSERT INTO `response_sheet` VALUES ('d9da35d8-c993-47f8-bac2-98945e0a40d5', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '为什么我不可以是个废物啊？', 'd57a6863-435f-4676-9357-323ca7d1207d', 'TempUser73303028238425831246', '2023-06-24 02:46:38');
INSERT INTO `response_sheet` VALUES ('e154ae25-3e23-4ba9-be6f-44c25e296229', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '为什么我不可以是个废物啊？', 'bfb46764-14f6-458e-84f9-392a1b652b6f', 'TempUser60996361850932982050', '2023-06-24 02:46:37');
INSERT INTO `response_sheet` VALUES ('e5f8fb9c-5d27-4e66-a7ec-37806fc6f6ff', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a', '你是人类吗?2', 'd203f47c-1f33-4a36-ab4b-de010046efbd', 'TempUser93605286269197981215', '2023-06-24 02:03:02');
INSERT INTO `response_sheet` VALUES ('e78c7192-689f-4065-be79-5fa91be1b68c', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '为什么我不可以是个废物啊？', '40a4b181-56c4-411a-bdc7-23870d82c736', 'TempUser02458371121691410834', '2023-06-24 02:46:35');

-- ----------------------------
-- Table structure for response_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `response_sheet_detail`;
CREATE TABLE `response_sheet_detail`  (
  `response_sheet_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `qnnre_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `question_id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of response_sheet_detail
-- ----------------------------
INSERT INTO `response_sheet_detail` VALUES ('0d5519c4-b308-4297-8627-094b4357705b', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '0');
INSERT INTO `response_sheet_detail` VALUES ('1a3cd048-eb7f-43e9-b077-d35ada8370b3', '94eb677d-f8c8-41e7-b5fc-563c13867454', '0');
INSERT INTO `response_sheet_detail` VALUES ('1a3cd048-eb7f-43e9-b077-d35ada8370b3', '94eb677d-f8c8-41e7-b5fc-563c13867454', '1');
INSERT INTO `response_sheet_detail` VALUES ('1a3cd048-eb7f-43e9-b077-d35ada8370b3', '94eb677d-f8c8-41e7-b5fc-563c13867454', '2');
INSERT INTO `response_sheet_detail` VALUES ('368b6253-e1ac-4a8d-b3fc-9532154c1bfe', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a', '0');
INSERT INTO `response_sheet_detail` VALUES ('368b6253-e1ac-4a8d-b3fc-9532154c1bfe', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a', '1');
INSERT INTO `response_sheet_detail` VALUES ('4181ab14-968d-4959-bbbd-79d910d70113', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a', '0');
INSERT INTO `response_sheet_detail` VALUES ('4181ab14-968d-4959-bbbd-79d910d70113', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a', '1');
INSERT INTO `response_sheet_detail` VALUES ('4cb1dfbe-92f3-42b2-990f-23b323a08ef3', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '0');
INSERT INTO `response_sheet_detail` VALUES ('56fedcfd-950a-4622-9c2f-7046a13cc654', '94eb677d-f8c8-41e7-b5fc-563c13867454', '0');
INSERT INTO `response_sheet_detail` VALUES ('56fedcfd-950a-4622-9c2f-7046a13cc654', '94eb677d-f8c8-41e7-b5fc-563c13867454', '1');
INSERT INTO `response_sheet_detail` VALUES ('56fedcfd-950a-4622-9c2f-7046a13cc654', '94eb677d-f8c8-41e7-b5fc-563c13867454', '2');
INSERT INTO `response_sheet_detail` VALUES ('5cea7c4f-fe5a-4273-a535-de1b075d2af7', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '0');
INSERT INTO `response_sheet_detail` VALUES ('9bf8cabf-e311-4824-b565-a8b1a3ddbb78', '94eb677d-f8c8-41e7-b5fc-563c13867454', '0');
INSERT INTO `response_sheet_detail` VALUES ('9bf8cabf-e311-4824-b565-a8b1a3ddbb78', '94eb677d-f8c8-41e7-b5fc-563c13867454', '1');
INSERT INTO `response_sheet_detail` VALUES ('9bf8cabf-e311-4824-b565-a8b1a3ddbb78', '94eb677d-f8c8-41e7-b5fc-563c13867454', '2');
INSERT INTO `response_sheet_detail` VALUES ('a6894c67-cb86-43ae-a386-1667ef369af9', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '0');
INSERT INTO `response_sheet_detail` VALUES ('aeca7905-3841-4c95-ba67-289355104910', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '0');
INSERT INTO `response_sheet_detail` VALUES ('c2bae578-f6d7-480a-9609-97c3c7624305', '94eb677d-f8c8-41e7-b5fc-563c13867454', '0');
INSERT INTO `response_sheet_detail` VALUES ('c2bae578-f6d7-480a-9609-97c3c7624305', '94eb677d-f8c8-41e7-b5fc-563c13867454', '1');
INSERT INTO `response_sheet_detail` VALUES ('c2bae578-f6d7-480a-9609-97c3c7624305', '94eb677d-f8c8-41e7-b5fc-563c13867454', '2');
INSERT INTO `response_sheet_detail` VALUES ('d9da35d8-c993-47f8-bac2-98945e0a40d5', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '0');
INSERT INTO `response_sheet_detail` VALUES ('e5f8fb9c-5d27-4e66-a7ec-37806fc6f6ff', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a', '0');
INSERT INTO `response_sheet_detail` VALUES ('e5f8fb9c-5d27-4e66-a7ec-37806fc6f6ff', '706d1d05-75cb-4c43-a1f4-29cc944e1c7a', '1');
INSERT INTO `response_sheet_detail` VALUES ('d9da35d8-c993-47f8-bac2-98945e0a40d5', 'd2771e99-2b77-4b00-bc13-eccb42b34d9b', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `username` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `start_time` datetime NOT NULL,
  `stop_time` datetime NOT NULL,
  `user_role` enum('ADMIN','NO_ROLE','STUDENT','TEACHER') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `user_status` enum('ENABLE','DISABLE') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `created_by` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `creation_time` datetime NULL DEFAULT NULL,
  `last_updated_by` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `last_update_time` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('d87fe3a4-a2b4-1144-2d93-6f2ff968c8f1', 'aftermarhjing', '0', '2023-06-23 15:30:46', '2023-06-30 15:30:49', 'ADMIN', 'ENABLE', NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
