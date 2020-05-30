SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `collegeid` int(11) NOT NULL COMMENT '学院id',
  `collegename` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学院名称',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '没用，凑数的',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '没用，凑数的',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学院地址',
  PRIMARY KEY (`collegeid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES (1, '信息科学与技术学院', '10086', '信息科学与技术学院成立于2000年3月，由原自动化系和计算机系合并而成。学院下设4个系：自动化系、测控技术系、计算机系、信息工程系；', '北京市朝阳区北三环东路15号北京化工大学信息学院楼');
INSERT INTO `college` VALUES (2, '化学工程学院', '1008611', '化工学院的简介', '化工学院楼');
INSERT INTO `college` VALUES (3, '材料科学与技术学院', '10010', '材料学院简介', '材料学院楼');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `cno` int(11) NOT NULL COMMENT '课程号',
  `cname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程名',
  `college` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '开课学院',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程介绍',
  `status` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标识课程的状态',
  PRIMARY KEY (`cno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, '高等数学', '信息科学与技术学院', '高等数学是由微积分学，较深入的代数学、几何学以及它们之间的交叉内容所形成的一门基础学科。主要内容包括：数列、极限、微积分、空间解析几何与线性代数、级数、常微分方程。工科、理科、财经类研究生考试的基础科目。', '1');
INSERT INTO `course` VALUES (2, '线性代数', '信息科学与技术学院', '线性代数很有用的', '1');
INSERT INTO `course` VALUES (3, '大学英语C', '理学院', '大学英语，容易又有趣', '1');
INSERT INTO `course` VALUES (123, '技术经济学', '信息科学与技术学院', '1234', 'true');
INSERT INTO `course` VALUES (214354, '技术经济学', '信息科学与技术学院', '12345', 'true');

-- ----------------------------
-- Table structure for major
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major`  (
  `majorid` int(11) NULL DEFAULT NULL,
  `collegeid` int(11) NULL DEFAULT NULL,
  `majorname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES (1, 1, '1');
INSERT INTO `major` VALUES (2, 1, '2');

-- ----------------------------
-- Table structure for planning
-- ----------------------------
DROP TABLE IF EXISTS `planning`;
CREATE TABLE `planning`  (
  `semesterid` int(11) NOT NULL COMMENT '学期',
  `majorid` int(11) NOT NULL COMMENT '专业id',
  `grade` int(11) NOT NULL COMMENT '年级',
  `cno` int(11) NOT NULL COMMENT '课程号',
  `credit` int(11) NULL DEFAULT NULL COMMENT '学分',
  `ifopen` int(11) NULL DEFAULT NULL COMMENT '是不是开',
  `type` int(11) NULL DEFAULT NULL COMMENT '课程类别：0必修，1选修，3通识',
  PRIMARY KEY (`semesterid`, `majorid`, `grade`, `cno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of planning
-- ----------------------------
INSERT INTO `planning` VALUES (1, 1, 1, 1, NULL, 1, 1);
INSERT INTO `planning` VALUES (1, 1, 1, 2, NULL, 1, 2);
INSERT INTO `planning` VALUES (1, 1, 1, 3, NULL, 1, 3);
INSERT INTO `planning` VALUES (1, 1, 1, 214354, NULL, 1, 1);

-- ----------------------------
-- Table structure for power
-- ----------------------------
DROP TABLE IF EXISTS `power`;
CREATE TABLE `power`  (
  `selectcourse` int(11) NOT NULL COMMENT '选课开关',
  `score` int(11) NULL DEFAULT NULL COMMENT '录入成绩的权限',
  `abnormal` int(11) NULL DEFAULT NULL COMMENT '学籍异动'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of power
-- ----------------------------
INSERT INTO `power` VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `questionid` int(11) NOT NULL COMMENT '题目的id',
  `topic` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课题标题',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课题简介',
  `difficulty` int(11) NOT NULL COMMENT '标注论题的难度',
  `tno` int(11) NOT NULL COMMENT '老师工号',
  `majorid` int(11) NOT NULL COMMENT '专业id',
  `sno` int(11) NULL DEFAULT NULL COMMENT '学生id',
  `ischosen` tinyint(1) NULL DEFAULT NULL COMMENT '是否被选中',
  PRIMARY KEY (`questionid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question_score
-- ----------------------------
DROP TABLE IF EXISTS `question_score`;
CREATE TABLE `question_score`  (
  `sno` int(11) NOT NULL COMMENT '学生的学号',
  `questionid` int(11) NOT NULL COMMENT '课题的id',
  `earlyperformance` int(11) NULL DEFAULT NULL COMMENT '初期表现，默认占总成绩的10%',
  `midexam` int(11) NULL DEFAULT NULL COMMENT '中期考核，默认占比20%',
  `thesisanswer` int(11) NULL DEFAULT NULL COMMENT '答辩，默认占比30%',
  `paper` int(11) NULL DEFAULT NULL COMMENT '论文，默认占比30%',
  `extracredit` int(11) NULL DEFAULT NULL COMMENT '额外加分，占比10%',
  PRIMARY KEY (`sno`, `questionid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question_student
-- ----------------------------
DROP TABLE IF EXISTS `question_student`;
CREATE TABLE `question_student`  (
  `questionid` int(11) NOT NULL COMMENT '课题的id',
  `sno` int(11) NOT NULL COMMENT '学生的学号',
  PRIMARY KEY (`questionid`, `sno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for schedule_major
-- ----------------------------
DROP TABLE IF EXISTS `schedule_major`;
CREATE TABLE `schedule_major`  (
  `semesterid` int(11) NOT NULL,
  `cno` int(11) NOT NULL,
  `majorid` int(11) NOT NULL,
  `grade` int(4) NULL DEFAULT NULL,
  PRIMARY KEY (`semesterid`, `cno`, `majorid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_major
-- ----------------------------
INSERT INTO `schedule_major` VALUES (1, 1, 1, 1);
INSERT INTO `schedule_major` VALUES (1, 2, 1, 1);
INSERT INTO `schedule_major` VALUES (1, 214354, 1, 1);

-- ----------------------------
-- Table structure for scheduling
-- ----------------------------
DROP TABLE IF EXISTS `scheduling`;
CREATE TABLE `scheduling`  (
  `semesterid` int(50) NOT NULL COMMENT '学期id',
  `cno` int(11) NOT NULL COMMENT '课程号',
  `tno` int(11) NOT NULL COMMENT '上课老师',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程状态（停开，开设）',
  `capacity` int(11) NULL DEFAULT NULL COMMENT '课程容量',
  `address` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '上课地点',
  `percent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分数占比，百分号分隔没每个比例因子',
  `major_grade` int(11) NULL DEFAULT NULL COMMENT '专业的年级',
  `course_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '(startweek-endweek,weekday,coursestarttime-courseendtime) -> (startweek-endweek,weekday,coursestarttime-courseendtime);(startweek-endweek,weekday,coursestarttime-courseendtime)',
  PRIMARY KEY (`semesterid`, `cno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of scheduling
-- ----------------------------
INSERT INTO `scheduling` VALUES (1, 1, 1000000000, '1', 150, '', NULL, 1, '(1-18,4,1-3)');
INSERT INTO `scheduling` VALUES (1, 2, 1000000001, '1', 149, '111', NULL, 1, '(1-1,1,1-3)');
INSERT INTO `scheduling` VALUES (1, 3, 1000000002, '1', 250, '图书馆', NULL, 1, '(8-10,4,11-13)');
INSERT INTO `scheduling` VALUES (1, 214354, 2014014302, '1', 149, '1234', '2,3,5', 1, '(1-9,5,11-13)');

-- ----------------------------
-- Table structure for select_course
-- ----------------------------
DROP TABLE IF EXISTS `select_course`;
CREATE TABLE `select_course`  (
  `semesterid` int(11) NOT NULL COMMENT '学期id',
  `sno` int(11) NOT NULL COMMENT '学号',
  `cno` int(11) NOT NULL COMMENT '课程号',
  `totalscore` int(11) NULL DEFAULT NULL COMMENT '总成绩',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '成绩分布，百分号分隔开每一个得分项',
  `type` int(11) NULL DEFAULT NULL COMMENT '课程类别 0 是必修，1是选修 2 是通识课程',
  PRIMARY KEY (`semesterid`, `sno`, `cno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of select_course
-- ----------------------------
INSERT INTO `select_course` VALUES (1, 2016014274, 2, NULL, NULL, NULL);
INSERT INTO `select_course` VALUES (1, 2016014274, 123, NULL, NULL, NULL);
INSERT INTO `select_course` VALUES (1, 2016014274, 214354, NULL, NULL, NULL);
INSERT INTO `select_course` VALUES (1, 2016014302, 1, NULL, NULL, NULL);
INSERT INTO `select_course` VALUES (1, 2016014302, 2, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for semester
-- ----------------------------
DROP TABLE IF EXISTS `semester`;
CREATE TABLE `semester`  (
  `semesterid` int(11) NOT NULL AUTO_INCREMENT COMMENT '学期的标识符号（为了防止mac 遇到varchar 类型的主键炸掉）',
  `start` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学年起始年',
  `end` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学年结束年',
  `semester` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '1,2,3 学期',
  PRIMARY KEY (`semesterid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of semester
-- ----------------------------
INSERT INTO `semester` VALUES (1, '2016', '2017', '1');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `sno` int(11) NOT NULL,
  `sname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sex` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `major` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `klass` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '注意，这里是班级的意思，因为class 是保留关键字，所以为了区分 c改为k',
  `come_year` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入学年份，用来获取年级信息',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `grade` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年级',
  `majorid` int(11) NULL DEFAULT NULL COMMENT '专业id，和专业对应',
  `college` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学院名称',
  `collegeid` int(11) NULL DEFAULT NULL COMMENT '学院id',
  PRIMARY KEY (`sno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (2016013001, '彭万里', '男', '金融', '金1601', '2016', '13113109809', '2016', 2, '金融学院', 2);
INSERT INTO `student` VALUES (2016013002, '高大山', '男', '计算机', '计1702', '2016', '13113109809', '2016', 1, '信息学院', 1);
INSERT INTO `student` VALUES (2016013003, '谢大海', '男', '计算机', '计1601', '2016', '13113109809', '2016', 1, '信息学院', 1);
INSERT INTO `student` VALUES (2016013004, '马宏宇', '男', '计算机', '计1701', '2016', '13113109809', '2016', 1, '金融学院', 2);
INSERT INTO `student` VALUES (2016013005, '林莽', '男', '金融', '金1601', '2016', '13113109809', '2016', 2, '金融学院', 2);
INSERT INTO `student` VALUES (2016013006, '黄强辉', '男', '计算机', '计1601', '2016', '13113109809', '2016', 1, '信息学院', 1);
INSERT INTO `student` VALUES (2016014007, '光头强', '男', '自动化', '自1501', '2016', '13113109809', '2016', 3, '信息学院', 1);
INSERT INTO `student` VALUES (2016014008, '熊大', '男', '自动化', '自1601', '2016', '13113109809', '2016', 3, '信息学院', 1);
INSERT INTO `student` VALUES (2016014009, '熊二', '男', '自动化', '自1601', '2016', '13113109809', '2016', 3, '信息学院', 1);
INSERT INTO `student` VALUES (2016014010, '琪亚娜', '女', '机械', '机1701', '2016', '13113109809', '2016', 4, '信息学院', 1);
INSERT INTO `student` VALUES (2016014011, '芽衣', '女', '机械', '机1602', '2016', '13113109809', '2016', 4, '信息学院', 1);
INSERT INTO `student` VALUES (2016014012, '布洛妮娅', '女', '机械', '机1702', '2016', '13113109809', '2016', 4, '信息学院', 1);
INSERT INTO `student` VALUES (2016014274, '王艺琳', '女', '计算机科学与技术', '计科1601', '2016', '12345678910', '2016', 1, '信息科学与技术学院', 1);
INSERT INTO `student` VALUES (2016014302, '高谦', '男', '计算机科学与技术', '计科1601', '2016', '15801209263', '2016', 1, '信息科学与技术学院', 1);
INSERT INTO `student` VALUES (2016015013, '卡莲', '女', '机械', '枪1401', '2016', '13113109809', '2016', 4, '信息学院', 1);
INSERT INTO `student` VALUES (2016015014, '蔡徐坤', '女', '数学', '数1401', '2016', '13113109809', '2016', 5, '理学院', 3);
INSERT INTO `student` VALUES (2016015015, '吴亦凡', '男', '数学', '数1301', '2016', '13113109809', '2016', 5, '理学院', 3);
INSERT INTO `student` VALUES (2016015016, '八重樱', '女', '数学', '数0901', '2016', '13113109809', '2016', 5, '理学院', 3);
INSERT INTO `student` VALUES (2016015018, '符华', '女', '数学', '数1201', '2016', '13113109809', '2016', 5, '理学院', 3);
INSERT INTO `student` VALUES (2016015019, '灰太狼', '男', '数学', '数1301', '2016', '13113109809', '2016', 5, '理学院', 3);
INSERT INTO `student` VALUES (2016015020, '喜羊羊', '男', '数学', '数1301', '2016', '13113109809', '2016', 5, '理学院', 3);
INSERT INTO `student` VALUES (2016015021, '懒羊羊', '男', '数学', '数1301', '2016', '13113109809', '2016', 5, '理学院', 3);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `tno` int(11) NOT NULL,
  `tname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sex` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `collegeid` int(11) NULL DEFAULT NULL COMMENT '学院',
  `office` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教师办公室地址',
  `rank` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '教师职称',
  PRIMARY KEY (`tno`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1000000000, '杨卫星', '女', '10086', 'mail', 1, '图书馆', '讲师');
INSERT INTO `teacher` VALUES (1000000001, '王一多', '男', '10088', 'mail', 1, '办公室', '讲师');
INSERT INTO `teacher` VALUES (1000000002, '陈雪峰', '女', '10010', 'mail', 2, '英语调研室', '讲师');
INSERT INTO `teacher` VALUES (2014014302, '谦谦君', '男', '10088', 'mail', 1, '图书馆', '教授');
INSERT INTO `teacher` VALUES (2015014302, '假装坏', '男', '10088', 'mail', 1, '校长办公室', '教授');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `account` int(11) NOT NULL COMMENT '登录账号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `type` int(11) NOT NULL COMMENT '用户类别：1代表学生，0代表老师，2代表教务管理人员',
  `status` int(11) NULL DEFAULT NULL COMMENT '用户状态',
  PRIMARY KEY (`account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2014014302, '19970329', 1, 1);
INSERT INTO `user` VALUES (2015014302, '19970329', 2, 1);
INSERT INTO `user` VALUES (2016014274, '123456', 0, 1);
INSERT INTO `user` VALUES (2016014302, '19970329', 0, 1);

SET FOREIGN_KEY_CHECKS = 1;