DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `password` varchar(128) NOT NULL,
  `salt` varchar(45) NOT NULL,
  `head_url` varchar(256) NOT NULL COMMENT 'It is a link to a user’s profile image.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL,
  `content` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `comment_count` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1

DROP TABLE IF EXISTS `login_ticket`;
CREATE TABLE `login_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `ticket` varchar(45) NOT NULL,
  `expired` datetime NOT NULL COMMENT 'The time when the ticket will be expired',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '1: means the ticket is invalid\n0: means the ticket is valid',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`ticket`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1
