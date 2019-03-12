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
    `desc` varchar(255),
    `longitude` float,
    `latitude` float,
    `url` varchar(255),
    `serie_id` varchar(255) NOT NULL,
    `user_id` varchar(255) NOT NULL,
    primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `parties`;
CREATE TABLE `parties`(
    `id` varchar(255) NOT NULL,
    `token` varchar(255),
    `nb_photos` int,
    `status` varchar(255),
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


INSERT INTO photos values (1, "photo1", 40.5, 40.4, "url", 1,1);
INSERT INTO photos values (2, "photo2", 40.5, 40.4, "url", 1,1);
INSERT INTO photos values (3, "photo3", 40.5, 40.4, "url", 1,1);
INSERT INTO photos values (4, "photo4", 40.5, 40.4, "url", 2,1);

INSERT INTO parties values (1, "token", 0, "Créée", 0, 1, "toto");
INSERT INTO parties values (2, "token2", 0, "Créée", 0, 2, "toto2");

INSERT INTO partie_photo values (1, 1);
INSERT INTO partie_photo values (1, 2);

INSERT INTO partie_photo values (2, 1);
INSERT INTO partie_photo values (2, 2);
INSERT INTO partie_photo values (2, 3);
INSERT INTO partie_photo values (2, 4);
