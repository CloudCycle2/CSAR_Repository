-- phpMyAdmin SQL Dump
-- version 4.2.10
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: May 22, 2015 at 04:01 PM
-- Server version: 5.5.38
-- PHP Version: 5.6.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `testrepo`
--

-- --------------------------------------------------------

--
-- Table structure for table `cloud_instance`
--

CREATE TABLE `cloud_instance` (
`cloud_instance_id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `csar_file_id` bigint(20) DEFAULT NULL,
  `open_tosca_server_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `csar`
--

CREATE TABLE `csar` (
`csar_id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `namespace` varchar(255) DEFAULT NULL,
  `service_template_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `csar_file`
--

CREATE TABLE `csar_file` (
`csar_file_id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `upload_date` datetime DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `csar_id` bigint(20) DEFAULT NULL,
  `hashed_file_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `csar_file_open_tosca_server`
--

CREATE TABLE `csar_file_open_tosca_server` (
  `location` varchar(255) DEFAULT NULL,
  `csar_file_id` bigint(20) NOT NULL DEFAULT '0',
  `open_tosca_server_id` bigint(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `csar_user`
--

CREATE TABLE `csar_user` (
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  `csar_id` bigint(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `csar_winery_server`
--

CREATE TABLE `csar_winery_server` (
  `winery_server_id` bigint(20) NOT NULL DEFAULT '0',
  `csar_id` bigint(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `hashed_file`
--

CREATE TABLE `hashed_file` (
`hashed_file_id` bigint(20) NOT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `hash` varchar(255) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `open_tosca_server`
--

CREATE TABLE `open_tosca_server` (
`open_tosca_server_id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `open_tosca_server_user`
--

CREATE TABLE `open_tosca_server_user` (
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  `open_tosca_server_id` bigint(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `plan`
--

CREATE TABLE `plan` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `hashed_file_id` bigint(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
`user_id` bigint(20) NOT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `mail`, `name`, `password`) VALUES
(1, 'cloud@cycle.rest', 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');

-- --------------------------------------------------------

--
-- Table structure for table `user_winery_server`
--

CREATE TABLE `user_winery_server` (
  `user_id` bigint(20) NOT NULL DEFAULT '0',
  `winery_server_id` bigint(20) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `winery_server`
--

CREATE TABLE `winery_server` (
`winery_server_id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cloud_instance`
--
ALTER TABLE `cloud_instance`
 ADD PRIMARY KEY (`cloud_instance_id`), ADD KEY `FK_kv7upv3cx4nvqyfrs5wh4lvhs` (`csar_file_id`), ADD KEY `FK_4cv04nqt8xdcyoolykc462s07` (`open_tosca_server_id`);

--
-- Indexes for table `csar`
--
ALTER TABLE `csar`
 ADD PRIMARY KEY (`csar_id`);

--
-- Indexes for table `csar_file`
--
ALTER TABLE `csar_file`
 ADD PRIMARY KEY (`csar_file_id`), ADD KEY `FK_j8blm9v5ptq4fbwrv47tafhn9` (`csar_id`), ADD KEY `FK_5cymcwyp9b006xaf3ttmencvo` (`hashed_file_id`);

--
-- Indexes for table `csar_file_open_tosca_server`
--
ALTER TABLE `csar_file_open_tosca_server`
 ADD PRIMARY KEY (`csar_file_id`,`open_tosca_server_id`), ADD KEY `FK_ra93u9rqql7senwvkwuoqxk3a` (`open_tosca_server_id`);

--
-- Indexes for table `csar_user`
--
ALTER TABLE `csar_user`
 ADD PRIMARY KEY (`csar_id`,`user_id`), ADD KEY `FK_50wllgq8dmn4h1sfw7yv7frxp` (`user_id`);

--
-- Indexes for table `csar_winery_server`
--
ALTER TABLE `csar_winery_server`
 ADD PRIMARY KEY (`csar_id`,`winery_server_id`), ADD KEY `FK_r1c0fq58fijutc0puu6dafj7o` (`winery_server_id`);

--
-- Indexes for table `hashed_file`
--
ALTER TABLE `hashed_file`
 ADD PRIMARY KEY (`hashed_file_id`);

--
-- Indexes for table `open_tosca_server`
--
ALTER TABLE `open_tosca_server`
 ADD PRIMARY KEY (`open_tosca_server_id`);

--
-- Indexes for table `open_tosca_server_user`
--
ALTER TABLE `open_tosca_server_user`
 ADD PRIMARY KEY (`open_tosca_server_id`,`user_id`), ADD KEY `FK_toj22it1iv7yaqenpym7nk9mw` (`user_id`);

--
-- Indexes for table `plan`
--
ALTER TABLE `plan`
 ADD PRIMARY KEY (`hashed_file_id`,`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`user_id`), ADD UNIQUE KEY `UK_gj2fy3dcix7ph7k8684gka40c` (`name`);

--
-- Indexes for table `user_winery_server`
--
ALTER TABLE `user_winery_server`
 ADD PRIMARY KEY (`user_id`,`winery_server_id`), ADD KEY `FK_f7tq0jjuti89v4brxa4bbsj6p` (`winery_server_id`);

--
-- Indexes for table `winery_server`
--
ALTER TABLE `winery_server`
 ADD PRIMARY KEY (`winery_server_id`), ADD KEY `FK_25nqrxpf8c3oaw3ewmgipqp0k` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cloud_instance`
--
ALTER TABLE `cloud_instance`
MODIFY `cloud_instance_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `csar`
--
ALTER TABLE `csar`
MODIFY `csar_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `csar_file`
--
ALTER TABLE `csar_file`
MODIFY `csar_file_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `hashed_file`
--
ALTER TABLE `hashed_file`
MODIFY `hashed_file_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `open_tosca_server`
--
ALTER TABLE `open_tosca_server`
MODIFY `open_tosca_server_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `winery_server`
--
ALTER TABLE `winery_server`
MODIFY `winery_server_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `cloud_instance`
--
ALTER TABLE `cloud_instance`
ADD CONSTRAINT `FK_4cv04nqt8xdcyoolykc462s07` FOREIGN KEY (`open_tosca_server_id`) REFERENCES `open_tosca_server` (`open_tosca_server_id`),
ADD CONSTRAINT `FK_kv7upv3cx4nvqyfrs5wh4lvhs` FOREIGN KEY (`csar_file_id`) REFERENCES `csar_file` (`csar_file_id`);

--
-- Constraints for table `csar_file`
--
ALTER TABLE `csar_file`
ADD CONSTRAINT `FK_5cymcwyp9b006xaf3ttmencvo` FOREIGN KEY (`hashed_file_id`) REFERENCES `hashed_file` (`hashed_file_id`),
ADD CONSTRAINT `FK_j8blm9v5ptq4fbwrv47tafhn9` FOREIGN KEY (`csar_id`) REFERENCES `csar` (`csar_id`);

--
-- Constraints for table `csar_file_open_tosca_server`
--
ALTER TABLE `csar_file_open_tosca_server`
ADD CONSTRAINT `FK_ra93u9rqql7senwvkwuoqxk3a` FOREIGN KEY (`open_tosca_server_id`) REFERENCES `open_tosca_server` (`open_tosca_server_id`),
ADD CONSTRAINT `FK_qsnycprnj27tvdo9arfexrixj` FOREIGN KEY (`csar_file_id`) REFERENCES `csar_file` (`csar_file_id`);

--
-- Constraints for table `csar_user`
--
ALTER TABLE `csar_user`
ADD CONSTRAINT `FK_1hqrgqbw58lrnm9hwosbni798` FOREIGN KEY (`csar_id`) REFERENCES `csar` (`csar_id`),
ADD CONSTRAINT `FK_50wllgq8dmn4h1sfw7yv7frxp` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `csar_winery_server`
--
ALTER TABLE `csar_winery_server`
ADD CONSTRAINT `FK_6kk5uj6ns6d1bord3ag2r4n4n` FOREIGN KEY (`csar_id`) REFERENCES `csar` (`csar_id`),
ADD CONSTRAINT `FK_r1c0fq58fijutc0puu6dafj7o` FOREIGN KEY (`winery_server_id`) REFERENCES `winery_server` (`winery_server_id`);

--
-- Constraints for table `open_tosca_server_user`
--
ALTER TABLE `open_tosca_server_user`
ADD CONSTRAINT `FK_sk33nsj3noa3pvclpa6qeub95` FOREIGN KEY (`open_tosca_server_id`) REFERENCES `open_tosca_server` (`open_tosca_server_id`),
ADD CONSTRAINT `FK_toj22it1iv7yaqenpym7nk9mw` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `plan`
--
ALTER TABLE `plan`
ADD CONSTRAINT `FK_i059sjv9uqp03kiumlv2e5hma` FOREIGN KEY (`hashed_file_id`) REFERENCES `hashed_file` (`hashed_file_id`);

--
-- Constraints for table `user_winery_server`
--
ALTER TABLE `user_winery_server`
ADD CONSTRAINT `FK_f7tq0jjuti89v4brxa4bbsj6p` FOREIGN KEY (`winery_server_id`) REFERENCES `winery_server` (`winery_server_id`),
ADD CONSTRAINT `FK_bf41r22dd4gt7gtctqaha19r7` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `winery_server`
--
ALTER TABLE `winery_server`
ADD CONSTRAINT `FK_25nqrxpf8c3oaw3ewmgipqp0k` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
