CREATE DATABASE `dbprime` /*!40100 DEFAULT CHARACTER SET latin1 */;

CREATE TABLE `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `val` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `domains` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `val` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `meanings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `val` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `questions` (
  `id` varchar(45) NOT NULL,
  `val` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `ragnar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tbl` int(11) DEFAULT NULL,
  `row` varchar(45) DEFAULT NULL,
  `val` varchar(45) DEFAULT NULL,
  `ref` int(11) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;

CREATE TABLE `term_categories` (
  `id` varchar(45) NOT NULL,
  `tid` varchar(45) DEFAULT NULL,
  `did` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tc1_idx` (`tid`),
  KEY `tc2_idx` (`did`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `term_domain` (
  `id` int(11) NOT NULL,
  `tid` int(11) DEFAULT NULL,
  `did` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `t_idx` (`tid`),
  KEY `d_idx` (`did`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `term_meaning` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `termId` int(11) DEFAULT NULL,
  `meaningId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `termId_idx` (`termId`),
  KEY `meaningId_idx` (`meaningId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `term_question` (
  `id` varchar(45) NOT NULL,
  `termId` varchar(45) DEFAULT NULL,
  `questionId` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `t_idx` (`termId`),
  KEY `q_idx` (`questionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `terms` (
  `id` varchar(45) NOT NULL,
  `val` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
