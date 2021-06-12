/*
 Navicat Premium Data Transfer

 Source Server         : myproject2021
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : myproject2021

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 12/06/2021 12:42:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `updatetime` datetime(0) NULL DEFAULT NULL,
  `age` int(10) NULL DEFAULT NULL,
  `updateuser` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------

-- ----------------------------
-- Table structure for t_finance_salary_payroll_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `t_finance_salary_payroll_operate_log`;
CREATE TABLE `t_finance_salary_payroll_operate_log`  (
  `id` char(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `organization_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织编码',
  `payroll_record_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复核记录ID',
  `year` int(11) NULL DEFAULT NULL COMMENT '年份 格式YYYY',
  `month` int(11) NULL DEFAULT NULL COMMENT '月份 ',
  `employee_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工编码',
  `employee_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工名称',
  `employee_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工编号',
  `operate_type` int(11) NULL DEFAULT NULL COMMENT '操作类型 说明： \r\n             1：HR-复核通过\r\n             2：HR-取消复核\r\n             3：HR-批量复核通过\r\n             4：HR-Excel导出\r\n             5：HR-复审通过\r\n             6：HR-复审驳回\r\n             7：HR-终审通过\r\n             8：HR-终审驳回\r\n             9：提交审批流\r\n            10：下发薪资条\r\n            11：申请付款\r\n            12：更新数据\r\n            13：财务-复核通过\r\n            14：财务-取消复核\r\n            15：财务-驳回\r\n            16：财务-复审通过\r\n            17：财务-复审驳回\r\n            18：财务-Excel导出\r\n            19：财务-批量复核通过',
  `busi_type` tinyint(4) NULL DEFAULT NULL COMMENT '业务类型 说明：1：HR复核 2：财务复核',
  `busi_unit` tinyint(4) NULL DEFAULT NULL COMMENT '业务单元 1 ：总览 2：详细',
  `operate_object` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作对象 说明：\r\n针对【复核通过】【取消复核】【驳回】【批量复核】【导出】等涉及到被操作对象的地方，存储员工姓名和员工编码\r\n 如：李晓（800658）多人用逗号分隔',
  `content` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作说明',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次更新时间',
  `update_user` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后一次更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `Idx_payroll_operate_log_payroll_record_id`(`payroll_record_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '薪资复核-复核操作日志表\r\n说明：\r\n针对复核模块的复核动作进行单独的日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_finance_salary_payroll_operate_log
-- ----------------------------
INSERT INTO `t_finance_salary_payroll_operate_log` VALUES ('3190af3e-303d-4aae-a936-05cf8b085c0a', 'ORG1343456435840925697', NULL, 2021, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-05-26 13:32:17', NULL);
INSERT INTO `t_finance_salary_payroll_operate_log` VALUES ('a2d6c664676d4be79a8aa16b41e2494c', 'ORG1343456435840925697', '3775aaafee314454a60b5d71279aef86', 2021, 4, 'oa1351459498209251329', '明明', '8600426', 7, 1, 1, '全部员工', '终审通过', '2021-04-06 15:39:17', 'oa1351459498209251329', '2021-04-06 15:39:17', 'oa1351459498209251329');
INSERT INTO `t_finance_salary_payroll_operate_log` VALUES ('a97e334f-7bb6-406b-b651-8e4b8ee10d8e', 'ORG1343456435840925697', NULL, 2021, 4, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-05-26 14:20:08', NULL);

SET FOREIGN_KEY_CHECKS = 1;
