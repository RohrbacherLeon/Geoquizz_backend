SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

DROP TABLE IF EXISTS `users`;
CREATE TABLE users (
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
    `dist` int,
    primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `photos`;
CREATE TABLE `photos`(
    `id` varchar(255) not null,
    `description` varchar(255),
    `longitude` float,
    `latitude` float,
    `url` varchar(255),
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
    `serie_id` varchar(255) NOT NULL,
    `joueur` varchar(255),
    primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS partie_photo;
CREATE TABLE partie_photo (
  partie_id varchar(255) NOT NULL,
  photo_id varchar(255) NOT NULL,
  CONSTRAINT FK65453245 FOREIGN KEY (partie_id) REFERENCES parties (id),
  CONSTRAINT FK98243789 FOREIGN KEY (photo_id) REFERENCES photos (id),
  primary key (`partie_id`, `photo_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO users values (1, "bob", "bob", "user1");

INSERT INTO `series`(`id`, `ville`, `map_long`, `map_lat`, `dist`) VALUES ('1', 'Nancy', 48.1, 49.654, 3),
('2', 'PAM', 41.2, 48, 1);


INSERT INTO photos values (1, "gare", 48.6896, 6.173900000000003, "gare.jpg", 1,1);
INSERT INTO photos values (2, "basilique_saint_epvre", 48.696003, 6.179917, "basilique_saint_epvre.jpg", 1,1);
INSERT INTO photos values (3, "hopital_central", 48.6845866, 6.1916289, "hopital_central.png", 1,1);
INSERT INTO photos values (4, "kinepolis", 8.6918103, 6.1958243, "kinepolis.jpg", 1,1);
INSERT INTO photos values (5, "place_carnot", 48.69351770490701, 6.1774060661316526 , "place_carnot.jpg", 1,1);
INSERT INTO photos values (6, "place_des_vosges", 48.6849, 6.18730000000005, "place_des_vosges.jpg", 1,1);
INSERT INTO photos values (7, "place_dombasle", 48.6916, 6.177900000000022, "place_dombasle.jpg", 1,1);
INSERT INTO photos values (8, "place_dstan", 48.6935244, 6.1832861, "place_stan.jpg", 1,1);
INSERT INTO photos values (9, "rue_saint_jean", 48.6902, 6.180600000000027, "rue_saint_jean.jpg", 1,1);
INSERT INTO photos values (10, "iut_nc", 48.682988, 6.1609476, "iut_nc.png", 1,1);

INSERT INTO parties values (1, "token", 0, 1, 0, 1, "toto");

INSERT INTO partie_photo values (1, 1);
INSERT INTO partie_photo values (1, 2);
