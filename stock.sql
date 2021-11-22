-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        5.7.25-log - MySQL Community Server (GPL)
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- stock 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `stock` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `stock`;

-- 테이블 stock.admin_user 구조 내보내기
CREATE TABLE IF NOT EXISTS `admin_user` (
  `user_id` varchar(20) NOT NULL COMMENT '관리자 ID',
  `password` varchar(200) NOT NULL COMMENT '비밀번호',
  `name` varchar(20) NOT NULL COMMENT '이름',
  `reg_date` char(8) NOT NULL COMMENT '등록일',
  `reg_time` char(6) NOT NULL DEFAULT '' COMMENT '등록시간',
  `role_id` varchar(15) NOT NULL COMMENT 'admin, manager , user',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '관리자 상태 :  1:사용, 2:삭제',
  `otp` int(1) DEFAULT '0' COMMENT 'OTP 발행 여부 0 : 미발행 , 1 : 발행',
  `otp_key` varchar(8) DEFAULT NULL COMMENT 'OTP 고유 키 값',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='관리툴 사용자 테이블';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 stock.parsing_data 구조 내보내기
CREATE TABLE IF NOT EXISTS `parsing_data` (
  `seq` int(11) NOT NULL AUTO_INCREMENT COMMENT '생성순서',
  `stock_code` varchar(6) NOT NULL DEFAULT '0' COMMENT '주식종목코드',
  `stock_name` varchar(60) DEFAULT '0' COMMENT '종목명',
  `stock_category_code` int(1) DEFAULT '0' COMMENT '종목구분코드 , 1 : 코스피 , 2 : 코스닥 , 3 : 코넥스 ,0 : 해당없임',
  `stock_category_name` varchar(10) DEFAULT '' COMMENT '종목구부명',
  `present_price` int(20) DEFAULT '0' COMMENT '현재가',
  `yesterday_price` int(20) DEFAULT '0' COMMENT '전일가',
  `current_price` int(20) DEFAULT '0' COMMENT '시가',
  `high_price` int(20) DEFAULT '0' COMMENT '고가',
  `upper_limit_price` int(20) DEFAULT '0' COMMENT '상한가',
  `low_price` int(20) DEFAULT '0' COMMENT '저가',
  `lower_limit_price` int(20) DEFAULT '0' COMMENT '하한가',
  `trading_volume` int(20) DEFAULT '0' COMMENT '거래량',
  `trading_value` varchar(50) DEFAULT '0' COMMENT '거래대금',
  `direction` varchar(4) DEFAULT '' COMMENT '방향',
  `direction_code` int(1) DEFAULT '0' COMMENT '방향코드, 1: 상향 , 2 :하향 , 3 : 보합',
  `price_gap` int(20) DEFAULT '0' COMMENT '가격차',
  `foreign_trade` int(20) DEFAULT '0' COMMENT '외국인 매매',
  `institution_trade` int(20) DEFAULT '0' COMMENT '기관 매매',
  `parsing_date` varchar(8) NOT NULL DEFAULT '0' COMMENT '기준 날짜',
  `parsing_date_detail` varchar(14) NOT NULL DEFAULT '0' COMMENT '상세 기준 날짜',
  `per` int(10) DEFAULT '0' COMMENT 'per(배)',
  `eps` int(10) DEFAULT '0' COMMENT 'eps(원)',
  `parsing_memo` varchar(250) DEFAULT NULL COMMENT '메모',
  PRIMARY KEY (`seq`),
  UNIQUE KEY `stock_code` (`stock_code`,`parsing_date`)
) ENGINE=InnoDB AUTO_INCREMENT=30539 DEFAULT CHARSET=utf8 COMMENT='주식 파싱 데이터';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 stock.parsing_schedule_log 구조 내보내기
CREATE TABLE IF NOT EXISTS `parsing_schedule_log` (
  `seq` int(11) NOT NULL AUTO_INCREMENT COMMENT '순번',
  `start_time` varchar(14) NOT NULL DEFAULT '0' COMMENT '시작시간(yyyyMMddHHmmss)',
  `end_time` varchar(14) NOT NULL DEFAULT '0' COMMENT '종료시간(yyyyMMddHHmmss)',
  `elapsed_time` varchar(50) NOT NULL DEFAULT '0' COMMENT '작업시간 (단위: 밀리초 1초 = 1000)',
  `schedule_date` varchar(8) NOT NULL DEFAULT '0' COMMENT '작업일(yyyyMMdd)',
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='파싱 작업 로그';

-- 내보낼 데이터가 선택되어 있지 않습니다.
-- 테이블 stock.stock_list 구조 내보내기
CREATE TABLE IF NOT EXISTS `stock_list` (
  `seq` int(11) NOT NULL AUTO_INCREMENT COMMENT '생성순서',
  `stock_code` varchar(6) NOT NULL DEFAULT '0' COMMENT '주식 단축 코드',
  `stock_long_code` varchar(12) NOT NULL DEFAULT '0' COMMENT '주식 표준 코드',
  `stock_name` varchar(60) NOT NULL DEFAULT '0' COMMENT '주식명',
  `stock_category_code` int(11) NOT NULL DEFAULT '0' COMMENT '주식 구분 코드, 1: 코스피 , 2: 코스닥 , 3: 코넥스',
  `stock_category_name` varchar(6) NOT NULL DEFAULT '0' COMMENT '주식 구분 명',
  `insert_date` varchar(14) NOT NULL DEFAULT '0' COMMENT '생성날짜',
  `update_date` varchar(14) NOT NULL DEFAULT '0' COMMENT '수정날짜',
  PRIMARY KEY (`seq`),
  UNIQUE KEY `stock_code` (`stock_code`,`stock_category_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2596 DEFAULT CHARSET=utf8 COMMENT='주식 종목';

-- 내보낼 데이터가 선택되어 있지 않습니다.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
