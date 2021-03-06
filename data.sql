SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
    `id` varchar(255) not null,
    `login` varchar(255),
    `password` varchar(255),
    `token` varchar(255),    
    primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `series`;
CREATE TABLE `series`(
    `id` varchar(255) not null,
    `ville` varchar(255),
    `map_long` float,
    `map_lat` float,
    primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `photos`;
CREATE TABLE `photos`(
    `id` varchar(255) not null,
    `description` varchar(255),
    `longitude` float,
    `latitude` float,
    `url` varchar(255),
    `token` varchar(255),
    `serie_id` varchar(255) NOT NULL,
    `user_id` varchar(255),
    primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `parties`;
CREATE TABLE `parties`(
    `id` varchar(255) NOT NULL,
    `token` varchar(255),
    `nb_photos` int,
    `status` int,
    `score` int,
    `joueur` varchar(255),
    `serie_id` varchar(255) NOT NULL,
    primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `partie_photo`;
CREATE TABLE `partie_photo` (
  `partie_id` varchar(255) NOT NULL,
  `photo_id` varchar(255) NOT NULL,
  primary key (`partie_id`, `photo_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `users` (`id`,`login`,`password`,`token`) VALUES
('6bf43c09-299b-4c74-ad85-97f8e05f27fb', "toto", "$2a$10$i4J579X9zuuKlF3kkro6IenLXPjxDbsItxb6dlpJwbROCNDb3q2cC", NULL);

INSERT INTO `series`(`id`, `ville`, `map_long`, `map_lat`) VALUES
('0', 'Awaiting_data', 0, 0),
('1', 'Nancy', 6.184417, 48.692054),
('2', 'Pont-à-Mousson', 6.054300000000012, 48.9044);

INSERT INTO `photos` (`id`,`description`,`longitude`, `latitude`, `url`, `token`, `serie_id`, `user_id`) values
('1', "Gare de Nancy", 6.173900000000003, 48.6896, "gare.jpg", 'token', '1', NULL),
('2', "Basilique Saint Epvre", 6.179917, 48.696003, "basilique_saint_epvre.jpg", 'token', '1', NULL),
('3', "Hopital Central", 6.1916289, 48.6845866, "hopital_central.jpg", 'token', '1', NULL),
('4', "Rue Saint Jean", 6.180600000000027, 48.6902, "rue_saint_jean.jpg", 'token', '1', NULL),
('5', "IUT Nancy-Charlemagne", 6.1609476, 48.682988, "iut_nc.png", 'token', '1', NULL),
('6', "Kinepolis", 6.1958243, 48.6918103, "kinepolis.jpg", 'token', '0', '6bf43c09-299b-4c74-ad85-97f8e05f27fa'),
('7', "Place Carnot", 6.1774060661316526, 48.69351770490701, "place_carnot.jpg", 'token', '0', '6bf43c09-299b-4c74-ad85-97f8e05f27fa'),
('8', "Place des Vosges", 6.18730000000005, 48.6849, "place_des_vosges.jpg", 'token', '0', '6bf43c09-299b-4c74-ad85-97f8e05f27fa'),
('9', "Place Dombasle", 6.177900000000022, 48.6916, "place_dombasle.jpg", 'token', '0', '6bf43c09-299b-4c74-ad85-97f8e05f27fa'),
('10', "Place Stanislas", 6.1832861, 48.6935244, "place_stan.jpg", 'token', '0', '6bf43c09-299b-4c74-ad85-97f8e05f27fa'),
('11', "Place Duroc", 6.054419999999936, 48.9024, "place_duroc.jpg", 'token', '2', NULL),
('12', "Abbaye des Prémontrés", 6.056063, 48.907777, 'premontres.jpg', 'token', '2', NULL);

#INSERT INTO `parties` (`id`, `token`, `nb_photos`, `status`, `score`, `joueur`, `serie_id`) values
#('1', "token", 0, 1, 0, "toto", '1');

#INSERT INTO `partie_photo` (`partie_id`, `photo_id`) values
#('1', '1'),
#('1', '2');